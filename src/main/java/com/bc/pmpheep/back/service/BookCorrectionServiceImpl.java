package com.bc.pmpheep.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookCorrectionDao;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookCorrectionAuditVO;
import com.bc.pmpheep.back.vo.BookCorrectionTrackVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * BookCorrectionService 实现
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午5:10:35
 *
 **/
@Service
public class BookCorrectionServiceImpl extends BaseService implements BookCorrectionService {
	
	@Autowired
	private BookCorrectionDao bookCorrectionDao; 
	@Autowired
	private PmphUserService pmphUserService;
	
	@Override
	public Integer updateToAcceptancing(Long id )throws CheckedServiceException{
		if (null == id ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		BookCorrection bookCorrection= this.getBookCorrectionById(id);
		if(!bookCorrection.getIsAuthorReplied() ){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "请先主编审核");
		}
		bookCorrection.setIsEditorHandling(true);
		return this.updateBookCorrection(bookCorrection);
	}
	
	@Autowired
	private WriterUserTrendstService writerUserTrendstService;
	
	@Override
	public Integer replyWriter(Long id ,Boolean result , String editorReply)throws CheckedServiceException{
		if (null == id ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		if (null == result ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "检查结果为空");
		}
		if (StringUtil.isEmpty(editorReply) ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "回复内容为空");
		}
		if(editorReply.length() > 500 ){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "回复内容超过最长限制500");
		}
		BookCorrection bookCorrection= this.getBookCorrectionById(id);
		if(!bookCorrection.getIsAuthorReplied() ){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "请先主编审核");
		}
		if(!bookCorrection.getIsEditorHandling() ){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "策划编辑未受理");
		}
		if(bookCorrection.getIsEditorReplied()){
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "策划编辑不能再次回复");
		}
		bookCorrection = new BookCorrection();
		bookCorrection.setId(id);
		bookCorrection.setResult(result);
		bookCorrection.setIsEditorReplied(true);
		bookCorrection.setEditorReply(editorReply);
		
		WriterUserTrendst writerUserTrendst = new WriterUserTrendst(); 
		writerUserTrendst.setUserId(bookCorrection.getUserId());
		writerUserTrendst.setIsPublic(false);//自己可见
		writerUserTrendst.setType(0);
		String detail ="";
		if(result){//有问题
			detail ="{title:\"" + CheckedExceptionBusiness.BOOKCORRECTION + "\",content:\"您的图书纠错已核实。\",img:1}";
		}else{
			detail ="{title:\"" + CheckedExceptionBusiness.BOOKCORRECTION + "\",content:\"您的图书纠错未核实到该问题。\",img:2}";
		}
		writerUserTrendst.setDetail(detail);
		Integer res = this.updateBookCorrection(bookCorrection);
		writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
		return res;
	}
	
	@Override
	public PageResult<BookCorrectionTrackVO> listBookCorrectionTrack(
			HttpServletRequest request,Integer pageNumber,Integer pageSize,String bookname ,Boolean isEditorReplied) throws CheckedServiceException{
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "会话过期");
		}
		if(null == pageNumber || pageNumber < 1 ){
			pageNumber = 1 ; 
		}
		if(null == pageSize   || pageSize < 1 ){
			pageSize = Integer.MAX_VALUE ; 
		}
		Map<String,Object> map = new HashMap<String,Object> (4);
		map.put("start", (pageNumber-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("isEditorReplied", isEditorReplied);
		map.put("bookname", StringUtil.toAllCheck(bookname));
		// 返回实体
		PageResult<BookCorrectionTrackVO> pageResult = new PageResult<BookCorrectionTrackVO>();
		pageResult.setPageNumber(pageNumber);
        pageResult.setPageSize(pageSize);
		// 获取总数
		Integer total = bookCorrectionDao.listBookCorrectionTrackTotal(map);
		if (null != total && total > 0) {
			List<BookCorrectionTrackVO> rows =  bookCorrectionDao.listBookCorrectionTrack(map);
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		return pageResult;
	}
	
	@Override
	public PageResult<BookCorrectionAuditVO> listBookCorrectionAudit(
			HttpServletRequest request,Integer pageNumber,Integer pageSize,String bookname ,Boolean isOver,Boolean result) throws CheckedServiceException{
		if (null == request.getSession(false)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "会话过期");
		}
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null== pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "请求用户不存在");
		}
		if(null == pageNumber || pageNumber < 1 ){
			pageNumber = 1 ; 
		}
		if(null == pageSize   || pageSize < 1 ){
			pageSize = Integer.MAX_VALUE ; 
		}
		Long editorId = pmphUser.getId() ;
		// 权限的检查
		List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
		// 系统管理员权限检查
		for (PmphRole pmphRole : pmphRoles) {
			if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName().trim())) {
				editorId = null; // 我是系统管理原
			}
		}
		Map<String,Object> map = new HashMap<String,Object> (4);
		map.put("start", (pageNumber-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("result", result);
		map.put("bookname", StringUtil.toAllCheck(bookname));
		map.put("editorId", editorId);
		if(null != isOver && isOver){
			map.put("isOver", 1); //APP的已完成
			
		}else{
			map.put("isOver", 2);
		}
		// 返回实体
		PageResult<BookCorrectionAuditVO> pageResult = new PageResult<BookCorrectionAuditVO>();
		pageResult.setPageNumber(pageNumber);
        pageResult.setPageSize(pageSize);
		// 获取总数
		Integer total = bookCorrectionDao.listBookCorrectionAuditTotal(map);
		if (null != total && total > 0) {
			List<BookCorrectionAuditVO> rows =  bookCorrectionDao.listBookCorrectionAudit(map);
			pageResult.setRows(rows);
		}
		pageResult.setTotal(total);
		return pageResult;
	}
	
	@Override
	public BookCorrection addBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException{
		if (null == bookCorrection) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == bookCorrection.getBookId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "纠错图书为空");
		}
		if (null == bookCorrection.getUserId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "纠错用户为空");
		}
		if (StringUtil.isEmpty(bookCorrection.getContent())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "纠错内容为空");
		}
		if (bookCorrection.getContent().length() > 500 ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.ILLEGAL_PARAM, "纠错内容长度超过500");
		} 
		bookCorrectionDao.addBookCorrection(bookCorrection) ;
		return bookCorrection;
	}
	
	@Override
	public Integer  deleteBookCorrectionById (Long id) throws CheckedServiceException {
		if (null == id ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		
		return bookCorrectionDao.deleteBookCorrectionById(id);
	}
    
	@Override
	public  Integer updateBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException{
		if (null == bookCorrection) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == bookCorrection.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "更新主键为空");
		}
		//因为是动态更新 ，因此不必验证其他字段了
		return bookCorrectionDao.updateBookCorrection(bookCorrection);
	}
	
	@Override
	public BookCorrection   getBookCorrectionById (Long id) throws CheckedServiceException {
		if (null == id ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_CORRECTION, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return bookCorrectionDao.getBookCorrectionById(id);
	}
	
	
}
