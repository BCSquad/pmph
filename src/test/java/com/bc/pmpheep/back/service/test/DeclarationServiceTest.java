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

import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DeclarationService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 下午3:24:59
 */
public class DeclarationServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DeclarationServiceTest.class);
	@Resource
	DeclarationService declarationService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDeclaration(){
		Declaration declaration = new Declaration();
		declaration.setMaterialId(5L);
		declaration.setUserId(6L);
		declaration = declarationService.addDeclaration(declaration);
		Assert.assertTrue("添加数据失败", declaration.getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteDeclaration(){
		long id = add().getId();
		Integer count = declarationService.deleteDeclarationById(id);
		Assert.assertTrue("删除数据失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateDeclaration(){
		Declaration declaration = add();
		declaration.setMaterialId(10L);
		declaration.setUserId(9L);
		Integer count = declarationService.updateDeclaration(declaration);
		Assert.assertTrue("数据更新失败", count > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDeclaration(){
		long id = add().getId();
		Declaration declaration = declarationService.getDeclarationById(id);
		Assert.assertNotNull("获取作家申报表信息失败", declaration);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListDeclaration(){
		add();
		List<Declaration> list = new ArrayList<>();
		list = declarationService.getDeclarationByMaterialId(2L);
		Assert.assertTrue("获取作家申报表信息集合失败", list.size() > 0);
	}
	
	private Declaration add(){
		Declaration declaration = new Declaration();
		declaration.setMaterialId(2L);
		declaration.setUserId(1L);
		declarationService.addDeclaration(declaration);
		Declaration declaration2 = new Declaration(2L, 2L);
		declarationService.addDeclaration(declaration2);
		Declaration declaration3 = new Declaration(1L, 3L);
		declarationService.addDeclaration(declaration3);
		return declaration3;
	}
}
