package com.bc.pmpheep.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.wx.util.SendWXMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("testReri")
    public String testReri(String val){
        System.out.println("11111111");
        return "redirect:"+val;
    }
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
        logger.info("resultUrl:   "+resultUrl);
        // 此处可以添加获取持久化的数据，如企业号id等相关信息
        String CropId = Constants.CORPID;
        String redirectUrl = "";
        if (resultUrl != null) {
            // String reqUrl = request.getLocalAddr();
           // String reqUrl = "medu.ipmph.com/pmpheepwx";// 备案域名
            Properties pp = new Properties();
            String reqUrl = "";
            InputStream fis  = OAuth2Controller.class.getClassLoader().getResourceAsStream("pmphapi-config.properties");
            try {
                pp.load(fis);
                reqUrl =pp.getProperty("rootAdrr").toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reqUrl = StringUtil.isEmpty("rootAdrr")?"medu.ipmph.com/pmpheep":reqUrl;// pmphwx 20097r18u8.iask.in
            //String reqUrl = "120.76.221.250:11000";// 备案域名
            // System.out.println("request.getServletPath()=" + request.getServletPath());
            // System.out.println("request.getRequestURL()=" + request.getRequestURL());
            // System.out.println("request.getLocalAddr()=" + request.getLocalAddr());
            // System.out.println("request.getRemoteHost()=" + request.getRemoteHost());
            // System.out.println("request.getRemoteHost()=" + request.getRemoteHost());
            // System.out.println("request.getServerName()=" + request.getServerName());
            //String backUrl = "http://" + reqUrl + "/pmpheep" + "/oauth2url?oauth2url=" + resultUrl;
            String backUrl = "http://" + reqUrl  + "/oauth2url?oauth2url=" + resultUrl;
            // System.out.println("backUrl=" + backUrl);
            redirectUrl = oAuth2Url(CropId, backUrl);
        }
        logger.info("redirectUrl:   "+redirectUrl);
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
    public void Oauth2MeUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam String code,
                               @RequestParam String oauth2url) {
        logger.info("oauth2url___:   "+oauth2url);
        AccessToken accessToken = QiYeUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
        HttpSession session = request.getSession();
        if (accessToken != null && accessToken.getToken() != null) {
            String Userid = getMemberGuidByCode(accessToken.getToken(), code, Constants.AGENTID);
            if (Userid != null) { //由于ngnix代理的原因（正式的地址是132 域名对应的ip 132）当我们转发到131的代码执行时，微信认为你的域名和请求不一致，会回调两次。
                session.setAttribute("UserId", Userid);
                // 这里简单处理,存储到session中
                logger.info("UserId:   "+session.getAttribute("UserId"));
                logger.info("oauth2url:   "+oauth2url);
                try {
                    request.getRequestDispatcher(oauth2url).forward(request,response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                logger.info("UserId:   "+session.getAttribute("UserId"));
                logger.info("oauth2url:   "+oauth2url);
            }
        }


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
        + redirect_uri + "&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
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
         System.out.println("code==" + code + "\ntoken=" + token + "\nagentid=" + agentId);
         Result<String> result = QiYeUtil.oAuth2GetUserByCode(token, code, agentId);
         System.out.println("result=" + result);
        if (result.getErrcode() == "0") {
            if (result.getObj() != null) {
                // 此处可以通过微信授权用code还钱的Userid查询自己本地服务器中的数据
                return result.getObj();
            }
        }
        return "";
    }

}
