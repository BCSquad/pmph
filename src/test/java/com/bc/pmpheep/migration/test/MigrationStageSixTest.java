package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.bc.pmpheep.migration.MigrationStageSix;
import com.bc.pmpheep.test.BaseTest;

/**
 *@author tyc 
 *@CreateDate 2017年11月6日 上午08:46:21
 *
 **/
public class MigrationStageSixTest extends BaseTest {
	
	@Resource
	MigrationStageSix migrationStageSix;
	
	@Test
	public void demo() throws Exception {
		migrationStageSix.start();
	}
}
