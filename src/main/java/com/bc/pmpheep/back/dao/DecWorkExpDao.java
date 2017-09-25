/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecWorkExp;


/**
 * <p>Title:作家工作经历表 数据访问层接口<p>
 * @author lyc
 * @date 2017年9月25日 上午10:43:25
 */
@Repository
public interface DecWorkExpDao {

	/**
	 * 新增一个作家工作经历信息
	 * 
	 * @Param DecWorkExp 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecWorkExp(DecWorkExp decWorkExp);

	/**
	 * 通过主键id删除作家工作经历信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecWorkExpById(Long id);

	/**
	 * 通过主键id更新作家工作经历信息
	 * 
	 * @Param DecWorkExp 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecWorkExp(DecWorkExp decWorkExp);

	/**
	 * 通过主键id查询作家作家工作经历信息
	 * 
	 * @Param id
	 * 
	 * @Return DecWorkExp
	 */
	DecWorkExp getDecWorkExpById(Long id);

	/**
	 * Description: 根据申报表id查询作家工作经历信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家作家工作经历信息
	 */
	List<DecWorkExp> getListDecWorkExpByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecWorkExp();
}
