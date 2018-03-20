package com.bc.pmpheep.back.service;
import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPointLog;
import com.bc.pmpheep.back.vo.WriterPointLogVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 积分日志记录业务层
 * @author mr
 *
 */

public interface WriterPointLogService {
	/**
	 * 查询积分日志
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 */
	WriterPointLog getWriterPointLog(Long id)throws CheckedServiceException;
	
	/**
	 * 修改积分日志
	 * @param writerPointLog
	 * @return
	 * @throws CheckedServiceException
	 */
	Integer update(WriterPointLog writerPointLog)throws CheckedServiceException;
	
	/**
	 * 添加积分记录
	 * @param writerPointLog
	 * @return
	 * @throws CheckedServiceException
	 */
	Integer add(WriterPointLog writerPointLog)throws CheckedServiceException;
	
	/**
	 * 删除积分日志
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 */
	Integer delete(Long id)throws CheckedServiceException;
	
	/**
	 * 分页查询用户积分获取记录
	 * @param pageParameter
	 * @return
	 */
	PageResult<WriterPointLogVO> getListWriterPointLog(PageParameter<WriterPointLogVO> pageParameter) 
			throws CheckedServiceException;
	
	/**
	 * 分页查询用户积分兑换记录
	 * @param pageParameter
	 * @return
	 */
	PageResult<WriterPointLogVO> getListWriterPointLogExchange(PageParameter<WriterPointLogVO> pageParameter) 
			throws CheckedServiceException;
	
	/**
	 *  通过用户id查询积分记录
	 * @param writerId
	 * @return
	 * @throws CheckedServiceException
	 */
	List<WriterPointLog> getWriterPointLogByUserId(Long userId) throws CheckedServiceException;
	
	/**
	 * 添加积分积累
	 * @param ruleName
	 * @param userId
	 * @throws CheckedServiceException
	 */
	void addWriterPointLogByRuleName(String ruleName, Long userId) throws CheckedServiceException;
}
