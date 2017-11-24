package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphRoleMaterialDao;
import com.bc.pmpheep.back.po.PmphRoleMaterial;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：角色-教材权限关联表 业务实现
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月23日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Service
public class PmphRoleMaterialServiceImpl implements PmphRoleMaterialService {

	@Autowired
	PmphRoleMaterialDao pmphRoleMaterialDao;

	@Override
	public Integer addPmphRoleMaterial(PmphRoleMaterial pmphRoleMaterial) throws CheckedServiceException {
		return pmphRoleMaterialDao.add(pmphRoleMaterial);
	}

	@Override
	public Integer updatePmphRoleMaterialByRoleId(PmphRoleMaterial pmphRoleMaterial) throws CheckedServiceException {
		return pmphRoleMaterialDao.update(pmphRoleMaterial);
	}

	@Override
	public PmphRoleMaterial getPmphRoleMaterialByRoleId(Long roleId) throws CheckedServiceException {
		return pmphRoleMaterialDao.getPmphRoleMaterial(roleId);
	}

	@Override
	public Integer deletePmphRoleMaterialByRoleId(Long roleId) throws CheckedServiceException {
		return pmphRoleMaterialDao.delete(roleId);
	}

}
