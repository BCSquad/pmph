package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.WriterUserRole;

/**
 * 
 *  WriterUserRole 实体类数据访问层接口
 *
 * @author Mryang
 *
 * @createDate 2017年9月19日 下午12:02:18
 *
 */
@Repository
public interface WriterUserRoleDao {

	/**
	 * 
	 * @param writerUserRole  实体对象
	 * @return 影响行数
	 */
	Integer addWriterUserRole(WriterUserRole writerUserRole) ;

	/**
	 * 
	 * @param id
	 * @return writerUserRole
	 */
	WriterUserRole getWriterUserRoleById(Long id) ;

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteWriterUserRoleById(Long id) ;

	/**
	 * 根据id 更新writerUserRole不为null和''的字段 
	 * @param writerUserRole
	 * @return 影响行数
	 */
	Integer updateWriterUserRole(WriterUserRole writerUserRole) ;
	
    /**
   * 
   * <pre>
   * 功能描述：查询表单的数据总条数
   * 使用示范：
   *
   * @return 表单的数据总条数
   * </pre>
   */
  Long getWriterUserRoleCount();
}
