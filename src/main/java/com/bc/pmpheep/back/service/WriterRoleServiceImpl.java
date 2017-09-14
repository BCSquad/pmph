package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;

/**
 * WriterRoleService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class WriterRoleServiceImpl extends BaseService implements WriterRoleService {

    @Autowired
    WriterRoleDao roleDao;

    @Override
    public Integer add(WriterRole role) throws Exception {
        return roleDao.add(role);
    }

    @Override
    public Integer delete(int id) throws Exception {
        return roleDao.delete(id);
    }

    @Transactional
    @Override
    public void deleteRoleAndResource(List<Integer> ids) throws Exception {
        roleDao.batchDelete(ids);
        roleDao.batchDeleteRoleResource(ids);

    }

    @Override
    public WriterRole get(int id) throws Exception {
        return roleDao.get(id);
    }

    @Override
    public List<WriterRole> getList() throws Exception {
        return roleDao.getListRole();
    }

    @Override
    public Integer update(WriterRole role) throws Exception {
        return roleDao.update(role);
    }

    @Override
    public List<WriterRole> getListRole() throws Exception {
        return roleDao.getListRole();
    }

    @Override
    public WriterUserRole getUserRole(int uid, int roleId) throws Exception {
        return roleDao.getUserRole(uid, roleId);
    }

    @Override
    public void addUserRole(int uid, int roleId) throws Exception {
        roleDao.addUserRole(uid, roleId);

    }

    @Override
    public void deleteUserRole(int uid, int roleId) throws Exception {
        roleDao.deleteUserRole(uid, roleId);

    }

    @Override
    public void deleteUserRoles(int uid) throws Exception {
        roleDao.deleteUserRoles(uid);

    }

    @Override
    public List<WriterPermission> getListRoleResource(int roleId) throws Exception {
        return roleDao.getListRoleResource(roleId);
    }

    @Override
    public void addRoleResource(int roleId, int resId) throws Exception {
        roleDao.addRoleResource(roleId, resId);

    }

    @Override
    public void deleteRoleResource(int roleId, int resId) throws Exception {
        roleDao.deleteRoleResource(roleId, resId);

    }

    @Override
    public WriterRolePermission getResourceRole(int roleId, int resId) throws Exception {
        return roleDao.getResourceRole(roleId, resId);
    }

    @Override
    public Integer deleteRoleAndUser(List<Integer> ids) throws Exception {
        return roleDao.deleteRoleAndUser(ids);
    }

}
