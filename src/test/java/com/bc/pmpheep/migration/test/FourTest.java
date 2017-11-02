package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.migration.MigrationStageFour;
import com.bc.pmpheep.test.BaseTest;

/**
 *@author MrYang 
 *@CreateDate 2017年11月2日 上午10:44:31
 *
 **/
public class FourTest extends BaseTest {
	
	@Resource
	MigrationStageFour migrationStageFour;

    @Test
    @Rollback(true)
    public void demo() throws Exception {
    	migrationStageFour.start();
    }
}
