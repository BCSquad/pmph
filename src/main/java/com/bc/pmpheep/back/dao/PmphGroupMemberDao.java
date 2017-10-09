package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;

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
	 *  
	 * 功能描述：根据小组id加载小组用户
	 *
	 * @param groupId  小组id
	 * @return 小组成员
	 *
	 */
	List<PmphGroupMemberVO> listPmphGroupMember(Long groupId);
	
	 /**
     * 
     * 功能描述：查询表单的数据总条数
     *
     * @return 表单的总条数
     */
    Long getPmphGroupMemberCount();
    
    /**
     * 
     *  
     * 功能描述： 解散小组时删除小组内所有成员
     *
     * @param groupId 小组id
     * @return 影响行数
     *
     */
    Integer deletePmphGroupMemberOnGroup(Long groupId);
}
