/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecTextbookPmph;


/**
 * 人卫社教材编写情况表数据访问层接口
 * @author tyc
 * @date 2018年02月28日下午17:07
 */
@Repository
public interface DecTextbookPmphDao {

	/**
	 * 新增
	 * 
	 * @Param DecTextbookPmph 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecTextbookPmph(DecTextbookPmph decTextbookPmph);

	/**
	 * 通过主键id删除
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecTextbookPmphById(Long id);

	/**
	 * 通过主键id更新
	 * 
	 * @Param DecTextbookPmph 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecTextbookPmph(DecTextbookPmph decTextbookPmph);

	/**
	 * 通过主键id查询
	 * 
	 * @Param id
	 * 
	 * @Return DecTextbookPmph
	 */
	DecTextbookPmph getDecTextbookPmphById(Long id);
	
	/**
	 * Description: 根据申报表id查询人卫社教材编写情况
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 人卫社教材编写情况
	 */
	List<DecTextbookPmph> getListDecTextbookPmphByDeclarationIds(List<Long> declarationId);
	
	/**
	 * Description: 根据申报表id查询人卫社教材编写情况
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 人卫社教材编写情况
	 */
	List<DecTextbookPmph> getListDecTextbookPmphByDeclarationId(Long declarationId);
}
