package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterRole;

public interface WriterRoleService {
    Integer addWriterRole(WriterRole writerRole) throws Exception;

    Integer deleteWriterRoleById(String[] ids) throws Exception;

    Integer updateWriterRoleById(WriterRole writerRole) throws Exception;

    WriterRole getWriterRoleByRoleName(String roleName) throws Exception;
}
