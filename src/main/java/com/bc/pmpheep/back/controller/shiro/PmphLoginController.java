package com.bc.pmpheep.back.controller.shiro;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：PMPH-Shiro登陆
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-9-20
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
@SuppressWarnings("all")
@RequestMapping(value = "/pmph")
@Controller
public class PmphLoginController {
    Logger                logger = LoggerFactory.getLogger(PmphLoginController.class);
    @Autowired
    PmphUserService       pmphUserService;
    @Autowired
    PmphPermissionService pmphPermissionService;
    @Autowired
    PmphRoleService       pmphRoleService;
    @Autowired
    CmsCategoryService    cmsCategoryService;

    /**
     * 
     * <pre>
	 * 功能描述：登陆
	 * 使用示范：
	 *
	 * &#64;param user
	 * &#64;param model
	 * &#64;return
	 * </pre>
     * 
     * //* @throws SingleSignOnException
     * 
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseBean login(@RequestParam("username") String username,
    @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        logger.info("username => " + username);
        logger.info("password => " + password);
        // HttpSingleSignOnService service = new HttpSingleSignOnService();
        // String url = service.getSingleSignOnURL();
        try {
            PmphUser pmphUser = pmphUserService.login(username, new DesRun("", password).enpsw);
            // PmphUser pmphUser = pmphUserService.login(userName, null);
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
        } catch (CheckedServiceException cException) {
            return new ResponseBean(cException);
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：SSO登陆
     * 使用示范：
     *
     * @param request
     * @return
     * </pre>
     */
    // @ResponseBody
    // @RequestMapping(value = "/sso", method = RequestMethod.GET)
    // public ResponseBean ssoLogin(HttpServletRequest request, HttpServletResponse response) {
    // String sessionId = CookiesUtil.getSessionId(request);
    // PmphUser pmUser = SessionUtil.getPmphUserBySessionId(sessionId);
    // if (ObjectUtil.isNull(pmUser)) {
    // throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
    // CheckedExceptionResult.NULL_PARAM, "用户为空");
    // }
    // Map<String, Object> resultMap = new HashMap<String, Object>();
    // HttpSingleSignOnService service = new HttpSingleSignOnService();
    // // String url = service.getSingleSignOnURL();
    // try {
    // Principal principal = service.singleSignOn(request);
    // String userName = principal.getName();
    // PmphUser pmphUser = pmphUserService.login(userName, null);
    // if (ObjectUtil.isNull(pmphUser)) {// 为空就新建一个用户
    // pmphUser =
    // pmphUserService.add(new PmphUser(userName, "888888", userName, "DEFAULT"));
    // pmphRoleService.addUserRole(pmphUser.getId(), 2L);// 添加默认权限
    // }
    // pmphUser.setLoginType(Const.LOGIN_TYPE_PMPH);
    // if (!RouteUtil.DEFAULT_USER_AVATAR.equals(pmphUser.getAvatar())) {
    // pmphUser.setAvatar(RouteUtil.userAvatar(pmphUser.getAvatar()));
    // }
    // // 根据用户Id查询对应角色(是否为管理员)
    // List<PmphRole> pmphRoles = pmphRoleService.getPmphRoleByUserId(pmphUser.getId());
    // List<Long> roleIds = new ArrayList<Long>(pmphRoles.size());
    // for (PmphRole pmphRole : pmphRoles) {
    // roleIds.add(pmphRole.getId());
    // if (ObjectUtil.notNull(pmphRole)) {
    // if (Const.LOGIN_USER_IS_ADMIN.equals(pmphRole.getRoleName())
    // || Const.LOGIN_USER_IS_ADMINS.equals(pmphRole.getRoleName())
    // || Const.LOGIN_SYS_USER_IS_ADMIN.equals(pmphRole.getRoleName())) {
    // pmphUser.setIsAdmin(true);
    // } else {
    // pmphUser.setIsAdmin(false);
    // }
    // }
    // if (Const.TRUE == pmphUser.getIsAdmin()) {
    // break;
    // }
    // }
    // // 根据用户Id查询对应权限Id
    // List<Long> pmphUserPermissionIds =
    // pmphUserService.getPmphUserPermissionByUserId(pmphUser.getId());
    // // 验证成功在Session中保存用户信息
    // request.getSession().setAttribute(Const.SESSION_PMPH_USER, pmphUser);
    // // 验证成功在Session中保存用户Token信息
    // request.getSession().setAttribute(Const.SEESION_PMPH_USER_TOKEN,
    // new DesRun(userName, userName).enpsw);
    // // pmphUserSessionId
    // resultMap.put(Const.USER_SEESION_ID, request.getSession().getId());
    // resultMap.put(Const.SESSION_PMPH_USER, pmphUser);
    // resultMap.put(Const.SEESION_PMPH_USER_TOKEN, new DesRun(userName, userName).enpsw);
    // resultMap.put("pmphUserPermissionIds", pmphUserPermissionIds);
    // return new ResponseBean(resultMap);
    // } catch (SingleSignOnException e) {
    // return new ResponseBean(e);
    // }
    // }

    /**
     * 
     * <pre>
     * 功能描述：退出
     * 使用示范：
     *
     * &#64;param model
     * &#64;return
     * </pre>
     * 
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
     * 
     * <pre>
	 * 功能描述：无权限
	 * 使用示范：
	 *
	 * &#64;return
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
}
