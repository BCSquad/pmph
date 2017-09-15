package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.WriterUserMessage;
import com.bc.pmpheep.back.service.WriterUserMessageService;
import com.bc.pmpheep.test.BaseTest;

public class WriterUserMessageServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(WriterUserMessageServiceTest.class);

	@Resource
	WriterUserMessageService writerUserMessageService;

	@Test
	public void addWriterUserMessage() {
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		try {
			writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != writerUserMessage) {
			logger.info("添加了{}", writerUserMessage.toString());
		} else {
			logger.info("失败了");
		}

	}

	@Test
	public void deleteWriterUserMessage() {
		int num = -1;
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		try {
			writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
			num = writerUserMessageService.deleteWriterUserMessageById(writerUserMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (-1 != num) {
			logger.info("成功删除了{}条数据", num);
		} else {
			logger.info("失败了");
		}
	}

	@Test
	public void updateWriterUserMessage() {
		int num = -1;
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		WriterUserMessage writerUserMessage2 = new WriterUserMessage();
		writerUserMessage2.setIsDelete(true);
		writerUserMessage2.setMsgId(321L);
		writerUserMessage2.setUserId(2L);
		try {
			writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
			writerUserMessage2.setId(writerUserMessage.getId());
			num = writerUserMessageService.updateWriterUserMessageById(writerUserMessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != writerUserMessage) {
			logger.info("成功修改了{}条数据", num);
		} else {
			logger.info("失败了");
		}

	}

	@Test
	public void getWriterUserMessage() {
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		WriterUserMessage writerUserMessage2 = new WriterUserMessage();
		try {
			writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
			writerUserMessage2.setId(writerUserMessage.getId());
			writerUserMessage2 = writerUserMessageService.getWriterUserMessageById(writerUserMessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != writerUserMessage) {
			logger.info("找到了{}", writerUserMessage.toString());
		} else {
			logger.info("失败了");
		}

	}
}
