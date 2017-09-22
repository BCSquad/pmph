/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.po.DecCourseConstruction;

/**
 * <p>
 * Title:作家精品课程建设情况数据访问层接口
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 下午3:34:58
 */
public interface DecCourseConstructionDao {
	/**
	 * Description:新增一个精品课程建设情况信息
	 * 
	 * @Param DecCourseConstruction 实体对象
	 * @Return 影响行数
	 */
	Integer addDecCourseConstruction(DecCourseConstruction decCourseConstruction);

	/**
	 * Description:根据id删除精品课程建设情况信息
	 * 
	 * @param id主键
	 * @Return 影响行数
	 */
	Integer deleteDecCourseConstrction(Long id);

	/**
	 * Description:更新精品课程建设情况信息
	 * 
	 * @param DecCourseConstructionDao 实体对象
	 * @Return 影响行数
	 */
	Integer updateDecCourseCnstruction(DecCourseConstruction decCourseConstruction);

	/**
	 * Description:根据id查询精品课程建设情况信息
	 * @Param id主键
	 * @Return DecCourseConstruction实体对象
	 */
	DecCourseConstructionDao getDecCourseConstructionById(Long id);
	
	/**
	 * Description:根据申报表id查询精品课程建设情况信息
	 * @Param declarationId
	 * @Return 精品课程建设情况信息
	 */
	List<DecCourseConstruction> getDecCourseConstructionByDeclarationId(Long declarationId);
	
	/**
	 * Description:查询精品课程建设情况表的总记录数
	 * @Return 表的总记录数
	 */
	Long getDecCourseConstruction(); 
}
