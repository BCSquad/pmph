package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.vo.WriterPointVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 用户积分业务层
 * @author mr
 *	2017-12-28
 */
public interface WriterPointService {
	
	/**
	 *	分页查询用户积分列表 
	 * @param pageParameter
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<WriterPointVO> getListWriterPoint(PageParameter<WriterPointVO> pageParameter) throws CheckedServiceException;
	PageResult<WriterPointVO> getAllWriterPoint() throws CheckedServiceException;
	/**
	 * 添加用户积分
	 * @param writerPoint
	 * @return
	 * @throws CheckedServiceException
	 */
	WriterPoint addWriterPoint(WriterPoint writerPoint)throws CheckedServiceException;
	
	/**
	 * 修改用户积分
	 * @param writerPoint
	 * @return
	 * @throws CheckedServiceException
	 */
    Integer updateWriterPoint(WriterPoint writerPoint)throws CheckedServiceException;
    
    /**
     * 删除积分用户
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    Integer deleteWriterPoint(Long id)throws CheckedServiceException;
    
    /**
     * 查询单个用户积分
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    WriterPoint getWriterPoint(Long id)throws CheckedServiceException;
    
    /**
     * 通过用户id查询积分
     * @param userId
     * @return
     * @throws CheckedServiceException
     */
	WriterPoint getWriterPointByUserId(Long userId)throws CheckedServiceException;
}
