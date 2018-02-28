package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecMoocDigital;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 参加人卫慕课、数字教材编写情况业务层接口
 * @author tyc
 * @date 2018年02月28日 下午17:56:12
 */
public interface DecMoocDigitalService {

	/**
	 * @param DecMoocDigital 实体对象
	 * @return DecMoocDigital带主键
	 * @throws CheckedServiceException
	 */
	DecMoocDigital addDecMoocDigital(DecMoocDigital decMoocDigital) throws CheckedServiceException;
	
	/**
	 * 根据declarationId查询  
	 * @author tyc
	 * @param  declarationId
	 * @return
	 * @throws CheckedServiceException
	 */
	DecMoocDigital getDecMoocDigitalByDeclarationId (Long declarationId) throws CheckedServiceException;

}
