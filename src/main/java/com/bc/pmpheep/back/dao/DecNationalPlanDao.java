/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecNationalPlan;


/**
 * <p>Title:作家主编国家级规划教材情况表 数据访问层<p>
 * @author lyc
 * @date 2017年9月24日 下午4:28:22
 */
@Repository
public interface DecNationalPlanDao {
	/**
	 * 新增一个作家作家主编国家级规划教材情况信息
	 * 
	 * @Param DecNationalPlan 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecNationalPlan(DecNationalPlan decNationalPlan);

	/**
	 * 通过主键id删除一个作家主编国家级规划教材情况信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecNationalPlanById(Long id);

	/**
	 * 通过主键id更新作家主编国家级规划教材情况信息
	 * 
	 * @Param DecNationalPlan 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecNationalPlan(DecNationalPlan decNationalPlan);

	/**
	 * 通过主键id查询作家主编国家级规划教材情况信息
	 * 
	 * @Param id
	 * 
	 * @Return DecNationalPlan
	 */
	DecNationalPlan getDecNationalPlanById(Long id);

	/**
	 * Description: 根据申报表id查询作家主编国家级规划教材情况信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 兼职学术组织信息
	 */
	List<DecNationalPlan> getListDecNationalPlanByDeclarationIds(List<Long> declarationId);
	
	/**
	 * Description: 根据申报表id查询作家主编国家级规划教材情况信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 兼职学术组织信息
	 */
	List<DecNationalPlan> getListDecNationalPlanByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecNationalPlan();
}
