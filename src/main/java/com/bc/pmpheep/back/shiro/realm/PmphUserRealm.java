package com.bc.pmpheep.back.shiro.realm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;

/**
 * 
 * @author Administrator
 * 
 */
public class PmphUserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(PmphUserRealm.class);
    @Autowired
    private PmphUserService     userService;

    public PmphUserRealm() {
        super();
    }

    /**
     * 授权
     * 
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("--- MyRealm doGetAuthorizationInfo ---");

        // 获得经过认证的主体信息
        PmphUser user = (PmphUser) principalCollection.getPrimaryPrincipal();
        Long userId = user.getId();
        // UserService userService = (UserService)InitServlet.getBean("userService");
        List<PmphPermission> resourceList = null;
        List<String> roleSnList = null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        try {
            resourceList = userService.getListAllResource(userId);
            roleSnList = userService.getListRoleSnByUser(userId);
            List<String> resStrList = new ArrayList<>();
            for (PmphPermission resource : resourceList) {
                resStrList.add(resource.getUrl());
            }
            info.setRoles(new HashSet<>(roleSnList));
            info.setStringPermissions(new HashSet<>(resStrList));
            // 以上完成了动态地对用户授权
            logger.info("role => " + roleSnList);
            logger.info("permission => " + resStrList);
        } catch (Exception e) {
            logger.info("message => " + e);
        }
        return info;
    }

    /**
     * 认证
     * 
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
    throws AuthenticationException {
        logger.info("--- MyRealm doGetAuthenticationInfo ---[SecondRealm] doGetAuthenticationInfo "
                    + authenticationToken);
        String username = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials());
        try {
            PmphUser user = userService.login(username, password);
            if (user != null) {
                // 第 1 个参数可以传一个实体对象，然后在认证的环节可以取出
                // 第 2 个参数应该传递在数据库中“正确”的数据，然后和 token 中的数据进行匹配
                SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(user, user.getPassword(), getName());
                // 设置盐值
                info.setCredentialsSalt(ByteSource.Util.bytes(username.getBytes()));
                return info;
            }
        } catch (Exception e) {
            logger.debug("message => " + e);
        }
        return null;
    }

    /**
     * 
     * <pre>
     * 功能描述：更新身份验证信息缓存
     * 使用示范：
     *
     * @param principals
     * </pre>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        Cache c = getAuthenticationCache();
        logger.info("清除【认证】缓存之前");
        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }
        super.clearCachedAuthenticationInfo(principals);
        logger.info("调用父类清除【认证】缓存之后");
        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }

        // 添加下面的代码清空【认证】的缓存
        PmphUser user = (PmphUser) principals.getPrimaryPrincipal();
        SimplePrincipalCollection spc =
        new SimplePrincipalCollection(user.getUsername(), getName());
        super.clearCachedAuthenticationInfo(spc);
        logger.info("添加了代码清除【认证】缓存之后");
        int cacheSize = c.keys().size();
        logger.info("【认证】缓存的大小:" + c.keys().size());
        if (cacheSize == 0) {
            logger.info("说明【认证】缓存被清空了。");
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：更新用户授权信息缓存
     * 使用示范：
     *
     * @param principals
     * </pre>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        logger.info("清除【授权】缓存之前");
        Cache c = getAuthorizationCache();
        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }
        super.clearCachedAuthorizationInfo(principals);
        logger.info("清除【授权】缓存之后");
        int cacheSize = c.keys().size();
        logger.info("【授权】缓存的大小:" + cacheSize);

        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }
        if (cacheSize == 0) {
            logger.info("说明【授权】缓存被清空了。");
        }

    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 
     * <pre>
     * 功能描述：
     * 使用示范：比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     *
     * @param key
     * @param value
     * </pre>
     */
    @SuppressWarnings("unused")
    private void setSession(Object key, Object value) {
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            Session session = subject.getSession();
            logger.debug("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
