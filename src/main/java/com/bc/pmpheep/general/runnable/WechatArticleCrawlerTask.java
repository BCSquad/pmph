/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.runnable;

import com.bc.pmpheep.back.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信公众号爬虫任务
 *
 * @author L.X <gugia@qq.com>
 */
public class WechatArticleCrawlerTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(WechatArticleCrawlerTask.class);
    WechatArticle wechatArticle;

    public WechatArticleCrawlerTask(WechatArticle wechatArticle) {
        this.wechatArticle = wechatArticle;
    }

    @Override
    public void run() {
        WechatArticleCrawler crawler = new WechatArticleCrawler(wechatArticle);
        try {
            crawler.start(1);
        } catch (Exception ex) {
            logger.error("微信公众号文章获取出现错误，错误信息：{}", ex.getMessage());
            wechatArticle.setDone(true);
            wechatArticle.setError(true);
            Const.WACT_MAP.put(wechatArticle.getGuid(), wechatArticle);
        }
    }

}
