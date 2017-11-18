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

import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.service.DecEduExpService;
import com.bc.pmpheep.back.util.Const;
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
	@Rollback(Const.ISROLLBACK)
	public void testAddDecEduExp(){
		DecEduExp decEduExp = new DecEduExp(8L, "第四军医大学", "脑科学", "博士", "博士", "2005-08", "2008-06", 10);
		decEduExp = decEduExpService.addDecEduExp(decEduExp);
		Assert.assertTrue("数据添加失败", decEduExp.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecEduExp(){
		long id = add().getId();
		Integer count = decEduExpService.deleteDecEduExpById(id);
		Assert.assertTrue("删除失败", count > 0);
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecEduExp(){
		DecEduExp decEduExp = add();
		decEduExp.setMajor("幸福心理学");
		decEduExp.setNote("专业选修");
		Integer count = decEduExpService.updateDecEduExp(decEduExp);
		Assert.assertTrue("更新数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecEduExp(){
		long id = add().getId();
		DecEduExp decEduExp = decEduExpService.getDecEduExpById(id);
		Assert.assertNotNull("获取学习经历相关信息失败", decEduExp);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecEduExp(){
		add();
		List<DecEduExp> list = new ArrayList<>();
		list = decEduExpService.getListDecEduExpByDeclarationId(3L);
		Assert.assertTrue("获取学习经历信息集合失败", list.size() > 1);
	}
	
	private DecEduExp add(){
		DecEduExp decEduExp = new DecEduExp();
		decEduExp.setDeclarationId(3L);
		decEduExp.setSchoolName("第三军医大学");
		decEduExp.setMajor("心理诊断学");
		decEduExp.setDegree("硕士研究生");
		decEduExp.setNote("精神分析流派");
		decEduExp.setDateBegin("1997-09");
		decEduExp.setDateEnd("2000-06");
		decEduExp.setSort(10);
		decEduExpService.addDecEduExp(decEduExp);
		DecEduExp decEduExp2 = new DecEduExp(5L, "首都医科大学", "神经学", "博士", "博士研究生", "2002-09", "2004-06", 15);
		decEduExpService.addDecEduExp(decEduExp2);
		DecEduExp decEduExp3 = new DecEduExp(3L, "北京师范大学", "发展心理学", "博士", "精神分析学派", "2001-09", "2003-06", 20);
		decEduExpService.addDecEduExp(decEduExp3);
		return decEduExp3;
	}
}
