package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.BookService;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/analysis")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataAnalysisController {
	@Autowired
	BookService bookService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "数据分析";

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
	 *            书籍名称
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取图书偏好分析")
	@RequestMapping(value = "/list/bookPreference", method = RequestMethod.GET)
	public ResponseBean bookPreference(Integer pageSize, Integer pageNumber, String bookname) {
		PageParameter<BookPreferenceAnalysisVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		BookPreferenceAnalysisVO bookPreferenceAnalysisVO = new BookPreferenceAnalysisVO();
		bookPreferenceAnalysisVO.setBookname(bookname);
		pageParameter.setParameter(bookPreferenceAnalysisVO);
		return new ResponseBean(bookService.getBookPreferenceAnalysis(pageParameter));
	}

}
