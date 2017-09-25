/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecTextbook;


/**
 * <p>Title:作家教材编写情况表 数据访问层接口<p>
 * @author lyc
 * @date 2017年9月25日 上午10:15:00
 */
@Repository
public interface DecTextbookDao {

	/**
	 * 新增一个作家教材编写情况信息
	 * 
	 * @Param DecTextbook 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecTextbook(DecTextbook decTextbook);

	/**
	 * 通过主键id删除一个作家教材编写情况信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecTextbookById(Long id);

	/**
	 * 通过主键id更新作家教材编写情况信息
	 * 
	 * @Param DecTextbook 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecTextbook(DecTextbook decTextbook);

	/**
	 * 通过主键id查询作家教材编写情况信息
	 * 
	 * @Param id
	 * 
	 * @Return DecTextbook
	 */
	DecTextbook getDecTextbookById(Long id);

	/**
	 * Description: 根据申报表id查询作家教材编写情况信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家教材编写情况信息
	 */
	List<DecTextbook> getListDecTextbookByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecTextbook();
}
