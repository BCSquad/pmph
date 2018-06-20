package com.bc.pmpheep.back.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.vo.BookFeedBack;
import com.bc.pmpheep.general.controller.FileDownLoadController;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.ExcelHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.BookCorrectionService;
import com.bc.pmpheep.controller.bean.ResponseBean;

import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/bookCorrection")
@SuppressWarnings({"rawtypes","unchecked"})
public class BookCorrectionController {

	@Autowired
	BookCorrectionService bookCorrectionService;
	@Resource
	ExcelHelper excelHelper;
	Logger logger = LoggerFactory.getLogger(BookCorrectionController.class);
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
	 * 读书反馈
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @param result
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取读书反馈页面的分页数据")
	@RequestMapping(value = "/bookList", method = RequestMethod.GET)
	public ResponseBean bookList(HttpServletRequest request,
							 @RequestParam(value = "pageNumber", required = false)    Integer pageNumber,
							 @RequestParam(value = "pageSize",   required = false)	Integer pageSize,
							 @RequestParam(value = "result",     required = false)	Boolean result) {
		return new ResponseBean(bookCorrectionService.bookFeedBaskList(request,pageNumber,pageSize,result));
	}



	/**
	 * 根据id获取审核详情
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年1月29日 上午10:03:08
	 * @param id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取审核纠错详情")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseBean detail( @RequestParam(value = "id",  required = true)	Long id) {
		return new ResponseBean(bookCorrectionService.getBookCorrectionAuditDetailById(id));
	}

	/**
	 * 读书反馈
	 * @param id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取审核读书反馈详情")
	@RequestMapping(value = "/bookFeekBackDetail", method = RequestMethod.GET)
	public ResponseBean bookFeekBackDetail( @RequestParam(value = "id",  required = true)	Long id) {
		return new ResponseBean(bookCorrectionService.getBookFeedBackDetailById(id));
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
			@RequestParam(value = "isEditorReplied",     required = false)	Boolean isEditorReplied,
			@RequestParam(value = "result",     required = false)	Boolean result) {
		return new ResponseBean(bookCorrectionService.listBookCorrectionTrack(request,pageNumber,pageSize,bookname ,isEditorReplied,result));
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
			@RequestParam(value = "authorReply", 		 required = false)  String authorReply,
			@RequestParam(value = "editorReply", 		 required = true)  String editorReply) {
		return new ResponseBean(bookCorrectionService.replyWriter(id , result , editorReply,authorReply));
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "读者反馈")
	@RequestMapping(value = "/replyBookFeedBackWriter", method = RequestMethod.PUT)
	public ResponseBean replyBookFeedBackWriter(HttpServletRequest request,
									@RequestParam(value = "id", 		         required = true)  Long id ,
									@RequestParam(value = "authorReply", 		 required = true)  String authorReply) {
		return new ResponseBean(bookCorrectionService.replyBookFeedBackWriter(id, authorReply,request));
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
