package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecIntention;

/**
 * 编写内容意向表数据访问层接口
 * @author tyc
 * @date 2018年02月28日下午17:41
 */
@Repository
public interface DecIntentionDao {

	/**
	 * 
	 * 添加
	 * 
	 * @author:tyc
	 * 
	 * @return Integer影响行数
	 */
	Integer addDecIntention(DecIntention decIntention);

	/**
	 * 根据declarationId查询
	 * 
	 * @author tyc
	 * 
	 * @return
	 */
	List<DecIntention> getDecIntentionByDeclarationIds(List<Long> declarationId);

	/**
	 * 根据declarationId查询
	 * 
	 * @author tyc
	 * 
	 * @return
	 */
	DecIntention getDecIntentionByDeclarationId(Long declarationId);

}
