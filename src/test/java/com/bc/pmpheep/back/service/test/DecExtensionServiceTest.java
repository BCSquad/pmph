package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.service.DecExtensionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 *
 * @author Mryang
 *
 * @createDate 2017年11月16日 下午1:49:38
 *
 */
@SuppressWarnings("all")
public class DecExtensionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecExtensionServiceTest.class);
	@Resource
	DecExtensionService decExtensionService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDecExtension(){
		DecExtension decExtension = new DecExtension(6L, 3L, null);
		decExtension = decExtensionService.addDecExtension(decExtension);
		Assert.assertTrue("添加数据失败", decExtension.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecExtension(){
		long id = add().getId();
		Integer count = decExtensionService.deleteDecExtension(id);
		Assert.assertTrue("根据id删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDecExtensionByExtensionId(){
		add();
		Integer count = decExtensionService.deleteDecExtensionByExtensionId(1L);
		Assert.assertTrue("根据扩展项id删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDecExtension(){
		DecExtension decExtension = add();
		decExtension.setDeclarationId(4L);
		decExtension.setExtensionId(4L);
		decExtension.setContent(null);
		Integer count = decExtensionService.updateDecExtension(decExtension);
		Assert.assertTrue("更新数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecExtension(){
		long id = add().getId();
		DecExtension decExtension = decExtensionService.getDecExtensionById(id);
		Assert.assertNotNull("获取作家扩展项填报信息失败", decExtension);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecExtensionsByExtensionId(){
		add();
		List<DecExtension> list = new ArrayList<>();
		list = decExtensionService.getListDecExtensionsByExtensionId(3L);
		boolean flag = list.size() > 0;
		list = decExtensionService.getListDecExtensionsByExtensionId(5L);
		boolean flag2 = list.size() > 0;
		Assert.assertTrue("根据扩展项id获取作家扩展项填报信息集合失败", flag && flag2);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDecExtensionsByDeclarationId(){
		add();
		List<DecExtension> list = new ArrayList<>();
		list = decExtensionService.getListDecExtensionsByDeclarationId(2L);
		boolean flag = list.size() > 1;
		list = decExtensionService.getListDecExtensionsByDeclarationId(1L);
		boolean flag2 = list.size() > 1;
		Assert.assertTrue("根据申报表id获取作家扩展项填报信息集合失败", flag && flag2);
	}
	
	private DecExtension add(){
		DecExtension decExtension = new DecExtension();
		decExtension.setExtensionId(3L);
		decExtension.setDeclarationId(2L);
		decExtension.setContent("心理访谈特邀嘉宾");
		decExtensionService.addDecExtension(decExtension);
		DecExtension decExtension2 = new DecExtension(3L, 3L, "西南地区心理学研究带头人");
		decExtensionService.addDecExtension(decExtension2);
		DecExtension decExtension3 = new DecExtension(5L, 1L, "催眠治疗研究人员");
		decExtensionService.addDecExtension(decExtension3);
		DecExtension decExtension4 = new DecExtension(5L, 2L, "一级心理咨询师");
		decExtensionService.addDecExtension(decExtension4);
		DecExtension decExtension5 = new DecExtension(1L, 1L, "北京师范大学博士生导师");
		decExtensionService.addDecExtension(decExtension5);
		return decExtension5;
	}
}
