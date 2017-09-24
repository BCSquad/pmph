package com.bc.pmpheep.back.service.test;


import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphUserMessage;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphUserMessageService;
import com.bc.pmpheep.back.util.Const;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphUserMessageSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphUserMessageSeviceTest.class);

	@Resource
	private PmphUserMessageService pmphUserMessageService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void addArea() {
		Random r= new Random();
		PmphUserMessage pmphUserMessage = new PmphUserMessage (new Long(String.valueOf(r.nextInt(200))),new Long(String.valueOf(r.nextInt(200))), true,false, null,null);
		pmphUserMessageService.addPmphUserMessage (pmphUserMessage);
		logger.info("----PmphUserMessageService-------------------------------------------------------------------------");
		Assert.assertTrue("添加失败",pmphUserMessage.getId() > 0 );
		pmphUserMessage.setUserId(new Long(String.valueOf(r.nextInt(200))));
		Assert.assertTrue("更新失败",pmphUserMessageService.updatePmphUserMessage(pmphUserMessage)> 0 );
		Assert.assertTrue("删除失败",pmphUserMessageService.deletePmphUserMessageById(2L)  >= 0 );
		Assert.assertNotNull("获取数据失败",pmphUserMessageService.getPmphUserMessageById(3L));
	}

}
