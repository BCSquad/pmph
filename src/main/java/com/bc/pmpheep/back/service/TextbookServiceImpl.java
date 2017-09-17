package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.TextbookDao;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * TextbookService 实现
 * 
 * @author 曾庆峰
 *
 */
@Service
public class TextbookServiceImpl implements TextbookService {

	@Autowired
	private TextbookDao textbookDao;

	/**
	 * 
	 * @param Textbook
	 *            实体对象
	 * @return 带主键的 Textbook
	 * @throws CheckedServiceException
	 */
	@Override
	public Textbook addTextbook(Textbook Textbook) throws CheckedServiceException {
		textbookDao.addTextbook(Textbook);
		return Textbook;
	}

	/**
	 * 
	 * @param id
	 * @return Textbook
	 * @throws CheckedServiceException
	 */
	@Override
	public Textbook getTextbookById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return textbookDao.getTextbookById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteTextbookById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return textbookDao.deleteTextbookById(id);
	}

	/**
	 * @param Textbook
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateTextbook(Textbook textbook) throws CheckedServiceException {
		if (null == textbook.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return textbookDao.updateTextbook(textbook);
	}

}
