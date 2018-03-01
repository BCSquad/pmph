package com.bc.pmpheep.back.controller.wechat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bc.pmpheep.back.po.PmphUserWechat;
import com.bc.pmpheep.back.service.PmphUserWechatService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.wechat.interceptor.OAuthRequired;
import com.bc.pmpheep.wechat.py.WeixinJSSDKSignUtil;
import com.bc.pmpheep.wechat.util.Constants;
import com.bc.pmpheep.wechat.util.WechatAccessToken;

//@Controller
public class PmphUserWechatController {
    @Autowired
    PmphUserWechatService pmphUserWechatService;

    /**
     * 
     * <pre>
     * 功能描述：加载个人信息，此处添加了@OAuthRequired注解
     * 使用示范：
     *
     * @param request
     * @return
     * </pre>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = { "/userInfo" })
    @OAuthRequired
    public ResponseBean load(HttpServletRequest request) {
        System.out.println("Load a User!");
        String userAgent = request.getHeader("user-agent").toLowerCase();
        System.out.println(userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String wechatUserId = (String) session.getAttribute("UserId");
        resultMap.put("UserId", session.getAttribute("UserId"));
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL.toString() + "==");
        String queryString = request.getQueryString();
        String url2 = "http://" + request.getServerName() // 服务器地址
                      + request.getContextPath() // 项目名称
                      + request.getServletPath(); // 请求页面或其他地址
        if (ObjectUtil.notNull(queryString)) {
            url2 += "?" + queryString;
        }
        String accessToken =
        WechatAccessToken.getAccessToken(Constants.CORPID, Constants.SECRET, 1).getToken();
        // 获得微信jssdk签名等
        String jsapi_ticket = WechatAccessToken.getWXjsTicket(accessToken).getJsTicket();
        Map<String, String> sign = WeixinJSSDKSignUtil.sign(jsapi_ticket, url2);
        //
        if (StringUtil.notEmpty(wechatUserId)) {
            PmphUserWechat pmphUserWechat =
            pmphUserWechatService.getPmphUserWechatByWechatId(wechatUserId);
            if (ObjectUtil.isNull(pmphUserWechat)) {

            } else {

            }
        }

        resultMap.put("appId", Constants.CORPID);
        resultMap.put("nonceStr", sign.get("nonceStr"));
        resultMap.put("timestamp", sign.get("timestamp"));
        resultMap.put("signature", sign.get("signature"));
        return new ResponseBean(resultMap);
    }
}
