/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service.test;

import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.test.BaseTest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MongoDB MessageService单元测试
 *
 * @author L.X <gugia@qq.com>
 */
public class MessageServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(MessageServiceTest.class);
    final String str = "I Love YOU";

    @Resource
    MessageService messageService;

    @Test
    @SuppressWarnings("deprecation")
    public void add() {
        Message message = new Message(str);
        Message msg = messageService.add(message);
        messageService.removeAll();
        logger.info(msg.getContent());
        Assert.assertNotNull("插入消息后返回的Message.id不应为空", msg.getId());
        logger.info(msg.getId());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void get() {
        Message message = new Message(str);
        Message msg = messageService.add(message);
        Assert.assertNotNull("插入消息后返回的Message.id不应为空", msg.getId());
        message = messageService.get(msg.getId());
        messageService.removeAll();
        Assert.assertTrue("获取到的消息内容和插入时不一致", str.equals(message.getContent()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void list() {
        int number = 10;
        List<Message> list = createMessages(number);
        List<String> ids = new ArrayList<>();
        for (Message message : list) {
            Message added = messageService.add(message);
            ids.add(added.getId());
        }
        list = messageService.list(ids);
        messageService.removeAll();
        Assert.assertTrue("插入消息后返回的集合大小应为" + String.valueOf(number), list.size() == number);
        for (Message message : list) {
            logger.info("取得的List<Message>包含对象：id={}， content={}", message.getId(), message.getContent());
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    public void update() {
        Message message = new Message(str);
        Message msg = messageService.add(message);
        Assert.assertNotNull("插入消息后返回的Message.id不应为空", msg.getId());
        msg.setContent("Do you LOVE me?");
        messageService.update(msg);
        message = messageService.get(msg.getId());
        messageService.removeAll();
        logger.info("更新后的Message对象 id={}， content={}", message.getId(), message.getContent());
        Assert.assertTrue("更新后的消息内容不正确", msg.getContent().equals(message.getContent()));
    }

    private List<Message> createMessages(int number) {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Message message = new Message(str + " " + String.valueOf(i));
            list.add(message);
        }
        return list;
    }
}
