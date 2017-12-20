package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookCorrectionDao;
import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.back.util.StringUtil;
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
