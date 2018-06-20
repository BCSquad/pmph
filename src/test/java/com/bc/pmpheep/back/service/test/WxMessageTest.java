package com.bc.pmpheep.back.service.test;

import com.bc.pmpheep.back.service.WxSendMessageService;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.dao.SendWxMessageDao;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cyx  on 2018/6/11
 */
public class WxMessageTest extends BaseTest {

    @Autowired
    WxSendMessageService wxSendMessageService;

    @Autowired
    WXQYUserService wXQYUserService;

    @Test
    public void insertTest(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("touser","111111");
        map.put("msgdbtype",0);
        map.put("content","2222222");
        Integer result = wxSendMessageService.insertMessage(map);
        System.out.println(result);
    }

    @Test
    public void batchInsertTest(){
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
       Integer resultInt =  wXQYUserService.batchInsertWxMessage("这是一条测试内容",1,list,"","","");
       System.out.println(resultInt);
    }


}
