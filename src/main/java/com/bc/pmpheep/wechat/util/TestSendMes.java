package com.bc.pmpheep.wechat.util;

import net.sf.json.JSONObject;

/**
 * 
 * <pre>
 * 功能描述：消息发送
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
public class TestSendMes {
    // 发送消息
    public static String SEND_MSG_URL =
                                      "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    public static void main(String[] args) {
        // 应用ID，账号，部门为""，标签为""，消息类型，内容
        // 部门不为""，标签不为""，将会给该部门的每个成员发送消息
        Send_msg("@all",
                 "200",
                 "",
                 "text",
                 1000002,
                 "企业微信发送消息测试.\n登陆<a href='http://medu.ipmph.com/pmphwx/sso/login'>人卫出版社</a>");
        // "企业微信发送消息测试.\n登陆<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx72eaeae98b5382c9&redirect_uri=http%3A%2F%2F20097r18u8.iask.in%2Fpmpheep%2Foauth2url%3Foauth2url%3Dhttp%3A%2F%2F192.168.100.109%3A8089%2Fpmpheep%2Fpmph%2Flogin%3Fusername%3Dadmin%26password%3D123%26_timer%3D1519954483077&response_type=code&scope=snsapi_base&state=sunlight&connect_redirect=1#wechat_redirect'>人卫出版社</a>");

    }

    /**
     * 
     * <pre>
     * 功能描述：主动发送文字给企业用户
     * 使用示范：
     *
     * @param touser 成员ID列表
     * @param toparty 部门ID列表
     * @param totag 标签ID列表
     * @param msgtype 消息类型，此时固定为：text （支持消息型应用跟主页型应用）
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看
     * @param content 消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
     * @return int 表示是否是保密消息，0表示否，1表示是，默认0
     * </pre>
     */
    public static int Send_msg(String touser, String toparty, String totag, String msgtype,
    int agentid, String content) {
        int errCode = 0;
        // 拼接请求地址
        String requestUrl =
        SEND_MSG_URL.replace("ACCESS_TOKEN",
                             WechatAccessToken.getAccessToken(Constants.CORPID, Constants.SECRET, 1)
                                              .getToken());
        // 需要提交的数据
        // String postJson =
        // "{\"agentid\":\"%s\",\"touser\":\"%s\",\"toparty\":\"[1,%s]\",\"totag\":\"%s\",\"msgtype\":\"text\",\"%s\":{\"content\":\"%s\"},\"safe\":\"0\"}";
        // String postJson =
        // "{\"agentid\":\"%s\",\"touser\":\"%s\",\"toparty\":\"%s\",\"totag\":\"%s\",\"msgtype\":\"text\",\"%s\":{\"content\":\"%s\"},\"safe\":\"0\"}";
        String postJson =
        "{\"agentid\":%s,\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\","
        + "\"msgtype\":\"%s\",\"text\": {\"content\": \"%s\"},\"safe\":0}";
        String outputStr =
        String.format(postJson, agentid, touser, toparty, totag, msgtype, content);
        // System.out.println(outputStr);
        // 创建成员
        JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "POST", outputStr);
        if (null != jsonObject) {
            // System.out.println(jsonObject.toString() + "=====");
            // int errcode = jsonObject.getInt("errcode");
            errCode = jsonObject.getInt("errcode");
        }
        return errCode;
    }
}
