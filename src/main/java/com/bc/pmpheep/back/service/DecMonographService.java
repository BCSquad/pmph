package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 主编学术专著情况表业务层接口
 * @author tyc
 * 2018年1月16日 09:40
 */
public interface DecMonographService {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日上午09:41:33
	 * @param decMonograph
	 * @return
	 * @throws CheckedServiceException
	 */
	DecMonograph addDecMonograph(DecMonograph decMonograph) throws CheckedServiceException;
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日上午09:45:52
	 * @param id
	 * @return
	 */
	Integer deleteDecMonograph(Long id) throws CheckedServiceException;
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日上午09:47:12
	 * @param decMonograph
	 * @return
	 */
	Integer updateDecMonograph(DecMonograph decMonograph) throws CheckedServiceException;
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日上午09:49:41
	 * @param id
	 * @return
	 */
	DecMonograph getDecMonograph(Long id) throws CheckedServiceException;
}
