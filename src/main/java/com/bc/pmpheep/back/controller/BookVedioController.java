package com.bc.pmpheep.back.controller;


import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookVedio;
import com.bc.pmpheep.back.service.BookVedioService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.BookVedioVO;
import com.bc.pmpheep.back.vo.BookVedioVO2;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2018年2月6日 下午5:19:00
 *
 **/
@Controller
@RequestMapping("/bookVedio")
public class BookVedioController {
	
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "微视频";
		
	@Autowired
	private BookVedioService bookVedioService;
	/**
	 * 查询书籍视频
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:34:12
	 * @param pageSize
	 * @param pageNumber
	 * @param bookName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getVedioList", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询书籍视频列表")
	public ResponseBean<PageResult<BookVedioVO2>> getVedioList(
			Integer pageSize, 
			Integer pageNumber, 
			String bookName,
			Boolean isAuth,
			String  upLoadTimeStart,
			String  upLoadTimeEnd){
		pageSize   = (pageSize   == null || pageSize   <= 0 )? 5:pageSize;
		pageNumber = (pageNumber == null || pageNumber <= 0 )? 1:pageNumber;
		return new ResponseBean<PageResult<BookVedioVO2>>(
				bookVedioService.getVedioList(pageSize,pageNumber,bookName,isAuth,upLoadTimeStart,upLoadTimeEnd));
	}
	
	
	/**
	 * 查询书籍视频
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:34:12
	 * @param pageSize
	 * @param pageNumber
	 * @param bookName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getList", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询书籍视频列表")
	public ResponseBean<PageResult<BookVedioVO>> getList(Integer pageSize, Integer pageNumber, String bookName){
		pageSize   = (pageSize   == null || pageSize   <= 0 )? 5:pageSize;
		pageNumber = (pageNumber == null || pageNumber <= 0 )? 1:pageNumber;
		return new ResponseBean<PageResult<BookVedioVO>>(bookVedioService.getList(pageSize, pageNumber, bookName));
	}
	
	/**
	 * 审核视频
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:34:12
	 * @param pageSize
	 * @param pageNumber
	 * @param bookName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/audit", method = RequestMethod.PUT)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "审核视频")
	public ResponseBean<Integer> audit(HttpServletRequest request,Long id,Boolean isAuth){
		BookVedio bookVedio = new BookVedio();
		bookVedio
			.setId(id)
			.setAuthDate(new Date())
			.setAuthUserId(SessionUtil.getPmphUserBySessionId(CookiesUtil.getSessionId(request)).getId())
			.setIsAuth(isAuth==null ? false:isAuth); 
		return new ResponseBean<Integer>(bookVedioService.updateBookVedio(bookVedio));
	}
	
	/**
	 * 删除籍视频
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:34:12
	 * @param pageSize
	 * @param pageNumber
	 * @param bookName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteBookVedio", method = RequestMethod.PUT)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除视频")
	public ResponseBean<Integer> deleteBookVedio(Long id){
		return new ResponseBean<Integer>(bookVedioService.deleteBookVedioByIds(Arrays.asList(new Long[]{id})));
	}
	
	/**
	 * 保存籍视频信息
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月10日 下午5:34:12
	 * @param bookVedio
	 * @return
	 * @throws Exception 
	 * @throws CheckedServiceException 
	 */
	@ResponseBody
	@RequestMapping(value="/addBookVedio", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存籍视频信息")
	public ResponseBean<Integer> addBookVedio(HttpServletRequest request,BookVedio bookVedio) throws Exception{
		return new ResponseBean<Integer>(bookVedioService.addBookVedio(request,bookVedio));
	}
	
}
