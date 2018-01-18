package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecMonographDao;
import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 主编学术专著情况
 * @author tyc
 * 2018年1月16日 09:51
 */
@Service
public class DecMonographServiceImpl implements DecMonographService{

	@Autowired
	private DecMonographDao decMonographDao;
	
	@Override
	public DecMonograph addDecMonograph(DecMonograph decMonograph)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decMonograph)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decMonograph.getDeclarationId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "申报表不能为空");
		}
		decMonographDao.addDecMonograph(decMonograph);
		return decMonograph;
	}

	@Override
	public Integer deleteDecMonograph(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decMonographDao.deleteDecMonograph(id);
	}

	@Override
	public Integer updateDecMonograph(DecMonograph decMonograph)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decMonograph.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decMonographDao.updateDecMonograph(decMonograph);
	}

	@Override
	public DecMonograph getDecMonograph(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decMonographDao.getDecMonograph(id);
	}

}
