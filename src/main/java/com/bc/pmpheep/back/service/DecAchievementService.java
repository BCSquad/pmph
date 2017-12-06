/**
 * 
 */
package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>Title:个人成就表<p>
 * <p>Description:业务层接口<p>
 * @author tyc
 * @date 2017年12月5日 下午17:42:55
 */
public interface DecAchievementService {

	/**
	 * @param DecAchievement 实体对象
	 * @return DecAchievement带主键
	 * @throws CheckedServiceException
	 */
	DecAchievement addDecAchievement(DecAchievement decAchievement) throws CheckedServiceException;

}
