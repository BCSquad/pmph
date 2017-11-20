package com.bc.pmpheep.back.service.test;

import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphGroupSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupSeviceTest.class);

	@Resource
	private PmphGroupService testService;
	Random r = new Random();
	PmphGroup pmphGroup = new PmphGroup("String groupName", "String groupImage",
			Long.parseLong(String.valueOf(r.nextInt(200))), Long.parseLong(String.valueOf(r.nextInt(200))),
			" String note", null, null, null);

	@Test
	public void testAddPmphGroup() {
		testService.addPmphGroup(pmphGroup);
		Assert.assertTrue("添加失败", pmphGroup.getId() > 0);
	}

	@Test
	public void testUpdateGroup() {
		testService.addPmphGroup(pmphGroup);
		pmphGroup.setGroupName(String.valueOf(r.nextInt(200)));
		Boolean flag = true;
		try {
			testService.updatePmphGroup(pmphGroup);
		} catch (Exception e) {
			flag = false;
		}
		Assert.assertTrue("修改失败", flag);
	}

	@Test
	public void testGetPmphGroupById() {
		testService.addPmphGroup(pmphGroup);
		Assert.assertNotNull("获取失败", testService.getPmphGroupById(pmphGroup.getId()));
	}

	@Test
	public void testUpdatePmphGroup() throws CheckedServiceException, IOException {
		testService.addPmphGroup(pmphGroup);
		pmphGroup.setGroupName(String.valueOf(r.nextInt(200)));
		Assert.assertTrue("更新失败", testService.updatePmphGroup(null, pmphGroup) > 0);
	}

}
