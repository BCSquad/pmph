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

import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.service.DecNationalPlanService;
import com.bc.pmpheep.back.util.Const;
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
	@Rollback(Const.ISROLLBACK)
	public void testAddDecNationalPlan(){
		DecNationalPlan decNationalPlan = new DecNationalPlan();
		decNationalPlan.setDeclarationId(3L);
		decNationalPlan.setMaterialName("人体解剖学");
		decNationalPlan.setIsbn("666");
		decNationalPlan.setRank(1);
		decNationalPlan.setRankText("教育部十二五");
		decNationalPlan.setNote("重点建设学科");
		decNationalPlan.setSort(1);
		decNationalPlan = decNationalPlanService.addDecNationalPlan(decNationalPlan);
		Assert.assertTrue("添加数据失败", decNationalPlan.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecNationalPlan(){
		long id = add().getId();
		Integer count = decNationalPlanService.deleteDecNationalPlanById(id);
		Assert.assertTrue("数据删除失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecNationalPlan(){
		DecNationalPlan decNationalPlan = add();
		Integer count = decNationalPlanService.updateDecNationalPlan(decNationalPlan);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecNationalPlan(){
		long id = add().getId();
		DecNationalPlan decNationalPlan = decNationalPlanService.getDecNationalPlanById(id);
		Assert.assertNotNull("获取主编国家级规划教材情况失败", decNationalPlan);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecNationalPlan(){
		add();
		List<DecNationalPlan> list = new ArrayList<>();
		list = decNationalPlanService.getListDecNationalPlanByDeclarationId(1L);
		Assert.assertTrue("获取主编国家级规划教材情况集合信息失败", list.size() > 1);
	}

	private DecNationalPlan add(){
		DecNationalPlan decNationalPlan = new DecNationalPlan();
		decNationalPlan.setDeclarationId(1L);
		decNationalPlan.setMaterialName("普通心理学");
		decNationalPlan.setIsbn("123456");
		decNationalPlan.setRank(2);
		decNationalPlan.setRankText("国家卫计委十二五");
		decNationalPlan.setSort(13);
		decNationalPlanService.addDecNationalPlan(decNationalPlan);
		DecNationalPlan decNationalPlan2 = new DecNationalPlan(1L, "社会心理学", "654321",
				1, "教育部十二五", "专业主修教材", null);
		decNationalPlanService.addDecNationalPlan(decNationalPlan2);
		DecNationalPlan decNationalPlan3 = new DecNationalPlan(2L, "变态心理学", "111", 3, "both", null, null);
		decNationalPlanService.addDecNationalPlan(decNationalPlan3);
		return decNationalPlan3;
	}
}
