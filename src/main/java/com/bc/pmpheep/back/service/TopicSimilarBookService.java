package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.TopicSimilarBook;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface TopicSimilarBookService {
	List<TopicSimilarBook> listTopicSimilarBookByTopicId(Long topicId) throws CheckedServiceException;
}
