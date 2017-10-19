package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphUserRoleDao;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * @introduction PmphUserRoleService 实现
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月19日 上午11:23:30
 * 
 */
@Service
public class PmphUserRoleServiceImpl extends BaseService implements PmphUserRoleService {

    @Autowired
    private PmphUserRoleDao pmphUserRoleDao;

    /**
     * 
     * @introduction 新增
     * @author Mryang
     * @createDate 2017年9月19日 上午11:34:12
     * @param pmphUserRole
     * @return 带主键的pmphUserRole
     * @throws CheckedServiceException
     */
    @Override
    public PmphUserRole addPmphUserRole(PmphUserRole pmphUserRole) throws CheckedServiceException {
        if (ObjectUtil.isNull(pmphUserRole)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数对象为空");
        }
        if (ObjectUtil.isNull(pmphUserRole.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户id为空");
        }
        if (ObjectUtil.isNull(pmphUserRole.getRoleId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "角色id为空");
        }
        pmphUserRoleDao.addPmphUserRole(pmphUserRole);
        return pmphUserRole;
    }

    /**
     * 
     * @introduction 根据id查询 PmphUserRole
     * @author Mryang
     * @createDate 2017年9月19日 上午11:33:43
     * @param id
     * @return PmphUserRole
     * @throws CheckedServiceException
     */
    @Override
    public PmphUserRole getPmphUserRoleById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphUserRoleDao.getPmphUserRoleById(id);
    }

    /**
     * 根据id删除一个 PmphUserRole
     * 
     * @introduction
     * @author Mryang
     * @createDate 2017年9月19日 上午11:32:59
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deletePmphUserRoleById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphUserRoleDao.deletePmphUserRoleById(id);
    }

    /**
     * 
     * @introduction 通过主键id更新PmphUserRole 不为null 的字段
     * @author Mryang
     * @createDate 2017年9月19日 上午11:31:17
     * @param pmphUserRole
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updatePmphUserRole(PmphUserRole pmphUserRole) throws CheckedServiceException {
        if (ObjectUtil.isNull(pmphUserRole.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphUserRoleDao.updatePmphUserRole(pmphUserRole);
    }

}
