package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookUserComment;
import com.bc.pmpheep.back.vo.BookUserCommentVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：图书评论业务层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年10月27日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public interface BookUserCommentService {

	/**
	 * 
	 * 
	 * 功能描述：分页初始化/模糊查询图书评论列表
	 *
	 * @param pageParameter
	 *            分页条件 ， name isbn/书籍名称 ， isAuth 是否通过审核
	 * @return 分好页的结果集
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<BookUserCommentVO> listBookUserComment(PageParameter<BookUserCommentVO> pageParameter)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：审核评论
	 *
	 * @param ids
	 *            审核的评论id
	 * @param isAuth
	 *            是否通过
	 * @param sessionId
	 *            当前用户信息
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String updateBookUserCommentByAuth(Long[] ids, Integer isAuth,String reason,String[] writerId,String[] bookname,String[] content, String sessionId) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：删除图书评论
	 * 
	 * @param ids
	 *            需要删除的评论id
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String deleteBookUserCommentById(Long[] ids) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：置顶、热门、精品推荐书评
	 *
	 * @param bookUserComment
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	String updateBookUserComment(Long[] ids, Boolean isStick, Boolean isPromote, Boolean isHot, Integer sort,
			Integer sortPromote, Integer sortHot) throws CheckedServiceException;
	
	/**
	 * admin 查询图书评论
	 * @param pageParameter
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<BookUserCommentVO> listBookUserCommentAdmin(PageParameter<BookUserCommentVO> pageParameter) 
			throws CheckedServiceException;
}
