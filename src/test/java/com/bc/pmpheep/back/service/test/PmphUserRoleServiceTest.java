package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphUserRoleServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphUserRoleServiceTest.class);

	@Resource
	private PmphUserRoleService testService;
	Random random = new Random();
	PmphUserRole testPar = new PmphUserRole(new Long(random.nextInt(200)), new Long(random.nextInt(200)));

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddPmphUserRole() {
		testService.addPmphUserRole(testPar);
		Assert.assertNotNull("是否保存成功", testPar.getId());
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdatePmphUserRole() {
		testService.addPmphUserRole(testPar);
		testPar.setRoleId(new Long(random.nextInt(200)));
		Integer aInteger = testService.updatePmphUserRole(testPar);
		Assert.assertTrue("是否修改成功", aInteger > 0);

	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeletePmphUserRoleById() {
		testService.addPmphUserRole(testPar);
		Integer bInteger = testService.deletePmphUserRoleById(testPar.getId());
		Assert.assertTrue("删除失败", bInteger > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() {
		testService.addPmphUserRole(testPar);
		PmphUserRole pur = testService.getPmphUserRoleById(testPar.getId());
		Assert.assertNotNull("不为空", pur);
	}

}
