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
    
    //加载静态块
    static {
		try {
			Class.forName(SQLParameters.DRIVER);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static Connection conn=null;
	
	public static Connection getConnection() throws Exception{
		if(conn==null||conn.isClosed()){
			conn=DriverManager.getConnection(SQLParameters.DB_URL,SQLParameters.DB_USERNAME,SQLParameters.DB_PASSWORD);
		}
		return conn;
	}
	
	//关闭相关连接
	@SuppressWarnings("finally")
	public static void colse(ResultSet rs,PreparedStatement ps,Connection con){
		try{
		    if(rs != null){
		    	rs.close();
		    	rs = null;
		    }  
		}catch(SQLException e){
		    e.printStackTrace();
		}finally{
		    try{
		        if(ps != null){
		            ps.close();
		            ps = null;
		        }
		    }catch(SQLException e){
		        e.printStackTrace();
		    }finally{
		        try{
				     if(con != null){
				    	 con.close();
				    	 con = null;
				     }
				}catch(SQLException e){
				     e.printStackTrace();
				}finally{
				     return ;
				}
		    }
		}
	}
}
