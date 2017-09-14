package com.bc.pmpheep.back.dao.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.test.BaseTest;

/**
 * WriterUserService 单元测试
 * 
 * @author 曾庆峰
 * 
 */
public class WriterUserServiceTest extends BaseTest {

    Logger            logger = LoggerFactory.getLogger(WriterUserServiceTest.class);
    Date              date   = new Date();
    @Resource
    WriterUserService writerUserService;

    @Test
    public void addWriterUser() {

    }

    @Test
    public void deleteWriterUserById() {
        int num = -1;
        String[] ids = { "1" };
        try {
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
        try {
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

        try {
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
