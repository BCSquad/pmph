/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecLastPositionDao;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecLastPosition业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午4:18:23
 */
@Service
public class DecLastPositionServiceImpl implements DecLastPositionService {

	@Autowired
	private DecLastPositionDao decLastPositionDao;

	@Override
	public DecLastPosition addDecLastPosition(DecLastPosition decLastPosition)
			throws CheckedServiceException {
		if (null == decLastPosition){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == decLastPosition.getDeclarationId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		if (null == decLastPosition.getMaterialName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "教材名称为空");
		}
		if (null == decLastPosition.getPosition()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "编写职务不能为空");
		}
		if (null == decLastPosition.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "显示顺序不能为空");
		}
		decLastPositionDao.addDecLastPosition(decLastPosition);
		return decLastPosition;
	}

	@Override
	public Integer deleteDecLastPositionById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decLastPositionDao.deleteDecLastPositionById(id);
	}

	@Override
	public Integer updateDecLastPosition(DecLastPosition decLastPosition)
			throws CheckedServiceException {
		if (null == decLastPosition.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decLastPositionDao.updateDecLastPosition(decLastPosition);
	}

	@Override
	public DecLastPosition getDecLastPositionById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return decLastPositionDao.getDecLastPositionById(id);
	}

	@Override
	public List<DecLastPosition> getListDecLastPositionByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (null == declarationId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "申报表id为空");
		}
		return decLastPositionDao.getListDecLastPositionByDeclarationId(declarationId);
	}

}
