package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.vo.GroupMemberWriterUserVO;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;

/**
 * WriterUser 实体类的数据访问层接口
 * 
 * @author 曾庆峰
 * 
 */
@Repository
public interface WriterUserDao {

    /**
     * 根据机构id集查询用户(逻辑没有删除和启用的)
     */
    List<WriterUser> getWriterUserListByOrgIds(List<Long> orgIds);

    /**
     * 添加一个用户
     * 
     * @ pmphUser 添加用户的详细信息
     * @return 添加的主键
     */
    Long add(WriterUser user);

    Integer update(WriterUser user);

    Integer delete(Long id);

    Integer batchDelete(@Param("ids") List<Long> ids);

    WriterUser get(Long id);

    List<WriterUser> getListUser();
	List<Long> getListUserId();

    WriterUser getByUsernameAndPassword(String username, String password);

    /**
     * 根据角色 id 查询所有是该角色的用户列表
     * 
     * @param rid
     * @return
     */
    List<WriterUser> getListByRole(Long rid);

    List<WriterPermission> getListAllResources(Long uid);

    List<String> getListRoleSnByUser(Long uid);

    List<WriterRole> getListUserRole(Long uid);

    /**
     * 
     * <pre>
     * 功能描述：根据用户Id查询对应权限
     * 使用示范：
     *
     * @param userId
     * @return
     * </pre>
     */
    List<Long> getWriterUserPermissionByUserId(Long userId);

    /**
     * 
     * 功能描述：分页查询作家用户
     * 
     * @param page 传入的查询条件
     * @return 需要的作家用户集合
     */
    List<WriterUserManagerVO> getListWriterUser(PageParameter<WriterUserManagerVO> page);
	/**
	 *
	 * 功能描述：分页查询作家用户
	 *
	 * @param page 传入的查询条件
	 * @return 需要的作家用户集合
	 */
	List<WriterUserManagerVO> getListexpertUser(PageParameter<WriterUserManagerVO> page);

    /**
     * 
     * <pre>
     * 功能描述：获取教师审核列表
     * 使用示范：
     *
     * @param pageParameter
     * @return
     * </pre>
     */
    List<WriterUserManagerVO> getTeacherCheckList(PageParameter<WriterUserManagerVO> pageParameter);

    /**
     * 
     * 功能描述： 查询总共的条数
     * 
     * @param page 传入查询条件
     * @return 查询到的条数
     */
    Integer getListWriterUserTotal(PageParameter<WriterUserManagerVO> page);

	Integer getListexpertUserTotal(PageParameter<WriterUserManagerVO> page);

    /**
     * 
     * Description:添加成员界面作家用户信息展示
     * 
     * @author:lyc
     * @date:2017年10月12日下午5:56:05
     * @ pageParameter若教材书籍、遴选职位、账号或姓名有值则为模糊查询
     * @return List<GroupMemberWriterUserVO>作家用户信息集合
     */
    List<GroupMemberWriterUserVO> listGroupMemberWriterUserVOs(
    PageParameter<GroupMemberWriterUserVO> pageParameter);

    /**
     * 
     * Description:作家用户总数
     * 
     * @author:lyc
     * @date:2017年10月12日下午5:59:59
     * @ pageParameter若教材书籍、遴选职位、账号或姓名有值则为模糊查询
     * @return Integer数据总数
     */
    Integer getGroupMemberWriterUserTotal(PageParameter<GroupMemberWriterUserVO> pageParameter);

    /**
     * 
     * 功能描述：查询表单的数据总条数
     * 
     * @return 表单的数据总条数
     */
    Long getWriterUserCount();
    
    /**
     * @des 根据用户输入的用户代码查询用户代码是否重复
     * @param username
     * @return 查询的username
     */
	WriterUser getUsername(String username);
	
	/**
	 * 通过作家用户id 审核后  修改为教师用户
	 * @param writerUsers
	 * @return
	 */
	Integer updateWriterUserRank(WriterUser writerUsers);
	
	/**
	 * 查询教师认证总数量
	 * @return
	 */
	Integer getCount();
	
