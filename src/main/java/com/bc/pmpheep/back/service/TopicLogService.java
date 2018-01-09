package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.TopicLog;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报受理日志业务层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018年1月9日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public interface TopicLogService {
	/**
	 * 
	 * 
	 * 功能描述：添加选题申报日志
	 *
	 * @param topicLog
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	TopicLog add(TopicLog topicLog) throws CheckedServiceException;
}
