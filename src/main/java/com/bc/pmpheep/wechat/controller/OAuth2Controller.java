package com.bc.pmpheep.wechat.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 功能描述： OAuth2 处理控制器
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
public class OAuth2Controller {
    private static Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    /**
     * 
     * <pre>
     * 功能描述：构造参数并将请求重定向到微信API获取登录信息
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param resultUrl 跳转页面地址
     * @return
     * </pre>
     */
    @RequestMapping(value = { "/oauth2" })
    public String Oauth2API(HttpServletRequest request, @RequestParam String resultUrl) {
        // 此处可以添加获取持久化的数据，如企业号id等相关信息
        String CropId = Constants.CORPID;
        // resultUrl = resultUrl.replaceAll("192.168.100.109:8080", "192.168.100.109:8089");
        String redirectUrl = "";
        if (resultUrl != null) {
            // String reqUrl = request.getLocalAddr();
            String reqUrl = "20097r18u8.iask.in";
            // System.out.println("request.getServletPath()=" + request.getServletPath());
            // System.out.println("request.getRequestURL()=" + request.getRequestURL());
            // System.out.println("request.getLocalAddr()=" + request.getLocalAddr());
            // System.out.println("request.getRemoteHost()=" + request.getRemoteHost());
            // System.out.println("request.getRemoteHost()=" + request.getRemoteHost());
            // System.out.println("request.getServerName()=" + request.getServerName());
            String backUrl = "http://" + reqUrl + "/pmpheep" + "/oauth2url?oauth2url=" + resultUrl;
            // System.out.println("backUrl=" + backUrl);
            redirectUrl = oAuth2Url(CropId, backUrl);
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * 
     * <pre>
     * 功能描述：根据code获取Userid后跳转到需要带用户信息的最终页面
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param code 获取微信重定向到自己设置的URL中code参数
     * @param oauth2url 跳转到最终页面的地址
     * @return
     * </pre>
     */
    @RequestMapping(value = { "/oauth2url" })
    public String Oauth2MeUrl(HttpServletRequest request, @RequestParam String code,
    @RequestParam String oauth2url) {
        AccessToken accessToken = QiYeUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
        HttpSession session = request.getSession();
        if (accessToken != null && accessToken.getToken() != null) {
            String Userid = getMemberGuidByCode(accessToken.getToken(), code, Constants.AGENTID);
            if (Userid != null) {
                session.setAttribute("UserId", Userid);
            }
        }
        // 这里简单处理,存储到session中
        return "redirect:" + oauth2url;
    }

    /**
     * 
     * <pre>
     * 功能描述：构造带员工身份信息的URL
     * 使用示范：
     *
     * @param corpid 企业id
     * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
     * @return
     * </pre>
     */
    private String oAuth2Url(String corpid, String redirect_uri) {
        try {
            redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("URL转码出现异常：{}", e.getMessage());
        }
        String oauth2Url =
        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + corpid + "&redirect_uri="
        + redirect_uri + "&response_type=code&scope=snsapi_base&state=sunlight#wechat_redirect";
        // System.out.println("oauth2Url=" + oauth2Url);
        return oauth2Url;
    }

    /**
     * 
     * <pre>
     * 功能描述：调用接口获取用户信息
     * 使用示范：
     *
     * @param token 企业任意填写，用于生成签名。
     * @param code 获取微信重定向到自己设置的URL中code参数
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
