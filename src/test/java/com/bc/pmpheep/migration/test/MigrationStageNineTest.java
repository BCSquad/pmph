/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.MigrationStageNine;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

/**
 * 出版图书数据迁移工具测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class MigrationStageNineTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(MigrationStageNineTest.class);

    @Resource
    MigrationStageNine migrationStageNine;

    @Test
    @Rollback(false)
    public void demo() {
        migrationStageNine.start();
    }
}
