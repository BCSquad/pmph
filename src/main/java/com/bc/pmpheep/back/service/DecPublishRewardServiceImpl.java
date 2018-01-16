package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.dao.DecPublishRewardDao;
import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 出版行业获奖情况
 * @author tyc
 * 2018年1月16日10:15
 */
public class DecPublishRewardServiceImpl implements DecPublishRewardService{

	@Autowired
	private DecPublishRewardDao decPublishRewardDao;
	
	@Override
	public DecPublishReward addDecPublishReward(
			DecPublishReward decPublishReward) throws CheckedServiceException {
		if (ObjectUtil.isNull(decPublishReward)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decPublishReward.getDeclarationId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "申报表不能为空");
		}
		decPublishRewardDao.addDecPublishReward(decPublishReward);
		return decPublishReward;
	}

	@Override
	public Integer deleteDecPublishReward(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decPublishRewardDao.deleteDecPublishReward(id);
	}

	@Override
	public Integer updateDecPublishReward(DecPublishReward decPublishReward)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decPublishReward.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decPublishRewardDao.updateDecPublishReward(decPublishReward);
	}

	@Override
	public DecPublishReward getDecPublishReward(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		return decPublishRewardDao.getDecPublishReward(id);
	}

}
