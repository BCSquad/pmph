package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphPermissionDao;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphPermissionService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphPermissionServiceImpl implements PmphPermissionService {
    @Autowired
    private PmphPermissionDao pmphPermissionDao;

    /**
     * 
     * @param PmphPermission 实体对象
     * @return 带主键的PmphPermission
     * @throws CheckedServiceException
     */
    @Override
    public PmphPermission addPmphPermission(PmphPermission pmphPermission)
    throws CheckedServiceException {
        pmphPermissionDao.addPmphPermission(pmphPermission);
        return  pmphPermission;
    }

    /**
     * 
     * @param PmphPermission 必须包含主键ID
     * @return PmphPermission
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public PmphPermission getPmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (null == pmphPermission.getId()) {
            throw new NullPointerException("主键id为空");
        }
        return pmphPermissionDao.getPmphPermissionById(pmphPermission);
    }

    /**
     * 
     * @param PmphPermission
     * @return 影响行数
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    @Override
    public Integer deletePmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (null == pmphPermission.getId()) {
            throw new NullPointerException("主键id为空");
        }
        return pmphPermissionDao.deletePmphPermissionById(pmphPermission);
    }

    /**
     * @param PmphPermission
     * @return 影响行数
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    @Override
    public Integer updatePmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException {
        if (null == pmphPermission.getId()) {
            throw new NullPointerException("主键id为空");
        }
        return pmphPermissionDao.updatePmphPermissionById(pmphPermission);
    }

    @Override
    public Integer delete(int id) throws CheckedServiceException {
        return pmphPermissionDao.delete(id);
    }

    @Override
    public PmphPermission get(int id) throws CheckedServiceException {
        return pmphPermissionDao.get(id);
    }

    @Override
    public List<PmphPermission> getListResource() throws CheckedServiceException {
        return pmphPermissionDao.getListResource();
    }
}
