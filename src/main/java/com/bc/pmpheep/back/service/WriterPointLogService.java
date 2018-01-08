package com.bc.pmpheep.back.service;
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
	 * 分页查询积分记录
	 * @param pageParameter
	 * @return
	 */
	PageResult<WriterPointLogVO> getListWriterPointLog (PageParameter<WriterPointLogVO> pageParameter);
}
