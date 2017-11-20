/**
 * 
 */
package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.migration.MigrationStageThree;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>Title:测试类<p>
 * <p>Description:社内模块测试类<p>
 * @author lyc
 * @date 2017年11月2日 上午12:39:30
 */
public class MigrationStageThreeTest extends BaseTest{

	Logger logger = LoggerFactory.getLogger(MigrationStageThreeTest.class);
	
	@Resource
	MigrationStageThree migrationStageThree;
	
	@Test
	public void test(){
		migrationStageThree.start();
	}
}
