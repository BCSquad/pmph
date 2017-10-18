/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * JDBC工具类
 *
 * @author L.X <gugia@qq.com>
 */
public class JdbcHelper {

    private static DriverManagerDataSource dataSource;
    
    public static JdbcTemplate getJdbcTemplate() {
        if (null == dataSource) {
            dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(SQLParameters.DRIVER);
            dataSource.setUrl(SQLParameters.DB_URL);
            dataSource.setUsername(SQLParameters.DB_USERNAME);
            dataSource.setPassword(SQLParameters.DB_PASSWORD);
        }
        return new JdbcTemplate(dataSource);
    }
}
