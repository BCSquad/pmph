/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DeclarationDao;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:Declaration业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午3:52:18
 */
@Service
public class DeclarationServiceImpl implements DeclarationService {

	@Autowired
	private DeclarationDao declarationDao;

	@Override
	public Declaration addDeclaration(Declaration declaration)
			throws CheckedServiceException {
		if (null == declaration.getMaterialId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "教材id为空");
		}
		Long id = declaration.getId();
		declarationDao.addDeclaration(declaration);
		if (null != id) {
			declaration.setId(id);
		}
		return declaration;
	}

	@Override
	public Integer deleteDeclarationById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return declarationDao.deleteDeclarationById(id);
	}

	@Override
	public Integer updateDeclaration(Declaration declaration)
			throws CheckedServiceException {
		if (null == declaration.getId()) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return declarationDao.updateDeclaration(declaration);
	}

	@Override
	public Declaration getDeclarationById(Long id)
			throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
		}
		return declarationDao.getDeclarationById(id);
	}

	@Override
	public List<Declaration> getDeclarationByMaterialId(Long materialId)
			throws CheckedServiceException {
		if (null == materialId) {
			throw new CheckedServiceException(
					CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.ILLEGAL_PARAM, "教材id为空");
		}
		return declarationDao.getDeclarationByMaterialId(materialId);
	}

}
