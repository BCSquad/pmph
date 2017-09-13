package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.po.WriterUser;

/**
 * WriterUserService 实现
 * 
 * @author 曾庆峰
 *
 */
@Service
public class WriterUserServiceImpl extends BaseService implements WriterUserService {

    @Autowired
    WriterUserDao writerUserDao;

    /**
     * 
     * @param WriterUser
     *            实体对象
     * @return 影响行数
     * @throws Exception
     */
    @Override
    public Integer addWriterUser(WriterUser writerUser) throws Exception {
	return writerUserDao.addWriterUser(writerUser);
    }

    /**
     * 
     * @param ids
     *            需要删除的id 数组
     * @return 影响行数
     * @throws Exception
     */
    @Override
    public Integer deleteWriterUserById(String[] ids) throws Exception {
	return writerUserDao.deleteWriterUserById(ids);
    }

    /**
     * 
     * @param WriterUser
     *            实体对象
     * @return 影响行数
     * @throws Exception
     */
    @Override
    public Integer updateWriterUserById(WriterUser writerUser) throws Exception {
	return writerUserDao.updateWriterUserById(writerUser);
    }

    /**
     * 
     * @param username
     *            作家用户用户名
     * @return 需要的作家用户信息
     * @throws Exception
     */
    @Override
    public WriterUser getWriterUserByUsername(String username) throws Exception {
	return writerUserDao.getWriterUserByUsername(username);
    }

}
