package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterRolePermissionDao;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterRolePermissionService 实现
 * 
 * @author 曾庆峰 <791038935@qq.com>
 * 
 */
@Service
public class WriterRolePermissionServiceImpl extends BaseService implements
WriterRolePermissionService {

    @Autowired
    private WriterRolePermissionDao writerRolePermissionDao;

    /**
     * 
     * @param WriterRolePermission 实体对象
     * @return 带主键的 WriterRolePermission
     * @throws CheckedServiceException
     */
    @Override
    public WriterRolePermission addWriterRolePermission(WriterRolePermission writerRolePermission)
    throws CheckedServiceException {
        writerRolePermissionDao.addWriterRolePermission(writerRolePermission);
        return writerRolePermission;
    }

    /**
     * 
     * @param id
     * @return WriterRolePermission
     * @throws CheckedServiceException
     */
    @Override
    public WriterRolePermission getWriterRolePermissionById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_ROLE_PERMISSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return writerRolePermissionDao.getWriterRolePermissionById(id);
    }

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteWriterRolePermissionById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_ROLE_PERMISSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return writerRolePermissionDao.deleteWriterRolePermissionById(id);
    }

    /**
     * @param WriterRolePermission
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateWriterRolePermission(WriterRolePermission writerRolePermission)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(writerRolePermission.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_ROLE_PERMISSION,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return writerRolePermissionDao.updateWriterRolePermission(writerRolePermission);
    }

}
