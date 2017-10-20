package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.MessageAttachment;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：消息附件
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface MessageAttachmentService {
    /**
     * 新增一个messageAttachment
     * 
     * @param MessageAttachment 实体对象
     * @return 带主键的 MessageAttachment thorws CheckedServiceException
     */
    MessageAttachment addMessageAttachment(MessageAttachment messageAttachment)
    throws CheckedServiceException;

    /**
     * 查询MessageAttachment集合 通过msgId
     * 
     * @param msgId
     * @return MessageAttachment
     * @throws CheckedServiceException
     */
    List<MessageAttachment> getMessageAttachmentByMsgId(String msgId)
    throws CheckedServiceException;

    /**
     * 删除MaterialType 通过msgIds
     * 
     * @param msgIds
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteMessageAttachment(List<String> msgIds) throws CheckedServiceException;

    /**
     * 根据主键id更新messageAttachment 不为null和不为‘’的字段
     * 
     * @param messageAttachment
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateMessageAttachment(MessageAttachment messageAttachment)
    throws CheckedServiceException;
}
