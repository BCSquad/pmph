package com.bc.pmpheep.back.servicetest;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.po.OrgUserMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgUserMessageService;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgUserMessageSeviceTest extends BaseTest {
	Logger l = LoggerFactory.getLogger(OrgUserMessageSeviceTest.class);
	
	@Resource
	private OrgUserMessageService testService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void testOrgUserMessage() throws Exception {
    	OrgUserMessage a=new OrgUserMessage(3L, 2L,true,true);
    	testService.addOrgUserMessage(a);
    	l.info("---OrgUserMessage--------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("---OrgUserMessage---------------------------------修改-------------------------------------------");
    	a.setMsgId(999L);
    	l.info(testService.updateOrgUserMessageById(a).toString());
    	a.setId(2L);
    	
    	l.info("---OrgUserMessage---------------------------------删除-------------------------------------------");
    	l.info(testService.deleteOrgUserMessageById(a).toString());
    	l.info("---OrgUserMessage--------------------------------查询-------------------------------------------");
    	l.info(testService.getOrgUserMessageById(new OrgUserMessage(1L)).toString());
    }
    
}





