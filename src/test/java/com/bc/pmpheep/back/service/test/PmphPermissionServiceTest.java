package com.bc.pmpheep.back.service.test;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphPermissionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphPermissionServiceTest.class);

	@Resource
	private PmphPermissionService pmphPermissionService;

	Random random = new Random();
	PmphPermission pmphPermission = new PmphPermission(new Long(random.nextInt(200)),
			String.valueOf(random.nextInt(200)), String.valueOf(random.nextInt(200)),
			String.valueOf(random.nextInt(200)), String.valueOf(random.nextInt(200)), true,
			String.valueOf(random.nextInt(200)), random.nextInt(200), null, null);

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddPmphPermission() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		Assert.assertNotNull("是否添加成功", pmphPermission.getId());
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdatePmphPermissionById() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		pmphPermission.setMenuName(String.valueOf(random.nextInt(200)));
		Integer aInteger = pmphPermissionService.updatePmphPermissionById(pmphPermission);
		Assert.assertTrue("更新失败", aInteger > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeletePmphPermissionById() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		Integer bInteger = pmphPermissionService.deletePmphPermissionById(new PmphPermission((pmphPermission.getId())));
		Assert.assertTrue("删除失败", bInteger > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDelete() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		Integer bInteger = pmphPermissionService.delete(pmphPermission.getId());
		Assert.assertTrue("删除失败", bInteger > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetPmphPermissionById() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		PmphPermission pp = pmphPermissionService.getPmphPermissionById(new PmphPermission((pmphPermission.getId())));
		Assert.assertNotNull("获取数据", pp);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGet() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		PmphPermission pp = pmphPermissionService.get(pmphPermission.getId());
		Assert.assertNotNull("获取数据", pp);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListAllParentMenu() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		List<PmphPermission> pmphPermissions = pmphPermissionService.getListAllParentMenu();
		Assert.assertNotNull("获取数据", pmphPermissions);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListResource() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		List<PmphPermission> pmphPermissions = pmphPermissionService.getListResource();
		Assert.assertNotNull("获取数据", pmphPermissions);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetListChildMenuByParentId() {
		pmphPermissionService.addPmphPermission(pmphPermission);
		List<PmphPermission> pmphPermissions = pmphPermissionService
				.getListChildMenuByParentId(pmphPermission.getParentId());
		Assert.assertNotNull("获取数据", pmphPermissions);
	}
}
