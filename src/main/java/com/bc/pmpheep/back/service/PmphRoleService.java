package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphRole;

/**
 * PmphRoleService接口
 * 
 * @author 曾庆峰
 *
 */
public interface PmphRoleService {
    /**
     * 添加用户角色
     * 
     * @param pmphRole 添加的用户角色详细信息
     * @return 影响行数
     * @throws Exception
     */
    Integer addPmphRole(PmphRole pmphRole) throws Exception;

    /**
     * 
     * @param ids 需要删除的角色id 数组  
     * @return 影响行数
     * @throws Exception
     */
    Integer deletePmphRoleById(String[] ids) throws Exception;

    /**
     * 
     * @param pmphRole 修改的用户角色
     * @return 影响行数
     * @throws Exception
     */
    Integer updatePmphRoleById(PmphRole pmphRole) throws Exception;

    /**
     * 
     * @param roleName 角色名称
     * @return 需要的角色详细信息
     * @throws Exception 
     */
    PmphRole getPmphRoleByRoleName(String roleName) throws Exception;

}
