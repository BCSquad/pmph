package com.bc.pmpheep.back.dao.test;

import java.util.Date;

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
    Date date = new Date();
    @Resource
    WriterUserService writerUserService;

    @Test
    public void addWriterUser() {

	WriterUser writerUser = new WriterUser("q", "123", false, 112L, "123132", 1, date, 12, "12313", "123w", "aweq",
		"12345678", "0215464", "123012015445", "354648765454", "ersfsdfserr", "123456", 1, false, "dfgdfgsdf",
		date, 1, 1L, false, false, "asfsdf", "gdsdfser", 2, false, null, null);
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

    @Test
    public void deleteWriterUserById() {
	int num = -1;
	String[] ids = { "1" };
	try {
	    WriterUser writerUser = new WriterUser("q", "123", false, 112L, "123132", 1, date, 12, "12313", "123w",
		    "aweq", "12345678", "0215464", "123012015445", "354648765454", "ersfsdfserr", "123456", 1, false,
		    "dfgdfgsdf", date, 1, 1L, false, false, "asfsdf", "gdsdfser", 2, false, null, null);
	    writerUserService.addWriterUser(writerUser);
	    num = writerUserService.deleteWriterUserById(ids);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (-1 != num) {
	    logger.info("删除了 '{}' 名用户", num + 1);
	} else {
	    logger.error("未删除用户");
	}

    }

    @Test
    public void updateWriterUserById() {
	int num = -1;
	WriterUser writerUser = new WriterUser("q", "123", false, 112L, "123132", 1, date, 12, "12313", "123w", "aweq",
		"12345678", "0215464", "123012015445", "354648765454", "ersfsdfserr", "123456", 1, false, "dfgdfgsdf",
		date, 1, 1L, false, false, "asfsdf", "gdsdfser", 2, false, null, null);
	WriterUser writerUser1 = new WriterUser("阿瑟家分公司及地方各级送给大家分工", "123", false, 112L, "123132", 1, date, 12, "12313",
		"123w", "aweq", "12345678", "0215464", "123012015445", "354648765454", "ersfsdfserr", "123456", 1,
		false, "dfgdfgsdf", date, 1, 1L, false, false, "asfsdf", "gdsdfser", 2, false, null, null);
	writerUser1.setId(1L);
	try {
	    writerUserService.addWriterUser(writerUser);
	    num = writerUserService.updateWriterUserById(writerUser1);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (-1 != num) {
	    logger.info("修改了了 '{}' 名用户", num + 1);
	} else {
	    logger.error("未修改用户");
	}

    }

    @Test
    public void getWriterUserByUsername() {
	WriterUser user = null;
	WriterUser writerUser = new WriterUser("曾庆峰", "123", false, 112L, "123", 1, date, 12, "12313", "123w", "aweq",
		"12345678", "0215464", "123012015445", "354648765454", "ersfsdfserr", "123456", 1, false, "dfgdfgsdf",
		date, 1, 1L, false, false, "asfsdf", "gdsdfser", 2, false, null, null);

	try {
	    writerUserService.addWriterUser(writerUser);
	    user = writerUserService.getWriterUserByUsername("曾庆峰");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if (null != user) {
	    logger.info("该用户为 '{}' ", user.toString());
	} else {
	    logger.error("未找到用户");
	}

    }
}
