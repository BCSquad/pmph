package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphUserDao;
import com.bc.pmpheep.back.po.PmphUser;

@Service
public class PmphUserServiceImpl extends BaseService implements PmphUserService {
	@Autowired
	PmphUserDao pmphUserDao;

	@Override
	public Integer addPmphUser(PmphUser pmphUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deletePmphUserById(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updatePmphUserById(PmphUser pmphUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PmphUser getByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
