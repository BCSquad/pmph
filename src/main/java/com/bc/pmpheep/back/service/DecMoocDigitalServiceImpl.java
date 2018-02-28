package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecMoocDigitalDao;
import com.bc.pmpheep.back.po.DecMoocDigital;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 参加人卫慕课、数字教材编写情况业务层实现类
 * @author tyc
 * @date 2018年02月28日 下午17:59:54
 */
@Service
public class DecMoocDigitalServiceImpl implements DecMoocDigitalService{

	@Autowired
	private DecMoocDigitalDao decMoocDigitalDao;
	
	@Override
	public DecMoocDigital addDecMoocDigital(DecMoocDigital decMoocDigital)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decMoocDigital)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decMoocDigital.getDeclarationId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		decMoocDigitalDao.addDecMoocDigital(decMoocDigital);
		return decMoocDigital;
	}
	
	@Override
	public DecMoocDigital getDecMoocDigitalByDeclarationId (Long declarationId) 
			throws CheckedServiceException{
		if (null == declarationId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, 
					CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		return decMoocDigitalDao.getDecMoocDigitalByDeclarationId(declarationId);
	}
}
