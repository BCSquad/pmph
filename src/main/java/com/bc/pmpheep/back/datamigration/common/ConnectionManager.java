package com.bc.pmpheep.back.datamigration.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 上午11:27:19
 *
 **/
public class ConnectionManager {
	private static Connection conn=null;
	private ConnectionManager(){}
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//默认新平台数据库
	public static Connection getConnection() throws Exception{
		if(conn==null||conn.isClosed()){
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/pmph_out","root","root");
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
