/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:DecTextbook业务层接口<p>
 * @author lyc
 * @date 2017年9月25日 上午10:28:14
 */
public interface DecTextbookService {

	/**
	 * @Param DecTextbook 实体对象
	 * @Return DecTextbook带主键
	 * @throws CheckedServiceException
	 */
	DecTextbook addDecTextbook(DecTextbook decTextbook) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecTextbookById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecTextbook 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecTextbook(DecTextbook decTextbook) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecTextbook 实体对象
	 * @Throws CheckedServiceException
	 */
	DecTextbook getDecTextbookById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecTextbook 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecTextbook> getListDecTextbookByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
