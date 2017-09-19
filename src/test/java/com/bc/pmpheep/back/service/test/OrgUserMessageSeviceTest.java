package com.bc.pmpheep.back.service.test;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.OrgUserMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgUserMessageService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgUserMessageSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgUserMessageSeviceTest.class);
	
	@Resource
	private OrgUserMessageService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK)  
    public void testOrgUserMessage() throws Exception {
    	OrgUserMessage a=new OrgUserMessage(3L, 2L,true,true);
    	testService.addOrgUserMessage(a);
    	logger.info("---OrgUserMessageService--------------------------------------------------------------------------");
    	logger.info(a.toString());
    	a.setMsgId(999L);
    	logger.info(testService.updateOrgUserMessage(a).toString());
    	a.setId(2L);
    	logger.info(testService.deleteOrgUserMessageById(2L).toString());
    	logger.info(testService.getOrgUserMessageById(1L).toString());
    }
    
}





