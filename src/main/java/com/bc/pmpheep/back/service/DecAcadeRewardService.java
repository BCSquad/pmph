package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecAcadeReward;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 学术荣誉授予情况表业务层接口
 * @author tyc
 * 2018年1月16日 15:55
 */
public interface DecAcadeRewardService {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日下午15:56:12
	 * @param decAcadeReward
	 * @return
	 * @throws CheckedServiceException
	 */
	DecAcadeReward addDecAcadeReward(DecAcadeReward decAcadeReward) 
			throws CheckedServiceException;
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日下午15:56:45
	 * @param id
	 * @return
	 */
	Integer deleteDecAcadeReward(Long id) throws CheckedServiceException;
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日下午15:57:32
	 * @param decAcadeReward
	 * @return
	 */
	Integer updateDecAcadeReward(DecAcadeReward decAcadeReward) 
			throws CheckedServiceException;
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日下午15:57:53
	 * @param id
	 * @return
	 */
	DecAcadeReward getDecAcadeReward(Long id) throws CheckedServiceException;
}
