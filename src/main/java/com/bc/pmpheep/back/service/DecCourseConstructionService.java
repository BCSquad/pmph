/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:DecCourseConstructionService<p>
 * <p>Description:精品课程建设情况业务层接口<p>
 * @author lyc
 * @date 2017年9月22日 下午4:42:25
 */
public interface DecCourseConstructionService {

	/**
	 * @Param DecCourseConstruction 实体对象
	 * @Return DecCourseConstruction带主键
	 * @throws CheckedServiceException
	 */
	DecCourseConstruction addDecCourseConstruction(DecCourseConstruction decCourseConstruction) throws CheckedServiceException;
	
	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecCourseConstruction(Long id) throws CheckedServiceException;
	
	/**
	 * @Param DecCourseConstruction 实体对象
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateDecCourseConstruction(DecCourseConstruction decCourseConstruction) throws CheckedServiceException;
	
	/**
	 * @Param id
	 * @Return DecCourseConstruction
	 * @throws CheckedServiceException
	 */
	DecCourseConstruction getDecCourseConstructionById(Long id) throws CheckedServiceException;
	
	/**
	 * @Param declarationId
	 * @Return DecCourseConstruction 对象集合
	 * @throws CheckedServiceException
	 */
	List<DecCourseConstruction> getDecCourseConstructionBydeclarationId(Long declarationId) throws CheckedServiceException;
}
