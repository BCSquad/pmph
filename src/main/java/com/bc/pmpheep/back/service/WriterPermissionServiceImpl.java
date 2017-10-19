package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterPermissionDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterPermissionService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class WriterPermissionServiceImpl implements WriterPermissionService {

    @Autowired
    WriterPermissionDao writerPermissionDao;

    @Override
    public Integer addWriterPermission(WriterPermission writerPermission)
    throws CheckedServiceException {
        if (Tools.isNullOrEmpty(writerPermission)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源属性为空时禁止添加!");
        }
        return writerPermissionDao.addWriterPermission(writerPermission);
    }

    @Override
    public Integer deleteWriterPermissionById(String[] ids) throws CheckedServiceException {
        if (0 == ids.length) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止删除!");
        }
        return writerPermissionDao.deleteWriterPermissionById(ids);
    }

    @Override
    public Integer updateWriterPermissionById(WriterPermission writerPermission)
    throws CheckedServiceException {
        if (Tools.isNullOrEmpty(writerPermission.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止更新!");
        }
        return writerPermissionDao.updateWriterPermissionById(writerPermission);
    }

    @Override
    public WriterPermission getWriterPermissionByPermissionName(String permissionName)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(permissionName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源名称为空时禁止查询!");
        }
        return writerPermissionDao.getWriterPermissionByPermissionName(permissionName);
    }

    @Override
    public Integer delete(Long id) throws CheckedServiceException {
        if (Tools.isNullOrEmpty(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止删除!");
        }
        return writerPermissionDao.delete(id);
    }

    @Override
    public WriterPermission get(Long id) throws CheckedServiceException {
        if (Tools.isNullOrEmpty(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.RESOUCE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "资源ID为空时禁止查询!");
        }
        return writerPermissionDao.get(id);
    }

    @Override
    public List<WriterPermission> getListResource() throws CheckedServiceException {
        return writerPermissionDao.getListResource();
    }

    @Override
    public List<WriterPermission> getListAllParentMenu() {
        List<WriterPermission> permissions = writerPermissionDao.getListAllParentMenu();
        for (WriterPermission permission : permissions) {
            List<WriterPermission> subList = this.getListChildMenuByParentId(permission.getId());
            permission.setChildren(subList);
        }
        return permissions;
    }

    @Override
    public List<WriterPermission> getListChildMenuByParentId(Long parentId) {
        return writerPermissionDao.getListChildMenuByParentId(parentId);
    }

}
