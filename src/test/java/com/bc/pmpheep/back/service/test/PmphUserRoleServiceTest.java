package com.bc.pmpheep.back.service.test;

import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphUserRoleServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphUserRoleServiceTest.class);
	
	@Resource
	private PmphUserRoleService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	PmphUserRole testPar=new PmphUserRole ( new Long(r.nextInt(200)), new Long(r.nextInt(200)));
    	logger.info("---MaterialService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addPmphUserRole(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setRoleId(new Long(r.nextInt(200)));
    	logger.info(testService.updatePmphUserRole(testPar).toString());
    	//删除
    	logger.info(testService.deletePmphUserRoleById(26L).toString());
    	//查询
    	logger.info(testService.getPmphUserRoleById(25L).toString());
    	
    }
    
    
    
}





