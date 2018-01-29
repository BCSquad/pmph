package com.bc.pmpheep.back.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Sensitive;
import com.bc.pmpheep.service.exception.CheckedServiceException;

public interface SensitiveService {
	/**
	 * 
	 * 
	 * 功能描述：添加一个敏感词对象
	 *
	 * @param sensitive
	 * @return
	 *
	 */
	Sensitive add(Sensitive sensitive) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：修改敏感词
	 *
	 * @param sensitive
	 * @return
	 *
	 */
	String update(Sensitive sensitive) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：获取敏感词列表并分页
	 *
	 * @param word
	 * @param pageSize
	 * @param start
	 * @return
	 *
	 */
	PageResult<Sensitive> list(PageParameter<Sensitive> pageParameter) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：批量逻辑删除敏感词
	 *
	 * @param id
	 * @return
	 *
	 */
	String updateIsDeleted(Long[] id) throws CheckedServiceException;

}
