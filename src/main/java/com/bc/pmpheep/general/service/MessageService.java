/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bc.pmpheep.general.dao.MessageDao;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MongoDB 消息服务
 * 
 * @author L.X <gugia@qq.com>
 */
@Service
public class MessageService {

    @Resource
    MessageDao messageDao;

    /**
     * 新增Message对象
     * 
     * @param message 消息对象
     * @return 返回插入对象(包括MongoDB生成的id)
     */
    public Message add(Message message) {
        if (null == message) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象为空");
        }
        if (null == message.getContent() || message.getContent().isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象内容为空");
        }
        return messageDao.save(message);
    }

    /**
     * 根据id查找Message对象
     * 
     * @param id Message主键
     * @return 查找结果，未找到时返回null
     */
    public Message get(String id) {
        if (null == id || id.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息获取时ID为空");
        }
        return messageDao.findOne(id);
    }

    /**
     * 批量查找Message对象
     * 
     * @param ids 主键集合
     * @return 返回Message对象集合
     */
    public List<Message> list(List<String> ids) {
        return (List<Message>) messageDao.findAll(ids);
    }

    /**
     * 更新Message对象
     * 
     * @param message 消息对象
     */
    public void update(Message message) {
        if (null == message) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象为空");
        }
        if (null == message.getId() || message.getId().isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象id为空");
        }
        if (null == message.getContent() || message.getContent().isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象内容为空");
        }
        Message msg = get(message.getId());
        if (null == msg) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "未找到更新对象");
        }
        msg.setContent(message.getContent());
        messageDao.save(msg);
    }

    /**
     * 本方法在业务中不提供，仅用于测试
     */
    public void removeAll() {
        messageDao.deleteAll();
    }
}
