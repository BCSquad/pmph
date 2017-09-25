/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.service.DecLastPositionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecLastPositionService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午5:40:26
 */
public class DecLastPositionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecLastPositionServiceTest.class);
	@Resource
	DecLastPositionService decLastPositionService;

	@Test
	public void test() {
          logger.info("----------作家上套教材参编情况----------");
          DecLastPosition decLastPosition = new DecLastPosition(7L, "心理诊断学", (short)1, "参编", 5);
          decLastPositionService.addDecLastPosition(decLastPosition);
          Assert.assertTrue("添加数据失败", decLastPosition.getId()>0);
          logger.info("---------添加数据成功----------");
          decLastPosition.setMaterialName("心理咨询技术");
          Assert.assertTrue("数据更新失败", decLastPositionService.updateDecLastPosition(decLastPosition)>0);
          logger.info("-------数据更新成功----------");
          Assert.assertNotNull("获取数据失败", decLastPositionService.getDecLastPositionById(3L));
          logger.info("-------单条信息获取成功---------");
          Assert.assertTrue("数据集合获取失败", decLastPositionService.getListDecLastPositionByDeclarationId(7L).size()>0);
          logger.info("-------数据集合获取成功-------");
          Assert.assertTrue("数据删除失败", decLastPositionService.deleteDecLastPositionById(3L) >= 0);
          logger.info("--------测试成功----------");
	}
}
