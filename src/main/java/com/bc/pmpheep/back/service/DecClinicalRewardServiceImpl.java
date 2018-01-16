package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.dao.DecClinicalRewardDao;
import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 临床医学获奖情况
 * @author tyc
 * 2018年1月16日 15:19
 */
public class DecClinicalRewardServiceImpl implements DecClinicalRewardService{

	@Autowired
	private DecClinicalRewardDao decClinicalRewardDao;
	
	@Override
	public DecClinicalReward addDecClinicalReward(DecClinicalReward decClinicalReward)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decClinicalReward)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decClinicalReward.getDeclarationId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "申报表不能为空");
		}
		decClinicalRewardDao.addDecClinicalReward(decClinicalReward);
		return decClinicalReward;
	}

	@Override
	public Integer deleteDecClinicalReward(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decClinicalRewardDao.deleteDecClinicalReward(id);
	}

	@Override
	public Integer updateDecClinicalReward(DecClinicalReward decClinicalReward)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decClinicalReward.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decClinicalRewardDao.updateDecClinicalReward(decClinicalReward);
	}

	@Override
	public DecClinicalReward getDecClinicalReward(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decClinicalRewardDao.getDecClinicalReward(id);
	}

}
