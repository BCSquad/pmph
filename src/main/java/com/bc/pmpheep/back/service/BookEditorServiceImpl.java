package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookEditorDao;
import com.bc.pmpheep.back.po.BookEditor;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * BookEditorService 实现
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午5:10:35
 *
 **/
@Service
public class BookEditorServiceImpl extends BaseService implements BookEditorService{
	
	@Autowired
	private BookEditorDao bookEditorDao;
	
	@Override
	public BookEditor addBookEditor(BookEditor bookEditor)
			throws CheckedServiceException {
		if (null == bookEditor) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == bookEditor.getBookId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "图书为空");
		}
		if (null == bookEditor.getAuthorId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "第一主编为空");
		}
		if (null == bookEditor.getEditorId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "策划编辑为空");
		}
		bookEditorDao.addBookEditor(bookEditor);
		return null;
	}

	@Override
	public Integer deleteBookEditorById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return bookEditorDao.deleteBookEditorById(id);
	}

	@Override
	public Integer updateBookEditor(BookEditor bookEditor) throws CheckedServiceException {
		if (null == bookEditor) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (null == bookEditor.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return bookEditorDao.updateBookEditor(bookEditor);
	}

	@Override
	public BookEditor getBookEditorById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return bookEditorDao.getBookEditorById(id);
	}
	
	
}
