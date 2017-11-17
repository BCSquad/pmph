/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service.test;

import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MongoDB ContentService单元测试
 *
 * @author L.X <gugia@qq.com>
 */
public class ContentServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(ContentServiceTest.class);
    final String html = "<p class=\"content-text\"><span class=\"content-text-num\">13352</span>&nbsp;名专业译员&nbsp;"
            + ",&nbsp;<span class=\"content-text-num\">2528</span>&nbsp;名母语审校</p>";

    @Resource
    ContentService contentService;

    @Test
    @SuppressWarnings("deprecation")
    public void add() {
        Content content = new Content(html);
        Content cnt = contentService.add(content);
        contentService.removeAll();
        logger.info(cnt.getContent());
        Assert.assertNotNull("插入内容后返回的Content.id不应为空", cnt.getId());
        logger.info(cnt.getId());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void get() {
        Content content = new Content(html);
        Content cnt = contentService.add(content);
        Assert.assertNotNull("插入内容后返回的Content.id不应为空", cnt.getId());
        content = contentService.get(cnt.getId());
        contentService.removeAll();
        Assert.assertTrue("获取到的内容和插入时不一致", html.equals(content.getContent()));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void update() {
        Content content = new Content(html);
        Content cnt = contentService.add(content);
        Assert.assertNotNull("插入内容后返回的Content.id不应为空", cnt.getId());
        cnt.setContent("Do you LOVE me?");
        contentService.update(cnt);
        content = contentService.get(cnt.getId());
        contentService.removeAll();
        logger.info("更新后的Message对象 id={}， content={}", content.getId(), content.getContent());
        Assert.assertTrue("更新后的消息内容不正确", cnt.getContent().equals(content.getContent()));
    }

    @Test
    public void delete() {
        Content content = new Content(html);
        Content cnt = contentService.add(content);
        Assert.assertNotNull("插入内容后返回的Content.id不应为空", cnt.getId());
        contentService.delete(cnt.getId());
        Assert.assertNull("删除内容失败", contentService.get(cnt.getId()));
    }
}
