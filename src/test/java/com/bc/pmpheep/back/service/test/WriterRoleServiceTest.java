package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 * 
 */
public class WriterRoleServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterRoleServiceTest.class);

	@Resource
	WriterRoleService writerRoleService;
	
	WriterRole writerRole = new WriterRole();
	
	List<Long> ids = new ArrayList<Long>();
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAdd() {
		writerRole.setRoleName("qi");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDelete() {
		int num = -1;
		writerRole.setRoleName("qi");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
		Long id = writerRole.getId();
		num = writerRoleService.delete(id);
		Assert.assertTrue("是否删除", num > 0 ? true : false);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteRoleAndResource() {
		int num=0;
		ids.add(1L);
		num = writerRoleService.deleteRoleAndResource(ids);
		Assert.assertTrue("删除失败", num > 0 ? true : false);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdate() {
		int num = -1;
		writerRole.setRoleName("123");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		WriterRole writerRole2 = new WriterRole();
		writerRole2.setIsDisabled(true);
		writerRole2.setNote("35324354321");
		writerRole2.setRoleName("123");
		writerRole2.setSort(22);
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
		writerRole2.setId(writerRole.getId());
		num = writerRoleService.update(writerRole2);
		Assert.assertTrue("是否更新成功", num > 0 ? true : false);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGet() {
		writerRole.setRoleName("qi");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		WriterRole writerRole2 = new WriterRole();
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
		Long id = writerRole.getId();
		writerRole2 = writerRoleService.get(id);
		Assert.assertNotNull("是否查询成功", writerRole2);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListRole() {
		List<WriterRole> list = writerRoleService.getListRole(null);
		Assert.assertNotNull("是否查询成功", list);
		logger.info("查询到了{}", list.toString());
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListRoleResource() {
		List<WriterPermission> list = writerRoleService.getListRoleResource(1L);
		Assert.assertNotNull("是否查询成功", list);
		List<WriterRolePermission> list1 = writerRoleService.getListWriterRolePermission(1L);
		Assert.assertNotNull("是否查询成功", list1);
		List<Long> ids = writerRoleService.getListPmphWriterPermissionIdByRoleId(1L);
		Assert.assertNotNull("是否查询成功", ids);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddUserRole() {
		int num = -1;
		num = writerRoleService.addUserRole(1L, 2L);
		Assert.assertNotNull("是否添加成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteUserRole() {
		int num = -1;
		num = writerRoleService.addUserRole(1L, 2L);
		Assert.assertNotNull("是否添加成功", num >= 0);
		WriterUserRole writerUserRole = writerRoleService.getUserRole(1L, 2L);
		Assert.assertNotNull("是否查询成功", writerUserRole);
		num = writerRoleService.deleteUserRole(1L, 2L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		num = writerRoleService.deleteUserRoles(1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddRoleResource() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		num = writerRoleService.addRoleResource(2L, permissionIds);
		Assert.assertNotNull("是否添加成功", num >= 0);
		WriterRolePermission writerRolePermission = writerRoleService.getResourceRole(42L, 1L);
		Assert.assertNotNull("是否查询成功", writerRolePermission);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteRoleResource() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		writerRoleService.addRoleResource(2L, permissionIds);
		num = writerRoleService.deleteRoleResource(42L, 1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		num = writerRoleService.deleteRoleResourceByRoleId(1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		ids.add(1L);
		num = writerRoleService.deleteRoleAndUser(ids);
		Assert.assertNotNull("是否删除成功", num >= 0);
	}
}
