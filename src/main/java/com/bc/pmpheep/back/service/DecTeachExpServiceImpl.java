/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecTeachExpDao;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecTeachExpServiceImpl
 * <p>
 * <p>
 * Description:TODO
 * <p>
 * 
 * @author Administrator
 * @date 2017年9月25日 上午9:43:52
 */
@Service
public class DecTeachExpServiceImpl implements DecTeachExpService {

	@Autowired
	private DecTeachExpDao decTeachExpDao;

	@Override
	public DecTeachExp addDecTeachExp(DecTeachExp decTeachExp)
			throws CheckedServiceException {
		if (null == decTeachExp.getSchoolName()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "学校名称为空");
		}
		Long id = decTeachExp.getId();
		decTeachExpDao.addDecTeachExp(decTeachExp);
		if (null != id) {
			decTeachExp.setId(id);
		}
		return decTeachExp;
	}

	@Override
	public Integer deleteDecTeachExpById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTeachExpDao.deleteDecTeachExpById(id);
	}

	@Override
	public Integer updateDecTeachExp(DecTeachExp decTeachExp)
			throws CheckedServiceException {
		if (null == decTeachExp.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTeachExpDao.updateDecTeachExp(decTeachExp);
	}

	@Override
	public DecTeachExp getDecTeachExpById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decTeachExpDao.getDecTeachExpById(id);
	}

	@Override
	public List<DecTeachExp> getListDecTeachExpByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decTeachExpDao.getListDecTeachExpByDeclarationId(declarationId);
	}

}
