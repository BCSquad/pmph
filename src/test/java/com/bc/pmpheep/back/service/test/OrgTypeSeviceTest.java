package com.bc.pmpheep.back.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class OrgTypeSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgTypeSeviceTest.class);

	@Resource
	private OrgTypeService orgTypeService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddOrgType() {
		OrgType orgType = new OrgType("测试", 0);
		orgTypeService.addOrgType(orgType);
		Assert.assertTrue("添加失败", orgType.getId() > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateOrgType() {
		OrgType orgType = new OrgType("测试", 0);
		orgTypeService.addOrgType(orgType);
		Assert.assertTrue("添加失败", orgType.getId() > 0);
		orgType.setTypeName("ceshiwwwwwwww" + orgType.getId());
		Assert.assertTrue("更新失败", orgTypeService.updateOrgType(orgType) > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteOrgType() {
		OrgType orgType = new OrgType("测试", 0);
		orgTypeService.addOrgType(orgType);
		Assert.assertTrue("添加失败", orgType.getId() > 0);
		Assert.assertTrue("删除失败", orgTypeService.deleteOrgTypeById(orgType.getId()) >= 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetOrgType() {
		OrgType orgType = new OrgType("测试", 0);
		orgTypeService.addOrgType(orgType);
		Assert.assertTrue("添加失败", orgType.getId() > 0);
		Assert.assertNotNull("获取数据失败", orgTypeService.getOrgType(orgType.getId()));
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListOrgType() {
		OrgType orgType = new OrgType("测试", 0);
		orgTypeService.addOrgType(orgType);
		Assert.assertTrue("添加失败", orgType.getId() > 0);
		Assert.assertNotNull("获取数据失败", orgTypeService.listOrgTypeByTypeName("测试"));
	}

}
