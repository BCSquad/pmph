package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupMemberService 接口
 * @author Mryang
 */
public interface PmphGroupMemberService {

	/**
	 * 
	 * @param  pmphGroupMember 实体对象
	 * @return  带主键的 PmphGroupMember
	 * @throws CheckedServiceException 
	 */
	PmphGroupMember addPmphGroupMember (PmphGroupMember pmphGroupMember) throws CheckedServiceException;
	
	/**
	 * 
	 * @param 主键id
	 * @return  PmphGroupMember
	 * @throws CheckedServiceException
	 */
	PmphGroupMember getPmphGroupMemberById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param 主键id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphGroupMemberById(Long id) throws CheckedServiceException;
	
	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException;
}
