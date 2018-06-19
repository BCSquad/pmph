package com.bc.pmpheep.wx.service;


import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.WxSendMessageService;
import com.bc.pmpheep.back.util.HttpUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.wechat.util.WXURLUtil;
import com.bc.pmpheep.wx.util.SendWXMessageUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WXQYUserService extends WXBaseService {

    static final String URL_USER_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";
    static final String URL_USERINFO_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s&agentid=%s";

    @Autowired
    WxSendMessageService wxSendMessageService;

    @Autowired
    PmphUserService pmphUserService;

    /**
     * @param hrefType 超链接类型 0 没有超链接 1 前台app 超链接 2 后台app超链接
     * @param hrefContentType 超链接内容 1 查看,2审核
     * @param touser 成员ID列表
     * @param toparty 部门ID列表
     * @param totag 标签ID列表
     * @param msgtype text
     * @param text 内容
     * @param safe  表示是否是保密消息，0表示否，1表示是，默认0
     *              paramUrl 参数预留
     * @return
     */
    public Map sendTextMessage(String hrefType,String hrefContentType,String touser,String toparty,String totag,String msgtype,String text,short safe,String paramUrl){
        Map<String,Object> map = new HashMap<String,Object>();
        if(!StringUtil.isEmpty(touser)){
           touser = touser.replaceAll("\\s+(?=,)|(?<=,)\\s+", "");//去掉 , 前后的空格
           touser = touser.replaceAll(",","|"); // , 替换成 |
           touser = touser.replaceAll("[\\[\\]]",""); //去掉[ 和 ]

        }

        if(!StringUtil.isEmpty(toparty)){
            toparty = toparty.replaceAll("\\s+(?=,)|(?<=,)\\s+", "");
            toparty = toparty.replaceAll(",","|");
            toparty = toparty.replaceAll("[\\[\\]]","");
        }

        if(!StringUtil.isEmpty(totag)){
            totag = totag.replaceAll("\\s+(?=,)|(?<=,)\\s+", "");
            totag = totag.replaceAll(",","|");
            totag = totag.replaceAll("[\\[\\]]","");
        }
        String url = WXURLUtil.SEND_MSG_URL.replaceAll("ACCESS_TOKEN",this.getAccessToken(false));
        map.put("url" ,url);
        map.put("paramUrl",paramUrl);
        map.put("touser" ,touser);
        map.put("toparty" ,toparty);
        map.put("totag" ,totag);
        map.put("msgtype" ,msgtype);
        map.put("agentid",this.agentid);
        map.put("hrefType" ,hrefType);
        map.put("hrefContentType" ,hrefContentType);
        map.put("content" ,text);
        map.put("safe" ,safe);
//        String msgdbtype = "1".equals(MapUtils.getString(map,"hrefContentType",""))?"0":"1";
//        map.put("msgdbtype",msgdbtype);
//        wxSendMessageService.insertMessage(map);
//        map.remove("msgdbType");
        return SendWXMessageUtil.sendWxTextMessage(map);
    }

    public Integer batchInsertWxMessage(String content,int msgdbtype,List<Long> useridList,String hrefType,String hrefContentType,String paramUrl){
       return  wxSendMessageService.batchInsertWxMessage(content,msgdbtype,useridList,hrefType,hrefContentType,paramUrl);
    }


    public Map findUser(String userid) throws  CheckedServiceException{
        String url = String.format(URL_USER_GET, this.getAccessToken(false), userid);
        if(StringUtil.isEmpty(userid)){
            throw new CheckedServiceException("微信请求", CheckedExceptionResult.NULL_PARAM,"userid为空");
        }
        try {

            HttpGet get = new HttpGet(url);
            HttpResponse res = client.execute(get);
            HttpEntity entity = res.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8");
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Map user = JSON.parseObject(responseContent, Map.class);
                return  user;
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public Map<String, String> getUserByWXCode(String code) {
        String url = String.format(URL_USERINFO_GET, this.getAccessToken(false), code, this.getAgentid());
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse res = client.execute(get);
            HttpEntity entity = res.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8");
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Map user = JSON.parseObject(responseContent, Map.class);
                String UserId = MapUtils.getString(user, "UserId");
                if (StringUtils.isNotEmpty(UserId)) {
                    return user;
                }
            }
            throw new RuntimeException("请从微信客户端访问！");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

    public String getSHA1_JS(String noncestr, String timestamp, String url) {
        try {
            String[] keys = new String[]{"jsapi_ticket", "noncestr", "timestamp", "url"};
            String[] values = new String[]{this.getJsapiTicket(false), noncestr, timestamp, url};
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb.append(keys[i] + "=" + values[i]);
                if (i < 3)
                    sb.append("&");
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
