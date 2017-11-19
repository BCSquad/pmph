package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.service.PmphRolePermissionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphRolePermissionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphRolePermissionServiceTest.class);

	@Resource
	private PmphRolePermissionService pmphRolePermissionService;
	Random random = new Random();
	PmphRolePermission pmphRolePermission = new PmphRolePermission(new Long(random.nextInt(200)),
			new Long(random.nextInt(200)));

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddPmphRolePermission() {
		pmphRolePermissionService.addPmphRolePermission(pmphRolePermission);
		Assert.assertNotNull("是否添加成功", pmphRolePermission.getId());
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdatePmphRolePermission() {
		pmphRolePermissionService.addPmphRolePermission(pmphRolePermission);
		pmphRolePermission.setPermissionId(new Long(random.nextInt(200)));
		Integer aInteger = pmphRolePermissionService.updatePmphRolePermission(pmphRolePermission);
		Assert.assertTrue("更新失败", aInteger > 0);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeletePmphRolePermissionById() {
		pmphRolePermissionService.addPmphRolePermission(pmphRolePermission);
		Integer bInteger = pmphRolePermissionService.deletePmphRolePermissionById(pmphRolePermission.getId());
		Assert.assertTrue("删除失败", bInteger > 0);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetPmphRolePermissionById() {
		pmphRolePermissionService.addPmphRolePermission(pmphRolePermission);
		PmphRolePermission pp = pmphRolePermissionService.getPmphRolePermissionById(pmphRolePermission.getId());
		Assert.assertNotNull("是否获取数据", pp);

	}
}
