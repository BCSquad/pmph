package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.BookCorrection;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * BookCorrectionService 接口
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午5:10:35
 *
 **/
public interface BookCorrectionService {
	
	 /**
     * 新增一个    BookCorrection
     * @param bookCorrection 实体对象
     * @return 带主键的 bookCorrection thorws CheckedServiceException
     */
	BookCorrection addBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException;
	
	/**
	 * 根据主键删除BookCorrection
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer   deleteBookCorrectionById (Long id) throws CheckedServiceException;
    
	 /**
     * 更新一个BookCorrection
     * @param bookCorrection 实体对象
     * @return 影响行数
     */
	Integer  updateBookCorrection(BookCorrection bookCorrection) throws CheckedServiceException;
	
	/**
	 * 根据主键获取BookCorrection
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月19日 下午5:12:57
	 * @param id
	 * @return BookCorrection
	 * @throws CheckedServiceException
	 */
	BookCorrection   getBookCorrectionById (Long id) throws CheckedServiceException;
	
	
}
