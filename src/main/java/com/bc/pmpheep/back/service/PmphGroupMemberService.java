package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;
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
	PmphGroupMemberVO getPmphGroupMemberById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * Description:根据组员id查找一个组员
	 * 
	 * @author:lyc
	 * @date:2017年10月12日下午2:56:39
	 * @param:组员id
	 * @return:PmphGroupMember
	 */
	PmphGroupMemberVO getPmphGroupMemberByMemberId(Long memberId, Long groupId, Boolean isWriter)
			throws CheckedServiceException;

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
	List<PmphGroupMemberVO> listPmphGroupMember(Long groupId, String sessionId) throws CheckedServiceException;

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
	String addPmphGroupMemberOnGroup(Long groupId,List<PmphGroupMember> pmphGroupMembers, String sessionId)
			throws CheckedServiceException;

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

	/**
	 * 
	 * Description:进行各种操作之前判断是否为创建者或管理者
	 * 
	 * @author:lyc
	 * @date:2017年10月12日上午11:18:08
	 * @param:
	 * @return:Boolean
	 */
	Boolean isFounderOrisAdmin(Long groupId, String sessionId) throws CheckedServiceException;

	/**
	 * 
	 * Description:进行各种操作之前判断是否为创建者
	 * 
	 * @author:Administrator
	 * @date:2017年10月12日上午11:18:34
	 * @param
	 * @return Boolean
	 */
	Boolean isFounder(Long groupId, String sessionId) throws CheckedServiceException;

	/**
	 * 
	 * Description:分页查询小组成员管理界面小组成员信息
	 * 
	 * @author:Administrator
	 * @date:2017年10月12日上午11:30:22
	 * @param PageParameter
	 *            若displayname和username不为空，则为模糊查询操作，否则为初始化
	 * @return PageResult<PmphGroupMemberManagerVO>
	 */
	PageResult<PmphGroupMemberManagerVO> listGroupMemberManagerVOs(
			PageParameter<PmphGroupMemberManagerVO> pageParameter) throws CheckedServiceException;

	/**
	 * 
	 * Description:批量删除小组内成员
	 * 
	 * @author:lyc
	 * @date:2017年10月12日下午2:38:14
	 * @param 成员表id集合
	 * @return String 删除成功与否状态
	 */
	String deletePmphGroupMemberByIds(Long groupId, List<Long> ids, String sessionId) throws CheckedServiceException;

	/**
	 * 
	 * Description:批量更改小组成员的权限
	 * 
	 * @author:lyc
	 * @date:2017年10月12日下午5:00:08
	 * @param members
	 *            小组成员集合
	 * @return String成功与否状态
	 */
	String updateMemberIdentity(Long groupId, List<PmphGroupMember> members, String sessionId)
			throws CheckedServiceException;

}
