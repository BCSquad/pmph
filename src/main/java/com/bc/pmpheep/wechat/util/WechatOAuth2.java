package com.bc.pmpheep.wechat.util;

import net.sf.json.JSONObject;

import com.bc.pmpheep.wechat.enums.EnumMethod;

/**
 * 
 * <pre>
 * 功能描述：获取成员信息
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
public class WechatOAuth2 {
    private static final String get_oauth2_url =
                                               "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";

    /**
     * 
     * <pre>
     * 功能描述：根据code获取成员信息
     * 使用示范：
     *
     * @param token 企业任意填写，用于生成签名。
     * @param code 获取微信重定向到自己设置的URL中code参数
     * @param AgentID 接收的应用id
     * @return
     * </pre>
     */
    public static JSONObject getUserByCode(String token, String code, int AgentID) {
        String menuUrl =
        get_oauth2_url.replace("ACCESS_TOKEN", token)
                      .replace("CODE", code)
                      .replace("AGENTID", AgentID + "");
        JSONObject jo = HttpRequestUtil.httpRequest(menuUrl, EnumMethod.GET.name(), null);
         System.out.println("jo=" + jo);
        return jo;
    }

}
