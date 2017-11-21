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

import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.service.DecTeachExpService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecTeachExpService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午9:13:51
 */
public class DecTeachExpServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecTeachExpServiceTest.class);
	@Resource
	DecTeachExpService decTeachExpService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDecTeachExp(){
		DecTeachExp decTeachExp = new DecTeachExp(5L, "吉林大学", "管理心理学", null, "2013-02",
				"至今", 14);
		decTeachExp = decTeachExpService.addDecTeachExp(decTeachExp);
		Assert.assertTrue("添加数据失败", decTeachExp.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecTeachExp(){
		long id = add().getId();
		Integer count = decTeachExpService.deleteDecTeachExpById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecTeachExp(){
		DecTeachExp decTeachExp = add();
		decTeachExp.setDeclarationId(2L);
		decTeachExp.setSort(13);
		Integer count = decTeachExpService.updateDecTeachExp(decTeachExp);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecTeachExp(){
		long id = add().getId();
		DecTeachExp decTeachExp = decTeachExpService.getDecTeachExpById(id);
		Assert.assertNotNull("获取作家教学经历信息失败", decTeachExp);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecTeachExp(){
		add();
		List<DecTeachExp> list = new ArrayList<>();
		list = decTeachExpService.getListDecTeachExpByDeclarationId(1L);
		Assert.assertTrue("获取作家教学经历信息集合失败", list.size() > 1);
	}
	
	private DecTeachExp add(){
		DecTeachExp decTeachExp = new DecTeachExp();
		decTeachExp.setDeclarationId(1L);
		decTeachExp.setSchoolName("西南大学");
		decTeachExp.setSubject("人格心理学");
		decTeachExp.setDateBegin("1997-05");
		decTeachExp.setDateEnd("至今");
		decTeachExpService.addDecTeachExp(decTeachExp);
		DecTeachExp decTeachExp2 = new DecTeachExp(1L, "北京师范大学", "发展心理学", "重点教学科目",
				"2005-06", "2012-09", 10);
		decTeachExpService.addDecTeachExp(decTeachExp2);
		DecTeachExp decTeachExp3 = new DecTeachExp(3L, "华东师范大学", "实验心理学", "国家级建设科目", 
				"2000-09", "2015-02", null);
		decTeachExpService.addDecTeachExp(decTeachExp3);
		return decTeachExp3;
	}
}
