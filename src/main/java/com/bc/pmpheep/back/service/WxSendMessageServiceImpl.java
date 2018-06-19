package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.SendWxMessageDao;
import com.bc.pmpheep.wx.util.SendWXMessageUtil;
import org.apache.commons.collections.MapUtils;
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
    public Integer batchInsertWxMessage(String content, int msgdbtype, List<Long> useridList,String hrefType,String hrefContentType,String paramUrl) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(Long userid:useridList){
            Map<String,Object> map = new HashMap<String,Object>();
            String hrefTypeFormat = SendWXMessageUtil.getHrefType(hrefType);
            String hrefContent = SendWXMessageUtil.getHrefContent(hrefContentType);
            map.put("content",content + String.format(hrefTypeFormat,paramUrl,hrefContent));
            map.put("msgdbtype",msgdbtype);
            map.put("userid",userid);
            map.put("isDeal",0);
            list.add(map);
        }
        return sendWxMessageDao.batchInsertWxMessage(list);
    }

}
