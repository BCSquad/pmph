/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao.pmphuser;

import com.bc.pmpheep.back.po.PmphUser;

/**
 * PmphUser实体类数据访问层接口
 *
 * @author L.X <gugia@qq.com>
 */
public interface PmphUserDao {
	/**
	 * 添加一个用户
	 * 
	 * @param pmphUser
	 *            添加用户的详细信息
	 * @return 影响的行数
	 */
	Integer addPmphUser(PmphUser pmphUser);

	/**
	 * 根据用户Id删除用户
	 * 
	 * @param ids
	 *            需要删除的用户的Id数组
	 * @return影响的行数
	 */
	Integer deletePmphUserById(String[] ids);

	/**
	 * 根据用户Id修改用户的详细信息
	 * 
	 * @param pmphUser
	 *            用户新的详细信息
	 * @return 影响的行数
	 */

	Integer updatePmphUserById(PmphUser pmphUser);

	/**
	 * 通过用户名（唯一字段）查找特定用户
	 *
	 * @param username
	 *            用户名
	 * @return 符合查找条件的PmphUser对象
	 */
	PmphUser getByUsername(String username);

}
