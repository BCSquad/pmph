/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.service.crawl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.RandomUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.runnable.DisableSSLCertificateCheckUtil;
import com.bc.pmpheep.general.runnable.WechatArticle;
import com.bc.pmpheep.general.runnable.WechatArticleCrawlerTask;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
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
    
    // 获取img标签正则 
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    
    @Autowired
    CmsContentService cmsContentService;
    @Autowired
	ContentService contentService;
    @Autowired
    private FileService fileService;
    
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
		return wechatArticle;
	}

	public CmsContent updateCmsContent(String guid) throws IOException {
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
            String contents = content.replace("data-src", "src"); // 替换内容
            //获取图片标签 
            List<String> imgUrl = getImageUrl(contents); 
            //获取图片src地址 
            List<String> imgSrc = getImageSrc(imgUrl); 
            //下载图片
            List<String> mongoImgs = Download(imgSrc);
            for (int i = 0; i < imgSrc.size(); i++) {
            	if (StringUtil.notEmpty(mongoImgs.get(i))) {
            		String imgsId = RouteUtil.MONGODB_FILE + mongoImgs.get(i); // 下载路径
            		contents = contents.replace(imgSrc.get(i), imgsId);	
            	}
            }
            if (StringUtil.isEmpty(contents)) {
    			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, 
    					CheckedExceptionResult.NULL_PARAM, "内容参数为空");
    		}
            // MongoDB内容插入
    		Content contentObj = contentService.add(new Content(contents));
    		if (StringUtil.isEmpty(contentObj.getId())) {
    			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, 
    					CheckedExceptionResult.PO_ADD_FAILED, "Content对象内容保存失败");
    		}
            cmsContent.setParentId(0L); // 上级id（0为内容）
            cmsContent.setPath("0"); // 根节点路径
            cmsContent.setMid(contentObj.getId()); // 内容id
            cmsContent.setCategoryId(Const.CMS_CATEGORY_ID_1); // 内容类型（1=随笔文章）
            cmsContent.setTitle(title.trim());
            cmsContent.setAuthorType((short) 0); // 作者类型
        }
		//防止map内存溢出，操作过后就移除
		Const.WACT_MAP.remove("guid");
		return cmsContentService.addCmsContent(cmsContent);
	}
	
	  /*** 
	   * 获取ImageUrl地址 
	   * 
	   * @param HTML 
	   * @return 
	   */
	  private List<String> getImageUrl(String HTML) { 
	    Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML); 
	    List<String> listImgUrl = new ArrayList<String>(); 
	    while (matcher.find()) { 
	      listImgUrl.add(matcher.group()); 
	    } 
	    return listImgUrl; 
	  } 
	  
	  /*** 
	   * 获取ImageSrc地址 
	   * 
	   * @param listImageUrl 
	   * @return 
	   */
	  private List<String> getImageSrc(List<String> listImageUrl) { 
	    List<String> listImgSrc = new ArrayList<String>(); 
	    for (String image : listImageUrl) {
            String srcStart = "src=\"";
            String srcEnd = "?";
            int srcS = image.indexOf(srcStart) + srcStart.length();
            int srcE = image.indexOf(srcEnd);
            if (srcE < srcS) {
            	continue;
            }
            String content = image.substring(srcS, srcE);
            listImgSrc.add(content);
	    } 
	    return listImgSrc; 
	  }
	  
	  /*** 
	   * 下载图片 
	   * 
	   * @param listImgSrc 
	 * @throws IOException 
	   */
	  private List<String> Download(List<String> listImgSrc) throws IOException { 
	    	List<String> listHtmlImgs = new ArrayList<String>();
	      for (String url : listImgSrc) { 
	        URL uri = new URL(url);
	        DisableSSLCertificateCheckUtil.disableChecks();
	        //打开链接  
	        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();  
	        //设置请求方式为"GET"  
	        conn.setRequestMethod("GET");  
	        //超时响应时间为5秒  
	        conn.setConnectTimeout(5 * 1000);  
	        //通过输入流获取图片数据  
	        InputStream in = conn.getInputStream();
	        double randomNumber = Math.random()*100; // 随机数
	        Random rand = new Random();
			String mongoId = null;
			if (in.available() != 0) {
				mongoId = fileService.save(in, String.valueOf(randomNumber), FileType.CMS_IMG, 
						(long) rand.nextInt(900)+ 100);
			}
			listHtmlImgs.add(mongoId);
			in.close();
	      }
		return listHtmlImgs; 
	  } 
}
