/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.test;

import com.bc.pmpheep.migration.GroupMigrationHelper;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.test.ZipHelperTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

/**
 * 小组数据迁移工具测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class GroupMigrationHelperTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(GroupMigrationHelperTest.class);

    @Resource
    GroupMigrationHelper helper;

    @Test
    @Rollback(false)
    public void demo() {
        helper.group();
        helper.groupMember();
        helper.groupMessage();
    }
}
