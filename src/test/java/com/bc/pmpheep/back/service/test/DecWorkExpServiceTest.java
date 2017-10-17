/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.service.DecWorkExpService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecWorkExpService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午10:43:24
 */
public class DecWorkExpServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecWorkExpServiceTest.class);
	@Resource
	DecWorkExpService decWorkExpService;

	@Test
	public void test() {
		logger.info("-----------作家工作经历-----------");
        DecWorkExp decWorkExp = new DecWorkExp(27L, "西华大学", "副校长", "教育学", "1984-02", "1995-12", 10);
        decWorkExpService.addDecWorkExp(decWorkExp);
        Assert.assertTrue("添加数据失败", decWorkExp.getId()>0);
        logger.info("------数据添加成功--------");
        decWorkExp.setPosition("校长");
        Assert.assertTrue("数据更新失败", decWorkExpService.updateDecWorkExp(decWorkExp)>0);
        logger.info("--------数据更新成功--------");
        Assert.assertNotNull("获取数据失败", decWorkExpService.getDecWorkExpById(3L));
        logger.info("--------获取单条信息成功---------");
        Assert.assertTrue("获取数据集合失败", decWorkExpService.getListDecWorkExpByDeclarationId(27L).size()>0);
        logger.info("--------获取数据集合成功---------");
        Assert.assertTrue("数据删除失败", decWorkExpService.deleteDecWorkExpById(3L) >= 0);
        logger.info("---------测试成功——----------");
	}
}
