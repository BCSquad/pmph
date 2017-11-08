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

	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() {
		Random random = new Random();
		PmphPermission pmphPermission = new PmphPermission(new Long(random.nextInt(200)),
				String.valueOf(random.nextInt(200)), String.valueOf(random.nextInt(200)),
				String.valueOf(random.nextInt(200)), String.valueOf(random.nextInt(200)), true,
				String.valueOf(random.nextInt(200)), random.nextInt(200), null, null);
		logger.info(
				"---PmphPermissionService 测试---------------------------------------------------------------------------------");
		// 新增
		pmphPermissionService.addPmphPermission(pmphPermission);
		Assert.assertNotNull("是否添加成功", pmphPermission.getId());
		// logger.info(testPar.toString());
		// 修改
		pmphPermission.setMenuName(String.valueOf(random.nextInt(200)));
		Integer aInteger = pmphPermissionService.updatePmphPermissionById(pmphPermission);
		Assert.assertTrue("更新失败", aInteger > 0 ? true : false);
		// logger.info(aInteger.toString());
		// 删除
		Integer bInteger = pmphPermissionService.deletePmphPermissionById(new PmphPermission((1L)));
		Assert.assertTrue("删除失败", bInteger > 0 ? true : false);
		bInteger = pmphPermissionService.delete(1L);
		Assert.assertTrue("删除失败", bInteger > 0 ? true : false);
		// logger.info(bInteger.toString());
		// 查询
		PmphPermission pp = pmphPermissionService.getPmphPermissionById(new PmphPermission((2L)));
		Assert.assertNotNull("获取数据", pp);
		pp = pmphPermissionService.get(2L);
		Assert.assertNotNull("获取数据", pp);
		List<PmphPermission> pmphPermissions = pmphPermissionService.getListAllParentMenu();
		Assert.assertNotNull("获取数据", pmphPermissions);
		pmphPermissions = pmphPermissionService.getListResource();
		Assert.assertNotNull("获取数据", pmphPermissions);
		pmphPermissions = pmphPermissionService.getListChildMenuByParentId(0L);
		Assert.assertNotNull("获取数据", pmphPermissions);
		// logger.info(pp.toString());

	}
}
