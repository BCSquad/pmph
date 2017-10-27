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
import org.springframework.stereotype.Component;

/**
 * JDBC工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class JdbcHelper {

    private static DriverManagerDataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcHelper.class);

    private static final String ADD_NEW_PK_COLUMN = "ALTER TABLE ? ADD new_pk BIGINT(20) NOT NULL COMMENT '新表主键'";
    private static final String UPDATE_NEW_PK = "UPDATE # SET new_pk = ? WHERE $ = ?";
    private static final String QUERY = "SELECT * FROM ?";
    private static final String GET_PARENT_PK = "SELECT new_pk FROM # WHERE $ = ?";
    private static final String GET_PARENTID = "SELECT % FROM # WHERE $ = ?";

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
    
    /**
     * 
     * Description:根据旧表父节点字段查询父节点对应的new_pk字段值，如果不为0，则递归调用自己，
     * 知道找到为旧表父节点为0的最高节点，拼接成path
     * @author:lyc
     * @date:2017年10月26日下午5:23:43
     * @param tableName 旧数据库表名
     * @param column 旧数据表中的主键列名
     * @param parentColumn 旧数据表中的父节点列名
     * @param parentColumnValue 对应字段值
     * @return 拼接的path
     */
    public static String getPath(String tableName, String column, String parentColumn, Object parentColumnValue) throws DataAccessException{
    	String sqlNewParentId = GET_PARENT_PK.replace("#", tableName);
    	String sqlParentId = GET_PARENTID.replace("#", tableName);
    	sqlNewParentId = sqlNewParentId.replace("$", column);
    	sqlParentId = sqlParentId.replace("$", column);
    	sqlParentId = sqlParentId.replace("%", parentColumn);
    	String path = "";
    	//如果旧表中父节点为0，则直接返回路径0，若不为0，再继续往父类节点查最后拼接
    	if ( !"0".equals(parentColumnValue.toString())){
    		//不为0，根据旧表父节点字段查询父节点对应的new_pk和父节点id字段值，依次类推，查最后拼接
    		String parentId = getJdbcTemplate().queryForObject(sqlParentId,String.class,parentColumnValue);
    		Long parentNewId = getJdbcTemplate().queryForObject(sqlNewParentId, Long.class, parentColumnValue);
    		path = getPath(tableName, column, parentColumn, parentId) + "-" + parentNewId;
    	}else{
    		path = "0";
    	}
    	return path;
    }
}
