 package com.bc.pmpheep.wechat.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.utils.SsoHelper;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import small.danfer.sso.http.HttpSingleSignOnService;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserWechat;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.PmphUserWechatService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.wechat.interceptor.OAuthRequired;

/**
 * 
 * <pre>
 * 功能描述：需要验证OAuth2控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-2-27
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@RequestMapping(value = "/sso")
@Controller
public class WeChatLoginController {
    Logger                logger = LoggerFactory.getLogger(WeChatLoginController.class);
    @Autowired
    PmphUserService       pmphUserService;
    @Autowired
    PmphUserWechatService pmphUserWechatService;
    @Autowired
    PmphRoleService       pmphRoleService;
    @Resource
    SsoHelper ssoHelper;

    /**
     * 
     * <pre>
     * 功能描述：加载个人信息，此处添加了@OAuthRequired注解
     * 使用示范：
     *
     * @param request
     * @param model
     * @return
     * </pre>
     */
    @RequestMapping(value = { "/login" })
    //@OAuthRequired
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response/*, Model model*/) throws Exception {
        String userAgent = request.getHeader("user-agent").toLowerCase();// 判断是否从企业微信App登陆
        Boolean isTrue =
        userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;
        PmphUser pmphUser = null;
        String username = null;
        String password =null;
        ModelAndView model = new ModelAndView();
        logger.info("login "+isTrue);
        if (isTrue) {
            HttpSession session = request.getSession();
            String wechatUserId = (String) session.getAttribute("UserId"); // userId 在session 中可以取到 微信--企业微信号 这个是pmph_user_wechat 表中的wechat_id
            if(StringUtil.isEmpty(wechatUserId)){ //app 访问登录
                wechatUserId = request.getParameter("UserId"); // userId 在request中可以取到 企业微信 此usserId 代表 社内用户字段openid
            }
            String appType = request.getParameter("appType"); //为空 微信 -- 企业微信号 不为空 企业微信
            // 微信--微信企业号直接访问app登录
            if(StringUtil.isEmpty(appType)){  /*微信 -- 企业微信号*/
                if (StringUtil.isEmpty(wechatUserId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                            CheckedExceptionResult.NULL_PARAM, "网络异常，请重新再试!");
                }
                model.addObject("UserId", wechatUserId);
                PmphUserWechat pmphUserWechat =
                        pmphUserWechatService.getPmphUserWechatByWechatId(wechatUserId);

                if (ObjectUtil.isNull(pmphUserWechat)) {
                    logger.info("wechatLoginController   "+ObjectUtil.isNull(pmphUserWechat));
                    model.addObject("isLogin", "0"); //查找不到对应的社内用户 跳转登录页面
                    model.addObject("sessionPmphUser", "1");
                    model.addObject("pmphUserPermissionIds", "");
                    model.addObject("isIndexOrCommission",((!StringUtil.isEmpty((String)session.getAttribute("UserId"))&&!StringUtil.isEmpty(request.getParameter("commission")))?"commission":"") );//commission 从微信 -- 企业微信号 代办
                    //if ((!StringUtil.isEmpty((String) session.getAttribute("UserId")) && !StringUtil.isEmpty(request.getParameter("commission")))) {
                    /*logger.info("http://medu.ipmph.com/wx/#/login?wechatUserId=" + wechatUserId+"&isIndexOrCommission="+((!StringUtil.isEmpty((String)session.getAttribute("UserId"))&&!StringUtil.isEmpty(request.getParameter("commission")))?"commission":""));
                    response.sendRedirect("http://medu.ipmph.com/wx/#/login?wechatUserId=" + wechatUserId+"&isIndexOrCommission=");*/
                   // }
                    model.setViewName("wechat");
                    return model;
                } else { //查找到对应的社内用户，跳转到首页
                    pmphUser = pmphUserService.getPmphUserByUsername(pmphUserWechat.getUsername(),pmphUserWechat.getUserid());
                    if (ObjectUtil.notNull(pmphUser)) {
                        username = new DesRun(null, pmphUserWechat.getUsername()).enpsw;
                        password = pmphUser.getPassword();
                        model.addObject(Const.PMPH_WECHAT_USER_TOKEN,
                                new DesRun(password, username + password + wechatUserId
                                        + "<pmpheep>").enpsw);
                        model.addObject("username", username);
                        model.addObject("password", password);
                        if(StringUtil.isEmpty(request.getParameter("commission"))){
                            model.addObject("isLogin", "1"); //跳转到首页
                        }else{
                            model.addObject("isLogin", "5"); //跳转到代办页面
                        }

                    }
                }
            }else{ /*企业微信 */
                if (StringUtil.isEmpty(wechatUserId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                            CheckedExceptionResult.NULL_PARAM, "网络异常，请重新再试!");
                }
                model.addObject("UserId", wechatUserId);
                PmphUserWechat pmphUserWechat =
                        pmphUserWechatService.getPmphUserWechatByWechatId(wechatUserId);

                if (ObjectUtil.isNull(pmphUserWechat)) {

                    pmphUser = pmphUserService.getPmphUserByOpenid(wechatUserId);

                    if (ObjectUtil.notNull(pmphUser)) {
                        username = new DesRun(null, pmphUser.getUsername()).enpsw;
                        password = pmphUser.getPassword();
                        model.addObject(Const.PMPH_WECHAT_USER_TOKEN,
                                new DesRun(password, username + password + wechatUserId
                                        + "<pmpheep>").enpsw);
                        model.addObject("username", username);
                        model.addObject("password", password);
                        model.addObject("isLogin", "3");
                    }
                } else {
                    pmphUser = pmphUserService.getPmphUserByUsername(pmphUserWechat.getUsername(),pmphUserWechat.getUserid());
                    if (ObjectUtil.notNull(pmphUser)) {
                        username = new DesRun(null, pmphUserWechat.getUsername()).enpsw;
                        password = pmphUser.getPassword();
                        model.addObject(Const.PMPH_WECHAT_USER_TOKEN,
                                new DesRun(password, username + password + wechatUserId
                                        + "<pmpheep>").enpsw);
                        model.addObject("username", username);
                        model.addObject("password", password);
                        model.addObject("isLogin", "4");
                    }
                }
                /*跳转到某个具体的页面*/
                if("1".equals(appType)){ //教材审核
                    String materialId = request.getParameter("materialId");
                    String declarationId = request.getParameter("declarationId");
                    model.addObject("materialId",materialId);
                    model.addObject("declarationId",declarationId);
                }else if("2".equals(appType)){ //选题申报

                }else if("3".equals(appType)){ //图书纠错
                    String bookName = request.getParameter("bookName");
                    String type = request.getParameter("type");
                    String id = request.getParameter("id");
                    model.addObject("bookName",bookName);
                    model.addObject("type",type);
                    model.addObject("id",id);
                }
                model.addObject("appType",appType);
            }

        } else {// SSO 登陆
            model.addObject("isLogin", "2");
            HttpSingleSignOnService service = new HttpSingleSignOnService();
            try {
                Principal principal = service.singleSignOn(request);
                String userName =principal.getName();
             //   String userName = "liub";
                assert userName != null;
                 pmphUser = pmphUserService.getPmphUserByUsername(userName,null);
                // Map map = ssoHelper.getUserInfo(userName,"123456");
                Map map = new HashMap(); // 预留
                if (ObjectUtil.isNull(pmphUser)) {// 为空就新建一个用户
                    pmphUser =
                            pmphUserService.add(new PmphUser(userName, "888888",false, MapUtils.getString(map,"RealName",""),0L, MapUtils.getString(map,"Mobile",""),MapUtils.getString(map,"Emial",""), "DEFAULT","",999,false));
                    pmphRoleService.addUserRole(pmphUser.getId(), 2L);// 添加默认权限
                }
                 username = new DesRun(null, pmphUser.getUsername()).enpsw;
                 password = pmphUser.getPassword();
                String wechatUserId = "sso";
                model.addObject("username", username);
                model.addObject("password", password);
                model.addObject("UserId", wechatUserId);
                model.addObject(Const.PMPH_WECHAT_USER_TOKEN, new DesRun(password,
                                                                            username + password
                                                                            + wechatUserId
                                                                            + "<pmpheep>").enpsw);



            } catch (Exception e) {
                logger.error("SSO登陆失败，异常信息'{}'", e.getMessage());
            }
        }

         /*--------------------------- 登录权限控制 session cookie ------------为了直接跳转到某个vue页面---------------------*/
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
        request.getSession().setAttribute(Const.SESSION_PMPH_USER, pmphUser);
        // 验证成功在Session中保存用户Token信息
        request.getSession().setAttribute(Const.SEESION_PMPH_USER_TOKEN,
                new DesRun(password, username).enpsw);
        model.addObject(Const.USER_SEESION_ID, request.getSession().getId());
        model.addObject(Const.SESSION_PMPH_USER, JSON.toJSON(pmphUser));
        model.addObject(Const.SEESION_PMPH_USER_TOKEN, new DesRun(password, username).enpsw);
        model.addObject("pmphUserPermissionIds", pmphUserPermissionIds);
                /*---------------------------------------------------------------*/
        model.setViewName("wechat");
        return model;
    }
}
