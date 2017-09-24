/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.po.DecResearch;


/**
 * <p>Title:作家科研情况表 数据访问层接口<p>
 * @author lyc
 * @date 2017年9月24日 下午5:08:20
 */
public interface DecResearchDao {

	/**
	 * 新增一个作家科研情况信息
	 * 
	 * @Param DecResearch 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecResearch(DecResearch decResearch);

	/**
	 * 通过主键id删除一个作家科研情况信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecResearchById(Long id);

	/**
	 * 通过主键id更新一个作家科研情况信息
	 * 
	 * @Param DecResearch 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecResearch(DecResearch decResearch);

	/**
	 * 通过主键id查询作家科研情况信息
	 * 
	 * @Param id
	 * 
	 * @Return DecResearch
	 */
	DecResearch getDecResearchById(Long id);

	/**
	 * Description: 根据申报表id查询作家科研情况信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家科研情况信息
	 */
	List<DecResearch> getListDecResearchByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecResearch();
}
