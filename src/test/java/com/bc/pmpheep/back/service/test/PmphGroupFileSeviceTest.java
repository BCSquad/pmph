package com.bc.pmpheep.back.service.test;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphGroupFileSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupFileSeviceTest.class);
	
	@Resource
	private PmphGroupFileService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	PmphGroupFile testPar=new PmphGroupFile(new Long(r.nextInt(200)), new Long(r.nextInt(200)), String.valueOf(r.nextInt(200)),
    			"String fileName",r.nextInt(200),
    		    null)  ;
    	logger.info("---PmphGroupFileSevice 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addPmphGroupFile(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setFileName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updatePmphGroupFile(testPar).toString());
    	//删除
    	logger.info(testService.deletePmphGroupFileById(2L).toString());
    	//查询
    	logger.info(testService.getPmphGroupFileById(5L).toString());
    	
    }
    
    
    
}





