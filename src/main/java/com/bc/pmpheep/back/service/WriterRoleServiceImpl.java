package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterRoleService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class WriterRoleServiceImpl implements WriterRoleService {

    @Autowired
    WriterRoleDao roleDao;

    @Override
    public WriterRole add(WriterRole writerRole) throws CheckedServiceException {
        if (null == writerRole) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增角色");
        }
        roleDao.add(writerRole);
        return writerRole;
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
    public WriterRole get(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.get(id);
    }

    @Override
    public List<WriterRole> getList() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public Integer update(WriterRole role) throws CheckedServiceException {
        if (null == role.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止更新");
        }
        return roleDao.update(role);
    }

    @Override
    public List<WriterRole> getListRole() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public WriterUserRole getUserRole(Long uid, Long roleId) throws CheckedServiceException {
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
    public List<WriterPermission> getListRoleResource(Long roleId) throws CheckedServiceException {
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
    public WriterRolePermission getResourceRole(Long roleId, Long resId)
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
