package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecPositionTemp;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface DecPositionTempService {
	/**
	 * @Param DecPosition 实体对象
	 * @Return DecPosition带主键
	 * @throws CheckedServiceException
	 */
	DecPositionTemp addDecPositionTemp(DecPositionTemp decPosition) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecPositionTemp(Long id) throws CheckedServiceException;
	

	/**
	 * @Param textbookId
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecPositionTempByTextbookId(Long textbookId) throws CheckedServiceException;

	/**
	 * @Param DecPosition 实体对象
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateDecPositionTemp(DecPositionTemp decPosition) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecPosition 实体对象
	 * @throws CheckedServiceException
	 */
	DecPositionTemp getDecPositionTempById(Long id) throws CheckedServiceException;
}
