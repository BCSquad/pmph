package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookUserCommentDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookUserComment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.po.WriterPointLog;
import com.bc.pmpheep.back.po.WriterPointRule;
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
	@Autowired
	WriterPointRuleService writerPointRuleService;
	@Autowired
	WriterPointLogService writerPointLogService;
	@Autowired
	WriterPointService writerPointService;

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
			if (bookUserComment.getIsAuth() != 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.ILLEGAL_PARAM,
						"有已经审核的评论了");
			}
			if (isAuth == 1) {
				WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
				writerUserTrendst.setUserId(bookUserComment.getWriterId());
				writerUserTrendst.setType(5);
				writerUserTrendst.setBookId(bookUserComment.getBookId());
				writerUserTrendst.setBookCommentId(id);
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
			// 当用户评论过后 增加相应积分
			if (isAuth == 1) {
				String ruleName = "图书评论";
				// 获取积分规则
				WriterPointRule writerPointRuleVOs = writerPointRuleService.getWriterPointRuleByName(ruleName);
				if (null != writerPointRuleVOs) {
					// 查询用户评论之前的积分值
					List<WriterPointLog> writerPointLog2 = writerPointLogService
							.getWriterPointLogByUserId(bookUserComment.getWriterId());
					WriterPointLog writerPointLog = new WriterPointLog();
					// 现在的规则的积分值+以前的积分
					Integer temp = 0;
					if (writerPointLog2.size() > 0) {
						temp = writerPointRuleVOs.getPoint() + writerPointLog2.get(0).getPoint();
						writerPointLog.setPoint(temp);
					} else {
						temp = writerPointRuleVOs.getPoint();
						writerPointLog.setPoint(temp);
					}
					// 积分规则id
					writerPointLog.setRuleId(writerPointRuleVOs.getId());
					writerPointLog.setUserId(bookUserComment.getWriterId());
					// 增加积分记录
					writerPointLogService.add(writerPointLog);
					WriterPoint point = writerPointService.getWriterPointByUserId(bookUserComment.getWriterId());
					WriterPoint writerPoint = new WriterPoint();
					// 当前获取的总积分=评论积分+以前的积分
					writerPoint.setGain(writerPointLog.getPoint());
					writerPoint.setUserId(bookUserComment.getWriterId());
					writerPoint.setTotal(writerPoint.getGain() + point.getLoss());
					writerPoint.setLoss(point.getLoss());
					writerPoint.setId(point.getId());
					writerPointService.updateWriterPoint(writerPoint);
				}
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
