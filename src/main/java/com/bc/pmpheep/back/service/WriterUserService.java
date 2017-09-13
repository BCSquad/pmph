package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterUser;

/**
 * WriterUserService 接口
 * 
 * @author 曾庆峰
 *
 */
public interface WriterUserService {

    /**
     * 添加一个作家用户
     * 
     * @param writerUser
     *            新增的作家用户
     * @return 影响行数
     */
    Integer addWriterUser(WriterUser writerUser) throws Exception;

    /**
     * 删除一个或者多个用户
     * 
     * @param ids
     *            作家用户id 数组
     * @return 影响行数
     */
    Integer deleteWriterUserById(String[] ids) throws Exception;

    /**
     * 修改作家用户信息
     * 
     * @param writerUser
     *            更新的用户信息
     * @return 影响行数
     */
    Integer updateWriterUserById(WriterUser writerUser) throws Exception;

    /**
     * 根据用户名详细查询作家用户
     * 
     * @param username
     *            作家用户用户名
     * @return 需要的作家用户详细信息
     * @throws Exception
     */
    WriterUser getWriterUserByUsername(String username) throws Exception;

}
