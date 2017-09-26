/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.service.DecAcadeService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecAcadeService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午11:26:59
 */
public class DecAcadeServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecAcadeServiceTest.class);
	@Resource
	DecAcadeService decAcadeService;

	@Test
	public void test() {
		logger.info("---------作家兼职学术组织信息测试------------");
		DecAcade decAcade = new DecAcade(10L, "心理咨询研究会", (short) 3, "会员",
				"实验室成员", 10);
		decAcadeService.addDecAcade(decAcade);
		Assert.assertTrue("添加信息失败", decAcade.getId() > 0);
		logger.info("----------添加成功---------");
		decAcade.setOrgName("行为矫正协会");
		Assert.assertTrue("更新组织信息失败",
				decAcadeService.updateDecAcade(decAcade) > 0);
		logger.info("---------更新成功---------");
		Assert.assertNotNull("获取数据失败", decAcadeService.getDecAcadeById(1L));
		logger.info("----------获取单条信息成功-----------");
		Assert.assertTrue("获取信息集合失败", decAcadeService
				.getListDecAcadeByDeclarationId(10L).size() > 0);
		logger.info("-----------获取信息集合成功------------");
		Assert.assertTrue("删除信息失败", decAcadeService.deleteDecAcadeById(1L) >= 0);
		logger.info("------------测试完成-------------");
	}
}
