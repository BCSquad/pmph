package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterRole;

public interface WriterRoleService {
	Integer addWriterRole(WriterRole writerRole);

	Integer deleteWriterRoleById(String[] ids);

	Integer updateWriterRoleById(WriterRole writerRole);

	WriterRole getWriterRoleByRoleName(String roleName);
}
