package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Service
public class BookServiceImpl extends BaseService implements BookService {

	@Autowired
	BookDao bookDao;

	@Override
	public PageResult<BookVO> listBookVO(PageParameter<BookVO> pageParameter) throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		int total = bookDao.getBookVOTotal(pageParameter);
		PageResult<BookVO> pageResult = new PageResult<>();
		if (total > 0) {
			PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
			pageResult.setRows(bookDao.listBookVO(pageParameter));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String updateBookById(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote)
			throws CheckedServiceException {
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "id为空");
		}
		if (ObjectUtil.isNull(type)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍类型为空");
		}
		if (ObjectUtil.isNull(isOnSale)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否上架为空");
		}
		if (ObjectUtil.isNull(isNew)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否新书为空");
		}
		if (ObjectUtil.isNull(isPromote)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否推荐为空");
		}
		String result = "FAIL";
		for (Long id : ids) {
			Book book = new Book();
			book.setId(id);
			book.setType(type);
			book.setIsNew(isNew);
			book.setIsOnSale(isOnSale);
			book.setIsPromote(isPromote);
			bookDao.updateBook(book);
		}
		result = "SUCCESS";
		return result;
	}

}
