package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupMemberService 接口
 * 
 * @author Mryang
 */
public interface PmphGroupMemberService {

	/**
	 * 
	 * @param pmphGroupMember
	 *            实体对象
	 * @return 带主键的 PmphGroupMember
	 * @throws CheckedServiceException
	 */
	PmphGroupMember addPmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException;

	/**
	 * 
	 * @param 主键id
	 * @return PmphGroupMember
	 * @throws CheckedServiceException
	 */
	PmphGroupMember getPmphGroupMemberById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * @param 主键id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphGroupMemberById(Long id) throws CheckedServiceException;

	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据小组id加载小组用户
	 *
	 * @param groupId
	 *            小组id
	 * @return 小组成员
	 *
	 */
	List<PmphGroupMemberVO> listPmphGroupMember(Long groupId) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述： 批量添加小组成员
	 *
	 * @param pmphGroupMembers
	 *            添加的小组成员
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String addPmphGroupMemberOnGroup(List<PmphGroupMember> pmphGroupMembers) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：解散小组时清除该小组所有成员
	 *
	 * @param groupId
	 *            小组id
	 * @return 是否成功
	 * @throws CheckedServiceException
	 *
	 */
	String deletePmphGroupMemberOnGroup(Long groupId) throws CheckedServiceException;

}
