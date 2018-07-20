package com.bc.pmpheep.wechat.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bc.pmpheep.wechat.po.AccessToken;
import com.bc.pmpheep.wechat.util.Constants;
import com.bc.pmpheep.wechat.util.QiYeUtil;
import com.bc.pmpheep.wechat.util.Result;

/**
 * 
 * <pre>
 * 功能描述：单纯实现OAuth2验证，不使用注解及拦截器
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
public class SimpleOAuth2Controller {
    /**
     * 
     * <pre>
     * 功能描述：拼接网页授权链接 此处步骤也可以用页面链接代替
     * 使用示范：
     *
     * @param request
     * @return
     * </pre>
     */
    @RequestMapping(value = { "/oauth2wx" })
    public String Oauth2API(HttpServletRequest request) {
        // 获取项目域名
        String reqUrl = request.getLocalAddr();
        // 拼接微信回调地址
        String backUrl = "http://" + reqUrl + "/oauth2me.do";
        String redirect_uri = "";
        try {
            redirect_uri = java.net.URLEncoder.encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oauth2Url =
        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.CORPID
        + "&redirect_uri=" + redirect_uri
        + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        return "redirect:" + oauth2Url;
    }

    /**
     * 
     * <pre>
     * 功能描述：授权回调请求处理
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param code 企业任意填写，用于生成签名。
     * @return
     * </pre>
     */
    @RequestMapping(value = { "/oauth2me" })
    public String oAuth2Url(HttpServletRequest request, @RequestParam String code) {
        AccessToken accessToken = QiYeUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
        HttpSession session = request.getSession();
        if (accessToken != null && accessToken.getToken() != null) {
            String Userid = getMemberGuidByCode(accessToken.getToken(), code, Constants.AGENTID);
            if (Userid != null) {
                session.setAttribute("UserId", Userid);
            }
        }
        // 这里简单处理,存储到session中
        return "wechat";
    }

    /**
     * 
     * <pre>
     * 功能描述：调用接口获取用户信息
     * 使用示范：
     *
     * @param token 企业任意填写，用于生成签名。
     * @param code  获取微信重定向到自己设置的URL中code参数
     * @param agentId 接收的应用id
     * @return
     * </pre>
     */
    public String getMemberGuidByCode(String token, String code, int agentId) {
        // System.out.println("code==" + code + "\ntoken=" + token + "\nagentid=" + agentId);
        Result<String> result = QiYeUtil.oAuth2GetUserByCode(token, code, agentId);
        // System.out.println("result=" + result);
        if (result.getErrcode() == "0") {
            if (result.getObj() != null) {
                // 此处可以通过微信授权用code还钱的Userid查询自己本地服务器中的数据
                return result.getObj();
            }
        }
        return "";
    }
}
