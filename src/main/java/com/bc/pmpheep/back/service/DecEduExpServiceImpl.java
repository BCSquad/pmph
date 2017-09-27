/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecEduExpDao;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecEduExp业务层接口实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午3:09:51
 */
@Service
public class DecEduExpServiceImpl implements DecEduExpService {

	@Autowired
	private DecEduExpDao decEduExpDao;

	@Override
	public DecEduExp addDecEduExp(DecEduExp decEduExp)
			throws CheckedServiceException {
		if (null == decEduExp){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decEduExp.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		if (null == decEduExp.getSchoolName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "学校名称不能为空");
		}
		if (null == decEduExp.getMajor()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "所学专业不能为空");
		}
		if (null == decEduExp.getDegree()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "学历不能为空");
		}
		if (null == decEduExp.getDateBegin()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "学习经历开始时间不能为空");
		}
		if (null == decEduExp.getDateEnd()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "学习经历终止时间不能为空");
		}
		if (null == decEduExp.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decEduExpDao.addDecEduExp(decEduExp);
		return decEduExp;
	}

	@Override
	public Integer deleteDecEduExpById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decEduExpDao.deleteDecEduExpById(id);
	}

	@Override
	public Integer updateDecEduExp(DecEduExp decEduExp)
			throws CheckedServiceException {
		if (null == decEduExp.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decEduExpDao.updateDecEduExp(decEduExp);
	}

	@Override
	public DecEduExp getDecEduExpById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decEduExpDao.getDecEduExpById(id);
	}

	@Override
	public List<DecEduExp> getListDecEduExpByDeclarationId(Long declarationId)
			throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decEduExpDao.getListDecEduExpByDeclarationId(declarationId);
	}

}
