package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.service.BookUserCommentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

public class BookUserCommentServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(BookUserCommentServiceTest.class);

	@Resource
	BookUserCommentService bookUserCommentService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void BookUserCommentTest() {
		
		
	}
}
