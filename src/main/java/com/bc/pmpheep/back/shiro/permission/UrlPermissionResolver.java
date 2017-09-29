package com.bc.pmpheep.back.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <pre>
 * 功能描述：
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-9-29
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class UrlPermissionResolver implements PermissionResolver {
    Logger logger = LoggerFactory.getLogger(UrlPermissionResolver.class);

    /**
     * 经过调试发现 subject.isPermitted(url) 中传入的字符串
     * 
     * 和自定义 Realm 中传入的权限字符串集合都要经过这个 resolver
     * 
     * @param s
     * @return
     */
    @Override
    public Permission resolvePermission(String s) {
        logger.info("s => " + s);

        if (s.startsWith("/")) {
            return new UrlPermission(s);
        }
        return new WildcardPermission(s);
    }
}
