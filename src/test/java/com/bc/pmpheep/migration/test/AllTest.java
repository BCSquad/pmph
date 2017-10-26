package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.MigrationStageOne;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.test.BaseTest;

/**
 * 数据迁移单元测试
 *
 * @author mryang
 */
@SuppressWarnings("all")
public class AllTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(AllTest.class);

    @Resource
    MigrationStageOne stageOne;

    @Test
    @Rollback(false)
    public void addArea() throws Exception {
        stageOne.start();
    }

}
