package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by cyx  on 2018/6/11
 */
public interface SendWxMessageDao {

    public Integer insertMessage(Map<String, Object> map);

    Integer batchInsertWxMessage( List<Map<String,Object>> list);
}
