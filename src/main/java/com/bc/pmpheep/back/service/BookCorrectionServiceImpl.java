package com.bc.pmpheep.back.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.vo.BookFeedBack;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookCorrectionDao;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.po.WriterPointLog;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookCorrectionAuditVO;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;

/**
 * BookCorrectionService 实现
 * 
 * @author MrYang
 * @CreateDate 2017年12月19日 下午5:10:35
 *
 **/
@Service
public class BookCorrectionServiceImpl extends BaseService implements BookCorrectionService {

	@Autowired
	private BookCorrectionDao bookCorrectionDao;
	@Autowired
	private PmphUserService pmphUserService;
	@Autowired
	private BookService bookService;
	@Autowired
	WriterPointRuleService writerPointRuleService;
	@Autowired
	WriterPointLogService writerPointLogService;
	@Autowired
	WriterPointService writerPointService;

	@Override
	public Integer updateToAcceptancing(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		BookCorrection bookCorrection = this.getBookCorrectionById(id);
		if (!bookCorrection.getIsAuthorReplied()) {
			// throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
			// CheckedExceptionResult.NULL_PARAM, "请先主编审核");
		}
		bookCorrection.setIsEditorHandling(true);
		return this.updateBookCorrection(bookCorrection);
	}

	@Autowired
	private WriterUserTrendstService writerUserTrendstService;

	@Override
	public Integer replyWriter(Long id, Boolean result, String editorReply,String authorReply) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		if (null == result) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "检查结果为空");
		}
