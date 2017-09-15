package com.bc.pmpheep.back.service.test;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.bc.pmpheep.back.po.OrgMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgMessageService;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgMessageSeviceTest extends BaseTest {
	Logger l = LoggerFactory.getLogger(OrgMessageSeviceTest.class);
	
	@Resource
	private OrgMessageService orgMessageService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void addArea() throws Exception {
    	OrgMessage a=new OrgMessage("测试",0);
    	orgMessageService.addOrgMessage(a);
    	l.info("---OrgMessage--------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("---OrgMessage---------------------------------修改-------------------------------------------");
    	a.setMsgCode("ceshiwwwwwwww"+a.getId());
    	l.info(orgMessageService.updateOrgMessage(a).toString());
    	a.setId(3L);
    	l.info("---OrgMessage---------------------------------删除-------------------------------------------");
    	l.info(orgMessageService.deleteOrgMessageById(1L).toString());
    	l.info("---OrgMessage--------------------------------查询-------------------------------------------");
    	l.info(orgMessageService.getOrgMessageById(2L).toString());
    }
    
}





