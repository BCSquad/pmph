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
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupMessageService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphGroupMessageServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupMessageServiceTest.class);
	
	@Resource
	private PmphGroupMessageService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	PmphGroupMessage testPar=new PmphGroupMessage(new Long(r.nextInt(200)), new Long(r.nextInt(200)), "String msgContent", null);  ;
    	logger.info("---PmphGroupMessageService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addPmphGroupMessage(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setMsgContent(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updatePmphGroupMessage(testPar).toString());
    	//删除
    	logger.info(testService.deletePmphGroupMessageById(1L).toString());
    	//查询
    	logger.info(testService.getPmphGroupMessageById(2L).toString());
    	
    }
    
    
    
}





