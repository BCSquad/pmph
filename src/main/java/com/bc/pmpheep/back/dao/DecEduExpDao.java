/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecEduExp;


/**
 * <p>Title:作家学习经历表 数据访问层接口<p>
 * @author lyc
 * @date 2017年9月24日 下午2:50:22
 */
@Repository
public interface DecEduExpDao {
	/**
	 * 新增一个作家学习经历信息
	 * 
	 * @Param DecEduExp 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecEduExp(DecEduExp decEduExp);

	/**
	 * 通过主键id删除作家学习经历信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecEduExpById(Long id);

	/**
	 * 通过主键id更新作家学习经历信息
	 * 
	 * @Param DecEduExp 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecEduExp(DecEduExp decEduExp);

	/**
	 * 通过主键id查询作家学习经历信息
	 * 
	 * @Param id
	 * 
	 * @Return DecEduExp
	 */
	DecEduExp getDecEduExpById(Long id);

	/**
	 * Description: 根据申报表id查询作家学习经历信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecEduExp> getListDecEduExpByDeclarationIds(List<Long> declarationId);
	
	/**
	 * Description: 根据申报表id查询作家学习经历信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecEduExp> getListDecEduExpByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecEduExp();
}
