package com.bc.pmpheep.back.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.TopicExtra;
import com.bc.pmpheep.back.po.TopicWriter;
import com.bc.pmpheep.back.service.TopicWriertService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * 
 * 功能描述：TopicWriterService的单元测试
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
public class TopicWriterServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(TopicWriterServiceTest.class);
	TopicWriter topicWriter = new TopicWriter(1L, "1", 0, 12, "qwe", "001");

	@Resource
	TopicWriertService topicWriertService;

	@Test
	public void testAdd() {
		TopicWriter writer = topicWriertService.add(topicWriter);
		logger.info("插入的TopicWriter对象=" + writer.toString());
		Assert.assertNotNull("插入内容后返回的TopicWriter不应为空", writer.getId());
	}

	@Test
	public void testListTopicWriterByTopicId() {
		topicWriertService.add(topicWriter);
		List<TopicWriter> list = topicWriertService.listTopicWriterByTopicId(1L);
		Assert.assertNotNull("查询内容后返回的list不应为空", list);
	}

}
