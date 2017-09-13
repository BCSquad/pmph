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
    Integer addWriterUser(WriterUser writerUser);

    /**
     * 
     * @param ids
     * @return
     */
    Integer deleteWriterUserById(String[] ids);

    Integer updateWriterUserById(WriterUser writerUser);

    WriterUser getWriterUserByUsername(String username);

}
