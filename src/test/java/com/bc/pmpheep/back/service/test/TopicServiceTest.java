package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.service.TopicService;
import com.bc.pmpheep.back.service.TopicServiceImpl;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.vo.TopicDeclarationVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * 
 * 功能描述：TopicService的单元测试
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
public class TopicServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(TopicServiceTest.class);
	Topic topic = new Topic(9L, "abc", 2, DateUtil.getCurrentTime(), 0, 100, 100, "pps", 0, 0, 1L, null, null, false,
			null, null, null, null, 1, null, null, true, 1L, false, null, false, null, false, null, false, null, false,
			false, false, null, null, null, null, null, DateUtil.getCurrentTime());

	@Resource
	TopicService topicService;

	 @Test
	 public void testAdd() {
	 Topic topic = topicService.add(this.topic);
	 logger.info("插入的Topic对象=" + topic.toString());
	 Assert.assertNotNull("插入内容后返回的Topic不应为空", topic.getId());
	 }
	
	 @Test
	 public void testListCheckTopic() {
	 topicService.add(topic);
	 PageParameter<TopicDeclarationVO> pageParameter = new PageParameter<>(1, 5);
	 TopicDeclarationVO topicDeclarationVO = new TopicDeclarationVO();
	 topicDeclarationVO.setBookname(null);
	 topicDeclarationVO.setSubmitTime(null);
	 pageParameter.setParameter(topicDeclarationVO);
	 List<Long> progress = new ArrayList<>();
	 progress.add(1L);
	 progress.add(0L);
	 progress.add(2L);
	 progress.add(3L);
	 PageResult<TopicDeclarationVO> declarationVOs =
	 topicService.listCheckTopic(progress, pageParameter);
	 Assert.assertTrue("查询失败", declarationVOs.getTotal() > 0);
	 }
	
	 @Test
	 public void testGetTopicTextVO() {
	 Topic topic = topicService.add(this.topic);
	 TopicTextVO topicTextVO = topicService.getTopicTextVO(topic.getId());
	 logger.info("详细情况=" + topicTextVO.toString());
	 Assert.assertNotNull("查询内容后返回的TopicTextVO不应为空", topicTextVO);
	 }
	
	 @Test
	 public void testUpdate() {
	 Topic topic = topicService.add(this.topic);
	 topic.setUserId(999L);
	 topic.setBookname("cba");
	 String result = topicService.update(topic);
	 logger.info("修改" + result);
	 Assert.assertTrue("修改失败", result.equals("SUCCESS"));
	 }

	@Test
	public void testUpdateTopic() {
		topicService.updateByErp();
	}
}
