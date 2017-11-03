/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.utils.ExcelHelper;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据迁移方案第十部分 - CMS相关数据迁移
 *
 * @author L.X <gugia@qq.com>
 */
public class MigrationStageTen {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageTen.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    FileService fileService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
        //TODO
    }
}
