package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupDao;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphGroupService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphGroupServiceImpl extends BaseService implements PmphGroupService {
	@Autowired
	private PmphGroupDao pmphGroupnDao;
	
	/**
	 * 
	 * @param  PmphGroup 实体对象
	 * @return  带主键的PmphGroup
	 * @throws Exception 
	 */
	@Override
	public PmphGroup addPmphGroup (PmphGroup pmphGroup) throws Exception{
		return pmphGroupnDao.addPmphGroup(pmphGroup);
	}
	
	/**
	 * 
	 * @param PmphGroup 必须包含主键ID
	 * @return  PmphGroup
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphGroup getPmphGroupById(PmphGroup pmphGroup) throws Exception{
		if(null==pmphGroup.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupnDao.getPmphGroupById(pmphGroup);
	}
	
	/**
	 * 
	 * @param PmphGroup
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphGroupById(PmphGroup pmphGroup) throws Exception{
		if(null==pmphGroup.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupnDao.deletePmphGroupById(pmphGroup);
	}
	
	/**
	 * @param PmphGroup
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphGroupById(PmphGroup pmphGroup) throws Exception{
		if(null==pmphGroup.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphGroupnDao.updatePmphGroupById(pmphGroup);
	}
}
