/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:DecLastPosition业务层接口<p>
 * @author lyc
 * @date 2017年9月24日 下午4:16:30
 */
public interface DecLastPositionService {

	/**
	 * @Param DecLastPosition 实体对象
	 * @Return DecLastPosition带主键
	 * @throws CheckedServiceException
	 */
	DecLastPosition addDecLastPosition(DecLastPosition decLastPosition) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecLastPositionById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecLastPosition 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecLastPosition(DecLastPosition decLastPosition) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecLastPosition 实体对象
	 * @Throws CheckedServiceException
	 */
	DecLastPosition getDecLastPositionById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecLastPosition 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecLastPosition> getListDecLastPositionByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
