package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.TopicLog;

/**
 * 
 * 
 * 功能描述：选题申报日志的持久层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月25日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public interface TopicLogDao {
	/**
	 * 
	 * 
	 * 功能描述：新增TopicLog
	 *
	 * @param topicLog
	 * @return
	 *
	 */
	Long add(TopicLog topicLog);
}
