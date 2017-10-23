package com.bc.pmpheep.back.datamigration.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午12:38:32
 *
 **/
public class Until {
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
		Connection con= ConnectionManager.getConnection();
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
		ConnectionManager.colse(rs,ps,con);
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
		List<Object[]> lst=getListData("describe "+tableName.replace(" ", ""));
		for(Object[] o:lst){
			String key= (String)o[3];
			if(null !=key && !"".equals(key) && "PRI".equals(key.toUpperCase())){
				pk=(String)o[0];
				break;
			}
		}
		return pk;
	}
	
	/**
	 * 
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月23日 下午12:53:47
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int getUpdateRes(String sql) throws Exception{
		//拿连接
		Connection con= ConnectionManager.getConnection();
		//拿PreparedStatement
		PreparedStatement ps = con.prepareStatement(sql);
		//获取执行结果
		int count = ps.executeUpdate();
		ConnectionManager.colse(null,ps,con);
		return count;
	} 
	/**
	 * 更新新的pk值
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月23日 下午2:08:21
	 * @param oldValue
	 * @param tableName
	 * @param newValue
	 * @return
	 * @throws Exception
	 */
	public static int updateNewPk(Object oldValue,String tableName,Long newValue) throws Exception{
		String pk=getPk(tableName);
		String newPk="NEW_"+pk.toUpperCase();
		//拿连接
		Connection con= ConnectionManager.getConnection();
		//拿PreparedStatement
		PreparedStatement ps = con.prepareStatement("update "+tableName+" set "+newPk+"= ? where "+pk+" = ? ");
		ps.setLong(1, newValue);
		ps.setObject(2, oldValue);
		//获取执行结果
		int count = ps.executeUpdate();
		ConnectionManager.colse(null,ps,con);
		return count;
	}
}
