package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphDepartmentDao;
import com.bc.pmpheep.back.po.PmphDepartment;

/**
 * PmphDepartmentService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphDepartmentServiceImpl extends BaseService  implements PmphDepartmentService {
	@Autowired
	private PmphDepartmentDao pmphDepartmentDao;
	
	/**
	 * 
	 * @param  PmphDepartment 实体对象
	 * @return  带主键的PmphDepartment
	 * @throws Exception 
	 */
	@Override
	public PmphDepartment addPmphDepartment(PmphDepartment pmphDepartment) throws Exception{
		pmphDepartmentDao.addPmphDepartment(pmphDepartment);
		return pmphDepartment;
	}
	
	/**
	 * 
	 * @param PmphDepartment 必须包含主键ID
	 * @return  PmphDepartment
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphDepartment getPmphDepartmentById(PmphDepartment pmphDepartment) throws Exception{
		if(null==pmphDepartment.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphDepartmentDao.getPmphDepartmentById(pmphDepartment);
	}
	
	/**
	 * 
	 * @param PmphDepartment
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphDepartmentById(PmphDepartment pmphDepartment) throws Exception{
		if(null==pmphDepartment.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphDepartmentDao.deletePmphDepartmentById(pmphDepartment);
	}
	
	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphDepartmentById(PmphDepartment pmphDepartment) throws Exception{
		if(null==pmphDepartment.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphDepartmentDao.updatePmphDepartmentById(pmphDepartment);
	}

}
