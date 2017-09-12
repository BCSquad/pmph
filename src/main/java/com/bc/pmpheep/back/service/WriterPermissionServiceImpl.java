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
	public Integer addWriterPermission(WriterPermission writerPermission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteWriterPermissionById(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateWriterPermissionById(WriterPermission writerPermission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriterPermission getWriterPermissionByPermissionName(String permissionName) {
		// TODO Auto-generated method stub
		return null;
	}

}
