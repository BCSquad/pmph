package com.bc.pmpheep.back.dao;

import java.util.List;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;

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
	 * 
	 * @param orgUser
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addOrgUser(OrgUser orgUser);

	/**
	 * 
	 * @param id
	 * @return OrgUser
	 */
	OrgUser getOrgUserById(Long id);

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
	 * &#64;param page 传入的查询条件
	 * &#64;return 需要的作家用户集合
	 * </pre>
	 */
	List<OrgUserManagerVO> getListOrgUser(Page<OrgUserManagerVO, OrgUserManagerVO> page);

	/**
	 * 
	 * <pre>
	 * 功能描述： 查询总共的条数
	 * 使用示范：
	 *
	 * &#64;param page 传入查询条件
	 * &#64;return 查询到的条数
	 * </pre>
	 */
	Integer getListOrgUserTotal(Page<OrgUserManagerVO, OrgUserManagerVO> page);

}
