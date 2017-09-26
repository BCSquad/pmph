/**
 * 
 */
package com.bc.pmpheep.back.service.test;


import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.DeclarationService;
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
	public void test() {
           logger.info("------------作家申报表------------");
           Declaration declaration = new Declaration(99L, 5L, null, (short)2, null, (short)18, null, null, null, null, null, null, null, (short)1, null, null, null, 13L, (short)1, 135L, null, (short)1, null, Boolean.valueOf("0"), Boolean.valueOf("0"));
           declarationService.addDeclaration(declaration);
           Assert.assertTrue("添加数据失败", declaration.getId() > 0);
           logger.info("------数据添加成功--------");
           declaration.setRealname("POW");
           Assert.assertTrue("更新失败", declarationService.updateDeclaration(declaration)>0);
           logger.info("------数据更新成功---------");
           Assert.assertNotNull("获取信息失败", declarationService.getDeclarationById(5L));
           logger.info("------获取单条信息成功-------");
           Assert.assertTrue("获取信息集合失败", declarationService.getDeclarationByMaterialId(99L).size()>0);
           logger.info("---------获取信息集合成功---------");
           Assert.assertTrue("删除数据失败", declarationService.deleteDeclarationById(3L) >= 0);
           logger.info("--------测试完成---------");
	}
}
