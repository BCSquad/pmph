package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * 
 *  WriterUserRoleService 接口
 *
 * @author Mryang
 *
 * @createDate 2017年9月19日 下午12:02:18
 *
 */
public interface WriterUserRoleService {
	
	/**
	 * 
	 * @param writerUserRole  实体对象
	 * @return 带主键的 writerUserRole
	 * @throws CheckedServiceException
	 */
	WriterUserRole addWriterUserRole(WriterUserRole writerUserRole) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 * @return writerUserRole
	 * @throws CheckedServiceException
	 */
	WriterUserRole getWriterUserRoleById(Long id) throws CheckedServiceException ;

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteWriterUserRoleById(Long id) throws CheckedServiceException;

	/**
	 * 根据id 更新writerUserRole不为null和''的字段 
	 * @param writerUserRole
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateWriterUserRole(WriterUserRole writerUserRole) throws CheckedServiceException;
}
