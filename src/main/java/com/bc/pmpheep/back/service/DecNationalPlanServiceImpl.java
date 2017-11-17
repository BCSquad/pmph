/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecNationalPlanDao;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecNationalPlan业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午4:41:40
 */
@Service
public class DecNationalPlanServiceImpl implements DecNationalPlanService {

	@Autowired
	private DecNationalPlanDao decNationalPlanDao;

	@Override
	public DecNationalPlan addDecNationalPlan(DecNationalPlan decNationalPlan)
			throws CheckedServiceException {
		if (null == decNationalPlan){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decNationalPlan.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		decNationalPlanDao.addDecNationalPlan(decNationalPlan);
		return decNationalPlan;
	}

	@Override
	public Integer deleteDecNationalPlanById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decNationalPlanDao.deleteDecNationalPlanById(id);
	}

	@Override
	public Integer updateDecNationalPlan(DecNationalPlan decNationalPlan)
			throws CheckedServiceException {
		if (null == decNationalPlan.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decNationalPlanDao.updateDecNationalPlan(decNationalPlan);
	}

	@Override
	public DecNationalPlan getDecNationalPlanById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decNationalPlanDao.getDecNationalPlanById(id);
	}

	@Override
	public List<DecNationalPlan> getListDecNationalPlanByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decNationalPlanDao.getListDecNationalPlanByDeclarationId(declarationId);
	}

}
