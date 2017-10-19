package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphPermissionDao;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphPermissionService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphPermissionServiceImpl implements PmphPermissionService {
    @Autowired
    private PmphPermissionDao pmphPermissionDao;

    /**
     * 
     * @param PmphPermissionTest 实体对象
     * @return 带主键的PmphPermission
     * @throws CheckedServiceException
     */
    @Override
    public PmphPermission addPmphPermission(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (Tools.isNullOrEmpty(pmphPermission)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源属性为空时禁止添加!");
        }
        pmphPermissionDao.addPmphPermission(pmphPermission);
        return pmphPermission;
    }

    /**
     * 
     * @param PmphPermissionTest 必须包含主键ID
     * @return PmphPermission
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public PmphPermission getPmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (Tools.isNullOrEmpty(pmphPermission.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止查询!");
        }
        return pmphPermissionDao.getPmphPermissionById(pmphPermission);
    }

    /**
     * 
     * @param PmphPermissionTest
     * @return 影响行数
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public Integer deletePmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (Tools.isNullOrEmpty(pmphPermission.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止删除!");
        }
        return pmphPermissionDao.deletePmphPermissionById(pmphPermission);
    }

    /**
     * @param PmphPermissionTest
     * @return 影响行数
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    @Override
    public Integer updatePmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (Tools.isNullOrEmpty(pmphPermission.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止更新!");
        }
        return pmphPermissionDao.updatePmphPermissionById(pmphPermission);
    }

    @Override
    public Integer delete(Long id) throws CheckedServiceException {
        if (Tools.isNullOrEmpty(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止删除!");
        }
        return pmphPermissionDao.delete(id);
    }

    @Override
    public PmphPermission get(Long id) throws CheckedServiceException {
        if (Tools.isNullOrEmpty(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止查询!");
        }
        return pmphPermissionDao.get(id);
    }

    @Override
    public List<PmphPermission> getListResource() throws CheckedServiceException {
        return pmphPermissionDao.getListResource();
    }

    @Override
    public List<PmphPermission> getListAllParentMenu() {
        List<PmphPermission> permissions = pmphPermissionDao.getListAllParentMenu();
        for (PmphPermission permission : permissions) {
            List<PmphPermission> subList = this.getListChildMenuByParentId(permission.getId());
            permission.setChildren(subList);
        }
        return permissions;
    }

    @Override
    public List<PmphPermission> getListChildMenuByParentId(Long parentId) {
        return pmphPermissionDao.getListChildMenuByParentId(parentId);
    }

}
