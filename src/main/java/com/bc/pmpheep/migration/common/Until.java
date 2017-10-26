package com.bc.pmpheep.migration.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午12:38:32
 **/
public class Until {
	
	/**
	 * 传入查询sql 获取 结果集,Object 下表和缩写字段顺序对应，从0开始;
	 * @author Mryang
	 * @createDate 2017年10月23日 下午12:03:31
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Object[]> getListData(String sql) throws Exception{
		//获取连接
		Connection con= JdbcHelper.getConnection();
		//获取PreparedStatement
		PreparedStatement ps = con.prepareStatement(sql);
		//获取结果集
		ResultSet rs = ps.executeQuery();
		//获取总共列数
		ResultSetMetaData rsmd = rs.getMetaData();
		int count=rsmd.getColumnCount();
		List<Object[]> list=new ArrayList<Object[]>(16);
		//遍历查询结果集
		while(rs.next()){
		   Object[] object=new Object[count];
		   //将每列的数据放入Object对象中;
		   for(int i=0;i<count;i++){
			   object[i]=rs.getObject(i+1);
		   }
		   list.add(object);
		}
		//关闭连接
		JdbcHelper.colse(rs,ps,con);
		return list;
	} 
	/**
	 * 获取表的主键 名称，不存在就返回null
	 * @author Mryang
	 * @createDate 2017年10月23日 下午12:12:45
	 * @param tableName
	 * @return
	 * @throws Exception 
	 */
	public static String getPk(String tableName) throws Exception{
		String pk=null;
		//获取表的字段属性
		List<Object[]> list=getListData("describe "+tableName.replace(" ", ""));
		//遍历
		for(Object[] o:list){
			//o[0]:是字段名称 ;o[1]:字段类型 ;o[2]:字段是否为空; o[3]:字段是否主键 ; o[4]:字段默认值; o[5]:其他信息。
			String key= (String)o[3];
			//PRI是 是主键的标识；
			if(null !=key && !"".equals(key) && "PRI".equals(key.toUpperCase())){
				pk=(String)o[0];
				break;
			}
		}
		return pk;
	}
	
	/**
	 * 
	 * 传入增删改sql，执行返回影响行数
	 * @author Mryang
	 * @createDate 2017年10月23日 下午12:53:47
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int getUpdateRes(String sql) throws Exception{
		//获取连接
		Connection con= JdbcHelper.getConnection();
		//获取PreparedStatement
		PreparedStatement ps = con.prepareStatement(sql);
		//获取执行结果
		int count = ps.executeUpdate();
		//关闭连接
		JdbcHelper.colse(null,ps,con);
		return count;
	} 
	/**
	 * 更新新的pk值
	 * @author Mryang
	 * @createDate 2017年10月23日 下午2:08:21
	 * @param oldValue
	 * @param tableName
	 * @param newValue
	 * @return
	 * @throws Exception
	 */
	public static int updateNewPk(Object oldValue,String tableName,Long newValue) throws Exception{
		//获取表的主键 名称，不存在就返回id
		String pk=getPk(tableName);
		//判断是否有主键
		if(null == pk){
			return 0;
		}
		//新主键
		String newPk="NEW_"+pk.toUpperCase();
		//执行下面更新语句
		return getUpdateRes("update "+tableName+" set "+newPk+"= ? where "+pk+" = ? ");
	}
}
