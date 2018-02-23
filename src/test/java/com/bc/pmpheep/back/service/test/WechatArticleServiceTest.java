/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.service.test;

import com.bc.pmpheep.back.service.crawl.WechatArticleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.general.runnable.WechatArticle;
import com.bc.pmpheep.test.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WechatArticleService测试类
 *
 * @author L.X <gugia@qq.com>
 */
public class WechatArticleServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(WechatArticleServiceTest.class);

    @Resource
    WechatArticleService WechatArticleService;

    @Test
    public void getArticle() throws Exception {
        String url = "https://mp.weixin.qq.com/s?__biz=MzA4NTQwNDcyMA==&mid=2650661978&idx=1&sn=2f5329f5b2bfda7050822cc5e3a4f03f&scene=21#wechat_redirect";
        String guid = WechatArticleService.runCrawler(url);
        logger.info("公众号文章id={}", guid);
        int count = 0;
        while (!Const.WACT_MAP.containsKey(guid)) {
            Thread.sleep(2000);
            count++;
            if (count > 4) {
                throw new Exception("可能是网络原因导致的公众号文章爬取失败");
            }
        }
        WechatArticle wechatArticle = Const.WACT_MAP.get(guid);
        logger.info(wechatArticle.getDone() ? "文章同步完成" : "文章同步未完成");
        if (wechatArticle.getError()) {
            logger.info("文章同步过程中出现问题");
        }
        logger.info("---以下为文章抓取内容---");
        logger.info(wechatArticle.getResult());
    }
}
