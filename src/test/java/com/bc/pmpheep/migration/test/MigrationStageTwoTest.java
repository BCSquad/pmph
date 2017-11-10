/**
 * 
 */
package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.migration.MigrationStageTwo;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>Title:测试类<p>
 * <p>Description:社内模块测试类<p>
 * @author lyc
 * @date 2017年11月2日 上午12:39:30
 */
public class MigrationStageTwoTest extends BaseTest{

	Logger logger = LoggerFactory.getLogger(MigrationStageTwoTest.class);
	
	@Resource
	MigrationStageTwo migrationStageTwo;
	
	@Test
	public void test(){
		migrationStageTwo.start();
	}
}
