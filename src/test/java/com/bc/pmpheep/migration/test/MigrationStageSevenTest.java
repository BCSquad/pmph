package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.migration.MigrationStageSeven;
import com.bc.pmpheep.test.BaseTest;

/**
 *@author MrYang 
 *@CreateDate 2017年11月2日 下午5:10:21
 *
 **/
public class MigrationStageSevenTest extends BaseTest {
	
	@Resource
	MigrationStageSeven migrationStageSeven;

    @Test
    @Rollback(true)
    public void demo() throws Exception {
    	migrationStageSeven.start();
    }
}