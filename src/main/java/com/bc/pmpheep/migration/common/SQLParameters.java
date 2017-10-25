/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.common;

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
    public static final String DB_URL = "jdbc:mysql://localhost:3306/pmph_out";

    /**
     * 现有平台数据库用户名
     */
    public static final String DB_USERNAME = "root";

    /**
     * 现有平台数据库密码
     */
    public static final String DB_PASSWORD = "root";

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
}
