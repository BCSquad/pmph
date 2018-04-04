package com.bc.pmpheep.wechat.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import small.danfer.sso.http.HttpSingleSignOnService;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserWechat;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.PmphUserWechatService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
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
    @OAuthRequired
    public Object load(HttpServletRequest request, Model model) {
        String userAgent = request.getHeader("user-agent").toLowerCase();// 判断是否从企业微信App登陆
        Boolean isTrue =
        userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;
        if (isTrue) {
            HttpSession session = request.getSession();
            String wechatUserId = (String) session.getAttribute("UserId");// 企业微信账号
            if (StringUtil.isEmpty(wechatUserId)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.NULL_PARAM, "网络异常，请重新再试!");
            }
            model.addAttribute("Userid", wechatUserId);
            PmphUserWechat pmphUserWechat =
            pmphUserWechatService.getPmphUserWechatByWechatId(wechatUserId);
            if (ObjectUtil.isNull(pmphUserWechat)) {
                model.addAttribute("isLogin", "0");
            } else {
                PmphUser pu = pmphUserService.getPmphUserByUsername(pmphUserWechat.getUsername());
                if (ObjectUtil.notNull(pu)) {
                    String username = new DesRun(null, pmphUserWechat.getUsername()).enpsw;
                    String password = pu.getPassword();
                    model.addAttribute(Const.PMPH_WECHAT_USER_TOKEN,
                                       new DesRun(password, username + password + wechatUserId
                                                            + "<pmpheep>").enpsw);
                    model.addAttribute("username", username);
                    model.addAttribute("password", password);
                    model.addAttribute("isLogin", "1");
                }
            }
        } else {// SSO 登陆
            model.addAttribute("isLogin", "2");
            HttpSingleSignOnService service = new HttpSingleSignOnService();
            try {
                Principal principal = service.singleSignOn(request);
                String userName = principal.getName();
                assert userName != null;
                PmphUser pmphUser = pmphUserService.getPmphUserByUsername(userName);
                if (ObjectUtil.isNull(pmphUser)) {// 为空就新建一个用户
                    pmphUser =
                    pmphUserService.add(new PmphUser(userName, new DesRun(null, "888888").enpsw,
                                                     userName, "DEFAULT"));
                    pmphRoleService.addUserRole(pmphUser.getId(), 2L);// 添加默认权限
                }
                String username = new DesRun(null, pmphUser.getUsername()).enpsw;
                String password = pmphUser.getPassword();
                String wechatUserId = "sso";
                model.addAttribute("username", username);
                model.addAttribute("password", password);
                model.addAttribute("UserId", wechatUserId);
                model.addAttribute(Const.PMPH_WECHAT_USER_TOKEN, new DesRun(password,
                                                                            username + password
                                                                            + wechatUserId
                                                                            + "<pmpheep>").enpsw);
            } catch (Exception e) {
                logger.error("SSO登陆失败，异常信息'{}'", e.getMessage());
            }
        }
        return "wechat";
    }
}
