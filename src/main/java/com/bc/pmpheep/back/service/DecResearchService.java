/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:DecResearch业务层接口<p>
 * @author lyc
 * @date 2017年9月24日 下午5:21:37
 */
public interface DecResearchService {

	/**
	 * @Param DecResearch 实体对象
	 * @Return DecResearch带主键
	 * @throws CheckedServiceException
	 */
	DecResearch addDecResearch(DecResearch decResearch) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecResearchById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecResearch 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecResearch(DecResearch decResearch) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecResearch 实体对象
	 * @Throws CheckedServiceException
	 */
	DecResearch getDecResearchById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecResearch 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecResearch> getListDecResearchByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
