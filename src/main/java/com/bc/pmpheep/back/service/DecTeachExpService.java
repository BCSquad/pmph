/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecTeachExp业务层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午9:41:05
 */
public interface DecTeachExpService {
	/**
	 * @Param DecTeachExp 实体对象
	 * @Return DecTeachExp带主键
	 * @throws CheckedServiceException
	 */
	DecTeachExp addDecTeachExp(DecTeachExp decTeachExp) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecTeachExpById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecTeachExp 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecTeachExp(DecTeachExp decTeachExp) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecTeachExp 实体对象
	 * @Throws CheckedServiceException
	 */
	DecTeachExp getDecTeachExpById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecTeachExp 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecTeachExp> getListDecTeachExpByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
