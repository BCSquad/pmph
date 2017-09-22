/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecAcade业务层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 下午1:58:29
 */
public interface DecAcadeService {

	/**
	 * @Param DecAcade 实体对象
	 * @Return DecAcade带主键
	 * @throws CheckedServiceException
	 */
	DecAcade addDecAcade(DecAcade decAcade) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteDecAcadeById(Long id) throws CheckedServiceException;

	/**
	 * @Param DecAcade 实体对象
	 * @Return 影响行数
	 * @Throws CheckedServiceException
	 */
	Integer updateDecAcade(DecAcade decAcade) throws CheckedServiceException;

	/**
	 * @Param id
	 * @Return DecAcade 实体对象
	 * @Throws CheckedServiceException
	 */
	DecAcade getDecAcadeById(Long id) throws CheckedServiceException;

	/**
	 * @Param declarationId
	 * @Return DecAcade 实体对象集合
	 * @Throws CheckedServiceException
	 */
	List<DecAcade> getListDecAcadeByDeclarationId(Long declarationId)
			throws CheckedServiceException;
}
