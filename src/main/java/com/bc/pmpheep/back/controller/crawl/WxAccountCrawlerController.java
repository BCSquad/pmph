package com.bc.pmpheep.back.controller.crawl;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.nextfilter.HashSetNextFilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.crawl.WxAccountCrawlerService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 微信公众号文章抓取
 *
 * @author tyc
 */
@Controller
@RequestMapping(value = "/accountCrawler")
@SuppressWarnings("all")
public class WxAccountCrawlerController {

	private final String Crawler = "文章抓取";
	
	@Autowired
	WxAccountCrawlerService wxAccountCrawlerService;
	
	/**
     * 文章抓取
     * 
     * @author tyc
     * @createDate 2018年1月25日 上午10:12:37
     * @param 
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = Crawler, logRemark = "文章抓取")
    @RequestMapping(value = "/Crawler", method = RequestMethod.GET)
    public ResponseBean Crawler(@RequestParam("url") Long url) 
    		throws CheckedServiceException, IOException {
        return new ResponseBean();
    }

}