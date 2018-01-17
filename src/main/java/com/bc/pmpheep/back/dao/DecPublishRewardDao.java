package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.back.po.DecSci;

/**
 * 出版行业获奖情况表数据访问层接口
 * @author tyc
 * 2018年1月16日 10:04
 */
@Repository
public interface DecPublishRewardDao {
	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日上午10:05:12
	 * @param decPublishReward
	 * @return
	 */
	Integer addDecPublishReward(DecPublishReward decPublishReward);
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日上午10:06:26
	 * @param id
	 * @return
	 */
	Integer deleteDecPublishReward(Long id);
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日上午10:06:57
	 * @param decPublishReward
	 * @return
	 */
	Integer updateDecPublishReward(DecPublishReward decPublishReward);
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日上午10:07:17
	 * @param id
	 * @return
	 */
	DecPublishReward getDecPublishReward(Long id);
	
	/**
	 * 根据申报表id查询
	 * @author:tyc
     * @date:2018年1月16日下午16:46:23
	 * @param declarationId
	 * @return
	 */
	List<DecPublishReward> getListDecPublishRewardByDeclarationId(Long declarationId);
	
	/**
	 * Description: 根据申报表id查询
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecPublishReward> getListDecPublishRewardByDeclarationIds(List<Long> declarationId);
}
