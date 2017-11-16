package com.bc.pmpheep.back.erp.service;

import net.sf.json.JSONArray;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.erp.DBFun.SqlHelper;

import java.util.ArrayList;

/**
 * 
 * 
 * 功能描述：在erp表中进行sql语句的编写
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月15日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public class BaseWorking extends BaseService {
	// /**
	// * 获取对应数据表所有需要拉取的数据
	// *
	// * @param tabName
	// * @return
	// */
	// protected JSONArray getList(String tabName) {
	// return SqlHelper.executeQuery("select * from " + tabName + " where states =
	// 0", null);
	// }
	//
	// /**
	// * 获取对应数据表所有需要拉取的数据
	// *
	// * @param tabName
	// * @return
	// */
	// protected JSONArray getList(String tabName, String states) {
	// return SqlHelper.executeQuery("select * from " + tabName + " where states ='"
	// + states + "'", null);
	// }
	//
	// /**
	// * 根据表名和标识修改获取状态
	// *
	// * @param tabName
	// * @param id
	// */
	// protected void SingleUpdateStates(String tabName, String id) {
	// SqlHelper.executeUpdate("update " + tabName + " set states=1 where
	// record_id=?", new String[] { id });
	// }
	//
	// /**
	// * 批量修改状态,跟表名
	// *
	// * @param tabName
	// */
	// protected void BatchUpdateStates(String tabName) {
	// SqlHelper.executeUpdate("update " + tabName + " set states =1", null);
	// }
	//
	// /**
	// * 批量修改状态,跟表名
	// *
	// * @param tabName
	// */
	// protected void BatchUpdateStates(String tabName, String states) {
	// SqlHelper.executeUpdate("update " + tabName + " set states ='" + states +
	// "'", null);
	// }
	//
	// /**
	// * 批量修改状态,跟表名
	// *
	// * @param tabName
	// */
	// protected void BatchUpdateStates(String tabName, ArrayList list) {
	// String record_ids = "";
	// for (int i = 0; i < list.size(); i++) {
	// record_ids += ",'" + list.get(i) + "'";
	// }
	// if (!record_ids.equals("")) {
	// record_ids = record_ids.substring(1);
	// }
	// SqlHelper.executeUpdate("update " + tabName + " set states =1 where record_id
	// in (" + record_ids + ")", null);
	// }
	
	/**
	 * 
	 * 
	 * 功能描述：获取所有的数据
	 * 
	 * @param tabName 表名
	 * @return
	 *
	 */
	protected JSONArray list(String tabName) {
		return SqlHelper.executeQuery("select editionnum from " + tabName, null);
	}
}