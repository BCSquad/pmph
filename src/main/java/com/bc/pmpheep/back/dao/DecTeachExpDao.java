/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecTeachExp;


/**
 * <p>
 * Title:作家教学经历表数据访问层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午9:27:29
 */
@Repository
public interface DecTeachExpDao {

	/**
	 * 新增一个作家教学经历信息
	 * 
	 * @Param DecTeachExp 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecTeachExp(DecTeachExp decTeachExp);

	/**
	 * 通过主键id删除作家教学经历信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecTeachExpById(Long id);

	/**
	 * 通过主键id更新作家教学经历信息
	 * 
	 * @Param DecTeachExp 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecTeachExp(DecTeachExp decTeachExp);

	/**
	 * 通过主键id查询作家教学经历信息
	 * 
	 * @Param id
	 * 
	 * @Return DecTeachExp
	 */
	DecTeachExp getDecTeachExpById(Long id);

	/**
	 * Description: 根据申报表id查询作家教学经历信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecTeachExp> getListDecTeachExpByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecTeachExp();
}
