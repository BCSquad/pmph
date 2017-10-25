package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialExtraDao;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialExtraService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class MaterialExtraServiceImpl extends BaseService implements MaterialExtraService {

    @Autowired
    private MaterialExtraDao materialExtraDao;

    /**
     * 
     * @param materialExtra 实体对象
     * @return 带主键的 MaterialExtra
     * @throws CheckedServiceException
     */
    @Override
    public MaterialExtra addMaterialExtra(MaterialExtra materialExtra)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialExtra.getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "教材为空");
        }
        if (StringUtil.isEmpty(materialExtra.getNotice())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "通知内容为空");
        }
        if (StringUtil.isEmpty(materialExtra.getNote())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "备注内容为空");
        }
        materialExtraDao.addMaterialExtra(materialExtra);
        return materialExtra;
    }

    /**
     * 
     * @param id
     * @return MaterialExtra
     * @throws CheckedServiceException
     */
    @Override
    public MaterialExtra getMaterialExtraById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtraDao.getMaterialExtraById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteMaterialExtraById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtraDao.deleteMaterialExtraById(id);
    }

    /**
     * 根据主键id 更新materialExtra 不为null和不为‘’的字段
     * 
     * @param materialExtra
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateMaterialExtra(MaterialExtra materialExtra) throws CheckedServiceException {
        if (null == materialExtra.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return materialExtraDao.updateMaterialExtra(materialExtra);
    }

}
