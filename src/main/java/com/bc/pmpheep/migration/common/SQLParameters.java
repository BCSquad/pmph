/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据迁移所需全局变量
 *
 * @author L.X <gugia@qq.com>
 */
public class SQLParameters {

    /**
     * JDBC驱动
     */
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    /**
     * 现有平台数据库地址
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/pmph_imesp";

    /**
     * 现有平台数据库用户名
     */
    public static final String DB_USERNAME = "root";

    /**
     * 现有平台数据库密码
     */
    public static final String DB_PASSWORD = "bcit2017=pmph";

    /**
     * 专家平台数据库地址
     */
    public static final String ZJ_URL = "";

    /**
     * 专家平台数据库用户名
     */
    public static final String ZJ_USERNAME = "";

    /**
     * 专家平台数据库密码
     */
    public static final String ZJ_PASSWORD = "";

    /**
     * 现有平台文件路径（最后以/结尾）
     */
    public static final String FILE_PATH = "/home/ftp/";

    /**
     * Excel异常导出统一表头
     */
    public static final String EXCEL_EX_HEADER = "exception";
    
    /**
     * 储存错误信息，方便测序完成一次打印或者导出完毕信息
     */
    public static List<Map<String, Object>> STATISTICS = new ArrayList<>(16);
    
    /**
     * 用于数据迁移后导出总体统计结果
     */
    public static List<Map<String, Object>> STATISTICS_RESULT = new ArrayList<>(16);
    
    /**
     * 总体统计结果表名列的表头
     */
    public static final String EXCEL_HEADER_TABLENAME = "表名";
    
    /**
     * 总体统计结果描述列的表头
     */
    public static final String EXCEL_HEADER_DESCRIPTION = "描述";
    
    /**
     * 总体统计结果数据总数目列的表头
     */
    public static final String EXCEL_HEADER_SUM_DATA = "总数据";
    
    /**
     * 总体统计结果已迁移数据数目列的表头
     */
    public static final String EXCEL_HEADER_MIGRATED_DATA = "已迁移数据";
    
    /**
     * 总体统计结果正常数据数目列的表头
     */
    public static final String EXCEL_HEADER_CORECT_DATA = "正常数据";
    
    /**
     * 总体统计结果异常经转换迁移数据数目列的表头
     */
    public static final String EXCEL_HEADER_TRANSFERED_DATA = "异常经转换迁移数据";
    
    /**
     * 总体统计结果未迁移数据数目列的表头
     */
    public static final String EXCEL_HEADER_NO_MIGRATED_DATA = "异常未迁移数据";
    
    /**
     * 总体统计结果异常原因的表头
     */
    public static final String EXCEL_HEADER_EXCEPTION_REASON = "异常原因";
    
    /**
     * 总体统计结果处理方式的表头
     */
    public static final String EXCEL_HEADER_DEAL_WITH = "处理方式";
}
