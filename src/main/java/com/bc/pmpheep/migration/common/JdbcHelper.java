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
    
    
    
    private static Connection conn=null;
	
	static {
		try {
			Class.forName(SQLParameters.DRIVER);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//默认新平台数据库
	public static Connection getConnection() throws Exception{
		if(conn==null||conn.isClosed()){
			conn=DriverManager.getConnection(SQLParameters.DB_URL,SQLParameters.DB_USERNAME,SQLParameters.DB_PASSWORD);
		}
		return conn;
	}
	
	public static void colse(ResultSet rs,PreparedStatement ps,Connection con){
		if(null != rs ){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != ps ){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != con ){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
