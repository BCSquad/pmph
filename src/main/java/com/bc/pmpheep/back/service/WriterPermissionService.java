package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterPermission;

public interface WriterPermissionService {

	Integer addWriterPermission(WriterPermission writerPermission);

	Integer deleteWriterPermissionById(String[] ids);

	Integer updateWriterPermissionById(WriterPermission writerPermission);

	WriterPermission getWriterPermissionByPermissionName(String permissionName);
}
