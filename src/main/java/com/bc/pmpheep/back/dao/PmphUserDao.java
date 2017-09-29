/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;

/**
 * PmphUser实体类数据访问层接口
 * 
 * @author L.X <gugia@qq.com>
 */
public interface PmphUserDao {

    /**
     * 添加一个用户
     * 
     * @param pmphUser 添加用户的详细信息
     * @return 影响的行数
     */
    Integer add(PmphUser user);

    Integer update(PmphUser user);

    Integer delete(Long id);

    Integer batchDelete(@Param("ids") List<Long> ids);

    PmphUser get(Long id);

    List<PmphUser> getListUser();

    List<PmphUser> getListByUsernameAndRealname(@Param("name") String name,
    @Param("start") int start, @Param("size") int size);

    PmphUser getByUsernameAndPassword(@Param("username") String username,
    @Param("password") String password);

    /**
     * 根据角色 id 查询所有是该角色的用户列表
     * 
     * @param rid
     * @return
     */
    List<PmphUser> getListByRole(Long rid);

    List<PmphPermission> getListAllResources(Long uid);

    List<String> getListRoleSnByUser(Long uid);

    List<PmphRole> getListUserRole(Long uid);

    /**
     * 
     * 功能描述：查询表单的数据总条数
     * 
     * @return 表单的总条数
     */
    Long getPmphUserCount();

    /**
     * 
     * 
     * 功能描述： 获取社内用户的
     * 
     * @param page
     * @return
     * 
     */
    Integer getListPmphUserTotal(Page<PmphUserManagerVO, PmphUserManagerVO> page);

    /**
     * 
     * 
     * 功能描述： 获取社内用户的
     * 
     * @param page
     * @return
     * 
     */
    List<PmphUserManagerVO> getListPmphUser(Page<PmphUserManagerVO, PmphUserManagerVO> page);

    /**
     * 
     * 
     * 功能描述：
     * 
     * @param user
     * @return
     * 
     */
    Integer updatePmphUserOfBack(PmphUserManagerVO pmphUserManagerVO);

}
