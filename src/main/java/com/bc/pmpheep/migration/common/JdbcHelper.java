/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * JDBC工具类
 *
 * @author L.X <gugia@qq.com>
 */
public class JdbcHelper {

    private static DriverManagerDataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcHelper.class);

    private static final String ADD_NEW_PK_COLUMN = "ALTER TABLE ? ADD new_pk BIGINT(20) NOT NULL COMMENT '新表主键'";
    private static final String UPDATE_NEW_PK = "UPDATE # SET new_pk = ? WHERE $ = ?";
    private static final String QUERY = "SELECT * FROM ?";
    private static final String GET_PARENT_PK = "SELECT new_pk FROM # WHERE $ = ?";

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

    /**
     * 为旧数据库指定表新增new_pk列
     *
     * @param tableName 旧数据库表名，例如"bbs_group"
     */
    public static void addColumn(String tableName) {
        String sql = ADD_NEW_PK_COLUMN.replace("?", tableName);
        try {
            getJdbcTemplate().execute(sql);
        } catch (DataAccessException ex) {
            LOG.info(ex.getMessage());
            LOG.warn("执行SQL时发生异常，可能表<{}>已存在'new_pk'字段", tableName);
        }
    }

    /**
     * 更新旧数据库指定表符合查询条件的new_pk字段
     *
     * @param tableName 旧数据库表名，例如"bbs_group"
     * @param pk 数据在新表中的主键
     * @param column 旧数据表中的主键列名
     * @param columnValue 旧数据表中的主键值
     */
    public static void updateNewPrimaryKey(String tableName, long pk, String column, Object columnValue) throws DataAccessException {
        String sql = UPDATE_NEW_PK.replace("#", tableName);
        sql = sql.replace("$", column);
        getJdbcTemplate().update(sql, pk, columnValue);
    }

    /**
     * 根据旧表父节点字段查询父节点对应的new_pk字段值
     *
     * @param tableName 旧数据库表名
     * @param column 旧数据表中的主键列名
     * @param parentColumnValue 对应字段值
     * @return 查询结果
     */
    public static long getParentPrimaryKey(String tableName, String column, Object parentColumnValue) throws DataAccessException {
        String sql = GET_PARENT_PK.replace("#", tableName);
        sql = sql.replace("$", column);
        return getJdbcTemplate().queryForObject(sql, Long.class, parentColumnValue);
    }

    /**
     * 查询旧数据库指定表的全部数据
     *
     * @param tableName 旧数据库表名，例如"bbs_group"
     * @return 查询到的结果集
     */
    public static List<Map<String, Object>> queryForList(String tableName) throws DataAccessException {
        String sql = QUERY.replace("?", tableName);
        return getJdbcTemplate().queryForList(sql);
    }
}
