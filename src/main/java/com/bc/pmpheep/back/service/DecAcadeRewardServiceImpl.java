package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.dao.DecAcadeRewardDao;
import com.bc.pmpheep.back.po.DecAcadeReward;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 学术荣誉授予情况
 * @author tyc
 * 2018年1月16日 15:55
 */
public class DecAcadeRewardServiceImpl implements DecAcadeRewardService{

	@Autowired
	private DecAcadeRewardDao decAcadeRewardDao;
	
	@Override
	public DecAcadeReward addDecAcadeReward(DecAcadeReward decAcadeReward)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decAcadeReward)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decAcadeReward.getDeclarationId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "申报表不能为空");
		}
		decAcadeRewardDao.addDecAcadeReward(decAcadeReward);
		return decAcadeReward;
	}

	@Override
	public Integer deleteDecAcadeReward(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decAcadeRewardDao.deleteDecAcadeReward(id);
	}

	@Override
	public Integer updateDecAcadeReward(DecAcadeReward decAcadeReward)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decAcadeReward.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decAcadeRewardDao.updateDecAcadeReward(decAcadeReward);
	}

	@Override
	public DecAcadeReward getDecAcadeReward(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decAcadeRewardDao.getDecAcadeReward(id);
	}

}
