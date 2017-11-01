/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.dao;

import com.bc.pmpheep.general.po.Content;
import org.springframework.data.repository.CrudRepository;

/**
 * MongoDB CMS内容数据访问层
 *
 * @author L.X <gugia@qq.com>
 */
public interface ContentDao extends CrudRepository<Content, String> {

}
