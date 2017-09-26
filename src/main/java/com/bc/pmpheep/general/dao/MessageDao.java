/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.dao;

import com.bc.pmpheep.general.po.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * MongoDB消息内容数据访问层
 *
 * @author L.X <gugia@qq.com>
 */
public interface MessageDao extends CrudRepository<Message, String> {

}
