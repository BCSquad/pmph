package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.WriterUser;

/**
 * WriterUser 实体类的数据访问层接口
 * 
 * @author 曾庆峰
 *
 */
@Component
public interface WriterUserDao {
	/**
	 * 添加一位作家用户
	 * 
	 * @param writerUser
	 *            作家用户的信息
	 * @return 影响行数
	 */
	Integer addWriterUser(WriterUser writerUser);

	/**
	 * 根据用户id 单个或者批量删除用户（物理）
	 * 
	 * @param ids
	 *            需要删除用户的id数组
	 * @return 影响行数
	 */
	Integer deleteWriterUserById(String[] ids);

	/**
	 * 根据id 修改单个用户的信息
	 * 
	 * @param writerUser
	 *            用户的最新信息
	 * 
	 * @return 影响行数
	 */
	Integer updateWriterUserById(WriterUser writerUser);

	/**
	 * 根据单一字段（用户名）精确查询用户
	 * 
	 * @param username
	 *            用户名
	 * @return 符合该用户名的用户信息
	 */
	WriterUser getWriterUserByUsername(String username);
}
