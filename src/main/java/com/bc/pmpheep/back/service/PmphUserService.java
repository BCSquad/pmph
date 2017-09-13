package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphUser;

/**
 * PmphUserService 接口
 * 
 * @author 曾庆峰
 *
 */
public interface PmphUserService {
    /**
     * 
     * @param pmphUser 添加的用户
     * @return 影响行数
     * @throws Exception
     */
    Integer addPmphUser(PmphUser pmphUser) throws Exception;

    /**
     * 
     * @param ids 删除的用户Id 数组
     * @return 影响行数
     * @throws Exception
     */
    Integer deletePmphUserById(String[] ids) throws Exception;

    /**
     * 
     * @param pmphUser 修改的用户信息
     * @return 影响行数
     * @throws Exception
     */
    Integer updatePmphUserById(PmphUser pmphUser) throws Exception;

    /**
     * 
     * @param username 用户名
     * @return 查询到的用户详细信息
     * @throws Exception
     */
    PmphUser getByUsername(String username) throws Exception;

}
