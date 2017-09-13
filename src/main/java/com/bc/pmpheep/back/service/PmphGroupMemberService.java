package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroupMember;

/**
 * PmphGroupMemberService 接口
 * @author Mryang
 */
public interface PmphGroupMemberService {

	/**
	 * 
	 * @param  PmphGroupMember 实体对象
	 * @return  带主键的 PmphGroupMember
	 * @throws Exception 
	 */
	PmphGroupMember addPmphGroupMember (PmphGroupMember pmphGroupMember) throws Exception;
	
	/**
	 * 
	 * @param PmphGroupMember 必须包含主键ID
	 * @return  PmphGroupMember
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroupMember getPmphGroupMemberById(PmphGroupMember pmphGroupMember) throws Exception;
	
	/**
	 * 
	 * @param PmphGroupMember
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupMemberById(PmphGroupMember pmphGroupMember) throws Exception;
	/**
	 * @param PmphGroupMember
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupMemberById(PmphGroupMember pmphGroupMember) throws Exception;
}
