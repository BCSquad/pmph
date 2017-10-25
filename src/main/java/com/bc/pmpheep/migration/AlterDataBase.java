package com.bc.pmpheep.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bc.pmpheep.migration.common.Until;


/**
 * 给每个表添加一个new_pk,这个类的addNewPk() 必须第一步执行;
 *@author MrYang 
 *@CreateDate 2017年10月23日 上午11:42:30
 *
 **/
@Component
public class AlterDataBase {
	
	/**
	 * 向数据表插入新的字段
	 * @introduction static
	 * @author Mryang
	 * @createDate 2017年10月23日 下午1:05:16
	 * @throws Exception
	 */
	public static void addNewPk() throws Exception{
		//获取到所有数据表
		List<Object[]> s=Until.getListData("show tables");
		for(Object[] d:s){
			//数据表名称
			String tableName= (String)d[0];
			//所有字段以及属性
			List<Object[]> lst=Until.getListData("describe "+tableName.replace(" ", ""));
			String pk="id";
			Map<String,String> f= new HashMap<String,String>(lst.size());
			for(Object[] o:lst){
				f.put(((String)o[0]).toUpperCase(),(String) o[0]);
				String key= (String)o[3];
				if(null !=key && !"".equals(key) && "PRI".equals(key.toUpperCase())){
					pk=(String)o[0];
				}
			}
			String newPk="NEW_"+pk.toUpperCase();
			if(!f.containsKey(newPk)){//不包含包含新主编
				try {
					Until.getUpdateRes("alter table "+tableName+" add "+newPk+" bigint(20) NOT NULL COMMENT '主新键'");
				} catch (Exception e) {
					System.out.println("添加新主键失败了:"+tableName);
				}
				
			}
		}
		System.out.println("主键添加完毕！");
	}

}
