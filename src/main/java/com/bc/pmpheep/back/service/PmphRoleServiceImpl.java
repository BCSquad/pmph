package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphRoleService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class PmphRoleServiceImpl implements PmphRoleService {

    @Autowired
    PmphRoleDao roleDao;

    @Override
    public Integer add(PmphRole role) throws CheckedServiceException {
        if (null == role) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增角色");
        }
        return roleDao.add(role);
    }

    @Override
    public Integer delete(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
        }
        return roleDao.delete(id);
    }

    @Override
    public void deleteRoleAndResource(List<Long> ids) throws CheckedServiceException {
        if (0 == ids.size()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
        }
        roleDao.batchDelete(ids);
        roleDao.batchDeleteRoleResource(ids);
    }

    @Override
    public PmphRole get(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.get(id);
    }

    @Override
    public List<PmphRole> getList() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public Integer update(PmphRole role) throws CheckedServiceException {
        if (null == role.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止更新");
        }
        return roleDao.update(role);
    }

    @Override
    public List<PmphRole> getListRole() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public PmphUserRole getUserRole(Long uid, Long roleId) throws CheckedServiceException {
        if (null == uid || null == roleId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止查询");
        }
        return roleDao.getUserRole(uid, roleId);
    }

    @Override
    public void addUserRole(Long uid, Long roleId) throws CheckedServiceException {
        if (null == uid || null == roleId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止新增");
        }
        roleDao.addUserRole(uid, roleId);

    }

    @Override
    public void deleteUserRole(Long uid, Long roleId) throws CheckedServiceException {
        if (null == uid || null == roleId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止删除");
        }
        roleDao.deleteUserRole(uid, roleId);

    }

    @Override
    public void deleteUserRoles(Long uid) throws CheckedServiceException {
        if (null == uid) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除");
        }
        roleDao.deleteUserRoles(uid);

    }

    @Override
    public List<PmphPermission> getListRoleResource(Long roleId) throws CheckedServiceException {
        if (null == roleId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.getListRoleResource(roleId);
    }

    @Override
    public void addRoleResource(Long roleId, Long resId) throws CheckedServiceException {
        if (null == roleId || null == resId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止新增");
        }
        roleDao.addRoleResource(roleId, resId);
    }

    @Override
    public void deleteRoleResource(Long roleId, Long resId) throws CheckedServiceException {
        if (null == roleId || null == resId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止删除");
        }
        roleDao.deleteRoleResource(roleId, resId);

    }

    @Override
    public PmphRolePermission getResourceRole(Long roleId, Long resId)
    throws CheckedServiceException {
        if (null == roleId || null == resId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止查询");
        }
        return roleDao.getResourceRole(roleId, resId);
    }

    @Override
    public Integer deleteRoleAndUser(List<Long> ids) throws CheckedServiceException {
        if (0 == ids.size()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除");
        }
        return roleDao.deleteRoleAndUser(ids);
    }

}
