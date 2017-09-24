/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:DecNationalPlanDao业务层接口<p>
 * @author lyc
 * @date 2017年9月24日 下午4:38:18
 */
public interface DecNationalPlanService {

	/**
	 * @Param DecNationalPlan 实体对象
	 * @Return DecNationalPlan带主键
	 * @throws CheckedServiceException
	 */
	DecNationalPlan addDecNationalPlan(DecNationalPlan decNationalPlan) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecNationalPlanById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecNationalPlan 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecNationalPlan(DecNationalPlan decNationalPlan) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecNationalPlan 实体对象
	 * @Throws CheckedServiceException
	 */
	DecNationalPlan getDecNationalPlanById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecNationalPlan 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecNationalPlan> getListDecNationalPlanByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
