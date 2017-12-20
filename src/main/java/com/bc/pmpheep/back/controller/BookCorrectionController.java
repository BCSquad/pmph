package com.bc.pmpheep.back.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.BookCorrectionService;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/bookCorrection")
@SuppressWarnings({"rawtypes","unchecked"})
public class BookCorrectionController {

	@Autowired
	BookCorrectionService bookCorrectionService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "图书纠错";

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取审核纠错页面的分页数据")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false)    Integer pageNumber,
			@RequestParam(value = "pageSize",   required = false)	Integer pageSize,
			@RequestParam(value = "bookname",   required = false)	String bookname ,
			@RequestParam(value = "result",     required = false)	Boolean result) {
		return new ResponseBean(bookCorrectionService.listBookCorrectionAudit(request,pageNumber,pageSize,bookname ,result));
	}

	
}
