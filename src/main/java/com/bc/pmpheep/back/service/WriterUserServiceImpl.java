package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.po.WriterUser;

@Service
public class WriterUserServiceImpl extends BaseService implements WriterUserService {

    @Autowired
    WriterUserDao writerUserDao;

    @Override
    public Integer addWriterUser(WriterUser writerUser) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer deleteWriterUserById(String[] ids) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer updateWriterUserById(WriterUser writerUser) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public WriterUser getWriterUserByUsername(String username) {
	// TODO Auto-generated method stub
	return null;
    }

}
