package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.po.PmphRole;

@Service
public class PmphRoleServiceImpl extends BaseService implements PmphRoleService {
	@Autowired
	PmphRoleDao pmphRoleDao;

	@Override
	public Integer addPmphRole(PmphRole pmphRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deletePmphRoleById(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updatePmphRoleById(PmphRole pmphRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PmphRole getPmphRoleByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

}
