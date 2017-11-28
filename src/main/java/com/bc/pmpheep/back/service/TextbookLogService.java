package com.bc.pmpheep.back.service;


import java.util.List;

import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.TextbookLog;
import com.bc.pmpheep.back.vo.TextbookLogVO;
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
	 *
	 * 根据书籍id分页查询日志 
	 * @author Mryang
	 * @createDate 2017年11月28日 上午11:56:40
	 * @param textbookId
	 * @param pageSize
	 * @param pageNumber
	 * @param updaterName
	 * @return PageResult<TextbookLogVO>
	 * @throws CheckedServiceException
	 */
	PageResult<TextbookLogVO> listTextbookLogByTextBookId(Long textbookId,Integer pageSize,Integer pageNumber,String updaterName) throws CheckedServiceException;
	
	/**
	 * 
	 * @introduction 书籍最终结果发布时插入日志
	 * @author Mryang
	 * @createDate 2017年11月28日 上午10:05:01
	 * @param textbookId
	 * @param updaterId
	 * @param userType 1机构用户  2 作家用户
	 * @return
	 * @throws CheckedServiceException
	 */
	TextbookLog addTextbookLogWhenPub(Long textbookId,Long updaterId,int userType) throws CheckedServiceException ;
	
	/**
	 * 
	 * @introduction 书籍名单锁定时候插入日志
	 * @author Mryang
	 * @createDate 2017年11月28日 上午10:05:21
	 * @param textbookId
	 * @param updaterId
	 * @param userType  1机构用户  2 作家用户
	 * @return
	 * @throws CheckedServiceException
	 */
	TextbookLog addTextbookLogWhenPass(Long textbookId,Long updaterId,int userType) throws CheckedServiceException ;
	
	
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
	 * 主编 ， 副主编 ，编委，策划编辑的遴选职位被修改
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年11月27日 下午5:47:37
	 * @param oldlist 修改之前的列表  = decPositionService.listChosenDecPositionsByTextbookId(textbookId)
	 * @param textbookId
	 * @param updaterId
	 * @param userType   1机构用户  2 作家用户
	 * @throws CheckedServiceException
	 */
	void  addTextbookLog(List<DecPosition> oldlist ,Long textbookId,Long updaterId,int userType) throws CheckedServiceException;
	
	
}
