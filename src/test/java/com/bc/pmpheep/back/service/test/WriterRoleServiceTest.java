package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.service.WriterRoleService;
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
	public void add() {
		WriterRole writerRole = new WriterRole();
		writerRole.setRoleName("qi");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
		logger.info("添加了{}", writerRole.toString());
	}

	@Test
	public void delete() {
		int num = -1;
		WriterRole writerRole = new WriterRole();
		writerRole.setRoleName("qi");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
		Long id = writerRole.getId();
		num = writerRoleService.delete(id);
		Assert.assertTrue("是否删除", num > 0 ? true : false);
		logger.info("删除了{}条数据", num);
	}

	@Test
	public void deleteRoleAndResource() {
		int num = -1;
		WriterRole writerRole = new WriterRole();
		writerRole.setRoleName("qi");
		writerRole.setNote("ajsdgahsgdyajd");
		writerRole.setSort(12);
		writerRole.setIsDisabled(false);
		writerRole = writerRoleService.add(writerRole);
		Assert.assertNotNull("是否添加成功", writerRole.getId());
		Long id = writerRole.getId();
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		num = writerRoleService.deleteRoleAndResource(ids);
		Assert.assertTrue("是否删除", num > 0 ? true : false);
		logger.info("删除了{}条数据", num);
	}

	@Test
	public void update() {
		int num = -1;
		WriterRole writerRole = new WriterRole();
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
		logger.info("修改了{}条数据", num);
	}

	@Test
	public void get() {
		WriterRole writerRole = new WriterRole();
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
		logger.info("查询到了{}", writerRole2.toString());
	}

	@Test
	public void getListRole() {
		List<WriterRole> list = writerRoleService.getListRole(null);
		Assert.assertNotNull("是否查询成功", list);
		logger.info("查询到了{}", list.toString());
	}

	@Test
	public void getListRoleResource() {
		List<WriterPermission> list = writerRoleService.getListRoleResource(1L);
		Assert.assertNotNull("是否查询成功", list);
		logger.info("查询到了{}", list.toString());
		List<WriterRolePermission> list1 = writerRoleService.getListWriterRolePermission(1L);
		Assert.assertNotNull("是否查询成功", list1);
		logger.info("查询到了{}", list1.toString());
		List<Long> ids = writerRoleService.getListPmphWriterPermissionIdByRoleId(1L);
		Assert.assertNotNull("是否查询成功", ids);
		logger.info("查询到了{}", ids.toString());
	}

	@Test
	public void getUserRole() {
		int num = -1;
		num = writerRoleService.addUserRole(1L, 2L);
		Assert.assertNotNull("是否添加成功", num >= 0);
		logger.info("添加了{}条数据", num);
		WriterUserRole writerUserRole = writerRoleService.getUserRole(1L, 2L);
		Assert.assertNotNull("是否查询成功", writerUserRole);
		logger.info("查询到了{}", writerUserRole.toString());
		num = writerRoleService.deleteUserRole(1L, 2L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		logger.info("删除了{}条数据", num);
		num = writerRoleService.deleteUserRoles(1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		logger.info("删除了{}条数据", num);
	}

	@Test
	public void addRoleResource() {
		int num = -1;
		List<Long> permissionIds = new ArrayList<>();
		permissionIds.add(1L);
		num = writerRoleService.addRoleResource(2L, permissionIds);
		Assert.assertNotNull("是否添加成功", num >= 0);
		logger.info("添加了{}条数据", num);
		WriterRolePermission writerRolePermission = writerRoleService.getResourceRole(42L, 1L);
		Assert.assertNotNull("是否查询成功", writerRolePermission);
		logger.info("查询到了{}", writerRolePermission.toString());
		num = writerRoleService.deleteRoleResource(42L, 1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		logger.info("删除了{}条数据", num);
		num = writerRoleService.deleteRoleResourceByRoleId(1L);
		Assert.assertNotNull("是否删除成功", num >= 0);
		logger.info("删除了{}条数据", num);
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		num = writerRoleService.deleteRoleAndUser(ids);
		Assert.assertNotNull("是否删除成功", num >= 0);
		logger.info("删除了{}条数据", num);
	}
}
