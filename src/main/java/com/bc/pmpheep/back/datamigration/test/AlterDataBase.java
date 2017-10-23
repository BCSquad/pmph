package com.bc.pmpheep.back.datamigration.test;

import java.util.List;

import com.bc.pmpheep.back.datamigration.common.ConnectionManager;




/**
 * 给每个表添加一个new_pk;
 *@author MrYang 
 *@CreateDate 2017年10月23日 上午11:42:30
 *
 **/
public class AlterDataBase {
	public static void main(String[] agrs) throws Exception{
		String sql="show tables";
		List<Object[]> s=ConnectionManager.getListData(sql);
		for(Object[] d:s){
			System.out.println(d[0]);
			System.out.println(ConnectionManager.getPk((String)d[0]));
		}
		
	}

}
