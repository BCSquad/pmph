package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecTextbookPmph;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 人卫社教材编写情况表业务层接口
 * @author tyc
 * @date 2018年02月28日 下午17:43
 */
public interface DecTextbookPmphService {

	/**
	 * @Param DecTextbookPmph 实体对象
	 * @Return DecTextbookPmph带主键
	 * @throws CheckedServiceException
	 */
	DecTextbookPmph addDecTextbookPmph(DecTextbookPmph decTextbookPmph) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecTextbookPmphById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecTextbookPmph 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecTextbookPmph(DecTextbookPmph decTextbookPmph) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecTextbookPmph 实体对象
	 * @Throws CheckedServiceException
	 */
	DecTextbookPmph getDecTextbookPmphById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecTextbookPmph 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecTextbookPmph> getListDecTextbookPmphByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
