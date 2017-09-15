package com.bc.pmpheep.back.service.test;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphGroupMemberServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupMemberServiceTest.class);
	
	@Resource
	private PmphGroupMemberService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	PmphGroupMember testPar=new PmphGroupMember(new Long(r.nextInt(200)), new Long(r.nextInt(200)),true,true, true,
    			"jadskjdkas", null, null)  ;
    	logger.info("---PmphGroupMemberService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addPmphGroupMember(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setDisplayName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updatePmphGroupMember(testPar).toString());
    	//删除
    	logger.info(testService.deletePmphGroupMemberById(1L).toString());
    	//查询
    	logger.info(testService.getPmphGroupMemberById(2L).toString());
    	
    }
    
    
    
}





