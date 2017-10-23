package com.bc.pmpheep.back.datamigration.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
	
	/**
	 * 传入sql 获取 结果集
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月23日 下午12:03:31
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Object[]> getListData(String sql) throws Exception{
		//拿连接
		Connection con= getConnection();
		//拿PreparedStatement
		PreparedStatement ps = con.prepareStatement(sql);
		//获取结果集
		ResultSet rs = ps.executeQuery();
		//获取总共列数
		ResultSetMetaData rsmd = rs.getMetaData();
		int count=rsmd.getColumnCount();
		List<Object[]> lst=new ArrayList<Object[]>(16);
		while(rs.next()){
		   Object[] object=new Object[count];
		   for(int i=0;i<count;i++){
			   object[i]=rs.getObject(i+1);
		   }
		   lst.add(object);
		}
		colse(rs,ps,con);
		return lst;
	} 
	/**
	 * 获取表的主键 名称，不存在就返回id
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月23日 下午12:12:45
	 * @param tableName
	 * @return
	 * @throws Exception 
	 */
	public static String getPk(String tableName) throws Exception{
		String pk="id";
		List<Object[]> lst=ConnectionManager.getListData("describe "+tableName.replace(" ", ""));
		for(Object[] o:lst){
			String key= (String)o[3];
			if(null !=key && !"".equals(key) && "PRI".equals(key.toUpperCase())){
				pk=(String)o[0];
				break;
			}
		}
		return pk;
	}
	
}
