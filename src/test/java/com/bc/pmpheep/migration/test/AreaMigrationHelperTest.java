package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.migration.AreaMigrationHelper;
import com.bc.pmpheep.migration.GroupMigrationHelper;
import com.bc.pmpheep.test.BaseTest;

/**
 * 区域数据迁移测试工具类
 * @author Mr
 *
 */
public class AreaMigrationHelperTest extends BaseTest{

    Logger logger = LoggerFactory.getLogger(AreaMigrationHelperTest.class);

    @Resource
    AreaMigrationHelper helper;

    @Test
    @Rollback(false)
    public void demo() {
    	helper.area();
    }
	
}
