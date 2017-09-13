package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterPermissionDao;
import com.bc.pmpheep.back.po.WriterPermission;

@Service
public class WriterPermissionServiceImpl extends BaseService implements WriterPermissionService {

    @Autowired
    WriterPermissionDao writerPermissionDao;

    @Override
    public Integer addWriterPermission(WriterPermission writerPermission) throws Exception {
	return writerPermissionDao.addWriterPermission(writerPermission);
    }

    @Override
    public Integer deleteWriterPermissionById(String[] ids) throws Exception {
	return writerPermissionDao.deleteWriterPermissionById(ids);
    }

    @Override
    public Integer updateWriterPermissionById(WriterPermission writerPermission) throws Exception {
	return writerPermissionDao.updateWriterPermissionById(writerPermission);
    }

    @Override
    public WriterPermission getWriterPermissionByPermissionName(String permissionName) throws Exception {
	return writerPermissionDao.getWriterPermissionByPermissionName(permissionName);
    }

}
