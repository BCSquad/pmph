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

import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.service.DecLastPositionService;
import com.bc.pmpheep.back.util.Const;
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
	@Rollback(Const.ISROLLBACK)
	public void testAddDecLastPosition(){
		DecLastPosition decLastPosition = new DecLastPosition();
		decLastPosition.setDeclarationId(23L);
		decLastPosition.setMaterialName("管理心理学");
		decLastPosition.setPosition(1);
		decLastPosition.setNote("国家级教授");
		decLastPosition.setSort(37);
		decLastPosition = decLastPositionService.addDecLastPosition(decLastPosition);
		Assert.assertTrue("添加数据失败", decLastPosition.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecLastPosition(){
		long id = add().getId();
		Integer count = decLastPositionService.deleteDecLastPositionById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecLastPosition(){
		DecLastPosition decLastPosition = add();
		decLastPosition.setDeclarationId(3L);
		decLastPosition.setMaterialName("心理统计学");
		Integer count = decLastPositionService.updateDecLastPosition(decLastPosition);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecLastPosition(){
		long id = add().getId();
		DecLastPosition decLastPosition = decLastPositionService.getDecLastPositionById(id);
		Assert.assertNotNull("获取作家上套教材参编情况失败", decLastPosition);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecLastPosition(){
		add();
		List<DecLastPosition> list = new ArrayList<>();
		list = decLastPositionService.getListDecLastPositionByDeclarationId(5L);
		Assert.assertTrue("获取作家上套教材参编情况集合失败", list.size() > 1);
	}
	
	private DecLastPosition add(){
		DecLastPosition decLastPosition = new DecLastPosition();
		decLastPosition.setDeclarationId(5L);
		decLastPosition.setMaterialName("心理诊断学");
		decLastPosition.setPosition(2);
		decLastPosition.setNote("研究人员");
		decLastPosition.setSort(25);
		decLastPositionService.addDecLastPosition(decLastPosition);
		DecLastPosition decLastPosition2 = new DecLastPosition(2L, "神经科学", 1, null, 12);
		decLastPositionService.addDecLastPosition(decLastPosition2);
		DecLastPosition decLastPosition3 = new DecLastPosition(5L, "人格心理学", 1, null, null);
		decLastPositionService.addDecLastPosition(decLastPosition3);
		return decLastPosition3;
	}
}
