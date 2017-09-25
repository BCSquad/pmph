/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.service.DecNationalPlanService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecNationalPlanService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午5:52:00
 */
public class DecNationalPlanServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecNationalPlanServiceTest.class);
	@Resource
	DecNationalPlanService decNationalPlanService;

	@Test
	public void test() {
		logger.info("-------作家主编国家级规划教材情况--------");
		DecNationalPlan decNationalPlan = new DecNationalPlan(14L, "心理学史",
				"ISBN-18956", (short) 2, "选修", 56);
		decNationalPlanService.addDecNationalPlan(decNationalPlan);
		Assert.assertTrue("添加数据失败", decNationalPlan.getId() > 0);
		logger.info("--------添加数据成功--------");
		decNationalPlan.setMaterialName("心理统计学");
		Assert.assertTrue(
				"更新失败",
				decNationalPlanService.updateDecNationalPlan(decNationalPlan) > 0);
		logger.info("--------数据更新成功--------");
		Assert.assertNotNull("获取数据失败",
				decNationalPlanService.getDecNationalPlanById(5L));
		logger.info("--------获取单条信息成功--------");
		Assert.assertTrue("获取信息集合失败", decNationalPlanService
				.getListDecNationalPlanByDeclarationId(14L).size() > 0);
		logger.info("--------获取数据集合成功---------");
		Assert.assertTrue("删除数据失败",
				decNationalPlanService.deleteDecNationalPlanById(5L) >= 0);
		logger.info("-------测试成功-----------");
	}
}
