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
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAdd() {
		WriterRole writerRole=this.addWriterRole();
		Assert.assertNotNull("是否添加成功", writerRole.getId());
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDelete() {
		WriterRole writerRole=this.addWriterRole();
		int num = -1;
		num = writerRoleService.delete(writerRole.getId());
		Assert.assertTrue("是否删除", num > 0 ? true : false);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteRoleAndResource() {
		WriterRole writerRole=this.addWriterRole();
		List<Long> ids=new ArrayList<>();
		int num=0;
		ids.add(writerRole.getId());
		writerRoleService.addRoleResource(writerRole.getId(), ids);
		num = writerRoleService.deleteRoleAndResource(ids);
		Assert.assertTrue("删除失败", num > 0 ? true : false);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdate() {
		WriterRole writerRole=this.addWriterRole();
		int num = -1;
		WriterRole writerRole2 = new WriterRole("角色2", false, null, null, null, null);
		writerRole2.setId(writerRole.getId());
		num = writerRoleService.update(writerRole2);
		Assert.assertTrue("是否更新成功", num > 0 ? true : false);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGet() {
		WriterRole writerRole=this.addWriterRole();
		Assert.assertNotNull("是否查询成功", writerRoleService.get(writerRole.getId()));
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListRole() {
		WriterRole writerRole=this.addWriterRole();
		List<WriterRole> list = writerRoleService.getListRole(null);
		Assert.assertNotNull("是否查询成功", list);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListRoleResource() {
		WriterRole writerRole=this.addWriterRole();
		List<WriterPermission> list = writerRoleService.getListRoleResource(writerRole.getId());
		Assert.assertNotNull("是否查询成功", list);
		List<WriterRolePermission> list1 = writerRoleService.getListWriterRolePermission(writerRole.getId());
		Assert.assertNotNull("是否查询成功", list1);
		List<Long> ids = writerRoleService.getListPmphWriterPermissionIdByRoleId(writerRole.getId());
		Assert.assertNotNull("是否查询成功", ids);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddUserRole() {
		WriterRole writerRole=this.addWriterRole();
		int num = -1;
		num = writerRoleService.addUserRole(writerRole.getId(), writerRole.getId());
		Assert.assertNotNull("是否添加成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetUserRole() {
		int num = -1;
		num = writerRoleService.addUserRole(1L, 2L);
		WriterUserRole writerUserRole = writerRoleService.getUserRole(1L, 2L);
		Assert.assertNotNull("是否查询成功", writerUserRole);
		num = writerRoleService.deleteUserRole(1L, 2L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		num = writerRoleService.deleteUserRoles(1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteUserRole() {
		int num = -1;
		writerRoleService.addUserRole(1L, 2L);
		num = writerRoleService.deleteUserRole(1L, 2L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		int result = -1;
		result = writerRoleService.deleteUserRoles(1L);
		Assert.assertNotNull("是否删除成功", result >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddRoleResource() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		Integer  writerUserRole= writerRoleService.addRoleResource(new Long(1L), permissionIds);
		Assert.assertNotNull("是否添加成功", num > 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetResourceRole() {
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		writerRoleService.addRoleResource(new Long(2L), permissionIds);
		WriterRolePermission writerRolePermission = writerRoleService.getResourceRole(2L,1L);
		Assert.assertNotNull("是否查询成功", writerRolePermission);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteRoleResource() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		writerRoleService.addRoleResource(2L, permissionIds);
		num = writerRoleService.deleteRoleResource(2L, 1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteRoleResourceByRoleId() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		writerRoleService.addRoleResource(2L, permissionIds);
		num = writerRoleService.deleteRoleResourceByRoleId(2L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		permissionIds.add(1L);
		num = writerRoleService.deleteRoleAndUser(permissionIds);
		Assert.assertNotNull("是否删除成功", num >= 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteRoleAndUser() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		writerRoleService.addRoleResource(2L, permissionIds);
		num = writerRoleService.deleteRoleAndUser(permissionIds);
		Assert.assertNotNull("是否删除成功", num >= 0);
	}
	private WriterRole addWriterRole(){
		WriterRole writerRole=writerRoleService.add(new WriterRole("角色", false, null, null, null, null));
		return writerRole;
	}
}
