/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.service.test;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.BookVideoService;
import com.bc.pmpheep.back.vo.BookVideoVO;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author L.X <gugia@qq.com>
 */
public class BookVideoServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(BookVideoServiceTest.class);

    @Resource
    BookVideoService bookVideoService;

    @Test
    public void getVideoList() {
        PageResult<BookVideoVO> videoList = bookVideoService.getVideoList(10, 1, null, null, null, null);
        logger.info("获取到的微视频数量为：{}", videoList.getPageTotal());
        if (0 != videoList.getPageTotal()) {
            logger.info("获取到的微视频为：{}", videoList.getRows().get(0).toString());
        }
    }
}
