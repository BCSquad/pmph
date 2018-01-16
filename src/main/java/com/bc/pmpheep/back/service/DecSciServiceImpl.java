package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.dao.DecSciDao;
import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * SCI论文投稿及影响因子情况
 * @author tyc
 * 2018年1月16日 14:19
 */
public class DecSciServiceImpl implements DecSciService{

	@Autowired
	private DecSciDao decSciDao;
	
	@Override
	public DecSci addDecSci(DecSci decSci)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decSci)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decSci.getDeclarationId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "申报表不能为空");
		}
		decSciDao.addDecSci(decSci);
		return decSci;
	}

	@Override
	public Integer deleteDecSci(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decSciDao.deleteDecSci(id);
	}

	@Override
	public Integer updateDecSci(DecSci decSci)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decSci.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decSciDao.updateDecSci(decSci);
	}

	@Override
	public DecSci getDecSci(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decSciDao.getDecSci(id);
	}

}
