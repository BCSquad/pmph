package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.back.po.DecSci;

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
	
	/**
	 * 根据申报表id查询
	 * @author:tyc
     * @date:2018年1月16日下午17:01:22
	 * @param declarationId
	 * @return
	 */
	List<DecClinicalReward> getListDecClinicalRewardByDeclarationId(Long declarationId);
	
	/**
	 * Description: 根据申报表id查询
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecClinicalReward> getListDecClinicalRewardByDeclarationIds(List<Long> declarationId);
}
