package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphUserDao;
import com.bc.pmpheep.back.po.PmphUser;

/**
 * PmphUserService 实现
 * 
 * @author 曾庆峰
 *
 */
@Service
public class PmphUserServiceImpl extends BaseService implements PmphUserService {

    @Autowired
    PmphUserDao pmphUserDao;

    @Override
    public Integer addPmphUser(PmphUser pmphUser) throws Exception {
	return pmphUserDao.addPmphUser(pmphUser);
    }

    @Override
    public Integer deletePmphUserById(String[] ids) throws Exception {
	return pmphUserDao.deletePmphUserById(ids);
    }

    @Override
    public Integer updatePmphUserById(PmphUser pmphUser) throws Exception {
	return pmphUserDao.updatePmphUserById(pmphUser);
    }

    @Override
    public PmphUser getByUsername(String username) throws Exception {
	return pmphUserDao.getByUsername(username);
    }

}
