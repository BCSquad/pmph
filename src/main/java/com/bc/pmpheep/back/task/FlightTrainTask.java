package com.bc.pmpheep.back.task;

import com.bc.pmpheep.back.util.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
public class FlightTrainTask {
    private static final Logger logger = LoggerFactory
            .getLogger(FlightTrainTask.class.getName());
    /**
     * 签名编码
     */
    public static final String UTF8 = "utf-8";
    /**
     * 签名key
     */
    public static final String SIGN_KEY = "sign";


    @Scheduled(cron = "0 0 1 1/1 * ? ")  //每隔一天执行一次定时任务
    public void consoleInfo() {
        System.out.println("定时任务");
    }


    public void SyncBookSellWell() throws UnsupportedEncodingException {
        Map<String, Object> api = new HashMap<String, Object>();
        api.put("app_key", "nmkt8v9NkWbQ9WPFl3l6lFNsyThsfcep");
        api.put("format", "json");
        api.put("method", "com.ai.ecp.pmph.order.saleRank");
        api.put("session", "MDzjI012CaqX4HG1HbOj35ps1yOYxJ7KfL1ezjKT89OLZZe0f22S6LY6eZ4DleBR");
        api.put("sign_method", "md5");
        api.put("timestamp", DateUtil.formatTimeStamp("yyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
        api.put("v", "1.0");
        String sign = DigestUtil.digest(api, "hbP5YsbmiWnkOP4IPtXE126JiIaFRCWD4gpfrcULPbs5hytCw06T2SooKfcUnc2g");
        String params = SyncUtils.getUrlApi(api);
        params += "&sign=" + sign;
        params += "&biz_content=" + CodecUtil.encodeURL("{\"num\":2}");
        /* params=CodecUtil.encodeURL(params);*/

        String url = "http://aip.pmph.com/route/rest";
        String s = SyncUtils.StringGet(params, url);
        System.out.println(s);
    }
}