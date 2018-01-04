package com.bc.pmpheep.back.controller.baiducount;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.HttpUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：百度统计控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-28
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/baidu")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaiduCountController {
	@Autowired
	BookService bookService;
	Logger logger = LoggerFactory.getLogger(BaiduCountController.class);
	// 站点列表
	private static String getSiteList = "https://api.baidu.com/json/tongji/v1/ReportService/getSiteList";
	// 站点数据
	private static String getData = "https://api.baidu.com/json/tongji/v1/ReportService/getData";
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "流量概况";

	/**
	 * 
	 * <pre>
	 * 功能描述：获取网站概况(趋势数据)
	 * 使用示范：
	 *
	 * @param method 请求方法
	 * @param startDate 对比查询起始时间
	 * @param endDate 对比查询结束时间
	 * @param metrics 所要获取的指标
	 * @param siteId 
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取网站概况(趋势数据)")
	@RequestMapping(value = "/rpt/trend", method = RequestMethod.GET)
	public ResponseBean trend(@RequestParam("method") String method, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("metrics") String metrics,
			@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize,
			@RequestParam("siteId") String siteId) {
		return new ResponseBean(HttpUtil.doPostSSL(getData,
				requestJson(method, startDate, endDate, metrics, pageNum, pageSize, siteId)));
	}

	/**
	 * 
	 * 
	 * 功能描述：获取图书偏好分析
	 * 
	 * @param pageSize
	 *            当前页的数据条数
	 * @param pageNumber
	 *            当前页数
	 * @param bookname
	 *            书籍名称/isbn
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书偏好分析")
	@RequestMapping(value = "/list/bookPreference", method = RequestMethod.GET)
	public ResponseBean bookPreference(Integer pageSize, Integer pageNumber, String bookname, Long type, String path) {
		PageParameter<BookPreferenceAnalysisVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		BookPreferenceAnalysisVO bookPreferenceAnalysisVO = new BookPreferenceAnalysisVO();
		bookPreferenceAnalysisVO.setBookname(bookname);
		bookPreferenceAnalysisVO.setType(type);
		bookPreferenceAnalysisVO.setPath(path);
		pageParameter.setParameter(bookPreferenceAnalysisVO);
		return new ResponseBean(bookService.getBookPreferenceAnalysis(pageParameter));
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：配置登陆、请求参数
	 * 使用示范：
	 *
	 * @param method 请求方法
	 * @param startDate 查询起始时间
	 * @param endDate 查询结束时间
	 * @param metrics 所要获取的指标
	 * @param siteId 
	 * @param pageNum 页码
	 * @param pageSize 显示条数
	 * @return
	 * </pre>
	 */
	private String requestJson(String method, String startDate, String endDate, String metrics, String pageNum,
			String pageSize, String siteId) {
		// 默认前一个月
		String start_date = DateUtil.date2Str(DateUtil.getDateBefore(new Date(), 30), "yyyyMMdd");
		if (StringUtil.notEmpty(startDate)) {
			start_date = startDate;
		}
		// 默认当前时间
		String end_date = DateUtil.getDays();
		if (StringUtil.notEmpty(endDate)) {
			end_date = endDate;
		}
		// 默认方法
		String methods = "overview/getTimeTrendRpt";
		if (StringUtil.notEmpty(method)) {
			methods = method;
		}
		// siteId
		String site_id = "11461884";
		if (StringUtil.notEmpty(siteId)) {
			site_id = siteId;
		}
		/**
		 * 默认对比指标
		 * 
		 * pv_count(浏览量(PV))
		 * 
		 * visitor_count(访客数(UV))
		 * 
		 * ip_count(IP数)
		 * 
		 * bounce_ratio(跳出率，%)
		 * 
		 * avg_visit_time(平均访问时长，秒)
		 */
		String metric = "pv_count,visitor_count,ip_count,bounce_ratio,avg_visit_time";
		if (StringUtil.notEmpty(metrics)) {
			metric = metrics;
		}
		// 页码
		String page_num = "0";
		if (StringUtil.notEmpty(pageNum)) {
			page_num = pageNum;
		}
		// 显示条数
		String page_size = "0";
		if (StringUtil.notEmpty(pageSize)) {
			page_size = pageSize;
		}
		// 登陆配置
		Map<String, String> header = new LinkedHashMap<String, String>();
		header.put("account_type", "1");
		header.put("password", "Zeng1314");
		header.put("token", "eb12db768a8b4d487cd69768e8d06781");
		header.put("username", "a406317048");
		// 请求参数
		Map<String, String> body = new LinkedHashMap<String, String>();
		body.put("siteId", site_id);
		body.put("method", methods);
		body.put("start_date", start_date);
		body.put("end_date", end_date);
		body.put("metrics", metric);
		logger.info("请求的method为：{}", methods);
		logger.info("请求的start_date为：{}", start_date);
		logger.info("请求的end_date为：{}", end_date);
		logger.info("请求的metrics为：{}", metric);
		// 分页
		body.put("start_index", page_num);
		body.put("max_results", page_size);
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("header", header);
		params.put("body", body);
		// Map 转Json
		String returnJson = JSON.toJSONString(params);
		logger.info("转换结果为:{}", returnJson);
		return returnJson;
	}
}
