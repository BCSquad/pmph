package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.SendWxMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cyx  on 2018/6/11
 */
@Service
public class WxSendMessageServiceImpl implements WxSendMessageService {

    @Autowired
    SendWxMessageDao sendWxMessageDao;

    public Integer insertMessage(Map<String, Object> map){
        return sendWxMessageDao.insertMessage(map);
    }

    @Override
    public Integer batchInsertWxMessage(String content, int msgdbtype, List<Long> useridList) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(Long userid:useridList){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("content",content);
            map.put("msgdbtype",msgdbtype);
            map.put("userid",userid);
            list.add(map);
        }
        return sendWxMessageDao.batchInsertWxMessage(list);
    }

}
