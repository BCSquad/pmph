/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.DecMoocDigital;

/**
 * 参加人卫慕课、数字教材编写情况数据访问层接口
 * @author tyc
 * @date 2018年02月28日下午17:35
 */
@Repository
public interface DecMoocDigitalDao {

	/**
	 * 
	 * 添加
	 * 
	 * @author:tyc
	 * 
	 * @return Integer影响行数
	 */
	Integer addDecMoocDigital(DecMoocDigital decMoocDigital);

	/**
	 * 根据declarationId查询
	 * 
	 * @author tyc
	 * 
	 * @return
	 */
	List<DecMoocDigital> getDecMoocDigitalByDeclarationIds(List<Long> declarationId);

	/**
	 * 根据declarationId查询
	 * 
	 * @author tyc
	 * 
	 * @return
	 */
	DecMoocDigital getDecMoocDigitalByDeclarationId(Long declarationId);

}
