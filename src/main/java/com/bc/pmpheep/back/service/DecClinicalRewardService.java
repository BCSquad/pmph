package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 临床医学获奖情况表业务层接口
 * @author tyc
 * 2018年1月16日 15:13
 */
public interface DecClinicalRewardService {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日下午15:15:45
	 * @param decClinicalReward
	 * @return
	 * @throws CheckedServiceException
	 */
	DecClinicalReward addDecClinicalReward(DecClinicalReward decClinicalReward) 
			throws CheckedServiceException;
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日下午15:16:12
	 * @param id
	 * @return
	 */
	Integer deleteDecClinicalReward(Long id) throws CheckedServiceException;
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日下午15:16:52
	 * @param decClinicalReward
	 * @return
	 */
	Integer updateDecClinicalReward(DecClinicalReward decClinicalReward) 
			throws CheckedServiceException;
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日下午15:17:34
	 * @param id
	 * @return
	 */
	DecClinicalReward getDecClinicalReward(Long id) throws CheckedServiceException;
}
