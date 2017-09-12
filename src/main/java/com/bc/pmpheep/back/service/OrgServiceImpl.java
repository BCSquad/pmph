package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgDao;
import com.bc.pmpheep.back.po.Org;

/**
 * OrgService 接口实现
 * @author Mryang
 *
 */
@Service
public class OrgServiceImpl extends BaseService implements OrgService {
	
	@Autowired
	private OrgDao orgDao;
	
	/**
	 * 
	 * @param area 实体对象
	 * @return  影响行数
	 * @throws Exception 
	 */
	@Override
	public Integer addOrg(Org org) throws Exception{
		return orgDao.addOrg(org);
	}
	
	/**
	 * 
	 * @param area 必须包含主键ID
	 * @return  area
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Org getOrgById(Org org) throws Exception{
		if(null==org.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgDao.getOrgById(org);
	}
	
	/**
	 * 
	 * @param area
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteOrgById(Org org) throws Exception{
		if(null==org.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgDao.deleteOrgById(org);
	}
	
	/**
	 * @param area
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateOrgById(Org org) throws Exception{
		if(null==org.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgDao.updateOrgById(org);
	}

}
