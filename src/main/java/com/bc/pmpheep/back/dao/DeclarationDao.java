/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Declaration;


/**
 * <p>Title:作家申报表 数据访问层接口<p>
 * @author lyc
 * @date 2017年9月24日 下午3:19:54
 */
@Repository
public interface DeclarationDao {

	/**
	 * 新增一个作家申报信息
	 * 
	 * @Param Declaration 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer addDeclaration(Declaration declaration);

	/**
	 * 通过主键id删除一个作家申报信息
	 * 
	 * @Param id
	 * 
	 * @Return 影响行数
	 */
	Integer deleteDeclarationById(Long id);

	/**
	 * 通过主键id更新作家申报信息
	 * 
	 * @Param Declaration 实体对象
	 * 
	 * @Return 影响行数
	 */
	Integer updateDeclaration(Declaration declaration);

	/**
	 * 通过主键id查询一个作家申报信息
	 * 
	 * @Param id
	 * 
	 * @Return Declaration
	 */
	Declaration getDeclarationById(Long id);

	/**
	 * Description: 根据教材id查询作家申报信息
	 * 
	 * @Param materialId 教材id
	 * 
	 * @Return 作家申报信息
	 */
	List<Declaration> getDeclarationByMaterialId(Long materialId);

	/**
	 * Description:查询表的数据总记录数
	 * 
	 * @Return 表的总记录数
	 */
	Long getDeclaration();
}
