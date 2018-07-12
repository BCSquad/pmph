package com.bc.pmpheep.back.controller.shiro;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import small.danfer.sso.SingleSignOnException;
import small.danfer.sso.http.HttpSingleSignOnService;

import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserWechat;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.PmphUserWechatService;
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.MD5;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.wechat.interceptor.OAuthRequired;

/**
 * <pre>
 * 功能描述：PMPH-Shiro登陆
 * 使用示范：
 *
 *
 * @author (作者) nyz
 *
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-9-20
 * @modify (最后修改时间)
 * @修改人 ：nyz
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("all")
@RequestMapping(value = "/pmph")
@Controller
public class PmphLoginController {

    Logger logger = LoggerFactory.getLogger(PmphLoginController.class);
    @Autowired
    PmphUserService pmphUserService;
    @Autowired
    PmphPermissionService pmphPermissionService;
    @Autowired
    PmphRoleService pmphRoleService;
    @Autowired
    CmsCategoryService cmsCategoryService;
    @Autowired
    PmphUserWechatService pmphUserWechatService;

    @Value("#{spring['login2front.url']}")
    private String login2frontUrl;

    /**
     * @return the login2frontUrl
     */
    public String getLogin2frontUrl() {
        return login2frontUrl;
    }

    /**
     * @param login2frontUrl the login2frontUrl to set
     */
    public void setLogin2frontUrl(String login2frontUrl) {
        this.login2frontUrl = login2frontUrl;
    }

    /**
     * <pre>
     * 功能描述：登陆
     * 使用示范：
     *
     * @param user
     * @param model
     * @return
     * </pre>
     * <p>
     * //* @throws SingleSignOnException
     */
    @ResponseBody
   // @OAuthRequired
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseBean login(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "wechatUserId", required = false) String wechatUserId,
                              @RequestParam(value = "token", required = false) String token, HttpServletRequest request)
            throws CheckedServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        logger.info("username => " + username);
        logger.info("password => " + password);
        // HttpSingleSignOnService service = new HttpSingleSignOnService();
        // String url = service.getSingleSignOnURL();
        // try {
        // 判断是否从企业微信App登陆
        String userAgent = request.getHeader("user-agent").toLowerCase();
        Boolean isTrue =
                userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;

        logger.info("user-agent  " + userAgent);
        if (isTrue) {
            if (StringUtil.notEmpty(token)) {
                String newToken = username + password + wechatUserId + "<pmpheep>";
                if (!newToken.equals(new DesRun(token).depsw)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                            CheckedExceptionResult.NULL_PARAM, "用户名密码错误!");
                }

                if ("sso".equals(wechatUserId)) {
                    username = new DesRun(username).depsw;
                    password = new DesRun(password).depsw;
                }

            }

        }

        PmphUser pmphUser = null;
        if (isTrue&&StringUtil.notEmpty(wechatUserId) && !"sso".equals(wechatUserId)&& !"pmphuserlogin".equals(wechatUserId)) { //由微信--我的企业号 登录过来
            //if (isTrue) {//用户名 如果不为空，手动输入
                // 如果是微信登录过来 且wechatUserId 与 username 同时不为空，此时 维护 pmph_user_wechat 表
                PmphUserWechat pmphUserWechat = new PmphUserWechat();
                pmphUserWechat.setUsername(username);
                pmphUserWechat.setWechatId(wechatUserId);

                if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                            CheckedExceptionResult.NULL_PARAM, "请输入用户名和密码!");
                }
                pmphUser = pmphUserService.login(username, new DesRun("", password).enpsw);
                pmphUserWechat.setUserid(pmphUser.getId());
                pmphUserWechatService.add(pmphUserWechat); //微信 我的企业号 绑定userid
                pmphUserService.updateUserOpenid(wechatUserId, username);
                //pmphUser = pmphUserService.login(username, null);
           /* } else {//已经绑定
                pmphUser = pmphUserService.login(wechatUserId);
                username = "";
                password = "";
            }
*/
        }else if(StringUtil.notEmpty(wechatUserId)&& "pmphuserlogin".equals(wechatUserId)){ // 社内用户单点登录
            if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                        CheckedExceptionResult.NULL_PARAM, "请输入用户名和密码!");
            }
            pmphUser = pmphUserService.login(username,  password);

        }
        else if(StringUtil.notEmpty(wechatUserId)&& "sso".equals(wechatUserId)){   //sso 登录
            if (StringUtil.isEmpty(username) ) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                        CheckedExceptionResult.NULL_PARAM, "单点登录失败");
            }
            pmphUser = pmphUserService.login(username,  null);
        }else{  // 正常输入用户名和密码登陆
            if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                        CheckedExceptionResult.NULL_PARAM, "请输入用户名和密码!");
            }
            pmphUser = pmphUserService.login(username, new DesRun("", password).enpsw);
        }

        // PmphUser pmphUser = pmphUserService.login(userName, null);
        pmphUser.setLoginType(Const.LOGIN_TYPE_PMPH);
        if (!RouteUtil.DEFAULT_USER_AVATAR.equals(pmphUser.getAvatar())) {
            pmphUser.setAvatar(RouteUtil.userAvatar(pmphUser.getAvatar()));
        }
        // 根据用户Id查询对应角色(是否为管理员)
        List<PmphRole> pmphRoles = pmphRoleService.getPmphRoleByUserId(pmphUser.getId());
        if (pmphRoles.isEmpty()) {
            pmphRoleService.addUserRole(pmphUser.getId(), 2L);// 添加默认权限
            pmphRoles = pmphRoleService.getPmphRoleByUserId(pmphUser.getId());
        }
        List<Long> roleIds = new ArrayList<Long>();
        for (PmphRole pmphRole : pmphRoles) {
            roleIds.add(pmphRole.getId());
            if (ObjectUtil.notNull(pmphRole)) {
                if (Const.LOGIN_USER_IS_ADMIN.equals(pmphRole.getRoleName())
                        || Const.LOGIN_USER_IS_ADMINS.equals(pmphRole.getRoleName())
                        || Const.LOGIN_SYS_USER_IS_ADMIN.equals(pmphRole.getRoleName())) {
                    pmphUser.setIsAdmin(true);
                } else {
                    pmphUser.setIsAdmin(false);
                }
            }
            if (Const.TRUE == pmphUser.getIsAdmin()) {
                break;
            }
        }
        // 根据用户Id查询对应权限Id
        List<Long> pmphUserPermissionIds =
                pmphUserService.getPmphUserPermissionByUserId(pmphUser.getId());
        // 判断是否从企业微信App登陆
       /* if (isTrue && StringUtil.notEmpty(username) && StringUtil.notEmpty(wechatUserId)) {
            pmphUserService.updateUserOpenid(wechatUserId, username);
        }*/
        // String materialPermission =
        // pmphUserService.getMaterialPermissionByUserId(pmphUser.getId()); 根据用户返回书籍
        // 验证成功在Session中保存用户信息
        request.getSession().setAttribute(Const.SESSION_PMPH_USER, pmphUser);
        // 验证成功在Session中保存用户Token信息
        request.getSession().setAttribute(Const.SEESION_PMPH_USER_TOKEN,
                new DesRun(password, username).enpsw);
        // pmphUserSessionId
        resultMap.put(Const.USER_SEESION_ID, request.getSession().getId());
        resultMap.put(Const.SESSION_PMPH_USER, pmphUser);
        resultMap.put(Const.SEESION_PMPH_USER_TOKEN, new DesRun(password, username).enpsw);
        resultMap.put("pmphUserPermissionIds", pmphUserPermissionIds);
        // resultMap.put("materialPermission", materialPermission);
            return new ResponseBean(resultMap);
        // } catch (CheckedServiceException cException) {
        // return new ResponseBean(cException);
        // }
    }

    @ResponseBody
    @RequestMapping(value = "/SSOIndex", method = RequestMethod.GET)
    public ResponseBean SSOIndex(String oldSessionId,String token,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(oldSessionId);
        HttpSession session = request.getSession(false);
        if(session==null){
            session = request.getSession();
        }
        // 验证成功在Session中保存用户信息
        session.setAttribute(Const.SESSION_PMPH_USER, pmphUser);
        // 验证成功在Session中保存用户Token信息
        session.setAttribute(Const.SEESION_PMPH_USER_TOKEN,
                token);
        resultMap.put(Const.USER_SEESION_ID, session.getId());
        return new ResponseBean(resultMap);
    }

    /**
     * <pre>
     * 功能描述：SSO登陆
     * 使用示范：
     *
     * @param request
     * @return
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public ResponseBean ssoLogin(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = CookiesUtil.getSessionId(request);
        PmphUser pmUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSingleSignOnService service = new HttpSingleSignOnService();
         String url = service.getSingleSignOnURL();
        try {
            Principal principal = service.singleSignOn(request);
            String userName = principal.getName();
            PmphUser pmphUser = pmphUserService.login(userName, null);
            if (ObjectUtil.isNull(pmphUser)) {// 为空就新建一个用户
                pmphUser =
                        pmphUserService.add(new PmphUser(userName, "888888",false,"",0L,"","", "DEFAULT","",999,false));
                pmphRoleService.addUserRole(pmphUser.getId(), 2L);// 添加默认权限
            }
            pmphUser.setLoginType(Const.LOGIN_TYPE_PMPH);
            if (!RouteUtil.DEFAULT_USER_AVATAR.equals(pmphUser.getAvatar())) {
                pmphUser.setAvatar(RouteUtil.userAvatar(pmphUser.getAvatar()));
            }
            // 根据用户Id查询对应角色(是否为管理员)
            List<PmphRole> pmphRoles = pmphRoleService.getPmphRoleByUserId(pmphUser.getId());
            List<Long> roleIds = new ArrayList<Long>(pmphRoles.size());
            for (PmphRole pmphRole : pmphRoles) {
                roleIds.add(pmphRole.getId());
                if (ObjectUtil.notNull(pmphRole)) {
                    if (Const.LOGIN_USER_IS_ADMIN.equals(pmphRole.getRoleName())
                            || Const.LOGIN_USER_IS_ADMINS.equals(pmphRole.getRoleName())
                            || Const.LOGIN_SYS_USER_IS_ADMIN.equals(pmphRole.getRoleName())) {
                        pmphUser.setIsAdmin(true);
                    } else {
                        pmphUser.setIsAdmin(false);
                    }
                }
                if (Const.TRUE == pmphUser.getIsAdmin()) {
                    break;
                }
            }
            // 根据用户Id查询对应权限Id
            List<Long> pmphUserPermissionIds =
                    pmphUserService.getPmphUserPermissionByUserId(pmphUser.getId());
            // 验证成功在Session中保存用户信息
            request.getSession().setAttribute(Const.SESSION_PMPH_USER, pmphUser);
            // 验证成功在Session中保存用户Token信息
            request.getSession().setAttribute(Const.SEESION_PMPH_USER_TOKEN,
                    new DesRun(userName, userName).enpsw);
            // pmphUserSessionId
            resultMap.put(Const.USER_SEESION_ID, request.getSession().getId());
            resultMap.put(Const.SESSION_PMPH_USER, pmphUser);
            resultMap.put(Const.SEESION_PMPH_USER_TOKEN, new DesRun(userName, userName).enpsw);
            resultMap.put("pmphUserPermissionIds", pmphUserPermissionIds);
            return new ResponseBean(resultMap);
        } catch (Exception e) {
            return new ResponseBean(e);
        }
    }

    /**
     * <pre>
     * 功能描述：退出
     * 使用示范：
     *
     * @param model
     * @return
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseBean logout(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("loginType") Short loginType) {
        // HttpSingleSignOnService service = new HttpSingleSignOnService();
        String sessionId = CookiesUtil.getSessionId(request);
        HttpSession session = SessionContext.getSession(sessionId);
        if (ObjectUtil.notNull(session)) {
            if (Const.LOGIN_TYPE_PMPH == loginType) {
                session.invalidate();
            }
            // try {
            // response.sendRedirect(service.getSingleSignOutURL());
            // } catch (IOException e) {
            // return new ResponseBean(e);
            // }
        }
        return new ResponseBean();
    }

    /**
     * <pre>
     * 功能描述：无权限
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/unAuthorization", method = RequestMethod.GET)
    public ResponseBean unAuthorization() {
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        responseBean.setCode(ResponseBean.NO_PERMISSION);
        responseBean.setMsg("没有权限！");
        return responseBean;
    }

    /**
     * <pre>
     * 功能描述：后台一键登陆到前台
     * 使用示范：
     *
     * @param userName 用户名
     * @param userType 用户类型
     * @return MD5加密字符串
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/keyToLand", method = RequestMethod.GET)
    public ResponseBean keyToLand(@RequestParam("userName") String userName,
                                  @RequestParam("userType") Integer userType) {
        if (StringUtil.isEmpty(userName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                    CheckedExceptionResult.NULL_PARAM, "用户名为空");
        }
        if (ObjectUtil.isNull(userType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                    CheckedExceptionResult.NULL_PARAM, "用户类型为空");
        }
        String key = "1005387596c57c2278f4f61058c78d1b";
        String today = DateUtil.getDays();
        String md5 = MD5.md5(userName + key + today);
        String url =
                getLogin2frontUrl() + "?username=" + userName + "&md5=" + md5 + "&usertype=" + userType;
        return new ResponseBean(url);
    }
}
