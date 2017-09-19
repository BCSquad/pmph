package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphRolePermissionDao;
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
     * @param pmphRolePermission 实体对象
     * @return 带主键的PmphRolePermission
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public PmphRolePermission addPmphRolePermission(PmphRolePermission pmphRolePermission)
    throws CheckedServiceException {
        if (null == pmphRolePermission) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增用户！");
        }
        pmphRolePermissionDao.addPmphRolePermission(pmphRolePermission);
        return pmphRolePermission;
    }

    /**
     * 
     * @param id 
     * @return PmphRolePermission
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    @Override
    public PmphRolePermission getPmphRolePermissionById(Long id )
    throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止新增查询！");
        }
        return pmphRolePermissionDao.getPmphRolePermissionById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public Integer deletePmphRolePermissionById(Long id)
    throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除！");
        }
        return pmphRolePermissionDao.deletePmphRolePermissionById(id);
    }

    /**
     * @param pmphRolePermission
     * @return 影响行数
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    @Override
    public Integer updatePmphRolePermission(PmphRolePermission pmphRolePermission)
    throws CheckedServiceException {
        if (null == pmphRolePermission.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止更新！");
        }
        return pmphRolePermissionDao.updatePmphRolePermission(pmphRolePermission);
    }
}
