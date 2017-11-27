package com.bc.pmpheep.back.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.bc.pmpheep.back.dao.TextbookLogDao;
import com.bc.pmpheep.back.po.TextbookLog;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * TextbookLogService 实现
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年11月27日 下午5:19:35
 *
 */
@Service
public class TextbookLogServiceImpl implements TextbookLogService {
	
	@Autowired
	private TextbookLogDao textbookLogDao;

	@Override
	public TextbookLog addTextbookLog(TextbookLog textbookLog) throws CheckedServiceException {
		if(null == textbookLog){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"参数为空！");
		}
		if(null == textbookLog.getTextbookId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"书籍为空！");
		}
		if(null == textbookLog.getUpdaterId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"修改者为空！");
		}
		if(StringUtils.isEmpty(textbookLog.getDetail()) ){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.NULL_PARAM,
					"详情为空！");
		}
		if(textbookLog.getDetail().length() > 100){
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK_LOG, CheckedExceptionResult.ILLEGAL_PARAM,
					"详情太长！");
		}
		textbookLogDao.addTextbookLog(textbookLog);
		return textbookLog;
	}
	
    
}
