/**
 * 
 */
package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterProfile;

/**
 * <p>
 * Title:作家简介与标签表 数据访问层接口
 * <p>
 * <p>
 * Description:作家简介与标签信息
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 下午5:11:16
 */
@Repository
public interface WriterProfileDao {
	/**
	 * Description:新增一个作家的简介与个人标签
	 * 
	 * @Param WriterProfile实体对象
	 * @Return 影响行数
	 */
	Integer addWriterProfile(WriterProfile writerProfile);

	/**
	 * Description:通过主键id删除一个作家的简介与个人标签信息
	 * 
	 * @Param id
	 * @Return 影响行数
	 */
	Integer deleteWriterProfileById(Long id);

	/**
	 * Description:更新作家的简介与个人标签信息
	 * 
	 * @Param WriterProfile实体对象 
	 * @Return 影响行数
	 */
	Integer updateWriterProfile(WriterProfile writerProfile);
	
	/**
	 * Description:根据id查询一个作家的个人简介与个人标签信息
	 * @Param id
	 * @Return WriterProfile
	 */
	WriterProfile getWriterProfileById(Long id);
	
	/**
	 * Description:查询作家简介与个人标签信息表的总记录数
	 * @Return 总记录数
	 */
	Long getWriterProfile();
}
