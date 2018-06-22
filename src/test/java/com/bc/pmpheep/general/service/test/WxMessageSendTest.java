package com.bc.pmpheep.general.service.test;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.controller.PropertiesImplController;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cyx  on 2018/5/24
 */
public class WxMessageSendTest extends BaseTest{

    @Autowired
    WXQYUserService service;

    protected static final HttpClient client = HttpClientBuilder.create().build();

    @Test
    public void findUser(){
        Map<String,Object> map = service.findUser("LiHuan");
        System.out.println(JSON.toJSONString(map));

    }

    @Test
    public void sendTextMessageTest(){

        String touser = "XiaoChengYan";
        String toparty = "";
        String totag = "";
        String msgtype = "text";
        String text = "您好,这是一个测试群发推送消息，可以忽略！";
        short safe = 0;
        Map<String,Object> map  = service.sendTextMessage("2","1",touser,toparty,totag,msgtype,text,safe,"");
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void findUserByUserId(){
        HttpGet get = new HttpGet("/findUserByUserId?userId=testOpenId");
        try{
        HttpResponse res = client.execute(get);
        HttpEntity entity = res.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Map user = JSON.parseObject(responseContent, Map.class);
        }
        }catch(Exception exception){

        }

    }
    @Test
    public void findUserTest(){
        Map user = service.findUser("LiuXiaoKang");
        System.out.println(JSON.toJSONString(user));

    }

    @Test
    public void TestPP(){
        Properties pp = new Properties();
        InputStream fis  = WxMessageSendTest.class.getClassLoader().getResourceAsStream("pmphapi-config.properties");
        try {
            pp.load(fis);
            System.out.println(pp.getProperty("rootAdrr").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testRex(){
        Pattern pattern_theSpaceBeSideComma= Pattern.compile("\\s+(?=,)|(?<=,)\\s+");
        Matcher m = pattern_theSpaceBeSideComma.matcher("a bc ,  |d  f  g");
        String t = " a bc ,  | d  f  g ";
        String re = t.replaceAll("\\s+(?=,)|(?<=,)\\s+", "");
        System.out.println("result:"+m.replaceAll(""));
        System.out.println("re="+re);
    }


}
