/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.migration.common.JdbcHelper;

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
public class OrgAndDepartmentMigrationHelper {

    private final Logger logger = LoggerFactory.getLogger(OrgAndDepartmentMigrationHelper.class);

    @Resource
    PmphDepartmentService pmphDepartmentService;
    @Resource
    OrgService orgService;
    @Resource
    OrgTypeService orgTypeService;

    public void orgType() {
        String sql = "SELECT a.orgtype,a.orgname,a.sortno FROM ba_organize a WHERE a.parentid = 0 AND a.orgcode NOT LIKE '15%'";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        for (Map<String, Object> map : maps) {
            OrgType orgType = new OrgType();
            String orgtype = map.get("orgtype").toString();
            String orgname = map.get("orgname").toString();
            String sortno = map.get("sortno").toString();
            
        }
    }
}
