package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.TopicExtra;
import com.bc.pmpheep.back.service.TopicExtraService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * 
 * 功能描述：TopicExtraService的单元测试
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
public class TopicExtraServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(TopicExtraServiceTest.class);
	TopicExtra topicExtra = new TopicExtra(1L, null, null, null,null);

	@Resource
	TopicExtraService topicExtraService;

	@Test
	public void testAdd() {
		TopicExtra extra = topicExtraService.add(topicExtra);
		logger.info("插入的TopicExtra对象=" + extra.toString());
		Assert.assertNotNull("插入内容后返回的TopicExtra不应为空", extra.getId());
	}

	@Test
	public void testGetTopicExtraByTopicId() {
		topicExtraService.add(topicExtra);
		TopicExtra topicExtra = topicExtraService.getTopicExtraByTopicId(1L);
		logger.info("查询的TopicExtra对象=" + topicExtra.toString());
		Assert.assertNotNull("查询内容后返回的TopicExtra不应为空", topicExtra);
	}
}
