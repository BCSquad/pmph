package com.bc.pmpheep.wx.service;


import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.security.MessageDigest;
import java.util.Map;


public class WXQYUserService extends WXBaseService {

    static final String URL_USER_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";
    static final String URL_USERINFO_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s&agentid=%s";


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
