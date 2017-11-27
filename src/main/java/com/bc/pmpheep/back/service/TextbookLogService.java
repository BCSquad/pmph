package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.TextbookLog;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * TextbookLogService 接口
 *@author MrYang 
 *
 *@CreateDate 2017年11月27日 下午5:16:20
 *
 **/
public interface TextbookLogService {
	
	/**
	 * 插入textbookLog
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年11月27日 下午5:33:10
	 * @param textbookLog
	 * @return
	 * @throws CheckedServiceException
	 */
	TextbookLog addTextbookLog(TextbookLog textbookLog) throws CheckedServiceException;
	
}
