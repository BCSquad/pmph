package com.bc.pmpheep.back.dao;

import java.util.List;

import javax.validation.constraints.Past;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;

/**
 * PmphGroupMember 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphGroupMemberDao {
	/**
	 * 
	 * @param pmphGroupMember
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addPmphGroupMember(PmphGroupMember pmphGroupMember);

	/**
	 * 
	 * @param 主键id
	 * @return PmphGroupMember
	 */
	PmphGroupMember getPmphGroupMemberById(Long id);

	/**
	 * 
	 * Description:根据当前用户id查找成员
	 * 
	 * @author:lyc
	 * @date:2017年10月12日下午2:52:57
	 * @Param:小组内成员id
	 * @Return:PmphGroupMember
	 */
	PmphGroupMemberVO getPmphGroupMemberByMemberId(@Param("groupId") Long groupId, @Param("userId") Long userId,
			@Param("isWriter") Boolean isWriter);

	/**
	 * 
	 * Description:根据当前用户id查找成员
	 * 
	 * @author:lyc
	 * @date:2017年10月12日下午2:52:57
	 * @Param:小组内成员id
	 * @Return:PmphGroupMember
	 */
	PmphGroupMemberVO getPmphGroupMemberByMemberIdAndIsWriter(Long memberId);

	/**
	 * 
	 * @param 主键id
	 * @return 影响行数
	 */
	Integer deletePmphGroupMemberById(Long id);

	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 */
	Integer updatePmphGroupMember(PmphGroupMember pmphGroupMember);

	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 */
	Integer update(PmphGroupMember pmphGroupMember);

	/**
	 * 逻辑移出小组成员
	 * 
	 * @param pmphGroupMember
	 * @return 影响行数
	 */
	Integer updateGroupMemberById(Long id);

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
	 * @param groupId
	 *            小组id
	 * @return 影响行数
	 *
	 */
	Integer deletePmphGroupMemberOnGroup(Long groupId);

	/**
	 * 
	 * Description:小组成员管理界面小组成员总数
	 * 
	 * @author:lyc
	 * @date:2017年10月11日下午3:33:02
	 * @Param:pageParameter
	 * @Return:Integer
	 */
	Integer groupMemberTotal(PageParameter<PmphGroupMemberManagerVO> pageParameter);

	/**
	 * 
	 * Description:小组成员管理界面小组成员信息
	 * 
	 * @author:lyc
	 * @date:2017年10月11日下午3:39:32
	 * @Param:pageParameter username或displayname不为空则为模糊查询
	 * @Return:List<PmphGroupMemberManagerVO>小组成员信息集合
	 */
	List<PmphGroupMemberManagerVO> listGroupMemberManagerVOs(PageParameter<PmphGroupMemberManagerVO> pageParameter);

	/**
	 * 根据小组id获取该小组所有成员
	 * 
	 * @param id
	 * @return
	 */
	List<PmphGroupMember> listPmphGroupMembers(Long groupId);

}
