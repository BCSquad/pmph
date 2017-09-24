/**
 * 
 */
package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:作家个人简介及标签业务层接口<p>
 * <p>Description:作家个人简介及个人标签信息<p>
 * @author lyc
 * @date 2017年9月22日 下午5:50:29
 */
public interface WriterProfileService {
     /**
      * @Param WriterProfile实体对象
      * @Return WriterProfile带主键
      * @throws CheckedServiceException
      */
	WriterProfile addWriterProfile(WriterProfile writerProfile) throws CheckedServiceException;
	
	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteWriterProfileById(Long id) throws CheckedServiceException;
	
	/**
	 * @Param WriterProfile 实体对象
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateWriterProfile(WriterProfile writerProfile) throws CheckedServiceException;
	
	/**
	 * @Param id
	 * @Return WriterProfile 实体对象
	 * @@throws CheckedServiceException
	 */
	WriterProfile getWriterProfileById(Long id) throws CheckedServiceException;
}
