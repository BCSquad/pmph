/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.vo.PmphEditorVO;
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

    /**
     * 根据主键 获取用户要更新的信息
     *
     * @param id
     * @return
     * @author Mryang
     * @createDate 2017年12月11日 下午5:27:20
     */
    PmphUser getInfo(Long id);

    /**
     * 根据id和原密码修改密码
     *
     * @param map
     * @return
     * @author Mryang
     * @createDate 2017年12月12日 上午8:46:44
     */
    Integer updatePassword(Map<String, Object> map);

    List<PmphUser> getListUser();

    List<PmphUser> getListByUsernameAndRealname(@Param("name") String name,
                                                @Param("start") int start, @Param("size") int size);

    PmphUser getByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);

    PmphUser getByOpenid(@Param("openid") String openid);

    int updateUserOpenid(@Param("openid") String openid, @Param("username") String username);

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
     * <pre>
     * 功能描述：根据用户Id查询对应权限
     * 使用示范：
     *
     * &#64;param userId
     * &#64;return
     * </pre>
     */
    List<Long> getPmphUserPermissionByUserId(Long userId);

    /**
     * <pre>
     * 功能描述：根据用户Id查询对应教材权限
     * 使用示范：
     *
     * &#64;param userId
     * &#64;return
     * </pre>
     */
    List<Integer> getMaterialPermissionByUserId(Long userId);

    /**
     * 功能描述：查询表单的数据总条数
     *
     * @return 表单的总条数
     */
    Long getPmphUserCount();

    /**
     * 功能描述： 获取社内用户的
     *
     * @param page
     * @return
     */
    Integer getListPmphUserTotal(PageParameter<PmphUserManagerVO> pageParameter);

    /**
     * 功能描述： 获取社内用户的
     *
     * @param page
     * @return
     */
    List<PmphUserManagerVO> getListPmphUser(PageParameter<PmphUserManagerVO> pageParameter);

    /**
     * 功能描述：
     *
     * @param user
     * @return
     */
    Integer updatePmphUserOfBack(PmphUserManagerVO pmphUserManagerVO);

    /**
     * 功能描述：根据部门id判断部门中是否还有用户
     *
     * @param departmentId 部门id
     * @return
     */
    List<PmphUser> getPmphUserByDepartmentId(Long departmentId);

    /**
     * Description:统计部门下的编辑数量
     *
     * @param
     * @return Integer
     * @author:lyc
     * @date:2017年12月27日下午4:14:53
     */
    Integer totalEditors(@Param("path") String path, @Param("departmentId") Long departmentId,
                         @Param("realName") String realName);

    /**
     * Description:选题申报部门主任获取部门编辑列表
     *
     * @param
     * @return List<PmphEditorVO>
     * @author:lyc
     * @date:2017年12月27日下午4:03:55
     */
    List<PmphEditorVO> listEditors(@Param("path") String path,
                                   @Param("departmentId") Long departmentId, @Param("realName") String realName,
                                   @Param("pageSize") Integer pageSize, @Param("start") Integer start);

    /**
     * 根据用户名获取用户 tyc
     *
     * @param username
     * @return
     */
    PmphUser getPmphUser(String username);

    /**
     * <pre>
     * 功能描述：根据部门ID查询部门下的所有用户
     * 使用示范：
     *
     * &#64;param departmentId 部门ID
     * &#64;return 对象集合
     * </pre>
     */
    List<PmphUser> listPmphUserByDepartmentId(Long departmentId);

    /**
     * 功能描述：判断当前用户是否是运维人员
     *
     * @param id
     * @return
     */
    List<PmphUser> isOpts(Long id);

    /**
     * 功能描述：判断当前用户是否是编辑
     *
     * @param id
     * @return
     */
    List<PmphUser> isEditor(Long id);

    /**
     * <pre>
     * 功能描述：根据username查询PmphUser对象
     * 使用示范：
     *
     * @param username 用户名
     * @return PmphUser对象
     * </pre>
     */
    PmphUser getPmphUserByUsername(@Param("username") String username, @Param("id") Long id);

    /**
     * 根据某人id查出其 本部门及上级各部门的某角色的用户
     * @param SbId A某 pmphUser的id
     * @param role_id 角色id （角色名称和id 仅需一个 另一个保留为null）
     * @param role_name 角色名称 （角色名称和id 仅需一个 另一个保留为null）
     * @return
     */
    List<PmphUser> getSomebodyParentDeptsPmphUserOfSomeRole(@Param(value = "userId") Long sbId,@Param(value = "role_id") Long role_id,@Param(value = "role_name") String role_name);

    /**
     * 通过企业微信用户id查询社内用户
     * @param wechatUserId
     * @return
     */
    PmphUser getPmphUserByOpenid(@Param(value = "wechatUserId") String wechatUserId);

    /**
     * 根据某人id查出其 本部门及上级各部门的部门主任（is_director=1）
     * @param sbId A某 pmphUser的id
     * @return
     */
    List<PmphUser> getSomebodyParentDeptsDirectorPmphUser(Long sbId);
}
