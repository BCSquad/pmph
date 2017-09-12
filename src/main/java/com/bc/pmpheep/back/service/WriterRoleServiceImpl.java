package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.po.WriterRole;

@Service
public class WriterRoleServiceImpl extends BaseService implements WriterRoleService {

	@Autowired
	WriterRoleDao writerRoleDao;

	@Override
	public Integer addWriterRole(WriterRole writerRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteWriterRoleById(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateWriterRoleById(WriterRole writerRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriterRole getWriterRoleByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

}
