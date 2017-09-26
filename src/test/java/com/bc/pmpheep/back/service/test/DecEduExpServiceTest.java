/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.service.DecEduExpService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecEduExpService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午3:12:54
 */
public class DecEduExpServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecEduExpServiceTest.class);
	@Resource
	DecEduExpService decEduExpService;

	@Test
	public void test() {
        logger.info("---------作家学习经历--------");
        DecEduExp decEduExp = new DecEduExp(8L, "第四军医大学", "脑科学", "博士", "博士", "2005-08-06", "2008-06-05", 10);
        decEduExpService.addDecEduExp(decEduExp);
        Assert.assertTrue("数据添加失败", decEduExp.getId()>0);
        logger.info("--------数据添加成功---------");
        decEduExp.setSchoolName("第三军医大学");
        Assert.assertTrue("更新失败", decEduExpService.updateDecEduExp(decEduExp)>0);
        logger.info("-------数据更新成功--------");
        Assert.assertNotNull("获取数据失败", decEduExpService.getDecEduExpById(3L));
        logger.info("--------获取单条信息成功--------");
        Assert.assertTrue("获取信息集合失败", decEduExpService.getListDecEduExpByDeclarationId(8L).size()>0);
        logger.info("--------获取信息集合成功---------");
        Assert.assertTrue("删除失败", decEduExpService.deleteDecEduExpById(3L) >= 0);
        logger.info("----------测试完成----------");
	}
}
