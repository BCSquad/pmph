package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SearchKeyword;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface SearchKeywordService {
	/**
	 * 
	 * 
	 * 功能描述：添加一个敏感词对象
	 *
	 * @param SearchKeyword
	 * @return
	 *
	 */
	SearchKeyword add(SearchKeyword SearchKeyword) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：修改敏感词
	 *
	 * @param SearchKeyword
	 * @return
	 *
	 */
	String update(SearchKeyword SearchKeyword) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：获取敏感词列表并分页
	 *
	 * @return
	 *
	 */
	PageResult<SearchKeyword> list(PageParameter<SearchKeyword> pageParameter) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：批量逻辑删除敏感词
	 *
	 * @param id
	 * @return
	 *
	 */
	String deletedIsDeleted(Long[] id) throws CheckedServiceException;

}
