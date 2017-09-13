package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterRole;

/**
 * WriterRoleService 接口
 * 
 * @author 曾庆峰
 *
 */
public interface WriterRoleService {
    /**
     * 
     * @param writerRole 添加的作家角色
     * @return 影响行数
     * @throws Exception
     */
    Integer addWriterRole(WriterRole writerRole) throws Exception;

    /**
     * 
     * @param ids 需要删除的角色id数组
     * @return 影响行数
     * @throws Exception 
     */
    Integer deleteWriterRoleById(String[] ids) throws Exception;

    /**
     * 
     * @param writerRole 修改的作家角色
     * @return 影响行数
     * @throws Exception
     */
    Integer updateWriterRoleById(WriterRole writerRole) throws Exception;

    /**
     * 
     * @param roleName 角色的名称
     * @return 影响行数
     * @throws Exception
     */
    WriterRole getWriterRoleByRoleName(String roleName) throws Exception;
}
