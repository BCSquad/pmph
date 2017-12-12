/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecLastPosition;


/**
 * <p>Title:DecLastPositionDao<p>
 * <p>Description:TODO<p>
 * @author Administrator
 * @date 2017年9月24日 下午4:02:19
 */
@Repository
public interface DecLastPositionDao {
   
	/**
	 * 新增一个作家上套教材参编情况信息
	 * 
	 * @Param DecLastPosition 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecLastPosition(DecLastPosition decLastPosition);

	/**
	 * 通过主键id删除作家上套教材参编情况信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecLastPositionById(Long id);

	/**
	 * 通过主键id更新作家上套教材参编情况信息
	 * 
	 * @Param DecLastPosition 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecLastPosition(DecLastPosition decLastPosition);

	/**
	 * 通过主键id查询作家上套教材参编情况信息
	 * 
	 * @Param id
	 * 
	 * @Return DecLastPosition
	 */
	DecLastPosition getDecLastPositionById(Long id);

	/**
	 * Description: 根据申报表id查询作家上套教材参编情况信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家上套教材参编情况信息
	 */
	List<DecLastPosition> getListDecLastPositionByDeclarationIds(List<Long> declarationId);
	
	/**
	 * Description: 根据申报表id查询作家上套教材参编情况信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家上套教材参编情况信息
	 */
	List<DecLastPosition> getListDecLastPositionByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecLastPosition();
}
