package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.po.TopicSimilarBook;

public interface TopicSimilarBookDao {
	List<TopicSimilarBook> listTopicSimilarBookByTopicId(Long topicId);
}
