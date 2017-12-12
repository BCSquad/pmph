/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecAcade;

/**
 * <p>
 * Title:作家兼职学术组织信息数据访问层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 上午10:50:04
 */

@Repository
public interface DecAcadeDao {
	/**
	 * 新增一个作家兼职学术组织信息
	 * 
	 * @Param DecAcade 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDecAcade(DecAcade decAcade);

	/**
	 * 通过主键id删除一个作家兼职学术组织信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDecAcadeById(Long id);

	/**
	 * 通过主键id更新一个作家兼职学术组织信息
	 * 
	 * @Param DecAcade 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDecAcade(DecAcade decAcade);

	/**
	 * 通过主键id查询作家的一个学术兼职组织信息
	 * 
	 * @Param id
	 * 
	 * @Return DecAcade
	 */
	DecAcade getDecAcadeById(Long id);

	/**
	 * Description: 根据申报表id查询学术兼职组织信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 兼职学术组织信息
	 */
	List<DecAcade> getListDecAcadeByDeclarationIds(List<Long> declarationId);
	
	/**
	 * Description: 根据申报表id查询学术兼职组织信息
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 兼职学术组织信息
	 */
	List<DecAcade> getListDecAcadeByDeclarationId(Long declarationId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDecAcade();
}
