package com.bc.pmpheep.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.TopicService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * 
 * 功能描述：选题申报控制层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Controller
@RequestMapping(value = "/topic")
public class TopicController {
	@Autowired
	TopicService topicService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "选题申报";

	/**
	 * 
	 * 
	 * 功能描述：维护人员选题申报审核页面
	 *
	 * @param request
	 * @param pageSize
	 *            当前页条数
	 * @param pageNumber
	 *            当前页数
	 * @param bookname
	 *            选题名称
	 * @param submitTime
	 *            提交时间
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "维护人员查询可操作的选题")
	@RequestMapping(value = "/listOpts", method = RequestMethod.GET)
	public ResponseBean listOpts(HttpServletRequest request, Integer pageSize, Integer pageNumber, String bookname,
			String submitTime) {
		PageParameter<TopicOPtsManagerVO> pageParameter = new PageParameter(pageNumber, pageSize);
		TopicOPtsManagerVO topicOPtsManagerVO = new TopicOPtsManagerVO();
		topicOPtsManagerVO.setBookname(bookname);
		topicOPtsManagerVO.setSubmitTime(DateUtil.str2Timestam(submitTime));
		String sessionId = CookiesUtil.getSessionId(request);
		pageParameter.setParameter(topicOPtsManagerVO);
		return new ResponseBean(topicService.listOpts(sessionId, pageParameter));
	}
}
