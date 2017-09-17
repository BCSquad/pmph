package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphDepartmentDao;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphDepartment addPmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException{
		if(null==pmphDepartment.getDpName()){
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT, CheckedExceptionResult.NULL_PARAM, "部门名称为空");
		}
		
		pmphDepartmentDao.addPmphDepartment(pmphDepartment);
		return pmphDepartment;
	}
	
	/**
	 * 
	 * @param id
	 * @return  PmphDepartment
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphDepartment getPmphDepartmentById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphDepartmentDao.getPmphDepartmentById(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphDepartmentById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphDepartmentDao.deletePmphDepartmentById(id);
	}
	
	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updatePmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException{
		if(null==pmphDepartment.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.PMPH_DEPARTMENT, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphDepartmentDao.updatePmphDepartment(pmphDepartment);
	}

}
