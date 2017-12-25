package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TopicExtraDao;
import com.bc.pmpheep.back.po.TopicExtra;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报额外信息业务实现层
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
@Service
public class TopicExtraServiceImpl implements TopicExtraService {

	@Autowired
	TopicExtraDao topicExtraDao;

	@Override
	public TopicExtra getTopicExtraByTopicId(Long topicId) throws CheckedServiceException {
		if (ObjectUtil.isNull(topicId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报id为空！");
		}
		return topicExtraDao.getTopicExtraByTopicId(topicId);
	}

	@Override
	public TopicExtra add(TopicExtra topicExtra) throws CheckedServiceException {
		if (ObjectUtil.isNull(topicExtra)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报为空！");
		}
		topicExtraDao.add(topicExtra);
		return topicExtra;
	}

}
