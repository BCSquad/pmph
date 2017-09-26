/**
 * 
 */
package com.bc.pmpheep.back.service.test;


import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.service.DecTextbookService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecTextbookService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午9:24:19
 */
public class DecTextbookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecTextbookServiceTest.class);
	@Resource
	DecTextbookService decTextbookService;

	@Test
	public void test() {
		Date date = new Date();
		DecTextbook decTextbook = new DecTextbook(19L, "心理诊断学", (short) 2,
				(short) 3, "科学出版社", date, "ISBN-15640", "必修", 10);
		decTextbookService.addDecTextbook(decTextbook);
		Assert.assertTrue("添加数据失败", decTextbook.getId() > 0);
		logger.info("--------数据添加成功---------");
		decTextbook.setIsbn("ISBN-95786");
		Assert.assertTrue("数据更新失败",
				decTextbookService.updateDecTextbook(decTextbook) > 0);
		logger.info("---------数据更新成功----------");
		Assert.assertNotNull("获取数据失败",
				decTextbookService.getDecTextbookById(3L));
		logger.info("---------获取单条数据成功----------");
		Assert.assertTrue("获取数据集合失败", decTextbookService
				.getListDecTextbookByDeclarationId(19L).size() > 0);
		logger.info("--------获取数据集合成功----------");
		Assert.assertTrue("删除数据失败",
				decTextbookService.deleteDecTextbookById(3L) >= 0);
		logger.info("-----------测试成功------------");
	}
}
