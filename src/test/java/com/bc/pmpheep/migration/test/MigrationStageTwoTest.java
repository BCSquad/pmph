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
 * <p>Title:图三测试类<p>
 * <p>Description:用户模块方法测试<p>
 * @author lyc
 * @date 2017年11月3日 下午5:14:24
 */
public class MigrationStageTwoTest extends BaseTest{
	Logger logger = LoggerFactory.getLogger(MigrationStageTwoTest.class);
	
	@Resource
	MigrationStageTwo migrationStageTwo;
	
	@Test
	public void Test(){
		migrationStageTwo.start();
	}
}
