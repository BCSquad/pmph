/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecExtensionDao;
import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:作家扩展项填报表<p>
 * <p>Description:业务层实现类<p>
 * @author lyc
 * @date 2017年10月30日 下午11:29:44
 */
@Service
public class DecExtensionServiceImpl implements DecExtensionService{

	@Autowired
	private DecExtensionDao decExtensionDao;
	@Override
	public DecExtension addDecExtension(DecExtension decExtension)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decExtension)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		decExtensionDao.addDecExtension(decExtension);
		return decExtension;
	}

	@Override
	public Integer deleteDecExtension(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "主键id不能为空");
		}
		return decExtensionDao.deleteDecExtension(id);
	}
	
	@Override
	public Integer  deleteDecExtensionByExtensionId(Long extensionId) throws CheckedServiceException {
		if (null == extensionId){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,CheckedExceptionResult.NULL_PARAM, "扩展id不能为空");
		}
		return decExtensionDao.deleteDecExtensionByExtensionId(extensionId);
	}

	@Override
	public Integer updateDecExtension(DecExtension decExtension)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(decExtension)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (ObjectUtil.isNull(decExtension.getId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "主键id为空时禁止更新作家扩展项填报表内容");
		}
		return decExtensionDao.updateDecExtension(decExtension);
	}

	@Override
	public DecExtension getDecExtensionById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "主键不能为空");
		}
		return decExtensionDao.getDecExtensionById(id);
	}

	@Override
	public List<DecExtension> getListDecExtensionsByExtensionId(Long extensionId)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(extensionId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "教材扩展项id不能为空");
		}
		return decExtensionDao.getListDecExtensionsByExtensionId(extensionId);
	}

	@Override
	public List<DecExtension> getListDecExtensionsByDeclarationId(
			Long declarationId) throws CheckedServiceException {
		if (ObjectUtil.isNull(declarationId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
		}
		return decExtensionDao.getListDecExtensionsByDeclarationId(declarationId);
	}

}
