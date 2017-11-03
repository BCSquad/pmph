package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.migration.MigrationStageFive;
import com.bc.pmpheep.test.BaseTest;
/**
 * 教材书籍迁移工具类
 * @author Mr
 *	2017—11—2
 */
public class MigrationStageFiveTest extends BaseTest{
	 Logger logger = LoggerFactory.getLogger(MigrationStageFiveTest.class);
     
     @Resource
     MigrationStageFive migrationStageFive;
     
     @Test
     @Rollback(false)
     public void test(){
    	 migrationStageFive.start();
     }
}
