/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:Declaration业务层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午3:49:19
 */
public interface DeclarationService {
	/**
	 * @Param Declaration 实体对象
	 * @Return Declaration带主键
	 * @throws CheckedServiceException
	 */
	Declaration addDeclaration(Declaration declaration)
			throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDeclarationById(Long id) throws CheckedServiceException;

	/**
	 * @Param Declaration 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDeclaration(Declaration declaration)
			throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return Declaration 实体对象
	 * @Throws CheckedServiceException
	 */
	Declaration getDeclarationById(Long id) throws CheckedServiceException;

	/**
	 * @Param materialId
	 * @Return Declaration 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<Declaration> getDeclarationByMaterialId(Long materialId)
			throws CheckedServiceException;
}
