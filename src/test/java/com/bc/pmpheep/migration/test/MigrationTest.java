/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.AddTestUser;
import com.bc.pmpheep.migration.MigrationPlus;
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

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

/**
 * 迁移集成测试类
 *
 * @author L.X <gugia@qq.com>
 */
@FixMethodOrder(MethodSorters.JVM)
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
    @Resource
    MigrationPlus migrationPlus;

    @Test
    @Rollback(false)
    public void testMigrationStageOne() {
        migrationStageOne.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageTwo() {
        migrationStageTwo.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageThree() {
        migrationStageThree.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageFour() {
        migrationStageFour.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageFive() {
        migrationStageFive.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageSix() {
        migrationStageSix.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageSeven() {
        migrationStageSeven.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageEight() {
        migrationStageEight.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageNine() {
        migrationStageNine.start();
    }

    @Test
    @Rollback(false)
    public void testMigrationStageTen() {
        migrationStageTen.start();
    }
    
    @Test
    @Rollback(false)
    public void testMigrationPlus() {
    	migrationPlus.start();
    }
    
    @Resource
    AddTestUser addTestUser;
    
    @Test
    @Rollback(false)
    public void testAddTestUser() {
    	addTestUser.addTestUser();
    }
}
