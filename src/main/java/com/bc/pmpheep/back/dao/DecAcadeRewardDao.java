package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecAcadeReward;


/**
 * 学术荣誉授予情况表数据访问层接口
 * @author tyc
 * 2018年1月16日 15:47
 */
@Repository
public interface DecAcadeRewardDao {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日下午15:47:41
	 * @param decAcadeReward
	 * @return
	 */
	Integer addDecAcadeReward(DecAcadeReward decAcadeReward);
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日下午15:48:06
	 * @param id
	 * @return
	 */
	Integer deleteDecAcadeReward(Long id);
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日下午15:48:55
	 * @param decAcadeReward
	 * @return
	 */
	Integer updateDecAcadeReward(DecAcadeReward decAcadeReward);
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日下午15:49:35
	 * @param id
	 * @return
	 */
	DecAcadeReward getDecAcadeReward(Long id);
	
	/**
	 * 根据申报表id查询
	 * @author:tyc
     * @date:2018年1月16日下午17:02:45
	 * @param declarationId
	 * @return
	 */
	List<DecAcadeReward> getListDecAcadeRewardByDeclarationId(Long declarationId);
}
