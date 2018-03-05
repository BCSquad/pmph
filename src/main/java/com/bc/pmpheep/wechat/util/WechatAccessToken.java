package com.bc.pmpheep.wechat.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.wechat.enums.EnumMethod;
import com.bc.pmpheep.wechat.po.AccessToken;
import com.bc.pmpheep.wechat.po.WXjsTicket;

/**
 * 
 * <pre>
 * 功能描述：公众平台通用接口工具类
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
public class WechatAccessToken {
    // 获取微信公众号：access_token的接口地址（GET） 限2000（次/天）
    public final static String access_token_url         =
                                                        "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 获取企业号access_token
    public final static String company_access_token_url =
                                                        "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

    // https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRECT

    /**
     * 
     * <pre>
     * 功能描述：获取access_token
     * 使用示范：
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @param type
     * @return
     * </pre>
     */
    public static AccessToken getAccessToken(String appid, String appsecret, int type) {
        AccessToken accessToken = null;
        String requestUrl =
        access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        if (type == 1) {
            requestUrl =
            company_access_token_url.replace("CORPID", appid).replace("CORPSECRET", appsecret);
            // System.err.println(requestUrl);
        }
        JSONObject jsonObject =
        HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
        if (jsonObject == null) {
            jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
        }
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
            }
        }
        return accessToken;
    }

    private static Logger log = LoggerFactory.getLogger(WechatAccessToken.class);

    /**
     * 
     * <pre>
     * 功能描述：获取wx_js_ticket
     * 使用示范：
     *
     * @param accessToken access_token
     * @return
     * </pre>
     */
    public static WXjsTicket getWXjsTicket(String accessToken) {
        WXjsTicket wXjsTicket = null;
        String requestUrl = WXURLUtil.JSAPIURL.replace("ACCESS_TOKEN", accessToken);
        // 发起GET请求获取凭证
        JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
        System.out.println("CommonUtil.java 调用了一次getWXjsTicket接口");
        if (null != jsonObject) {
            try {
                wXjsTicket = new WXjsTicket();
                wXjsTicket.setJsTicket(jsonObject.getString("ticket"));
                wXjsTicket.setJsTicketExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                wXjsTicket = null;
                // 获取wXjsTicket失败
                log.error("获取wXjsTicket失败 errcode:{} errmsg:{}",
                          jsonObject.getInt("errcode"),
                          jsonObject.getString("errmsg"));
            }
        }
        return wXjsTicket;
    }

}