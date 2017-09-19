package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * TextbookService 接口
 * 
 * @author 曾庆峰
 *
 */
public interface TextbookService {

	/**
	 * 新增一个Textbook
	 * 
	 * @param Textbook
	 *            实体对象
	 * @return 带主键的 Textbook thorws CheckedServiceException
	 */
	Textbook addTextbook(Textbook textbook) throws CheckedServiceException;

	/**
	 * 查询一个 Textbook 通过主键id
	 * 
	 * @param id
	 * @return Textbook
	 * @throws CheckedServiceException
	 */
	Textbook getTextbookById(Long id) throws CheckedServiceException;

	/**
	 * 删除Textbook 通过主键id
	 * 
	 * @param Textbook
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteTextbookById(Long id) throws CheckedServiceException;

	/**
	 * 更新一个 Textbook通过主键id
	 * 
	 * @param Textbook
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateTextbook(Textbook textbook) throws CheckedServiceException;

}
