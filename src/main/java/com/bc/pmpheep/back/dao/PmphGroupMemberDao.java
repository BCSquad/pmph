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
	 * @param  pmphGroupMember 实体对象
	 * @return 影响行数
	 */
	Integer addPmphGroupMember (PmphGroupMember pmphGroupMember) ;
	
	/**
	 * 
	 * @param 主键id
	 * @return  PmphGroupMember
	 */
	PmphGroupMember getPmphGroupMemberById(Long id) ;
	
	/**
	 * 
	 * @param 主键id
	 * @return  影响行数
	 */
	Integer deletePmphGroupMemberById(Long id) ;
	
	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 */
	Integer updatePmphGroupMember(PmphGroupMember pmphGroupMember);
	
	 /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphGroupMemberCount();
}
