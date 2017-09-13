package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.po.PmphRole;

/**
 * PmphRoleService 实现
 * 
 * @author 曾庆峰
 *
 */
@Service
public class PmphRoleServiceImpl extends BaseService implements PmphRoleService {

    @Autowired
    PmphRoleDao pmphRoleDao;

    @Override
    public Integer addPmphRole(PmphRole pmphRole) throws Exception {
	return pmphRoleDao.addPmphRole(pmphRole);
    }

    @Override
    public Integer deletePmphRoleById(String[] ids) throws Exception {
	return pmphRoleDao.deletePmphRoleById(ids);
    }

    @Override
    public Integer updatePmphRoleById(PmphRole pmphRole) throws Exception {
	return pmphRoleDao.updatePmphRoleById(pmphRole);
    }

    @Override
    public PmphRole getPmphRoleByRoleName(String roleName) throws Exception {
	return pmphRoleDao.getPmphRoleByRoleName(roleName);
    }

}
