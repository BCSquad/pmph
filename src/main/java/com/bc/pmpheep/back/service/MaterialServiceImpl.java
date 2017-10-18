package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialDao;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialServiceImpl extends BaseService implements MaterialService {

    @Autowired
    private MaterialDao materialDao;

    /**
     * 
     * @param Material 实体对象
     * @return 带主键的 Material
     * @throws CheckedServiceException
     */
    @Override
    public Material addMaterial(Material material) throws CheckedServiceException {
        materialDao.addMaterial(material);
        return material;
    }

    /**
     * 
     * @param id
     * @return Material
     * @throws CheckedServiceException
     */
    @Override
    public Material getMaterialById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialDao.getMaterialById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteMaterialById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialDao.deleteMaterialById(id);
    }

    /**
     * 通过主键id更新material 不为null 的字段
     * 
     * @param Material
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateMaterial(Material material) throws CheckedServiceException {
        if (null == material.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialDao.updateMaterial(material);
    }

    @Override
    public List<Material> getListMaterial(String materialName) {
        return materialDao.getListMaterial(materialName);
    }

}
