package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.test.BaseTest;

/**
 * PmphRole 单元测试
 * 
 * @author Administrator
 * 
 */
public class PmphRoleServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(PmphRoleServiceTest.class);

	@Autowired
	PmphRoleService roleService;
	Random random = new Random();
	PmphRole pmphRole = new PmphRole("不可能的名字" + random.nextInt(100), false, "aa", 999, null, null);

	@Test
	public void testAddPmphRole() {
		roleService.addPmphRole(pmphRole);
		Assert.assertTrue("添加失败", pmphRole.getId() > 0);
	}

	@Test
	public void testAdd() {
		Long[] ids = { 1L, 2L };
		roleService.add(pmphRole, ids);
		Assert.assertTrue("添加失败", pmphRole.getId() > 0);
	}

	@Test
	public void testAddUserRole() {
		Assert.assertTrue("添加失败", roleService.addUserRole(1L, 1L) > 0);
	}

	@Test
	public void testAddRoleResource() {
		List<Long> list = new ArrayList<>();
		list.add(1L);
		String materialId = "00000000";
		Assert.assertTrue("添加失败", roleService.addRoleResource(1L, list, materialId) > 0);
	}

	@Test
	public void testUpdate() {
		roleService.addPmphRole(pmphRole);
		pmphRole.setNote("xxa");
		Assert.assertTrue("修改失败", roleService.update(pmphRole) > 0);

	}

	@Test
	public void testDelete() {
		roleService.addPmphRole(pmphRole);
		Assert.assertTrue("删除失败", roleService.delete(pmphRole.getId()) > 0);

	}

	@Test
	public void testDeleteRoleResource() {
		Long[] ids = { 1L, 2L };
		roleService.add(pmphRole, ids);
		Assert.assertTrue("删除失败", roleService.deleteRoleResource(1L, 1L) > 0);
	}

	@Test
	public void testDeleteRoleAndResource() {
		Long[] ids = { 1L, 2L };
		roleService.add(pmphRole, ids);
		List<Long> id = new ArrayList<>();
		id.add(pmphRole.getId());
		Assert.assertTrue("删除失败", roleService.deleteRoleAndResource(id) > 0);
	}

	@Test
	public void testDeleteRoleAndUser() {
		roleService.deleteRoleResource(1L, 1L);
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		Assert.assertTrue("删除失败", roleService.deleteRoleAndUser(ids) > 0);
	}

	@Test
	public void testDeleteUserRole() {
		roleService.addUserRole(1L, 1L);
		Assert.assertTrue("删除失败", roleService.deleteUserRole(1L, 1L) > 0);
	}

	@Test
	public void testDeleteRoleResourceByRoleId() {
		Long[] ids = { 1L, 2L };
		roleService.add(pmphRole, ids);
		Assert.assertTrue("删除失败", roleService.deleteRoleResourceByRoleId(pmphRole.getId()) > 0);
	}

	@Test
	public void testDeleteUserRoles() {
		roleService.addUserRole(1L, 1L);
		Assert.assertTrue("删除失败", roleService.deleteUserRoles(1L) > 0);
	}

	@Test
	public void testGet() {
		roleService.addPmphRole(pmphRole);
		Assert.assertNotNull("获取数据失败", roleService.get(pmphRole.getId()));
	}

	@Test
	public void testGetList() {
		roleService.addPmphRole(pmphRole);
		Assert.assertNotNull("获取数据失败", roleService.getList(pmphRole.getRoleName()));
	}

	@Test
	public void testGetListPmphRolePermission() {
		roleService.addPmphRole(pmphRole);
		Assert.assertNotNull("获取数据失败", roleService.getListPmphRolePermission(pmphRole.getId()));
	}

	@Test
	public void testGetListRole() {
		roleService.addPmphRole(pmphRole);
		Assert.assertNotNull("获取数据失败", roleService.getListRole());
	}

	@Test
	public void testGetListPmphRolePermissionIdByRoleId() {
		roleService.addPmphRole(pmphRole);
		Assert.assertNotNull("获取数据失败", roleService.getListPmphRolePermissionIdByRoleId(pmphRole.getId()));
	}

	@Test
	public void testGetListRoleResource() {
		roleService.addPmphRole(pmphRole);
		Assert.assertNotNull("获取数据失败", roleService.getListRoleResource(pmphRole.getId()));
	}

	@Test
	public void testGetPmphRoleByUserId() {
		roleService.addUserRole(1L, 1L);
		Assert.assertNotNull("获取数据失败", roleService.getPmphRoleByUserId(1L));
	}

}
