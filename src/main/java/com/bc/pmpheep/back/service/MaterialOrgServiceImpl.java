package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.MaterialOrgDao;
import com.bc.pmpheep.back.po.MaterialOrg;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年11月1日 下午3:12:18
 * 
 **/
@Service
public class MaterialOrgServiceImpl extends BaseService implements MaterialOrgService {

    @Autowired
    private MaterialOrgDao materialOrgDao;

    @Override
    public MaterialOrg addMaterialOrg(MaterialOrg materialOrg) throws CheckedServiceException {
        if (null == materialOrg.getMaterialId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
        if (null == materialOrg.getOrgId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "发布学校为空");
        }
        materialOrgDao.addMaterialOrg(materialOrg);
        return materialOrg;
    }

    @Override
    public List<Long> getListMaterialOrgByMaterialId(Long materialId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID为空");
        }
        return materialOrgDao.getListMaterialOrgByMaterialId(materialId);
    }

    @Override
    public Integer addMaterialOrgs(List<MaterialOrg> materialOrgs) throws CheckedServiceException {
        if (null == materialOrgs || materialOrgs.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return materialOrgDao.addMaterialOrgs(materialOrgs);
    }

    @Override
    public List<OrgExclVO> getOutPutExclOrgByMaterialId(Long materialId)
    throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
        return materialOrgDao.getOutPutExclOrgByMaterialId(materialId);
    }

    @Override
    public Integer deleteMaterialOrgByMaterialId(Long materialId) throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
        return materialOrgDao.deleteMaterialOrgByMaterialId(materialId);
    }

    @Override
    public Integer deleteMaterialOrgByOrgId(Long orgId) throws CheckedServiceException {
        if (null == orgId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                                              CheckedExceptionResult.NULL_PARAM, "发布学校为空");
        }
        return materialOrgDao.deleteMaterialOrgByOrgId(orgId);
    }

    @Override
    public Integer deleteMaterialOrgByMaterialAndOrgId(Map<String, Object> params) {
        return materialOrgDao.deleteMaterialOrgByMaterialAndOrgId(params);
    }

}
