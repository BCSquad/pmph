package com.bc.pmpheep.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.BookUserComment;
import com.bc.pmpheep.back.service.BookUserCommentService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.BookUserCommentVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/bookusercomment")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BookUserCommentController {

	@Autowired
	BookUserCommentService bookUserCommentService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "评论审核";

	/**
	 * 
	 * 
	 * 功能描述：分页初始化/模糊查询图书评论
	 *
	 * @param pageSize
	 *            当页的数据条数
	 * @param pageNumber
	 *            当前页码
	 * @param name
	 *            数据名称/isbn
	 * @param isAuth
	 *            是否通过审核
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分页初始化/模糊查询图书评论")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(Integer pageSize, Integer pageNumber, String name, Integer isAuth, Boolean isLong) {
		PageParameter<BookUserCommentVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		BookUserCommentVO bookUserCommentVO = new BookUserCommentVO();
		bookUserCommentVO.setIsAuth(isAuth);
		bookUserCommentVO.setName(name.replaceAll(" ", ""));// 去除空格
		bookUserCommentVO.setIsLong(isLong);
		pageParameter.setParameter(bookUserCommentVO);
		return new ResponseBean(bookUserCommentService.listBookUserComment(pageParameter));
	}

	/**
	 * 
	 * 
	 * 功能描述：批量审核图书评论
	 *
	 * @param ids
	 *            审核的评论id
	 * @param isAuth
	 *            是否通过
	 * @param sessionId
	 *            当前用户信息
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量审核图书评论")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(Long[] ids, Integer isAuth, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(bookUserCommentService.updateBookUserCommentByAuth(ids, isAuth, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：批量删除图书评论
	 *
	 * @param ids
	 *            评论id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除图书评论")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseBean delete(Long[] ids) {
		return new ResponseBean(bookUserCommentService.deleteBookUserCommentById(ids));
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "置顶、热门、精品推荐书评")
	@RequestMapping(value = "/bookUserComment", method = RequestMethod.PUT)
	public ResponseBean bookUserComment(BookUserComment bookUserComment) {
		return new ResponseBean();

	}
}