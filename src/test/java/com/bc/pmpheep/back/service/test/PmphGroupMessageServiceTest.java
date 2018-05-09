package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.service.PmphGroupMessageService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.vo.PmphGroupMessageVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphGroupMessageServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupMessageServiceTest.class);

	@Resource
	private PmphGroupMessageService pmphGroupMessageService;
	Random random = new Random();
	PmphGroupMessage pmphGroupMessage = new PmphGroupMessage(new Long(random.nextInt(200)),
			new Long(random.nextInt(200)), "String msgContent");

	@Test
	public void testAddPmphGroupMessage() {
		pmphGroupMessageService.addPmphGroupMessage(pmphGroupMessage);
		Assert.assertTrue("添加失败", pmphGroupMessage.getId() > 0);
	}

	@Test
	public void testUpdatePmphGroupMessage() {
		pmphGroupMessageService.addPmphGroupMessage(pmphGroupMessage);
		pmphGroupMessage.setMsgContent(String.valueOf(random.nextInt(200)));
		Assert.assertTrue("更新失败", pmphGroupMessageService.updatePmphGroupMessage(pmphGroupMessage) > 0);
	}

	@Test
	public void testGetPmphGroupMessageById() {
		pmphGroupMessageService.addPmphGroupMessage(pmphGroupMessage);
		Assert.assertNotNull("获取数据失败", pmphGroupMessageService.getPmphGroupMessageById(pmphGroupMessage.getId()));
	}

	@Test
	public void testListPmphGroupMessage() {
		pmphGroupMessageService.addPmphGroupMessage(pmphGroupMessage);
		PageParameter<PmphGroupMessageVO> pageParameter = new PageParameter<>(1, 20);
		PmphGroupMessageVO pmphGroupMessageVO = new PmphGroupMessageVO();
		pmphGroupMessageVO.setGmtCreate(DateUtil.getCurrentTime());
		pmphGroupMessageVO.setGroupId(pmphGroupMessage.getGroupId());
		pageParameter.setParameter(pmphGroupMessageVO);
//		Assert.assertNotNull("获取数据失败", pmphGroupMessageService.listPmphGroupMessage(pageParameter, req));
	}

	@Test
	public void testDeletePmphGroupMessageByGroupId() {
		pmphGroupMessageService.addPmphGroupMessage(pmphGroupMessage);
		Assert.assertTrue("删除失败",
				pmphGroupMessageService.deletePmphGroupMessageByGroupId(pmphGroupMessage.getGroupId()) >= 0);
	}

}
