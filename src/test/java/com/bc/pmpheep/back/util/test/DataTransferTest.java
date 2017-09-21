package com.bc.pmpheep.back.util.test;

import com.bc.pmpheep.back.service.AreaService;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.util.DataTransfer;
import com.bc.pmpheep.test.BaseTest;
import org.junit.Assert;

/*
 * author:lyc
 * 数据迁移工具测试类
 */
public class DataTransferTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(DataTransferTest.class);

    @Resource
    DataTransfer dataTransfer;

    @Test
    @Rollback(false)
    public void area() {
        int count = dataTransfer.area();
        Assert.assertTrue("数据迁移总条数应与当前数据库条目数一致", count > 0);
    }
}
