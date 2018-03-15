package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.vo.DepartmentOptsVO;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphDepartmentService 接口
 * 
 * @author Mryang
 */
public interface PmphDepartmentService {

	/**
	 * 
	 * @param PmphDepartment
	 *            实体对象
	 * @return 带主键的PmphDepartment
	 * @throws CheckedServiceException
	 */
	PmphDepartment addPmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException;

	/**
	 * 
	 * @param PmphDepartment
	 *            实体对象
	 * @return 带主键的PmphDepartment
	 * @throws CheckedServiceException
	 */
	PmphDepartment add(PmphDepartment pmphDepartment) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 * @return PmphDepartment
	 * @throws CheckedServiceException
	 */
	PmphDepartment getPmphDepartmentById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param name
	 * @return PmphDepartment
	 * @throws CheckedServiceException
	 */
	PmphDepartment getPmphDepartmentByName(String name) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphDepartmentById(Long id) throws CheckedServiceException;

	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphDepartment(PmphDepartment pmphDepartment) throws CheckedServiceException;

	/**
	 * 
	 * 获取出版社所有部门
	 * 
	 * @author 曾庆峰
	 * @param parentId
	 *            父级部门id 为null时初始化
	 * @return 已经分级的社内部门
	 * @throws CheckedServiceException
	 * @update Mryang
	 */
	PmphUserDepartmentVO listPmphDepartment(Long id) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：模糊查询部门
	 *
	 * @param dpName
	 *            部门名称
	 * @return 查询的结果集
	 * @throws CheckedServiceException
	 *
	 */
	List<PmphUserDepartmentVO> listPmphUserDepartmentByDpName(String dpName) throws CheckedServiceException;

	/**
	 * 删除该部门
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:39:03
	 * @param id
	 * @return 删除条数
	 */
	Integer deletePmphDepartmentBatch(Long id);

	/**
	 * 
	 * 
	 * 功能描述：选题申报获取分配的部门以及主任名称
	 *
	 * @param pageParameter 
	 * @return
	 *
	 */
	PageResult<DepartmentOptsVO> listOpts(PageParameter<DepartmentOptsVO> pageParameter) throws CheckedServiceException;
	
	/**功能描述： 获取所有部门
	 * 
	 * @return
	 */
	List<PmphDepartment> getPmphDepartmentList()throws CheckedServiceException;;

}
