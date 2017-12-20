package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.BookEditor;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * BookEditorService  接口
 * @author MrYang 
 * @CreateDate 2017年12月20日 上午8:39:50
 *
 **/
public interface BookEditorService  {
	
	/**
     * 新增一个      BookEditor
     * @param bookEditor 实体对象
     * @return 带主键的bookEditor 
     * thorws CheckedServiceException
     */
	BookEditor addBookEditor(BookEditor bookEditor) throws CheckedServiceException;
	
	/**
	 * 根据主键删除BookEditor
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer   deleteBookEditorById (Long id) throws CheckedServiceException;
    
	 /**
     * 更新BookEditor不为null的字段 
     * @param bookEditor 实体对象
     * @return 影响行数
     */
	Integer  updateBookEditor(BookEditor bookEditor) throws CheckedServiceException;
	
	/**
	 * 根据主键获取BookEditor
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return BookEditor
	 * @throws CheckedServiceException
	 */
	BookEditor   getBookEditorById (Long id) throws CheckedServiceException;
}
