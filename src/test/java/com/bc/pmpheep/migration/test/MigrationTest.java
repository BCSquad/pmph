/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.MigrationStageEight;
import com.bc.pmpheep.migration.MigrationStageFive;
import com.bc.pmpheep.migration.MigrationStageFour;
import com.bc.pmpheep.migration.MigrationStageNine;
import com.bc.pmpheep.migration.MigrationStageOne;
import com.bc.pmpheep.migration.MigrationStageSeven;
import com.bc.pmpheep.migration.MigrationStageSix;
import com.bc.pmpheep.migration.MigrationStageTen;
import com.bc.pmpheep.migration.MigrationStageThree;
import com.bc.pmpheep.migration.MigrationStageTwo;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

/**
 * 迁移集成测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class MigrationTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(MigrationTest.class);

    @Resource
    MigrationStageOne migrationStageOne;
    @Resource
    MigrationStageTwo migrationStageTwo;
    @Resource
    MigrationStageThree migrationStageThree;
    @Resource
    MigrationStageFour migrationStageFour;
    @Resource
    MigrationStageFive migrationStageFive;
    @Resource
    MigrationStageSix migrationStageSix;
    @Resource
    MigrationStageSeven migrationStageSeven;
    @Resource
    MigrationStageEight migrationStageEight;
    @Resource
    MigrationStageNine migrationStageNine;
    @Resource
    MigrationStageTen migrationStageTen;

    @Test
    @Rollback(false)
    public void testStart() {
        migrationStageOne.start();
        migrationStageTwo.start();
        migrationStageThree.start();
        migrationStageFour.start();
        migrationStageFive.start();
        migrationStageSix.start();
        migrationStageSeven.start();
        migrationStageEight.start();
        migrationStageNine.start();
        migrationStageTen.start();
    }
}
