package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialTypeDao;
import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialTypeService 实现
 * 
 * @author 曾庆峰
 *
 */
@Service
public class MaterialTypeServiceImpl extends BaseService implements MaterialTypeService {

	@Autowired
	private MaterialTypeDao materialTypeDao;

	/**
	 * 
	 * @param materialType
	 *            实体对象
	 * @return 带主键的 MaterialType
	 * @throws CheckedServiceException
	 */
	@Override
	public MaterialType addMaterialType(MaterialType materialType) throws CheckedServiceException {
		if(null==materialType.getParentId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,CheckedExceptionResult.NULL_PARAM,"上级类型为空");
		}
		if(Tools.isEmpty(materialType.getPath())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,CheckedExceptionResult.NULL_PARAM,"节点路径为空");
		}
		if(Tools.isEmpty(materialType.getTypeName())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,CheckedExceptionResult.NULL_PARAM,"类型名称为空");
		}
		if(null==materialType.getSort()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,CheckedExceptionResult.NULL_PARAM,"显示顺序为空");
		}
		if(Tools.isEmpty(materialType.getNote())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE,CheckedExceptionResult.NULL_PARAM,"备注为空");
		}
		materialTypeDao.addMaterialType(materialType);
		return materialType;
	}

	/**
	 * 
	 * @param id
	 * @return MaterialType
	 * @throws CheckedServiceException
	 */
	@Override
	public MaterialType getMaterialTypeById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialTypeDao.getMaterialTypeById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteMaterialTypeById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialTypeDao.deleteMaterialTypeById(id);
	}

	/**
	 * 根据主键id更新materialType 不为null和不为‘’的字段
	 * @param materialType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateMaterialType(MaterialType materialType) throws CheckedServiceException {
		if (null == materialType.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_TYPE, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return materialTypeDao.updateMaterialType(materialType);
	}

}
