/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.dao.MessageAttachmentDao;
import com.bc.pmpheep.general.po.MessageAttachment;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：MongoDB 消息服务
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-19
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
public class MessageAttachmentService {

    @Resource
    MessageAttachmentDao messageAttachmentDao;

    /**
     * 新增MessageAttachment对象
     * 
     * @param messageAttachment 消息对象
     * @return 返回插入对象(包括MongoDB生成的id)
     */
    public MessageAttachment add(MessageAttachment messageAttachment) {
        if (ObjectUtil.isNull(messageAttachment)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件更新对象为空");
        }
        if (StringUtil.isEmpty(messageAttachment.getAttachment())
            || StringUtil.isEmpty(messageAttachment.getMsgId())
            || StringUtil.isEmpty(messageAttachment.getAttachmentName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件更新对象属性为空");
        }
        return messageAttachmentDao.save(messageAttachment);
    }

    /**
     * 根据id查找MessageAttachment对象
     * 
     * @param id MessageAttachment主键
     * @return 查找结果，未找到时返回null
     */
    public MessageAttachment get(String id) {
        if (StringUtil.isEmpty(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件获取时ID为空");
        }
        return messageAttachmentDao.findOne(id);
    }

    /**
     * 批量查找MessageAttachment对象
     * 
     * @param ids 主键集合
     * @return 返回MessageAttachment对象集合
     */
    public List<MessageAttachment> list(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "ID为空！");
        }
        return (List<MessageAttachment>) messageAttachmentDao.findAll(ids);
    }

    /**
     * 更新MessageAttachment对象
     * 
     * @param messageAttachment 消息对象
     */
    public void update(MessageAttachment messageAttachment) {
        if (null == messageAttachment) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件更新对象为空");
        }
        if (StringUtil.isEmpty(messageAttachment.getMsgId())
            || StringUtil.isEmpty(messageAttachment.getAttachment())
            || StringUtil.isEmpty(messageAttachment.getAttachmentName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件更新对象属性为空");
        }
        MessageAttachment mat = get(messageAttachment.getId());
        if (null == mat) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "未找到更新对象");
        }
        mat.setMsgId(messageAttachment.getMsgId());
        mat.setAttachment(messageAttachment.getAttachment());
        mat.setAttachmentName(messageAttachment.getAttachmentName());
        messageAttachmentDao.save(mat);
    }

    /**
     * 本方法在业务中不提供，仅用于测试
     */
    public void removeAll() {
        messageAttachmentDao.deleteAll();
    }
}
