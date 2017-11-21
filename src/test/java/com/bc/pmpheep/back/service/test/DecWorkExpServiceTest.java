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

import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.service.DecWorkExpService;
import com.bc.pmpheep.back.util.Const;
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
	@Rollback(Const.ISROLLBACK)
	public void testAddDecWorkExp(){
		DecWorkExp decWorkExp = new DecWorkExp();
		decWorkExp.setDeclarationId(10L);
		decWorkExp.setOrgName("华中师范大学");
		decWorkExp.setPosition("教授");
		decWorkExp.setSort(10);
		decWorkExp = decWorkExpService.addDecWorkExp(decWorkExp);
		Assert.assertTrue("添加数据失败", decWorkExp.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecWorkExp(){
		long id = add().getId();
		Integer count = decWorkExpService.deleteDecWorkExpById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecWorkExp(){
		DecWorkExp decWorkExp = add();
		decWorkExp.setNote("考职称");
		decWorkExp.setDateEnd("至今");
		decWorkExp.setSort(9);
		Integer count = decWorkExpService.updateDecWorkExp(decWorkExp);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecWorkExp(){
		long id = add().getId();
		DecWorkExp decWorkExp = decWorkExpService.getDecWorkExpById(id);
		Assert.assertNotNull("作家工作经历信息获取失败", decWorkExp);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecWorkExp(){
		add();
		List<DecWorkExp> list = new ArrayList<>();
		list = decWorkExpService.getListDecWorkExpByDeclarationId(7L);
		Assert.assertTrue("作家工作经历信息集合获取失败", list.size() > 1);
	}
	
	private DecWorkExp add(){
		DecWorkExp decWorkExp = new DecWorkExp();
		decWorkExp.setDeclarationId(7L);
		decWorkExp.setOrgName("西南大学");
		decWorkExp.setPosition("讲师");
		decWorkExp.setNote("职称变更");
		decWorkExp.setDateBegin("2010-05");
		decWorkExp.setDateEnd("2014-02");
		decWorkExp.setSort(7);
		decWorkExpService.addDecWorkExp(decWorkExp);
		DecWorkExp decWorkExp2 = new DecWorkExp(7L, "湖南师范大学", "副教授", "副院长", "2000-01",
				"2010-03", 8);
		decWorkExpService.addDecWorkExp(decWorkExp2);
		DecWorkExp decWorkExp3 = new DecWorkExp(6L, "天津师范大学", "教授", null, "1995-05", null,
				null);
		decWorkExpService.addDecWorkExp(decWorkExp3);
		return decWorkExp3;
	}
}
