package com.bc.pmpheep.back.service;


import java.util.List;

import com.bc.pmpheep.back.po.DecPosition;
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
	
	/**
	 * 主编 ， 副主编 ，编委，策划编辑的修改
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年11月27日 下午5:47:37
	 * @param oldlist 修改之前的列表  = decPositionService.listChosenDecPositionsByTextbookId(textbookId)
	 * @param textbookId
	 * @param updaterId
	 * @param userType
	 * @throws CheckedServiceException
	 */
	void  addTextbookLog(List<DecPosition> oldlist ,Long textbookId,Long updaterId,int userType) throws CheckedServiceException;
	
	
}
