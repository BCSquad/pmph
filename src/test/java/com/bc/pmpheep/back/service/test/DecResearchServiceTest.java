/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.service.DecResearchService;
import com.bc.pmpheep.back.util.Const;
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
	@Rollback(Const.ISROLLBACK)
	public void testAddDecResearch(){
		DecResearch decResearch = new DecResearch();
		decResearch.setDeclarationId(1L);
		decResearch.setResearchName("心理防御机制的负面作用");
		decResearch.setApprovalUnit("心理协会");
		decResearch.setAward("一等奖");
		decResearch = decResearchService.addDecResearch(decResearch);
		Assert.assertTrue("添加数据失败", decResearch.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecResearch(){
		long id = add().getId();
		Integer count = decResearchService.deleteDecResearchById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecResearch(){
		DecResearch decResearch = add();
		decResearch.setAward("二等奖");
		decResearch.setNote("脑科学与心理学研究课题");
		Integer count = decResearchService.updateDecResearch(decResearch);
		Assert.assertTrue("更新数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecResearch(){
		long id = add().getId();
		DecResearch decResearch = decResearchService.getDecResearchById(id);
		Assert.assertNotNull("获取作家科研情况信息失败",decResearch);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecResearch(){
		add();
		List<DecResearch> list = new ArrayList<>();
		list = decResearchService.getListDecResearchByDeclarationId(2L);
		Assert.assertTrue("获取作家科研情况信息集合失败", list.size() > 0);
	}
	
	private DecResearch add(){
		DecResearch decResearch = new DecResearch();
		decResearch.setDeclarationId(2L);
		decResearch.setResearchName("催眠治疗");
		decResearch.setApprovalUnit("催眠协会");
		decResearch.setAward("二等奖");
		decResearch.setNote("催眠治疗的效果研究");
		decResearch.setSort(45);
		decResearchService.addDecResearch(decResearch);
		DecResearch decResearch2 = new DecResearch(2L, "儿童智力成长", "发展心理学研究会",
				"三等奖", "智力成长的阶段性研究", null);
		decResearchService.addDecResearch(decResearch2);
		DecResearch decResearch3 = new DecResearch(9L, "脑电波活动", "脑科学协会", "一等奖", null, 1);
		decResearchService.addDecResearch(decResearch3);
		return decResearch3;
	}
}
