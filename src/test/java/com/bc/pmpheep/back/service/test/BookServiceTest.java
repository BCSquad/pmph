package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

public class BookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

	@Resource
	BookService bookService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void BookTest() {

	}

}
