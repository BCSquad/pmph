package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.vo.ApplicationVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *  WriterApplication 接口 实现
 *@author MrYang 
 *@CreateDate 2017年9月30日 下午5:21:24
 *
 **/
public class WriterApplicationServiceImpl extends BaseService implements WriterApplicationService {
	
	@Override
	public Boolean addApplication(ApplicationVO applicationVO){
		if(null == applicationVO){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Long materialId = applicationVO.getDeclaration().getMaterialId();
		if(null == materialId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "教材id为空");
		}
		
		return null;
	}
}
