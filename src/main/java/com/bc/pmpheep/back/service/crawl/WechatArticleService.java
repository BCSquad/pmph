/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.service.crawl;

import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.runnable.WechatArticle;
import com.bc.pmpheep.general.runnable.WechatArticleCrawlerTask;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import javax.annotation.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 微信公众号文章获取服务
 *
 * @author L.X <gugia@qq.com>
 */
@Service
public class WechatArticleService {

    @Resource(name = "taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;

    public String runCrawler(String url) throws CheckedServiceException {
        if (StringUtil.isEmpty(url)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WECHAT_ARTICLE,
                    CheckedExceptionResult.NULL_PARAM, "给定链接不能为空");
        }
        String guid = String.valueOf(System.currentTimeMillis()).concat(String.valueOf(RandomUtil.getRandomNum()));
        taskExecutor.execute(new WechatArticleCrawlerTask(new WechatArticle(guid, url)));
        return guid;
    }

	public WechatArticle get(String guid) throws CheckedServiceException{
		if(StringUtil.isEmpty(guid)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WECHAT_ARTICLE,
                    CheckedExceptionResult.NULL_PARAM, "文章唯一标识不能为空");
		}
		WechatArticle wechatArticle=Const.WACT_MAP.get(guid);
		if(null==wechatArticle){
			throw new CheckedServiceException(CheckedExceptionBusiness.WECHAT_ARTICLE,
                    CheckedExceptionResult.NULL_PARAM, "请先进行文章搜索");
		}
		return wechatArticle;
	}
}
