package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecIntention;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 编写内容意向表业务层接口
 * @author tyc
 * @date 2018年02月28日 下午18:03:25
 */
public interface DecIntentionService {

	/**
	 * @param DecIntention 实体对象
	 * @return DecIntention带主键
	 * @throws CheckedServiceException
	 */
	DecIntention addDecIntention(DecIntention decIntention) throws CheckedServiceException;
	
	/**
	 * 根据declarationId查询  
	 * @author tyc
	 * @param  declarationId
	 * @return
	 * @throws CheckedServiceException
	 */
	DecIntention getDecIntentionByDeclarationId (Long declarationId) throws CheckedServiceException;

}
