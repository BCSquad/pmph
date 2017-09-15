package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;
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
        roleDao.add(writerRole);
        return writerRole;
    }

    @Override
    public Integer delete(int id) throws CheckedServiceException {
        return roleDao.delete(id);
    }

    @Transactional
    @Override
    public void deleteRoleAndResource(List<Integer> ids) throws CheckedServiceException {
        roleDao.batchDelete(ids);
        roleDao.batchDeleteRoleResource(ids);

    }

    @Override
    public WriterRole get(int id) throws CheckedServiceException {
        return roleDao.get(id);
    }

    @Override
    public List<WriterRole> getList() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public Integer update(WriterRole role) throws CheckedServiceException {
        return roleDao.update(role);
    }

    @Override
    public List<WriterRole> getListRole() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public WriterUserRole getUserRole(int uid, int roleId) throws CheckedServiceException {
        return roleDao.getUserRole(uid, roleId);
    }

    @Override
    public void addUserRole(int uid, int roleId) throws CheckedServiceException {
        roleDao.addUserRole(uid, roleId);

    }

    @Override
    public void deleteUserRole(int uid, int roleId) throws CheckedServiceException {
        roleDao.deleteUserRole(uid, roleId);

    }

    @Override
    public void deleteUserRoles(int uid) throws CheckedServiceException {
        roleDao.deleteUserRoles(uid);

    }

    @Override
    public List<WriterPermission> getListRoleResource(int roleId) throws CheckedServiceException {
        return roleDao.getListRoleResource(roleId);
    }

    @Override
    public void addRoleResource(int roleId, int resId) throws CheckedServiceException {
        roleDao.addRoleResource(roleId, resId);

    }

    @Override
    public void deleteRoleResource(int roleId, int resId) throws CheckedServiceException {
        roleDao.deleteRoleResource(roleId, resId);

    }

    @Override
    public WriterRolePermission getResourceRole(int roleId, int resId)
    throws CheckedServiceException {
        return roleDao.getResourceRole(roleId, resId);
    }

    @Override
    public Integer deleteRoleAndUser(List<Integer> ids) throws CheckedServiceException {
        return roleDao.deleteRoleAndUser(ids);
    }

}
