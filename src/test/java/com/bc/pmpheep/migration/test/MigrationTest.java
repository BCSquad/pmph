/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.AddTestUser;
import com.bc.pmpheep.migration.MigrationBook;
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
import org.junit.Ignore;
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
    @Resource
    MigrationBook migrationBook;
    @Resource
    AddTestUser addTestUser;

    @Test
    @Rollback(true)
    public void testMigrationStageOne() {
        //migrationStageOne.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageTwo() {
        //migrationStageTwo.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageThree() {
        //migrationStageThree.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageFour() {
        //migrationStageFour.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageFive() {
        //migrationStageFive.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageSix() {
        //migrationStageSix.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageSeven() {
        //migrationStageSeven.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageEight() {
        //migrationStageEight.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageNine() {
        //migrationStageNine.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationStageTen() {
        //migrationStageTen.start();
    }

    @Test
    @Rollback(true)
    public void testMigrationPlus() {
       // migrationPlus.start();
    }

    @Ignore
    @Test
    @Rollback(true)
    public void testAddTestUser() {
        //addTestUser.addTestUser();
    }

    @Test
    @Rollback(true)
    public void addBook() {
        //migrationBook.start();
    }
}
