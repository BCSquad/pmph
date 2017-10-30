/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:教材扩展项填报表<p>
 * <p>Description:业务层接口<p>
 * @author lyc
 * @date 2017年10月30日 下午11:23:49
 */
public interface DecExtensionService {

	/**
	 * @param DecExtension 实体对象
	 * @return DecExtension带主键
	 * @throws CheckedServiceException
	 */
	DecExtension addDecExtension(DecExtension decExtension) throws CheckedServiceException;
	
	/**
	 * @param id主键
	 * @return Integer影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecExtension(Long id) throws CheckedServiceException;
	
	/**
	 * @param DecExtension 实体对象
	 * @return Integer影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateDecExtension(DecExtension decExtension) throws CheckedServiceException;
	
	/**
	 * @param id主键
	 * @return DecExtension对象
	 * @throws CheckedServiceException
	 */
	DecExtension getDecExtensionById(Long id) throws CheckedServiceException;
	
	/**
	 * @param extensionId教材扩展项id
	 * @return DecExtension对象集合
	 * @throws CheckedServiceException
	 */
	List<DecExtension> getListDecExtensionsByExtensionId(Long extensionId) throws CheckedServiceException;
	
	/**
	 * @param declarationId申报表id
	 * @return DecExtension对象集合
	 * @throws CheckedServiceException
	 */
	List<DecExtension> getListDecExtensionsByDeclarationId(Long declarationId) throws CheckedServiceException;
}
