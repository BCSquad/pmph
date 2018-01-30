package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Sensitive;
import com.bc.pmpheep.back.service.SensitiveService;
import com.bc.pmpheep.test.BaseTest;

public class SensitiveServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(SensitiveServiceTest.class);

	@Resource
	SensitiveService sensitiveService;
	Sensitive sensitive = new Sensitive("请问权威", null, null, null, null, null, null);

	@Test
	public void addTest() {
		Sensitive sen = sensitiveService.add(sensitive);
		Assert.assertNotNull("添加失败", sen.getId());
	}

	@Test
	public void updateTest() {
		Sensitive sen = sensitiveService.add(sensitive);
		sen.setWord("jkgh");
		String result = sensitiveService.update(sen);
		Assert.assertTrue("修改失败", result.equals("SUCCESS"));
	}

	@Test
	public void updateIsDeletedTest() {
		Sensitive sen = sensitiveService.add(sensitive);
		Long[] id = { sen.getId() };
		String result = sensitiveService.updateIsDeleted(id);
		Assert.assertTrue("删除失败", result.equals("SUCCESS"));
	}

	@Test
	public void listTest() {
		Sensitive sen = sensitiveService.add(sensitive);
		PageParameter<Sensitive> pageParameter = new PageParameter<>(1, 5);
		Sensitive sensitive = new Sensitive();
		pageParameter.setParameter(sensitive);
		PageResult<Sensitive> pageResult = sensitiveService.list(pageParameter);
		Assert.assertNotNull("获取失败", pageResult.getRows());
	}

}
