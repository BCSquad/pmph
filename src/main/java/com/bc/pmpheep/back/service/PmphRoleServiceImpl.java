package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphRoleService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class PmphRoleServiceImpl extends BaseService implements PmphRoleService {

    @Autowired
    PmphRoleDao roleDao;

    @Override
    public Integer add(PmphRole role) throws CheckedServiceException {
        return roleDao.add(role);
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
    public PmphRole get(int id) throws CheckedServiceException {
        return roleDao.get(id);
    }

    @Override
    public List<PmphRole> getList() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public Integer update(PmphRole role) throws CheckedServiceException {
        return roleDao.update(role);
    }

    @Override
    public List<PmphRole> getListRole() throws CheckedServiceException {
        return roleDao.getListRole();
    }

    @Override
    public PmphUserRole getUserRole(int uid, int roleId) throws CheckedServiceException {
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
    public List<PmphPermission> getListRoleResource(int roleId) throws CheckedServiceException {
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
    public PmphRolePermission getResourceRole(int roleId, int resId) throws CheckedServiceException {
        return roleDao.getResourceRole(roleId, resId);
    }

    @Override
    public Integer deleteRoleAndUser(List<Integer> ids) throws CheckedServiceException {
        return roleDao.deleteRoleAndUser(ids);
    }

}
