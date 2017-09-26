package com.bc.pmpheep.back.service.test;
import javax.annotation.Resource;

import org.junit.Assert;
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
	private OrgUserMessageService orgUserMessageService;
	
    @Test
    @Rollback(Const.ISROLLBACK)  
    public void testOrgUserMessage()  {
    	OrgUserMessage orgUserMessage=new OrgUserMessage(3L, 2L,true,true);
    	orgUserMessageService.addOrgUserMessage(orgUserMessage);
    	logger.info("---OrgUserMessageService--------------------------------------------------------------------------");
    	Assert.assertTrue("添加失败",orgUserMessage.getId() > 0 );
    	orgUserMessage.setMsgId(999L);
    	Assert.assertTrue("更新失败", orgUserMessageService.updateOrgUserMessage(orgUserMessage) > 0 );
    	Assert.assertTrue("删除失败",orgUserMessageService.deleteOrgUserMessageById(2L)  >= 0 );
    	Assert.assertNotNull("获取数据失败",orgUserMessageService.getOrgUserMessageById(1L));
    }
    
}





