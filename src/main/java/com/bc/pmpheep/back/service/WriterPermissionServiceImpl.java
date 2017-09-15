package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterPermissionDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterPermissionService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class WriterPermissionServiceImpl extends BaseService implements WriterPermissionService {

    @Autowired
    WriterPermissionDao writerPermissionDao;

    @Override
    public Integer addWriterPermission(WriterPermission writerPermission)
    throws CheckedServiceException {
        return writerPermissionDao.addWriterPermission(writerPermission);
    }

    @Override
    public Integer deleteWriterPermissionById(String[] ids) throws CheckedServiceException {
        return writerPermissionDao.deleteWriterPermissionById(ids);
    }

    @Override
    public Integer updateWriterPermissionById(WriterPermission writerPermission)
    throws CheckedServiceException {
        return writerPermissionDao.updateWriterPermissionById(writerPermission);
    }

    @Override
    public WriterPermission getWriterPermissionByPermissionName(String permissionName)
    throws CheckedServiceException {
        return writerPermissionDao.getWriterPermissionByPermissionName(permissionName);
    }

    @Override
    public Integer delete(int id) throws CheckedServiceException {
        return writerPermissionDao.delete(id);
    }

    @Override
    public WriterPermission get(int id) throws CheckedServiceException {
        return writerPermissionDao.get(id);
    }

    @Override
    public List<WriterPermission> getListResource() throws CheckedServiceException {
        return writerPermissionDao.getListResource();
    }

}
