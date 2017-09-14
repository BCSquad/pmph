/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.dao.PmphUserDao;
import com.bc.pmpheep.test.BaseTest;

/**
 * PmphUserDao单元测试
 * 
 * @author L.X <gugia@qq.com>
 */
public class PmphUserDaoTest extends BaseTest {

    Logger      logger = LoggerFactory.getLogger(PmphUserDaoTest.class);

    @Resource
    PmphUserDao pmphUserDao;

    @Test
    public void findByUsername() {
    }
}
