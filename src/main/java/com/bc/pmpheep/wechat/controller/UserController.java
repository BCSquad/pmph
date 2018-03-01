package com.bc.pmpheep.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bc.pmpheep.wechat.interceptor.OAuthRequired;

/**
 * 需要验证OAuth2控制器
 * 
 */
// @Controller
public class UserController {
    /**
     * 加载个人信息，此处添加了@OAuthRequired注解
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = { "pmph/userInfo" })
    @OAuthRequired
    public String load(HttpServletRequest request, Model model) {
        // System.out.println("Load a User!");
        // HttpSession session = request.getSession();
        // model.addAttribute("Userid", session.getAttribute("Userid"));
        // StringBuffer requestURL = request.getRequestURL();
        // System.out.println(requestURL.toString() + "==");
        // String queryString = request.getQueryString();
        // String url2 = "http://" + request.getServerName() // 服务器地址
        // + request.getContextPath() // 项目名称
        // + request.getServletPath(); // 请求页面或其他地址
        // if (queryString != null) {
        // url2 += "?" + queryString;
        // }

        // String accessToken =
        // WechatAccessToken.getAccessToken(Constants.CORPID, Constants.SECRET, 1).getToken();
        // 获得微信jssdk签名等
        // String jsapi_ticket = WechatAccessToken.getWXjsTicket(accessToken).getJsTicket();
        // Map<String, String> sign = WeixinJSSDKSignUtil.sign(jsapi_ticket, url2);
        //
        // model.addAttribute("appId", Constants.CORPID);
        // model.addAttribute("nonceStr", sign.get("nonceStr"));
        // model.addAttribute("timestamp", sign.get("timestamp"));
        // model.addAttribute("signature", sign.get("signature"));
        return "user";
    }
}
