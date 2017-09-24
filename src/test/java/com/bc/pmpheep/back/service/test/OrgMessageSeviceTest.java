package com.bc.pmpheep.back.service.test;
import javax.annotation.Resource;

import org.junit.Assert;
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
    	OrgMessage orgMessage=new OrgMessage("测试",0);
    	orgMessageService.addOrgMessage(orgMessage);
    	logger.info("---OrgMessage---------------------------------------------------------------------------");
    	Assert.assertTrue("添加失败",orgMessage.getId() > 0 );
    	orgMessage.setMsgCode("ceshiwwwwwwww"+orgMessage.getId());
    	Assert.assertTrue("更新失败",orgMessageService.updateOrgMessage(orgMessage) > 0 );
    	orgMessage.setId(3L);
    	Assert.assertTrue("删除失败",orgMessageService.deleteOrgMessageById(1L) >= 0 );
    	Assert.assertNotNull("获取数据失败",orgMessageService.getOrgMessageById(2L));
    }
    
}





