/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.PmphUser;

/**
 * PmphUser实体类数据访问层接口
 *
 * @author L.X <gugia@qq.com>
 */
public interface PmphUserDao {

    /**
     * 通过用户名（唯一字段）查找特定用户
     *
     * @param username 用户名
     * @return 符合查找条件的PmphUser对象
     */
    PmphUser getByUsername(String username);

}
