/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.dao;

import org.springframework.data.repository.CrudRepository;

import com.bc.pmpheep.general.po.MessageAttachment;

/**
 * 
 * <pre>
 * 功能描述：MongoDB消息内容数据访问层
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
public interface MessageAttachmentDao extends CrudRepository<MessageAttachment, String> {

}
