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

import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.service.DecCourseConstructionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecCourseConstructionService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午2:21:00
 */
public class DecCourseConstructionServiceTest extends BaseTest{
	Logger logger = LoggerFactory.getLogger(DecCourseConstructionServiceTest.class);

	@Resource
	DecCourseConstructionService decCourseConstructionService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDecCourseConstruction(){
		DecCourseConstruction decCourseConstruction = new DecCourseConstruction(4L, "心理学史",
				"36", 2, "专业选修", null);
		decCourseConstruction = decCourseConstructionService.addDecCourseConstruction(decCourseConstruction);
		Assert.assertTrue("数据添加失败", decCourseConstruction.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecCourseConstruction(){
		long id = add().getId();
		Integer count = decCourseConstructionService.deleteDecCourseConstruction(id);
		Assert.assertTrue("数据删除失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecCourseConstruction(){
		DecCourseConstruction decCourseConstruction = add();
		decCourseConstruction.setNote("专业通识课");
		decCourseConstruction.setDeclarationId(3L);
		decCourseConstruction.setCourseName("神经病学");
		Integer count = decCourseConstructionService.updateDecCourseConstruction(decCourseConstruction);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecCourseConstruction(){
		long id = add().getId();
		DecCourseConstruction decCourseConstruction = decCourseConstructionService.getDecCourseConstructionById(id);
		Assert.assertNotNull("获取作家精品课程建设情况信息失败", decCourseConstruction);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecCourseConstruction(){
		add();
		List<DecCourseConstruction> list = new ArrayList<>();
		list = decCourseConstructionService.getDecCourseConstructionBydeclarationId(2L);
		Assert.assertTrue("获取作家精品课程建设情况信息集合失败", list.size() > 0);
	}
	
	private DecCourseConstruction add(){
		DecCourseConstruction decCourseConstruction = new DecCourseConstruction();
		decCourseConstruction.setDeclarationId(2L);
		decCourseConstruction.setCourseName("神经内科学");
		decCourseConstruction.setClassHour("72");
		decCourseConstruction.setType(2);
		decCourseConstruction.setNote("通识课");
		decCourseConstruction.setSort(19);
		decCourseConstructionService.addDecCourseConstruction(decCourseConstruction);
		DecCourseConstruction decCourseConstruction2 = new DecCourseConstruction(3L, "咨询心理学",
				"36", 3, null, null);
		decCourseConstructionService.addDecCourseConstruction(decCourseConstruction2);
		DecCourseConstruction decCourseConstruction3 = new DecCourseConstruction(2L, "脑科学", "72",
				1, null, 20);
		decCourseConstructionService.addDecCourseConstruction(decCourseConstruction3);
		return decCourseConstruction3;
	}
}
