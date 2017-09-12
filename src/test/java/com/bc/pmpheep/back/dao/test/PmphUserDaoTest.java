/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao.test;

import com.bc.pmpheep.back.dao.pmphuser.PmphUserDao;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PmphUserDao单元测试
 *
 * @author L.X <gugia@qq.com>
 */
public class PmphUserDaoTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(PmphUserDaoTest.class);

    @Resource
    PmphUserDao pmphUserDao;

    @Test
    public void findByUsername() {
        String username = "admin";
        PmphUser pmphUser = pmphUserDao.getByUsername(username);
        if (null != pmphUser) {
            logger.info("已找到用户名为 '{}' 的用户", pmphUser.getUsername());
        } else {
            logger.error("未找到用户名为 'admin' 的用户");
        }
    }
}
