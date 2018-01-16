package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 出版行业获奖情况表业务层接口
 * @author tyc
 * 2018年1月16日 10:09
 */
public interface DecPublishRewardService {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日上午10:10:46
	 * @param decPublishReward
	 * @return
	 * @throws CheckedServiceException
	 */
	DecPublishReward addDecPublishReward(DecPublishReward decPublishReward) throws CheckedServiceException;
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日上午10:11:09
	 * @param id
	 * @return
	 */
	Integer deleteDecPublishReward(Long id) throws CheckedServiceException;
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日上午10:11:47
	 * @param decPublishReward
	 * @return
	 */
	Integer updateDecPublishReward(DecPublishReward decPublishReward) throws CheckedServiceException;
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日上午10:12:04
	 * @param id
	 * @return
	 */
	DecPublishReward getDecPublishReward(Long id) throws CheckedServiceException;
}
