package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.util.ObjectUtil;
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
    public WriterRole get(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.get(id);
    }

    @Override
    public List<WriterRole> getListRole(String roleName) throws CheckedServiceException {
        return roleDao.getListRole(roleName);
    }

    @Override
    public WriterUserRole getUserRole(Long uid, Long roleId) throws CheckedServiceException {
        if (ObjectUtil.isNull(uid) || ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止查询");
        }
        return roleDao.getUserRole(uid, roleId);
    }

    @Override
    public List<WriterPermission> getListRoleResource(Long roleId) throws CheckedServiceException {
        if (ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.getListRoleResource(roleId);
    }

    @Override
    public WriterRolePermission getResourceRole(Long roleId, Long resId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(roleId) || ObjectUtil.isNull(resId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止查询");
        }
        return roleDao.getResourceRole(roleId, resId);
    }

    @Override
    public List<Long> getListPmphWriterPermissionIdByRoleId(Long roleId) {
        if (ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.getWriterRolePermissionIdByRoleId(roleId);
    }

    @Override
    public List<WriterRolePermission> getListWriterRolePermission(Long roleId) {
        if (ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
        }
        return roleDao.getWriterRolePermissionByRoleId(roleId);
    }

    @Override
    public WriterRole add(WriterRole writerRole) throws CheckedServiceException {
        if (ObjectUtil.isNull(writerRole)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增角色");
        }
        roleDao.add(writerRole);
        return writerRole;
    }

    @Override
    public Integer addUserRole(Long uid, Long roleId) throws CheckedServiceException {
        if (ObjectUtil.isNull(uid) || ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止新增");
        }
        return roleDao.addUserRole(uid, roleId);

    }

    @Override
    public Integer addRoleResource(Long roleId, List<Long> permissionIds)
    throws CheckedServiceException {
        // 添加时先删除当前权限
        deleteRoleResourceByRoleId(roleId);
        if (ObjectUtil.isNull(roleId) || permissionIds.size() < 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止新增");
        }
        List<WriterRolePermission> lists = new ArrayList<WriterRolePermission>();
        WriterRolePermission writerRolePermission;
        for (Long permissionId : permissionIds) {
            writerRolePermission = new WriterRolePermission();
            writerRolePermission.setRoleId(roleId);
            writerRolePermission.setPermissionId(permissionId);
            lists.add(writerRolePermission);
        }
        return roleDao.addRoleResource(lists);

    }

    @Override
    public Integer update(WriterRole role) throws CheckedServiceException {
        if (ObjectUtil.isNull(role.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止更新");
        }
        return roleDao.update(role);
    }

    @Override
    public Integer delete(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
        }
        return roleDao.delete(id);
    }

    @Override
    public Integer deleteRoleAndResource(List<Long> ids) throws CheckedServiceException {
        if (0 == ids.size()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
        }
        roleDao.batchDelete(ids);
        Integer count = roleDao.batchDeleteRoleResource(ids);
        return count;
    }

    @Override
    public Integer deleteUserRole(Long uid, Long roleId) throws CheckedServiceException {
        if (ObjectUtil.isNull(uid) || ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止删除");
        }
        return roleDao.deleteUserRole(uid, roleId);

    }

    @Override
    public Integer deleteUserRoles(Long uid) throws CheckedServiceException {
        if (ObjectUtil.isNull(uid)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除");
        }
        return roleDao.deleteUserRoles(uid);

    }

    @Override
    public Integer deleteRoleResource(Long roleId, Long resId) throws CheckedServiceException {
        if (ObjectUtil.isNull(roleId) || ObjectUtil.isNull(resId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止删除");
        }
        return roleDao.deleteRoleResource(roleId, resId);

    }

    @Override
    public Integer deleteRoleAndUser(List<Long> ids) throws CheckedServiceException {
        if (0 == ids.size()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除");
        }
        return roleDao.deleteRoleAndUser(ids);
    }

    @Override
    public Integer deleteRoleResourceByRoleId(Long roleId) throws CheckedServiceException {
        if (ObjectUtil.isNull(roleId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
        }
        return roleDao.deleteRoleResourceByRoleId(roleId);
    }

}
