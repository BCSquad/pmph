package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.MessageAttachmentDao;
import com.bc.pmpheep.back.po.MessageAttachment;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：消息附件接口实现类
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
@Service
public class MessageAttachmentServiceImpl implements MessageAttachmentService {
    @Autowired
    private MessageAttachmentDao messageAttachmentDao;

    @Override
    public MessageAttachment addMessageAttachment(MessageAttachment messageAttachment)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(messageAttachment)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件对象为空 ");

        }
        return messageAttachmentDao.addMessageAttachment(messageAttachment);
    }

    @Override
    public List<MessageAttachment> getMessageAttachmentByMsgId(String msgId)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(msgId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户消息ID为空 ");

        }
        return messageAttachmentDao.getMessageAttachmentByMsgId(msgId);
    }

    @Override
    public Integer deleteMessageAttachment(List<String> msgIds) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(msgIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户消息ID为空 ");

        }
        return messageAttachmentDao.deleteMessageAttachment(msgIds);
    }

    @Override
    public Integer updateMessageAttachment(MessageAttachment messageAttachment)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(messageAttachment)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件对象为空 ");

        }
        return messageAttachmentDao.updateMessageAttachment(messageAttachment);
    }

}
