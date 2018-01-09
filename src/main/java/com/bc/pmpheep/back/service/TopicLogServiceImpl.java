package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TopicLogDao;
import com.bc.pmpheep.back.po.TopicLog;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报受理日志业务实现层
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
@Service
public class TopicLogServiceImpl implements TopicLogService {
	@Autowired
	TopicLogDao topicLogDao;

	@Override
	public TopicLog add(TopicLog topicLog) throws CheckedServiceException {
		topicLogDao.add(topicLog);
		return topicLog;
	}

}
