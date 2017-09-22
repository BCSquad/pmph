package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphRolePermissionService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphRolePermissionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphRolePermissionServiceTest.class);
	
	@Resource
	private PmphRolePermissionService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test(){
    	Random r =new Random();
    	PmphRolePermission testPar=new PmphRolePermission(new Long(r.nextInt(200)), new Long(r.nextInt(200)))  ;
    	logger.info("---PmphRolePermissionService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addPmphRolePermission(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setPermissionId(new Long(r.nextInt(200)));
    	logger.info(testService.updatePmphRolePermission(testPar).toString());
    	//删除
    	logger.info(testService.deletePmphRolePermissionById(2L).toString());
    	
    	//查询
    	logger.info(testService.getPmphRolePermissionById(7L).toString());
    	
    }
    
    
    
}





