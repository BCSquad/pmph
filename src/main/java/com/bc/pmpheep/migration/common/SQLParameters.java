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
}
