package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphPermissionDao;
import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphPermissionService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphPermissionServiceImpl extends BaseService implements PmphPermissionService {
	@Autowired
	private PmphPermissionDao pmphPermissionDao;
	
	/**
	 * 
	 * @param  PmphPermission 实体对象
	 * @return  带主键的PmphPermission
	 * @throws Exception 
	 */
	@Override
	public PmphPermission addPmphPermission(PmphPermission pmphPermission) throws Exception{
		return pmphPermissionDao.addPmphPermission(pmphPermission);
	}
	
	/**
	 * 
	 * @param PmphPermission 必须包含主键ID
	 * @return  PmphPermission
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphPermission getPmphPermissionById(PmphPermission pmphPermission) throws Exception{
		if(null==pmphPermission.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphPermissionDao.getPmphPermissionById(pmphPermission);
	}
	
	/**
	 * 
	 * @param PmphPermission
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphPermissionById(PmphPermission pmphPermission) throws Exception{
		if(null==pmphPermission.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphPermissionDao.deletePmphPermissionById(pmphPermission);
	}
	
	/**
	 * @param PmphPermission
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphPermissionById(PmphPermission pmphPermission) throws Exception{
		if(null==pmphPermission.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphPermissionDao.updatePmphPermissionById(pmphPermission);
	}
}
