package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialExtensionDao;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialExtensionService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialExtensionServiceImpl extends BaseService implements MaterialExtensionService {

    @Autowired
    private MaterialExtensionDao materialExtensionDao;

    /**
     * 
     * @param materialExtension 实体对象
     * @return 带主键的 MaterialExtension
     * @throws CheckedServiceException
     */
    @Override
    public MaterialExtension addMaterialExtension(MaterialExtension materialExtension)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialExtension.getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "教材为空");
        }
        if (StringUtil.isEmpty(materialExtension.getExtensionName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "扩展项名称为空");
        }
        materialExtensionDao.addMaterialExtension(materialExtension);
        return materialExtension;
    }

    /**
     * 
     * @param id
     * @return MaterialExtension
     * @throws CheckedServiceException
     */
    @Override
    public MaterialExtension getMaterialExtensionById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtensionDao.getMaterialExtensionById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteMaterialExtensionById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtensionDao.deleteMaterialExtensionById(id);
    }

    /**
     * 通过主键更新materialExtension不为null的字段
     * 
     * @param materialExtension
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateMaterialExtension(MaterialExtension materialExtension)
    throws CheckedServiceException {
        if (null == materialExtension.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTENSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtensionDao.updateMaterialExtension(materialExtension);
    }

}
