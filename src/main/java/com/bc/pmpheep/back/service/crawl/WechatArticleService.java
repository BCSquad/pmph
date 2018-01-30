/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.service.crawl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.runnable.WechatArticle;
import com.bc.pmpheep.general.runnable.WechatArticleCrawlerTask;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 微信公众号文章获取服务
 *
 * @author L.X <gugia@qq.com>
 */
@Service
public class WechatArticleService {

    @Resource(name = "taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    CmsContentService cmsContentService;
    @Autowired
	ContentService contentService;
    
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
                    CheckedExceptionResult.NULL_PARAM, "文章唯一标识不正确或未获取微信公众号文章");
		}
		//防止map内存溢出，操作过后就移除
		//Const.WACT_MAP.remove("guid");
		return wechatArticle;
	}

	public CmsContent updateCmsContent(String guid) {
		CmsContent cmsContent = new CmsContent();
		if(StringUtil.isEmpty(guid)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WECHAT_ARTICLE,
                    CheckedExceptionResult.NULL_PARAM, "文章唯一标识不能为空");
		}
		if (Const.WACT_MAP.containsKey(guid)) {
            WechatArticle wechatArticle = Const.WACT_MAP.get(guid);
            String html = wechatArticle.getResult();
            String titleStart = "<h2 class=\"rich_media_title\" id=\"activity-name\">";
            String titleEnd = "</h2>";
            int titleS = html.indexOf(titleStart) + titleStart.length();
            int titleE = html.indexOf(titleEnd);
            String title = html.substring(titleS, titleE); // 获取标题
            String contentStart = "<div class=\"rich_media_content \" id=\"js_content\">";
            String contentEnd = "</div>";
            int contentS = html.indexOf(contentStart) + contentStart.length();
            int contentE = html.lastIndexOf(contentEnd);
            String content = html.substring(contentS, contentE); // 获取内容
            if (StringUtil.isEmpty(content)) {
    			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, 
    					CheckedExceptionResult.NULL_PARAM, "内容参数为空");
    		}
            // MongoDB内容插入
    		Content contentObj = contentService.add(new Content(content));
    		if (StringUtil.isEmpty(contentObj.getId())) {
    			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, 
    					CheckedExceptionResult.PO_ADD_FAILED, "Content对象内容保存失败");
    		}
            cmsContent.setParentId(0L); // 上级id（0为内容）
            cmsContent.setPath("0"); // 根节点路径
            cmsContent.setMid(contentObj.getId()); // 内容id
            cmsContent.setCategoryId(1L); // 内容类型（1=随笔文章）
            cmsContent.setCategoryId(Const.CMS_CATEGORY_ID_1);
            cmsContent.setTitle(title.trim());
            cmsContent.setAuthorType((short) 0); // 作者类型
        }
		return cmsContentService.addCmsContent(cmsContent);
	}
}
