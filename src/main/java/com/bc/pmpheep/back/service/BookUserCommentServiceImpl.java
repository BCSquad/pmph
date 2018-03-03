package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.dao.BookUserCommentDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookUserComment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.BookUserCommentVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：图书评论的业务实现
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
@Service
public class BookUserCommentServiceImpl extends BaseService implements BookUserCommentService {

	@Autowired
	BookUserCommentDao bookUserCommentDao;
	@Autowired
	private BookService bookService;
	@Autowired
	WriterUserTrendstService writerUserTrendstService;

	@Override
	public PageResult<BookUserCommentVO> listBookUserComment(PageParameter<BookUserCommentVO> pageParameter)
			throws CheckedServiceException {
		PageResult<BookUserCommentVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = bookUserCommentDao.getBookUserCommentTotal(pageParameter);
		if (total > 0) {
			List<BookUserCommentVO> list = bookUserCommentDao.listBookUserComment(pageParameter);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String updateBookUserCommentByAuth(Long[] ids, Integer isAuth, String sessionId)
			throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"评论id为空");
		}
		if (ObjectUtil.isNull(isAuth)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"审核内容为空");
		}
		int num = 0;
		for (Long id : ids) {
			BookUserComment bookUserComment = bookUserCommentDao.getBookUserCommentById(id);
			if (bookUserComment.getIsAuth() == 1) {
				throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.ILLEGAL_PARAM,
						"有已经审核的评论了");
			}
			if (isAuth == 1) {
				WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
				writerUserTrendst.setUserId(bookUserComment.getWriterId());
				writerUserTrendst.setType(5);
				writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
			}
			bookUserComment.setId(id);
			bookUserComment.setIsAuth(isAuth);
			bookUserComment.setAuthUserId(pmphUser.getId());
			bookUserComment.setAuthDate(DateUtil.getCurrentTime());
			num += bookUserCommentDao.updateBookUserComment(bookUserComment);
			if (isAuth == 1) {
				// 更新评分
				bookService.updateBookCore(bookUserComment.getBookId());
				// 更新书的评论数
				bookService.updateUpComments(bookUserComment.getBookId());
			}
		}
		if (num > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public String deleteBookUserCommentById(Long[] ids) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"评论id为空");
		}
		String result = "FAIL";
		for (Long id : ids) {
			BookUserComment bookUserComment = bookUserCommentDao.getBookUserCommentById(id);
			Long bookId = bookUserComment.getBookId();
			if (bookUserComment.getIsAuth() == 1) {
				bookService.updateDownComments(bookId);
			}
		}
		int num = bookUserCommentDao.deleteBookUserCommentById(ids);
		if (num > 0) {
			result = "SUCCESS";
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "删除失败");
		}
		return result;
	}

	@Override
	public String updateBookUserComment(Long[] ids, Boolean isStick, Boolean isPromote, Boolean isHot, Integer sort,
			Integer sortPromote, Integer sortHot) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"评论id为空");
		}
		String result = "FAIL";
		int num = 0;
		for (Long id : ids) {
			BookUserComment bookUserComment = new BookUserComment();
			bookUserComment.setId(id);
			bookUserComment.setIsHot(isHot);
			bookUserComment.setIsPromote(isPromote);
			bookUserComment.setIsStick(isStick);
			bookUserComment.setSort(sort);
			bookUserComment.setSortHot(sortHot);
			bookUserComment.setSortPromote(sortPromote);
			num += bookUserCommentDao.updateBookUserComment(bookUserComment);
		}
		if (num > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public PageResult<BookUserCommentVO> listBookUserCommentAdmin(PageParameter<BookUserCommentVO> pageParameter) {
		PageResult<BookUserCommentVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = bookUserCommentDao.getBookUserCommentAdminTotal(pageParameter);
		if (total > 0) {
			List<BookUserCommentVO> list = bookUserCommentDao.listBookUserCommentAdmin(pageParameter);
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

}
