package com.bc.pmpheep.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecPositionTempDao;
import com.bc.pmpheep.back.po.DecPositionTemp;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Service
public class DecPositionTempServiceImpl implements DecPositionTempService {

	@Autowired
	DecPositionTempDao decPositionTempDao;

	@Override
	public DecPositionTemp addDecPositionTemp(DecPositionTemp decPositionTemp) throws CheckedServiceException {
		if (null == decPositionTemp) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"参数不能为空");
		}
		if (null == decPositionTemp.getDeclarationId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"申报表id不能为空");
		}
		if (null == decPositionTemp.getTextbookId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"书籍id不能为空");
		}
		if (null == decPositionTemp.getPresetPosition()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"申报职务不能为空");
		}
		decPositionTempDao.addDecPositionTemp(decPositionTemp);
		return decPositionTemp;
	}

	@Override
	public Integer deleteDecPositionTemp(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"id不能为空");
		}
		return decPositionTempDao.deleteDecPositionTemp(id);
	}

	@Override
	public Integer updateDecPositionTemp(DecPositionTemp decPositionTemp) throws CheckedServiceException {
		if (null == decPositionTemp.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"id不能为空");
		}
		return decPositionTempDao.updateDecPositionTemp(decPositionTemp);
	}

	@Override
	public DecPositionTemp getDecPositionTempById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"id不能为空");
		}
		return decPositionTempDao.getDecPositionTempById(id);
	}

}
