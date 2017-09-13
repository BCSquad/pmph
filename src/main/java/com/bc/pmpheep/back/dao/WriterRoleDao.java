package com.bc.pmpheep.back.dao;


import com.bc.pmpheep.back.po.WriterRole;

/**
 * WriterRole 实体类的数据访问层接口
 * 
 * @author 曾庆峰
 *
 */
public interface WriterRoleDao {
	/**
	 * 添加一个作家角色
	 * 
	 * @param writerRole
	 *            需要添加的作家角色的详细信息
	 * @return 影响行数
	 */
	Integer addWriterRole(WriterRole writerRole);

	/**
	 * 根据角色的id 单个或者批量删除作家权限
	 * 
	 * @param ids
	 *            需要删除的id数组
	 * @return 影响行数
	 */
	Integer deleteWriterRoleById(String[] ids);

	/**
	 * 根据权限id 修改这个角色的详细信息
	 * 
	 * @param writerRole
	 *            修改的角色信息
	 * @return 影响行数
	 */
	Integer updateWriterRoleById(WriterRole writerRole);

	/**
	 * 根据角色名称精确查找作家角色
	 * 
	 * @param realname
	 *            角色名称
	 * @return 返回所需要的角色信息
	 */
	WriterRole getWriterRoleByRoleName(String roleName);
}
