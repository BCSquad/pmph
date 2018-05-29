package com.bc.pmpheep.general.service.test;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by cyx  on 2018/5/24
 */
public class WxMessageSendTest extends BaseTest{

    @Autowired
    WXQYUserService service;


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
        Map<String,Object> map  = service.sendTextMessage("","1",touser,toparty,totag,msgtype,text,safe);
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void getUserByWXCode(){
        Map<String,String> map = service.getUserByWXCode("LiHuan");
        System.out.println(JSON.toJSONString(map));

    }




}
