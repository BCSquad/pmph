/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:DecWorkExp业务层接口<p>
 * @author lyc
 * @date 2017年9月25日 上午10:54:21
 */
public interface DecWorkExpService {

	/**
	 * @Param DecWorkExp 实体对象
	 * @Return DecWorkExp带主键
	 * @throws CheckedServiceException
	 */
	DecWorkExp addDecWorkExp(DecWorkExp decWorkExp) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecWorkExpById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecWorkExp 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecWorkExp(DecWorkExp decWorkExp) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecWorkExp 实体对象
	 * @Throws CheckedServiceException
	 */
	DecWorkExp getDecWorkExpById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecWorkExp 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecWorkExp> getListDecWorkExpByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
