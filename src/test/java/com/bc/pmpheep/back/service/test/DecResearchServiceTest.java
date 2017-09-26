/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.service.DecResearchService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecResearchService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午8:59:23
 */
public class DecResearchServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecResearchServiceTest.class);
	@Resource
	DecResearchService decResearchService;

	@Test
	public void test() {
		logger.info("---------作家科研情况---------");
        DecResearch decResearch = new DecResearch(13L, "儿童智力发展理论研究", "中国心理学研究会", "创新一等奖", "发展心理学研究", 10);
        decResearchService.addDecResearch(decResearch);
        Assert.assertTrue("数据添加失败", decResearch.getId()>0);
        logger.info("------数据添加成功---------");
        decResearch.setAward("科研三等奖");
        Assert.assertTrue("数据更新失败", decResearchService.updateDecResearch(decResearch)>0);
        logger.info("-------数据更新成功-------");
        Assert.assertNotNull("获取数据失败", decResearchService.getDecResearchById(4L));
        logger.info("------获取单条信息成功----------");
        Assert.assertTrue("获取数据集合失败", decResearchService.getListDecResearchByDeclarationId(13L).size()>0);
        logger.info("--------获取数据集合成功---------");
        Assert.assertTrue("删除失败", decResearchService.deleteDecResearchById(4L) >= 0);
        logger.info("----------测试成功----------");
	}
}
