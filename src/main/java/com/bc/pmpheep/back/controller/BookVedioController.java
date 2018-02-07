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
import com.bc.pmpheep.controller.bean.ResponseBean;

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
	
}
