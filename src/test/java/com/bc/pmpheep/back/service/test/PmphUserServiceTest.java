/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.service.test;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 社内用户服务测试
 *
 * @author L.X <gugia@qq.com>
 */
public class PmphUserServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    PmphUserService pmphUserService;

    @Test
    public void add() {
        PmphUser user = new PmphUser("高富帅", null);
        try {
            pmphUserService.add(user);
        } catch (CheckedServiceException ex) {
            logger.error(ex.toString());
        }
        user = new PmphUser("", "随机密码");
        try {
            pmphUserService.add(user);
            logger.error("Shit, 这里应该有异常才对！");
        } catch (CheckedServiceException ex) {
            logger.error(ex.toString());
        }
        user = new PmphUser("高富帅", "随机密码");
        try {
            pmphUserService.add(user);
        } catch (CheckedServiceException ex) {
            logger.error(ex.toString());
        }
    }
}
