package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphDepartmentSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphDepartmentSeviceTest.class);

	@Resource
	private PmphDepartmentService pmphDepartmentService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() {
		Random random = new Random();
		PmphDepartment pmphDepartment = new PmphDepartment(5L, "String path", "String dpName", random.nextInt(1000000),
				"String note");
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		logger.info(
				"---PmphDepartmentService---------------------------------------------------------------------------");
		Assert.assertTrue("添加失败", pmphDepartment.getId() > 0);
		pmphDepartment.setDpName(String.valueOf(random.nextLong()));
		Assert.assertTrue("更新失败", pmphDepartmentService.updatePmphDepartment(pmphDepartment) > 0);
		Assert.assertTrue("删除失败", pmphDepartmentService.deletePmphDepartmentById(2L) >= 0);
		Assert.assertNotNull("获取数据失败", pmphDepartmentService.getPmphDepartmentById(1L));
		PmphUserDepartmentVO departmentVO = pmphDepartmentService.listPmphDepartment(0L);
		Assert.assertNotNull("获取数据失败", departmentVO);
	}

	@Test
	public void pmphDepartmentServiceTest() {
		Long id = 0L;
		Assert.assertTrue("删除失败", pmphDepartmentService.deletePmphDepartmentBatch(id) > 0);
		String dpName = "人民";
		Assert.assertTrue("获取数据失败",
				CollectionUtil.isNotEmpty(pmphDepartmentService.listPmphUserDepartmentByDpName(dpName)));

	}
}
