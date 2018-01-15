package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 已公布作家申报职位表业务层接口
 * @author tyc
 * 2018年1月15日 16:20
 */
public interface DecPositionPublishedService {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月15日下午16:20:55
	 * @param decPositionPublished
	 * @return
	 */
	DecPositionPublished addDecPositionPublished(DecPositionPublished decPositionPublished) 
			throws CheckedServiceException;
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月15日下午16:21:05
	 * @param id
	 * @return
	 */
	Integer deleteDecPositionPublished(Long id) throws CheckedServiceException;
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月15日下午16:21:37
	 * @param decPositionPublished
	 * @return
	 */
	Integer updateDecPositionPublished(DecPositionPublished decPositionPublished) 
			throws CheckedServiceException;
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月15日下午16:21:49
	 * @param id
	 * @return
	 */
	DecPositionPublished getDecPositionPublishedById(Long id) throws CheckedServiceException;
}
