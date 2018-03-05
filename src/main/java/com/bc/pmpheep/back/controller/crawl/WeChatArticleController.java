package com.bc.pmpheep.back.controller.crawl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.crawl.WechatArticleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.runnable.WechatArticle;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 微信公众号文章抓取
 * 
 * @author tyc
 */
@Controller
@RequestMapping(value = "/cms/wechat")
@SuppressWarnings("all")
public class WeChatArticleController {

    private static final String BUSSINESS_TYPE = "文章管理";

    @Autowired
    WechatArticleService        wechatArticleService;

    /**
     * 启动微信公众号文章爬虫
     * 
     * @author tyc
     * @param url 获取文章的URL地址
     * @return 包含guid的ResponseBean对象
     * @createDate 2018年1月25日 上午10:12:37
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "启动人卫健康微信公众号文章同步")
    @RequestMapping(value = "/article/getArticle", method = RequestMethod.POST)
    public ResponseBean getArticle(@RequestParam("url") String url) {
        return new ResponseBean(wechatArticleService.runCrawler(url));
    }

    /**
     * 查询文章获取结果（仅供测试）
     * 
     * @author L.X
     * @param guid 任务唯一标识
     * @return 包含所有HTML元素的字符串
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "完成人卫健康微信公众号文章同步")
    @RequestMapping(value = "/article/{guid}", method = RequestMethod.GET)
    public ResponseBean getResult(@PathVariable("guid") String guid) {
        if (Const.WACT_MAP.containsKey(guid)) {
            WechatArticle wechatArticle = Const.WACT_MAP.get(guid);
            return new ResponseBean(wechatArticle);
        }
        return new ResponseBean(
                                new CheckedServiceException(
                                                            CheckedExceptionBusiness.WECHAT_ARTICLE,
                                                            CheckedExceptionResult.ILLEGAL_PARAM,
                                                            "非法的请求参数"));
    }

    /**
     * @author mr 查询人卫健康微信公众号文章
     * @param guid
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询人卫健康微信公众号文章")
    @RequestMapping(value = "/article/get", method = RequestMethod.POST)
    public void get(@RequestParam("guid") String guid, HttpServletResponse response) {
        WechatArticle wechatArticle = wechatArticleService.get(guid);
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.write(wechatArticle.getResult());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            new CheckedServiceException(CheckedExceptionBusiness.WECHAT_ARTICLE,
                                        CheckedExceptionResult.ILLEGAL_PARAM, "非法的请求参数");
        }
    }

    /**
     * @author 抓取文章同步到数据库
     * @param guid
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "抓取文章同步到数据库")
    @RequestMapping(value = "/article/synchro", method = RequestMethod.GET)
    public ResponseBean synchro(@RequestParam("guid") String guid,HttpServletRequest request) throws IOException {
        return new ResponseBean(wechatArticleService.synchroCmsContent(guid,request));
    }
}
