/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecEduExp业务层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 下午1:58:29
 */
public interface DecEduExpService {

	/**
	 * @Param DecEduExp 实体对象
	 * @Return DecEduExp带主键
	 * @throws CheckedServiceException
	 */
	DecEduExp addDecEduExp(DecEduExp decEduExp) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecEduExpById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecEduExp 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecEduExp(DecEduExp decEduExp) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecEduExp 实体对象
	 * @Throws CheckedServiceException
	 */
	DecEduExp getDecEduExpById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecEduExp 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecEduExp> getListDecAcadeByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
