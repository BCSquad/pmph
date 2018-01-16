package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * SCI论文投稿及影响因子情况表业务层接口
 * @author tyc
 * 2018年1月16日 14:14
 */
public interface DecSciService {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日下午14:15:21
	 * @param decMonograph
	 * @return
	 * @throws CheckedServiceException
	 */
	DecSci addDecSci(DecSci decSci) throws CheckedServiceException;
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日下午14:15:56
	 * @param id
	 * @return
	 */
	Integer deleteDecSci(Long id) throws CheckedServiceException;
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日下午14:16:11
	 * @param decMonograph
	 * @return
	 */
	Integer updateDecSci(DecSci decSci) throws CheckedServiceException;
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日下午14:16:46
	 * @param id
	 * @return
	 */
	DecSci getDecSci(Long id) throws CheckedServiceException;
}
