/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.service.DecCourseConstructionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecCourseConstructionService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午2:21:00
 */
public class DecCourseConstructionServiceTest extends BaseTest{
	Logger logger = LoggerFactory.getLogger(DecCourseConstructionServiceTest.class);

	@Resource
	DecCourseConstructionService decCourseConstructionService;

	@Test
	public void test() {
		logger.info("---------作家精品课程建设情况信息测试------------");
		DecCourseConstruction decCourseConstruction = new DecCourseConstruction(
				9L, "管理心理学", "36", (Integer) 1, "专业必修", 8);
		decCourseConstructionService.addDecCourseConstruction(decCourseConstruction);
		Assert.assertTrue("添加数据失败", decCourseConstruction.getId()>0);
		logger.info("--------添加数据成功---------");
		decCourseConstruction.setType((Integer)72);
		Assert.assertTrue("更新失败", decCourseConstructionService.updateDecCourseConstruction(decCourseConstruction)>0);
		logger.info("---------数据更新成功----------");
		Assert.assertNotNull("获取数据失败", decCourseConstructionService.getDecCourseConstructionById(2L));
		logger.info("-------------获取单条信息成功-------------");
		Assert.assertTrue("获取信息集合失败", decCourseConstructionService.getDecCourseConstructionBydeclarationId(8L).size()>0);
		logger.info("----------获取数据集合成功-----------");
		Assert.assertTrue("删除信息失败", decCourseConstructionService.deleteDecCourseConstruction(3L) >= 0);
		logger.info("------------测试完成--------------");
	}
}
