package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.WriterMessage;
import com.bc.pmpheep.back.service.WriterMessageService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 *
 */
public class WriterMessageServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterMessageServiceTest.class);

	@Resource
	WriterMessageService writerMessageService;

	@Test
	public void add() {
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		writerMessage = writerMessageService.addWriterMessage(writerMessage);
		Assert.assertNotNull("失败了",writerMessage);
	}

	@Test
	public void delete() {
		int num = -1;
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		WriterMessage writerMessage2 = new WriterMessage();
		writerMessage = writerMessageService.addWriterMessage(writerMessage);
		writerMessage2.setId(writerMessage.getId());
		num = writerMessageService.deleteWriterMessageById(writerMessage2);
		Assert.assertTrue("失败",num >=0 );
	}

	@Test
	public void update() {
		int num = -1;
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		WriterMessage writerMessage2 = new WriterMessage("qweqwe", 2);
		writerMessage = writerMessageService.addWriterMessage(writerMessage);
		writerMessage2.setId(writerMessage.getId());
		num = writerMessageService.updateWriterMessageById(writerMessage2);
		Assert.assertTrue("失败",num >=0 );
	}

	@SuppressWarnings("unused")
	@Test
	public void get() {
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		WriterMessage writerMessage2 = new WriterMessage();
		WriterMessage message = new WriterMessage();
		writerMessage = writerMessageService.addWriterMessage(writerMessage);
		writerMessage2.setId(writerMessage.getId());
		message = writerMessageService.getWriterMessageById(writerMessage2);
		Assert.assertTrue("失败",null != writerMessage2);
	}
}
