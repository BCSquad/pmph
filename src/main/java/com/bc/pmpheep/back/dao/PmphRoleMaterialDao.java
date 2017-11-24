/**
 * 
 */
package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.PmphRoleMaterial;

/**
 * <p>Title:PmphRoleMaterialDao<p>
 * <p>Description:TODO<p>
 * @author Administrator
 * @date 2017年11月23日 下午3:51:35
 */
public interface PmphRoleMaterialDao {

	/**
	 * 
	 * Description:添加权限
	 * @author:lyc
	 * @date:2017年11月23日下午3:52:05
	 * @param 
	 * @return Integer
	 */
	Integer add(PmphRoleMaterial pmphRoleMaterial);
	
	/**
	 * 
	 * Description:删除权限
	 * @author:lyc
	 * @date:2017年11月23日下午3:54:12
	 * @param 
	 * @return Integer
	 */
	Integer delete(Long roleId);
	
	/**
	 * 
	 * Description:更新权限
	 * @author:Administrator
	 * @date:2017年11月23日下午3:54:59
	 * @param 
	 * @return Integer
	 */
	Integer update(PmphRoleMaterial pmphRoleMaterial);
	
	/**
	 * 
	 * Description:根据角色id获取权限
	 * @author:Administrator
	 * @date:2017年11月23日下午3:56:35
	 * @param 
	 * @return PmphRoleMaterial
	 */
	PmphRoleMaterial getPmphRoleMaterial(Long roleId);
	
	/**
	 * 
	 * Description:统计数量
	 * @author:lyc
	 * @date:2017年11月23日下午3:57:57
	 * @param 
	 * @return Long
	 */
	Long getPmphRoleMaterialTotal();
}
