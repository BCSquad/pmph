/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.DecAchievement;

/**
 * <p>
 * Title:个人成就表
 * <p>
 * <p>
 * Description:数据访问层
 * <p>
 * 
 * @author tyc
 * @date 2017年12月5日 下午17:37:55
 */
@Repository
public interface DecAchievementDao {

	/**
	 * 
	 * Description:添加一个人成就
	 * 
	 * @author:tyc
	 * @date:2017年12月5日 下午17:37:55
	 * @param DecExtension对象
	 * @return Integer影响行数
	 */
	Integer addDecAchievement(DecAchievement decAchievement);

	/**
	 * 根据declarationId查询
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月6日 上午10:42:22
	 * @param declarationId
	 * @return
	 */
	List<DecAchievement> getDecAchievementByDeclarationIds(List<Long> declarationId);

	/**
	 * 根据declarationId查询
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2017年12月6日 上午10:42:22
	 * @param declarationId
	 * @return
	 */
	DecAchievement getDecAchievementByDeclarationId(Long declarationId);

}
