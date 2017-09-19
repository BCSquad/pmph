package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

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
		try {
			writerMessage = writerMessageService.addWriterMessage(writerMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != writerMessage) {
			logger.info("添加了{}条数据", writerMessage.toString());
		} else {
			logger.info("失败了");
		}
	}

	@Test
	public void delete() {
		int num = -1;
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		WriterMessage writerMessage2 = new WriterMessage();
		try {
			writerMessage = writerMessageService.addWriterMessage(writerMessage);
			writerMessage2.setId(writerMessage.getId());
			num = writerMessageService.deleteWriterMessageById(writerMessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (-1 != num) {
			logger.info("删除了{}条数据", num);
		} else {
			logger.info("失败了");
		}
	}

	@Test
	public void update() {
		int num = -1;
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		WriterMessage writerMessage2 = new WriterMessage("qweqwe", 2);
		try {
			writerMessage = writerMessageService.addWriterMessage(writerMessage);
			writerMessage2.setId(writerMessage.getId());
			num = writerMessageService.updateWriterMessageById(writerMessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (-1 != num) {
			logger.info("修改了{}条数据", num);
		} else {
			logger.info("失败了");
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void get() {
		WriterMessage writerMessage = new WriterMessage("asdasd", 1);
		WriterMessage writerMessage2 = new WriterMessage();
		WriterMessage message = new WriterMessage();
		try {
			writerMessage = writerMessageService.addWriterMessage(writerMessage);
			writerMessage2.setId(writerMessage.getId());
			message = writerMessageService.getWriterMessageById(writerMessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != writerMessage2) {
			logger.info("查询到了{}", message.toString());
		} else {
			logger.info("失败了");
		}
	}
}
