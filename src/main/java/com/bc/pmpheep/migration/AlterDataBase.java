package com.bc.pmpheep.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger  logger = LoggerFactory.getLogger(AlterDataBase.class);
	/**
	 * 向数据表插入新的字段
	 * @introduction static
	 * @author Mryang
	 * @createDate 2017年10月23日 下午1:05:16
	 * @throws Exception
	 */
	public  void addNewPk() throws Exception{
		//获取到所有数据表
		List<Object[]> tables=Until.getListData("show tables");
		//遍历所有数据表
		for(Object[] table:tables){
			//数据表名称
			String tableName= (String)table[0];
			//所有字段以及属性
			List<Object[]> colums=Until.getListData("describe "+tableName.replace(" ", ""));
			String pk="id";
			//用来储存字段名，为后面判断是否含有新主键做准备
			Map<String,String> keys= new HashMap<String,String>(colums.size());
			//遍历所有列
			for(Object[] colum:colums){
				keys.put(((String)colum[0]).toUpperCase(),(String) colum[0]);
				String key= (String)colum[3];
				if(null !=key && !"".equals(key) && "PRI".equals(key.toUpperCase())){
					pk=(String)colum[0];
				}
			}
			String newPk="NEW_"+pk.toUpperCase();
			//不包含新主编
			if(!keys.containsKey(newPk)){
				try {
					Until.getUpdateRes("alter table "+tableName+" add "+newPk+" bigint(20) NOT NULL COMMENT '主新键'");
				} catch (Exception e) {
					logger.info("添加新主键失败了:"+tableName);
				}
				
			}
			//插入新的id值（个人做单元的时候需要执行，后面顺序执行不需要执行）
			try {
				String sql="UPDATE  "+tableName+" oldobj, "+
							"(SELECT @rowno:=@rowno+1 rowno,r."+pk+" from "+tableName+" r,(select @rowno:=0) t) newobj "+
							"SET oldobj."+newPk+"=newobj.rowno where oldobj."+pk+" =newobj."+pk;
				Until.getUpdateRes(sql);
			} catch (Exception e) {
				logger.info("插入新主键抛出异常:"+tableName);
			}
			
		}
		logger.info("主键添加完毕！");
	}

}
