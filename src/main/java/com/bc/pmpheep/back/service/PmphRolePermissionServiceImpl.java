package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphRolePermissionDao;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphRolePermissionService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphRolePermissionServiceImpl implements PmphRolePermissionService {
    @Autowired
    private PmphRolePermissionDao pmphRolePermissionDao;

    /**
     * 
     * @param PmphRolePermission 实体对象
     * @return 带主键的PmphRolePermission
     * @throws CheckedServiceException
     */
    @Override
    public PmphGroup addPmphRolePermission(PmphRolePermission pmphRolePermission)
    throws CheckedServiceException {
        if (null == pmphRolePermission) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增用户！");
        }
        return pmphRolePermissionDao.addPmphRolePermission(pmphRolePermission);
    }

    /**
     * 
     * @param PmphRolePermission 必须包含主键ID
     * @return PmphRolePermission
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public PmphRolePermission getPmphRolePermissionById(PmphRolePermission pmphRolePermission)
    throws CheckedServiceException {
        if (null == pmphRolePermission.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止新增查询！");
        }
        return pmphRolePermissionDao.getPmphRolePermissionById(pmphRolePermission);
    }

    /**
     * 
     * @param PmphRolePermission
     * @return 影响行数
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public Integer deletePmphRolePermissionById(PmphRolePermission pmphRolePermission)
    throws CheckedServiceException {
        if (null == pmphRolePermission.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除！");
        }
        return pmphRolePermissionDao.deletePmphRolePermissionById(pmphRolePermission);
    }

    /**
     * @param PmphRolePermission
     * @return 影响行数
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    @Override
    public Integer updatePmphRolePermissionById(PmphRolePermission pmphRolePermission)
    throws CheckedServiceException {
        if (null == pmphRolePermission.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止更新！");
        }
        return pmphRolePermissionDao.updatePmphRolePermissionById(pmphRolePermission);
    }
}