//		if (StringUtil.isEmpty(authorReply)) {
//			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
//					CheckedExceptionResult.NULL_PARAM, "回复内容为空");
//		}

		if (StringUtil.isEmpty(editorReply)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "回复内容为空");
		}
		if (editorReply.length() > 500) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "回复内容超过最长限制500");
		}
		BookCorrection bookCorrection = this.getBookCorrectionById(id);
		Long bookId = bookCorrection.getBookId();
		Long submitUserId = bookCorrection.getUserId();
		if (!bookCorrection.getIsAuthorReplied()) {
			// throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
			// CheckedExceptionResult.NULL_PARAM, "请先主编审核");
		}
		if (!bookCorrection.getIsEditorHandling()) {
			// throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
			// CheckedExceptionResult.NULL_PARAM, "策划编辑未受理");
		}
		if (bookCorrection.getIsEditorReplied()) {
			/*throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "策划编辑不能再次回复");*/
		}
		bookCorrection = new BookCorrection();
		bookCorrection.setId(id);
		bookCorrection.setResult(result);
		bookCorrection.setIsEditorReplied(true);
		bookCorrection.setEditorReply(editorReply);
		bookCorrection.setIsEditorHandling(true);
		if (!StringUtil.isEmpty(authorReply)) {
			if (authorReply.length() > 500) {
				throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
						CheckedExceptionResult.NULL_PARAM, "回复内容超过最长限制500");
			}
			bookCorrection.setIsAuthorReplied(true);
			bookCorrection.setAuthorReply(authorReply);
		}


		WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
		writerUserTrendst.setUserId(submitUserId);
		writerUserTrendst.setBookId(bookId);
		writerUserTrendst.setIsPublic(false);// 自己可见
		writerUserTrendst.setType(10);
		String detail = "";
		if (result) {// 有问题
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", CheckedExceptionBusiness.BOOKCORRECTION);
			map.put("content", "您提交的纠错信息已回复。");
			map.put("img", 1);
			detail = new Gson().toJson(map);
			// 更新评论数
			// bookService.updateComments(bookId);
			
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", CheckedExceptionBusiness.BOOKCORRECTION);
			map.put("content", "您提交的纠错信息已回复。");
			map.put("img", 2);
			detail = new Gson().toJson(map);
		}
		//当审核有问题的时候则给用户增加积分
		if(result){
			String ruleName="图书纠错";
			writerPointLogService.addWriterPointLogByRuleName(ruleName, submitUserId);
		}
		writerUserTrendst.setDetail(detail);
		Integer res = this.updateBookCorrection(bookCorrection);
		writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
		return res;
	}

	@Override
	public PageResult<BookCorrectionTrackVO> listBookCorrectionTrack(HttpServletRequest request, Integer pageNumber,
			Integer pageSize, String bookname, Boolean isEditorReplied,Boolean result) throws CheckedServiceException {
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"会话过期");
		}
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = Integer.MAX_VALUE;
		}
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("start", (pageNumber - 1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("isEditorReplied", isEditorReplied);
		map.put("bookname", StringUtil.toAllCheck(bookname));
		map.put("result", result);
		// 返回实体
		PageResult<BookCorrectionTrackVO> pageResult = new PageResult<BookCorrectionTrackVO>();
		pageResult.setPageNumber(pageNumber);
		pageResult.setPageSize(pageSize);
		// 获取总数
		Integer total = bookCorrectionDao.listBookCorrectionTrackTotal(map);
		if (null != total && total > 0) {
			List<BookCorrectionTrackVO> rows = bookCorrectionDao.listBookCorrectionTrack(map);
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public PageResult<BookCorrectionAuditVO> listBookCorrectionAudit(HttpServletRequest request, Integer pageNumber,
			Integer pageSize, String bookname, Boolean isOver, Boolean result) throws CheckedServiceException {
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"会话过期");
		}
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = Integer.MAX_VALUE;
		}
		Long editorId = pmphUser.getId();
		// 权限的检查
		List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
		// 系统管理员权限检查
		for (PmphRole pmphRole : pmphRoles) {
			if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName().trim())) {
				editorId = null; // 我是系统管理原
			}
		}
		// 权限由菜单控制
		editorId = null;
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("start", (pageNumber - 1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("result", result);
		map.put("bookname", StringUtil.toAllCheck(bookname));
		map.put("editorId", editorId);
		if(ObjectUtil.isNull(isOver)) {
			map.put("isOver", "");
		}else if (null != isOver && isOver) {
			map.put("isOver", 1); // APP的已完成

		} else {
			map.put("isOver", 2);
		}
		// 返回实体
		PageResult<BookCorrectionAuditVO> pageResult = new PageResult<BookCorrectionAuditVO>();
		pageResult.setPageNumber(pageNumber);
		pageResult.setPageSize(pageSize);
		// 获取总数
		Integer total = bookCorrectionDao.listBookCorrectionAuditTotal(map);
		if (null != total && total > 0) {
			List<BookCorrectionAuditVO> rows = bookCorrectionDao.listBookCorrectionAudit(map);
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public List<BookCorrectionAuditVO> exportBookCheck(String bookname){
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("bookname", StringUtil.toAllCheck(bookname));
		List<BookCorrectionAuditVO> rows = bookCorrectionDao.exportBookCheck(map);
		for(BookCorrectionAuditVO bookCorrectionAuditVO:rows){
			SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				//bookCorrectionAuditVO.setAuthDateS(ObjectUtil.isNull(bookCorrectionAuditVO.getAuthDate())?"":lsdStrFormat.format(bookCorrectionAuditVO.getAuthDate()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;

	}
	@Override
	public PageResult<BookFeedBack> bookFeedBaskList(HttpServletRequest request, Integer pageNumber, Integer pageSize, Boolean result) {
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"会话过期");
		}
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = Integer.MAX_VALUE;
		}
//		Long editorId = pmphUser.getId();
//		// 权限的检查
//		List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
//		for (PmphRole pmphRole : pmphRoles) {
//			if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName().trim())) {
//				editorId = null; // 我是系统管理原
//			}
//		}
//		// 权限由菜单控制
//		editorId = null;
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("start", (pageNumber - 1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("result", result);
		//map.put("editorId", editorId);
		// 返回实体
		PageResult<BookFeedBack> pageResult = new PageResult<BookFeedBack>();
		pageResult.setPageNumber(pageNumber);
		pageResult.setPageSize(pageSize);
		// 获取总数
		Integer total = bookCorrectionDao.bookFeedBackListTotal(map);
		if (null != total && total > 0) {
			List<BookFeedBack> rows = bookCorrectionDao.bookFeedBackList(map);
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public BookCorrectionAuditVO getBookCorrectionAuditDetailById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "参数ID为空");
		}

		return bookCorrectionDao.getBookCorrectionAuditDetailById(id);
	}

	@Override
	public BookFeedBack getBookFeedBackDetailById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "参数ID为空");
		}
		BookFeedBack BookFeedBackList = bookCorrectionDao.getBookFeedBackDetailById(id);
		return BookFeedBackList;
	}

	@Override
	public Integer replyBookFeedBackWriter(Long id, String authorReply,HttpServletRequest request) {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		if (StringUtil.isEmpty(authorReply)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "回复内容为空");
		}
		if (authorReply.length() > 500) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "回复内容超过最长限制500");
		}
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"会话过期");
		}
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		Long authorId = pmphUser.getId();
		return bookCorrectionDao.replyBookFeedBackWriter(id,authorReply,authorId);
	}

	@Override
	public List<BookFeedBack> exportfeedback(Boolean result) {
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("start", null);
		map.put("pageSize", null);
		map.put("result", result);
		List<BookFeedBack> rows = null;
		Integer total = bookCorrectionDao.bookFeedBackListTotal(map);
		if (null != total && total > 0) {
			rows = bookCorrectionDao.bookFeedBackList(map);
			for(BookFeedBack bookFeedBack:rows){
				bookFeedBack.setResultS(bookFeedBack.getResult() == 1?"已审核":"未审核");
				bookFeedBack.setAuthorReply("null".equals(bookFeedBack.getAuthorReply())?"":bookFeedBack.getAuthorReply());
				SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					bookFeedBack.setAuthDateS(ObjectUtil.isNull(bookFeedBack.getAuthDate())?"":lsdStrFormat.format(bookFeedBack.getAuthDate()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return rows;
	}

	@Override
	public BookCorrection addBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException {
		if (null == bookCorrection) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == bookCorrection.getBookId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "纠错图书为空");
		}
		if (null == bookCorrection.getUserId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "纠错用户为空");
		}
		if (StringUtil.isEmpty(bookCorrection.getContent())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "纠错内容为空");
		}
		if (bookCorrection.getContent().length() > 500) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.ILLEGAL_PARAM, "纠错内容长度超过500");
		}
		bookCorrectionDao.addBookCorrection(bookCorrection);
		return bookCorrection;
	}

	@Override
	public Integer deleteBookCorrectionById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}

		return bookCorrectionDao.deleteBookCorrectionById(id);
	}

	@Override
	public Integer updateBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException {
		if (null == bookCorrection) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == bookCorrection.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "更新主键为空");
		}
		// 因为是动态更新 ，因此不必验证其他字段了
		return bookCorrectionDao.updateBookCorrection(bookCorrection);
	}

	@Override
	public BookCorrection getBookCorrectionById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return bookCorrectionDao.getBookCorrectionById(id);
	}



}
