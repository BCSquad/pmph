package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.WriterUserRoleService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class WriterUserRoleServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(WriterUserRoleServiceTest.class);
	
	@Resource
	private WriterUserRoleService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	WriterUserRole testPar=new WriterUserRole ( new Long(r.nextInt(200)), new Long(r.nextInt(200)));
    	logger.info("---WriterUserRoleService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addWriterUserRole(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setRoleId(new Long(r.nextInt(200)));
    	logger.info(testService.updateWriterUserRole(testPar).toString());
    	//删除
    	logger.info(testService.deleteWriterUserRoleById(1L).toString());
    	//查询
    	logger.info(testService.getWriterUserRoleById(2L).toString());
    	
    }
    
    
    
}





