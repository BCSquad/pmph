package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.TopicExtra;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报额外信息业务层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月21日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public interface TopicExtraService {
	/**
	 * 
	 * 
	 * 功能描述：新增TopicExtra
	 *
	 * @param topicExtra
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	TopicExtra add(TopicExtra topicExtra) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据选题申报id获取额外信息
	 *
	 * @param topicId
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	TopicExtra getTopicExtraByTopicId(Long topicId) throws CheckedServiceException;
}
