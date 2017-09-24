package com.bc.pmpheep.back.service.test;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.OrgMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgMessageService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgMessageSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgMessageSeviceTest.class);
	
	@Resource
	private OrgMessageService orgMessageService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void addArea()  {
    	OrgMessage a=new OrgMessage("测试",0);
    	orgMessageService.addOrgMessage(a);
    	logger.info("---OrgMessage---------------------------------------------------------------------------");
    	logger.info(a.toString());
    	a.setMsgCode("ceshiwwwwwwww"+a.getId());
    	logger.info(orgMessageService.updateOrgMessage(a).toString());
    	a.setId(3L);
    	logger.info(orgMessageService.deleteOrgMessageById(1L).toString());
    	logger.info(orgMessageService.getOrgMessageById(2L).toString());
    }
    
}





