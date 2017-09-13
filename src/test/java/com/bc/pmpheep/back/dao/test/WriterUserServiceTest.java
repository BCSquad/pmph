package com.bc.pmpheep.back.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.test.BaseTest;

/**
 * WriterUserService 单元测试
 * 
 * @author 曾庆峰
 *
 */
public class WriterUserServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(WriterUserServiceTest.class);

    @Resource
    WriterUserService writerUserService;

    @Test
    public void addWriterUser() {
	WriterUser writerUser = new WriterUser("name", "123");
	writerUser.setOrgId(10L);
	writerUser.setRealname("asdasd");
	writerUser.setSex(1);
	int num = -1;
	try {
	    num = writerUserService.addWriterUser(writerUser);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (-1 != num) {
	    logger.info("添加了 '{}' 名用户", num);
	} else {
	    logger.error("未添加用户");
	}
    }
}
