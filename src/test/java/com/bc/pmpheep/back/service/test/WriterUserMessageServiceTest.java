package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
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
		writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
		Assert.assertTrue("失败了",writerUserMessage.getId()>0 );

	}

	@Test
	public void deleteWriterUserMessage() {
		int num = -1;
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
		num = writerUserMessageService.deleteWriterUserMessageById(writerUserMessage.getId());
		Assert.assertTrue("失败了",num >=0);
	}

	@Test
	public void updateWriterUserMessage() {
		int num = -1;
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		WriterUserMessage writerUserMessage2 = new WriterUserMessage();
		writerUserMessage2.setIsDeleted(true);
		writerUserMessage2.setMsgId(321L);
		writerUserMessage2.setUserId(2L);
		writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
		writerUserMessage2.setId(writerUserMessage.getId());
		num = writerUserMessageService.updateWriterUserMessage(writerUserMessage2);
		Assert.assertTrue("失败了",num>0);

	}

	@Test
	public void getWriterUserMessage() {
		WriterUserMessage writerUserMessage = new WriterUserMessage();
		writerUserMessage.setMsgId(123L);
		writerUserMessage.setUserId(1L);
		WriterUserMessage writerUserMessage2 = new WriterUserMessage();
		writerUserMessage = writerUserMessageService.addWriterUserMessage(writerUserMessage);
		writerUserMessage2.setId(writerUserMessage.getId());
		writerUserMessage2 = writerUserMessageService.getWriterUserMessageById(writerUserMessage2.getId());
		Assert.assertTrue("失败了",null != writerUserMessage);

	}
}
