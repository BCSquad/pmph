package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.vo.OrgAndOrgUserVO;

/**
 * OrgUser 实体类数据访问层接口
 * 
 * @author mryang
 */
public interface OrgUserDao {
    /**
     * 根据机构id集查询用户 (逻辑没有删除和启用的)
     */
    List<OrgUser> getOrgUserListByOrgIds(List<Long> orgIds);

    /**
     * 根据机构id获取机构管理员
     */
    OrgUser getOrgUserByOrgId(Long orgId);

    /**
     * 
     * @param orgUser 实体对象
     * @return 影响行数
     */
    Integer addOrgUser(OrgUser orgUser);
    /**
     *
     * @param orgUser 实体对象
     * @return 影响行数
     */
    Integer addOrgUserToWriterUser(OrgUser orgUser);

    /**
     * 根据主键Id查询对象
     * 
     * @param id 主键id
     * @return OrgUser OrgUser对象
     */
    OrgUser getOrgUserById(Long id);

    /**
     * 
     * <pre>
	 * 功能描述：根据主键Id集合查询对象集合
	 * 使用示范：
	 *
	 * &#64;param ids 主键id 集合
	 * &#64;return
	 * </pre>
     */
    List<OrgUser> getOrgUserByIds(List<Long> ids);

    /**
     * 
     * @param id
     * @return 影响行数
     */
    Integer deleteOrgUserById(Long id);

    /**
     * @param orgUser
     * @return 影响行数
     */
    Integer updateOrgUser(OrgUser orgUser);

    /**
     * 
     * <pre>
	 * 功能描述：查询表单总条数
	 * 使用示范：
	 *
	 * &#64;return 表单的总条数
	 * </pre>
     */
    Long getOrgUserCount();

    /**
     * 
     * <pre>
	 * 功能描述：分页查询作家用户
	 * 使用示范：
	 *
	 * &#64;param pageParameter 传入的查询条件
	 * &#64;return 需要的作家用户集合
	 * </pre>
     */
    List<OrgAndOrgUserVO> getListOrgUser(PageParameter<OrgAndOrgUserVO> pageParameter);

    /**
     * 
     * <pre>
	 * 功能描述： 查询总共的条数
	 * 使用示范：
	 *
	 * &#64;param pageParameter 传入查询条件
	 * &#64;return 查询到的条数
	 * </pre>
     */
    Integer getListOrgUserTotal(PageParameter<OrgAndOrgUserVO> pageParameter);

    /**
     * 
     * <pre>
	 * 功能描述：学校管理员审核(按Id更新审核状态)
	 * 使用示范：
	 *
	 * &#64;param orgUser
	 * &#64;return
	 * </pre>
     */
    Integer updateOrgUserProgressById(List<OrgUser> orgUser);

    /**
     * 
     * 
     * 功能描述：通过机构id获取机构用户
     * 
     * @param orgId 机构id
     * @return
     * 
     */
    List<OrgUser> listOrgUserByOrgId(Long orgId);

    /**
     * 根据username查询是否存在该机构名称
     * 
     * @param username
     * @return
     */
    List<OrgUser> getOrgUsername(String username);

    /**
     * 查询机构认证总数
     * 
     * @return
     */
    Integer geCount();

    /**
     * 
     * <pre>
     * 功能描述：分页查询作家用户
     * 使用示范：
     *
     * &#64;param pageParameter 传入的查询条件
     * &#64;return 需要的作家用户集合
     * </pre>
     */
    List<OrgAndOrgUserVO> getListAllOrgUser(PageParameter<OrgAndOrgUserVO> pageParameter);

}
