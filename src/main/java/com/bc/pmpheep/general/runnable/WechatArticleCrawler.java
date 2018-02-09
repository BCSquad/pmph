/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.runnable;

import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import com.bc.pmpheep.back.util.Const;

/**
 * 微信公众号文章爬虫
 * 
 * @author L.X <gugia@qq.com>
 */
public class WechatArticleCrawler extends BreadthCrawler {

    WechatArticle wechatArticle;

    public WechatArticleCrawler(WechatArticle wechatArticle) {
        super(wechatArticle.getGuid(), false);
        this.wechatArticle = wechatArticle;
        this.addSeed(wechatArticle.getUrl());
        setThreads(1);
        getConf().setTopN(100);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        Elements all = page.select("div[id=js_article]");
        Elements img = page.select("div[id=img-content]");
        LOG.info(all.toString());
        LOG.info(img.toString());
        wechatArticle.setImg(img.toString());
        wechatArticle.setDone(true);
        wechatArticle.setResult(all.toString());
        Const.WACT_MAP.put(wechatArticle.getGuid(), wechatArticle);
    }
}
