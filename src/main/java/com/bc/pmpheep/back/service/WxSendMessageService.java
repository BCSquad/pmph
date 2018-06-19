package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

/**
 * Created by cyx  on 2018/6/11
 */
public interface WxSendMessageService {

    public Integer insertMessage(Map<String, Object> map);

    Integer batchInsertWxMessage(String content, int msgdbtype, List<Long> useridList,String hrefType,String hrefContentType,String paramUrl);
}
