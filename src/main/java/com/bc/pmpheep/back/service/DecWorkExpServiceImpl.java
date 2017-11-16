/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecWorkExpDao;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecWorkExp业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午10:56:21
 */
@Service
public class DecWorkExpServiceImpl implements DecWorkExpService {

	@Autowired
	private DecWorkExpDao decWorkExpDao;

	@Override
	public DecWorkExp addDecWorkExp(DecWorkExp decWorkExp)
			throws CheckedServiceException {
		if (null == decWorkExp){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decWorkExp.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id不能为空");
		}
		/*if (null == decWorkExp.getOrgName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "工作单位不能为空");
		}
		if (null == decWorkExp.getPosition()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "职务不能为空");
		}
		if (null ==decWorkExp.getDateBegin() ){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "工作经历起始时间不能为空");
		}
		if (null == decWorkExp.getDateEnd()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "工作经历终止时间不能为空");
		}*/
		if (null == decWorkExp.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decWorkExpDao.addDecWorkExp(decWorkExp);
		return decWorkExp;
	}

	@Override
	public Integer deleteDecWorkExpById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decWorkExpDao.deleteDecWorkExpById(id);
	}

	@Override
	public Integer updateDecWorkExp(DecWorkExp decWorkExp)
			throws CheckedServiceException {
		if (null == decWorkExp.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decWorkExpDao.updateDecWorkExp(decWorkExp);
	}

	@Override
	public DecWorkExp getDecWorkExpById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decWorkExpDao.getDecWorkExpById(id);
	}

	@Override
	public List<DecWorkExp> getListDecWorkExpByDeclarationId(Long declarationId)
			throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decWorkExpDao.getListDecWorkExpByDeclarationId(declarationId);
	}

}
