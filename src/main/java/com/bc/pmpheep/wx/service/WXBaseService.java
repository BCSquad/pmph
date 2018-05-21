package com.bc.pmpheep.wx.service;

import com.alibaba.fastjson.JSON;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * Created by lihuan on 2018/5/7.
 */
public abstract class WXBaseService {

    final static String URL_GET_TOKEN_QY = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    final static String URL_GET_TICKET_QY = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s";

    protected static final HttpClient client = HttpClientBuilder.create().build();

    String wxId;    //coprid / AppID
    String agentid;
    String secret;  //corpSecret / AppSecret


    @Autowired
    CacheManager cacheManager;

    /**
     * 从缓存中获取access_token
     *
     * @return 返回 空串 表示失败
     */
    private String getAccessTokenFromCache() {
        Cache cache = cacheManager.getCache("wxTokenCache");
        String key = this.getWxId();
        Element element = cache.get(key);
        if (element == null || element.isExpired()) {
            return "";
        }
        return element.getObjectValue().toString();
    }

    /**
     * 缓存access_token
     *
     * @param token
     */
    private void setAccessTokenToCache(String token) {
        Cache cache = cacheManager.getCache("wxTokenCache");
        Element element = new Element(this.getWxId(), token);
        cache.put(element);
    }

    /**
     * 从缓存获取jsapi_ticket
     *
     * @param access_token
     * @return
     */
    private String getJsapiTicketFromCache(String access_token) {
        Cache cache = cacheManager.getCache("wxTokenCache");
        String key = access_token;
        Element element = cache.get(key);
        if (element == null || element.isExpired()) {
            return "";
        }
        return element.getObjectValue().toString();
    }


    /**
     * 缓存 jsapi_ticket
     *
     * @param token  acess_token
     * @param ticket jsapi_ticket
     */
    private void setJsapiTicketToCache(String token, String ticket) {
        Cache cache = cacheManager.getCache("wxTokenCache");
        Element element = new Element(token, ticket);
        cache.put(element);
    }


    /**
     * 获取access_token
     *
     * @param forceFromWX 如果为true，标识强制从微信服务器获取
     * @return
     */
    public String getAccessToken(boolean forceFromWX) {
        if (forceFromWX) {
            return this.getAccessTokenFromWX();
        }
        String token = this.getAccessTokenFromCache();
        if (StringUtils.isEmpty(token)) {
            token = this.getAccessTokenFromWX();
        }
        return token;
    }

    /**
     * 获取jsapi_ticket
     *
     * @param forceFromWX 如果为true，标识强制从微信服务器获取
     * @return
     */
    public String getJsapiTicket(boolean forceFromWX) {
        String access_token = this.getAccessToken(false);
        if (forceFromWX) {
            return this.getJsapiTicketFromWX(access_token);
        }
        String ticket = this.getJsapiTicketFromCache(access_token);
        if (StringUtils.isEmpty(ticket)) {
            ticket = this.getJsapiTicketFromWX(access_token);
        }
        return ticket;
    }

    /**
     * 从微信获取access_token
     *
     * @return
     */
    private String getAccessTokenFromWX() {

        String url = String.format(URL_GET_TOKEN_QY, this.getWxId(), getSecret());

        String token = "";
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Map ret = JSON.parseObject(responseContent, Map.class); // 将json字符串转换为json对象;
                token = MapUtils.getString(ret, "access_token", "");
                if (StringUtils.isNotEmpty(token)) {
                    this.setAccessTokenToCache(token);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return token;
    }

    /**
     * 从微信服务器获取jsapi_ticket
     *
     * @param access_token
     * @return
     */
    private String getJsapiTicketFromWX(String access_token) {
        String url = String.format(URL_GET_TICKET_QY, access_token);

        String ticket = "";
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Map ret = JSON.parseObject(responseContent, Map.class); // 将json字符串转换为json对象;
                ticket = MapUtils.getString(ret, "ticket", "");
                if (StringUtils.isNotEmpty(ticket)) {
                    this.setJsapiTicketToCache(access_token, ticket);
                }
            }
            return ticket;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ticket;
        }
    }

    @PreDestroy
    protected void destroy() {
        if (client != null && client.getConnectionManager() != null) {
            client.getConnectionManager().shutdown();
        }
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
