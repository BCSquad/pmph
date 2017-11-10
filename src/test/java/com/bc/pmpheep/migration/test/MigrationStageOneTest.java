/**
 * 
 */
package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.migration.MigrationStageOne;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>Title:测试类<p>
 * <p>Description:迁移工具图一测试类<p>
 * @author lyc
 * @date 2017年10月31日 下午5:39:19
 */
public class MigrationStageOneTest extends BaseTest{
       Logger logger = LoggerFactory.getLogger(MigrationStageOneTest.class);
       
       @Resource
       MigrationStageOne migrationStageOne;
       
       @Test
       public void test(){
    	   migrationStageOne.start();
       }
}
