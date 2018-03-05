package com.bc.pmpheep.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
public class WeChatLoginController {
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
    @RequestMapping(value = { "/wechatUserInfo" })
    @OAuthRequired
    public Object load(HttpServletRequest request, Model model) {
        // System.out.println("Load a User!");
        HttpSession session = request.getSession();
        model.addAttribute("Userid", session.getAttribute("UserId"));// 判断是否从企业微信App登陆
        String userAgent = request.getHeader("user-agent").toLowerCase();
        Boolean isTrue =
        userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;
        if (isTrue) {
            String wechatUserId = (String) session.getAttribute("UserId");
            if (StringUtil.isEmpty(wechatUserId)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.NULL_PARAM, "网络异常，请重新再试!");
            }
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
        }
        return "wechat";
    }
}
