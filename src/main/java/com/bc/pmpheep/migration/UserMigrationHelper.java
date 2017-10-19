/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.WriterUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户数据迁移工具类
 *
 * @author L.X <gugia@qq.com>
 */
public class UserMigrationHelper {

    private final Logger logger = LoggerFactory.getLogger(UserMigrationHelper.class);

    @Resource
    PmphUserService pmphUserService;
    @Resource
    WriterUserService writerUserService;
    @Resource
    OrgUserService orgUserService;

    public void user() {
        String sql_user = "SELECT * FROM sys_user";
        String sql_userext = "SELECT * FROM sys_userext";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql_user);
        int count = 0;//迁移成功的条目数
        for (Map<String, Object> map : maps) {
            String usertype = map.get("usertype").toString();
        }
    }
}
