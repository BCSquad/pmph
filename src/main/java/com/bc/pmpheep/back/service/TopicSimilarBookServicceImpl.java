package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TopicSimilarBookDao;
import com.bc.pmpheep.back.po.TopicSimilarBook;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Service
public class TopicSimilarBookServicceImpl implements TopicSimilarBookService {
	@Autowired
	TopicSimilarBookDao topicSimilarBookDao;

	@Override
	public List<TopicSimilarBook> listTopicSimilarBookByTopicId(Long topicId) throws CheckedServiceException {
		if (ObjectUtil.isNull(topicId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"选题申报的id为空！");
		}
		return topicSimilarBookDao.listTopicSimilarBookByTopicId(topicId);
	}

}
