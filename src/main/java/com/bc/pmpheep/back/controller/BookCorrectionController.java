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

	/**
	 * 获取审核纠错页面的分页数据
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 下午3:20:15
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param bookname
	 * @param result
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取审核纠错页面的分页数据")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false)    Integer pageNumber,
			@RequestParam(value = "pageSize",   required = false)	Integer pageSize,
			@RequestParam(value = "bookname",   required = false)	String bookname ,
			@RequestParam(value = "isOver",     required = false)	Boolean isOver,
			@RequestParam(value = "result",     required = false)	Boolean result) {
		return new ResponseBean(bookCorrectionService.listBookCorrectionAudit(request,pageNumber,pageSize,bookname ,isOver,result));
	}
	
	/**
	 * 获取审核纠错跟踪页面的分页数据
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 下午3:20:15
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param bookname
	 * @param isEditorReplied
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取审核纠错跟踪页面的分页数据")
	@RequestMapping(value = "/listTrack", method = RequestMethod.GET)
	public ResponseBean listTrack(HttpServletRequest request,
			@RequestParam(value = "pageNumber", 		 required = false)  Integer pageNumber,
			@RequestParam(value = "pageSize",            required = false)	Integer pageSize,
			@RequestParam(value = "bookname",            required = false)	String  bookname ,
			@RequestParam(value = "isEditorReplied",     required = false)	Boolean isEditorReplied) {
		return new ResponseBean(bookCorrectionService.listBookCorrectionTrack(request,pageNumber,pageSize,bookname ,isEditorReplied));
	}
	
	/**
	 * 回复作家用户
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 下午4:05:21
	 * @param request
	 * @param id
	 * @param result
	 * @param editorReply
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "回复作家用户")
	@RequestMapping(value = "/replyWriter", method = RequestMethod.PUT)
	public ResponseBean replyWriter(HttpServletRequest request,
			@RequestParam(value = "id", 		         required = true)  Long id ,
			@RequestParam(value = "result", 		     required = true)  Boolean result , 
			@RequestParam(value = "editorReply", 		 required = true)  String editorReply) {
		return new ResponseBean(bookCorrectionService.replyWriter(id , result , editorReply));
	}
	
	/**
	 * 将状态更新至受理中
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月20日 下午4:05:29
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "将状态更新至受理中")
	@RequestMapping(value = "/updateToAcceptancing", method = RequestMethod.PUT)
	public ResponseBean updateToAcceptancing(HttpServletRequest request,
			@RequestParam(value = "id", 		         required = true)  Long id ) {
		return new ResponseBean(bookCorrectionService.updateToAcceptancing(id ));
	}
	

	
}
