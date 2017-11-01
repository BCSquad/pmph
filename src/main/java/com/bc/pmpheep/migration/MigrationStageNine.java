/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.service.BookUserCommentService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据迁移方案第九部分 - 出版图书相关数据迁移
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class MigrationStageNine {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageNine.class);
    
    @Resource
    BookService bookService;
    @Resource
    BookUserCommentService bookUserCommentService;
}
