package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecClinicalReward;

/**
 * 临床医学获奖情况表数据访问层接口
 * @author tyc
 * 2018年1月16日 15:04
 */
@Repository
public interface DecClinicalRewardDao {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日下午15:09:11
	 * @param decClinicalReward
	 * @return
	 */
	Integer addDecClinicalReward(DecClinicalReward decClinicalReward);
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日下午15:09:54
	 * @param id
	 * @return
	 */
	Integer deleteDecClinicalReward(Long id);
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日下午15:10:22
	 * @param decClinicalReward
	 * @return
	 */
	Integer updateDecClinicalReward(DecClinicalReward decClinicalReward);
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日下午15:10:47
	 * @param id
	 * @return
	 */
	DecClinicalReward getDecClinicalReward(Long id);
}
