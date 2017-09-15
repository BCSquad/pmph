package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphDepartmentService;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphDepartmentSeviceTest extends BaseTest {
	Logger l = LoggerFactory.getLogger(PmphDepartmentSeviceTest.class);
	
	@Resource
	private PmphDepartmentService testService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void testPmphDepartment() throws Exception {
    	test(false);
    	test(true);
    }
    
    private void test(boolean c) throws Exception{
    	Random r =new Random();
    	PmphDepartment a=new PmphDepartment(5L, "String path", "String dpName", r.nextInt(1000000), "String note") ;
    	testService.addPmphDepartment(a);
    	l.info("---PmphDepartment--------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("---PmphDepartment---------------------------------修改-------------------------------------------");
    	a.setDpName(String.valueOf(r.nextLong()));
    	l.info(testService.updatePmphDepartmentById(a).toString());
    	a.setId(2L);
    	if(c){
    		l.info("---PmphDepartment---------------------------------删除-------------------------------------------");
        	l.info(testService.deletePmphDepartmentById(a).toString());
        	l.info("---PmphDepartment--------------------------------查询-------------------------------------------");
        	l.info(testService.getPmphDepartmentById(new PmphDepartment(1L)).toString());
    	}
    }
    
}





