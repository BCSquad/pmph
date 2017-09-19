package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * @introduction  PmphUserRoleService  接口
 *
 * @author Mryang
 *
 * @createDate 2017年9月19日 上午11:38:28
 *
 */
public interface PmphUserRoleService {
	

	/**
	 * 
	 * @introduction  新增 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:34:12
	 * @param pmphUserRole
	 * @return  带主键的pmphUserRole
	 * @throws CheckedServiceException
	 */
	PmphUserRole addPmphUserRole(PmphUserRole pmphUserRole) throws CheckedServiceException;
    
	/**
	 * 
	 * @introduction   根据id查询 PmphUserRole 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:33:43
	 * @param id
	 * @return  PmphUserRole
	 * @throws CheckedServiceException
	 */
	PmphUserRole getPmphUserRoleById(Long id) throws CheckedServiceException ;

	/**
	 * 根据id删除一个 PmphUserRole 
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:32:59
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphUserRoleById(Long id) throws CheckedServiceException ;

	/**
	 * 
	 * @introduction  通过主键id更新PmphUserRole 不为null 的字段
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:31:17
	 * @param pmphUserRole
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphUserRole(PmphUserRole pmphUserRole) throws CheckedServiceException;
}
