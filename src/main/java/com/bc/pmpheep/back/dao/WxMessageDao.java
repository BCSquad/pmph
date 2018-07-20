package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * 
 * 功能描述：微信消息数据持久层
 *
 */
@Repository
public interface WxMessageDao {


    /**
     * 查询微信消息列表
     * @return
     * @param pageParameter
     */
    List<WxMessageVO> queryMessageList(PageParameter<WxMessageVO> pageParameter);

    /**
     * 查询微信消息列表总数
     * @param pageParameter
     * @return
     */
    int queryMessageListCount(PageParameter<WxMessageVO> pageParameter);

    /**
     * 设为已读
     * @param id
     * @return
     */
    int haveRead(Long id);
}
