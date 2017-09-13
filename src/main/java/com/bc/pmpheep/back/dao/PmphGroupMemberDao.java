package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroupMember;

/**
 * PmphGroupMember 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface  PmphGroupMemberDao {
	/**
	 * 
	 * @param  PmphGroupMember 实体对象
	 * @return  带主键的 PmphGroupMember
	 * @throws Exception 
	 */
	PmphGroupMember addPmphGroupMember (PmphGroupMember pmphGroupMember) ;
	
	/**
	 * 
	 * @param PmphGroupMember 必须包含主键ID
	 * @return  PmphGroupMember
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroupMember getPmphGroupMemberById(PmphGroupMember pmphGroupMember) ;
	
	/**
	 * 
	 * @param PmphGroupMember
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupMemberById(PmphGroupMember pmphGroupMember) ;
	/**
	 * @param PmphGroupMember
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupMemberById(PmphGroupMember pmphGroupMember) ;
}
