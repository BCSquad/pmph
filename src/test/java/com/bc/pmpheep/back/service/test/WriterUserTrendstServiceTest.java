package com.bc.pmpheep.back.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.service.WriterUserTrendstService;
import com.bc.pmpheep.test.BaseTest;

public class WriterUserTrendstServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(WriterUserTrendstServiceTest.class);
	@Autowired
	WriterUserTrendstService writerUserTrendstService;

	WriterUserTrendst writerUserTrendst = new WriterUserTrendst();

	@Test
	public void testAddWriterUserTrendst() {
		writerUserTrendst.setType(0);
		writerUserTrendst.setUserId(20L);
		writerUserTrendst.setDetail("111");
		WriterUserTrendst userTrendst = writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
		Assert.assertTrue("新增失败", userTrendst.getId() != null);
	}
}
