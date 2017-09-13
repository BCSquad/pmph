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
    public Integer addWriterRole(WriterRole writerRole) throws Exception {
	return writerRoleDao.addWriterRole(writerRole);
    }

    @Override
    public Integer deleteWriterRoleById(String[] ids) throws Exception {
	return writerRoleDao.deleteWriterRoleById(ids);
    }

    @Override
    public Integer updateWriterRoleById(WriterRole writerRole) throws Exception {
	return writerRoleDao.updateWriterRoleById(writerRole);
    }

    @Override
    public WriterRole getWriterRoleByRoleName(String roleName) throws Exception {
	return writerRoleDao.getWriterRoleByRoleName(roleName);
    }

}
