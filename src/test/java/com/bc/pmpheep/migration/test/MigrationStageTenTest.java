/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.MigrationStageTen;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

/**
 * 内容管理数据迁移工具测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class MigrationStageTenTest extends BaseTest{

    Logger logger = LoggerFactory.getLogger(MigrationStageTenTest.class);

    @Resource
    MigrationStageTen migrationStageTen;

    @Test
    @Rollback(false)
    public void demo() {
        migrationStageTen.start();
    }
}