	/**
	 * 通过id查询用户级别
	 * @param writerUsers
	 * @return
	 */
	List<WriterUser> getWriterUserRankList(List<WriterUser> writerUsers);
	
	/**
	 * 查询教师用户数量
	 * @param pageParameter
	 * @return
	 */
	Integer getLsitisTeacherTotal(PageParameter<WriterUserManagerVO> pageParameter);
	
	/**
	 * 查询教师用户
	 * @param pageParameter
	 * @return
	 */
	List<WriterUserManagerVO> getLsitisTeacher(PageParameter<WriterUserManagerVO> pageParameter);
	/**
	 * 通过id修改为教师
	 * @param writerUsers
	 * @return
	 */
	Integer updateWriterUser(WriterUser writerUsers);
	/**
	 * 通过用户id查询用户信息
	 * @param userIds
	 * @return
	 */
	List<WriterUser> getWriterUserList(Long[] userIds);

	/**
	 * 是否置顶
	 * @param id
	 * @param isTop
	 * @return
	 */
    Integer isTop(@Param("id") Long id, @Param("isTop") Boolean isTop);

	/**
	 * 导出个人用户信息
	 * @param map
	 * @return
	 */
	List<WriterUserManagerVO> exportWriterInfo(Map map);

	// /**
    // *
    // * <pre>
    // * 功能描述：查询总条数
    // * 使用示范：
    // *
    // * @param page传入的查询条件
    // * @return 查询到的条数
    // * </pre>
    // */
    // Integer getListTotal(Page<WriterUser, Map<String, String>> page);
    // /**
    // *
    // * <pre>
    // * 功能描述：分页查询作家用户
    // * 使用示范：
    // *
    // * @param page 传入的查询条件
    // * @return 需要的作家用户集合
    // * </pre>
    // */
    // List<WriterUser> getList(Page<WriterUser,Map<String, String>> page);

	/**
	 * 查看用户信息
	 */
	Map<String, Object> queryUserInfo(Long user_id);
	//1查询学习经历
	List<Map<String,Object>> queryPerStu(Map<String, Object> map);
	//2查询工作经历
	List<Map<String,Object>> queryPerWork(Map<String, Object> map);
	//3查询教学经历
	List<Map<String,Object>> queryPerStea(Map<String, Object> map);
	//4作家学术
	List<Map<String,Object>> queryPerZjxs(Map<String, Object> map);
	//5查询上版教材编辑
	List<Map<String,Object>> queryPerJcbj(Map<String, Object> map);
	//6查询精品课程建设
	List<Map<String,Object>> queryPerGjkcjs(Map<String, Object> map);
	//7作家主编国家级规划教材情况
	List<Map<String,Object>> queryPerGjghjc(Map<String, Object> map);
	//8查询其他社教材编写情况
	List<Map<String,Object>> queryPerJcbx(Map<String, Object> map);
	//9查询人卫社教材编写情况
	List<Map<String,Object>> queryPerRwsjc(Map<String, Object> map);
	//10作家科研情况表
	List<Map<String,Object>> queryPerZjkyqk(Map<String, Object> map);
	//11个人成就
	Map<String,Object> queryPerAchievement(Map<String, Object> map);
	//12主编学术专著情况
	List<Map<String,Object>> queryPerMonograph(Map<String, Object> map);
	//13出版行业获奖情况
	List<Map<String,Object>> queryPerPublish(Map<String, Object> map);
	//14SCI论文投稿及影响因子
	List<Map<String,Object>> queryPerSci(Map<String, Object> map);
	//临床医学获奖情况
	List<Map<String,Object>> queryPerClinicalreward(Map<String, Object> map);
	//15学术荣誉授予情况
	List<Map<String,Object>> queryPerAcadereward(Map<String, Object> map);
	//16主编或参编图书情况
	List<Map<String,Object>> queryPerEditor(Map<String, Object> map);
	//17文章发表详情
	//per_article_published
	//18本专业获奖详情
	//per_profession_award
}
