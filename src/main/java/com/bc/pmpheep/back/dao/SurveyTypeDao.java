package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyType;
/**
 * SurveyType实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyTypeDao {

	/**
	 * 新增一个SurveyType
	 * @author:tyc
     * @date:2017年12月20日上午17:40:37
	 * @param SurveyType
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addSurveyType(SurveyType surveyType);

	/**
	 * 删除SurveyType通过主键id
	 * @author:tyc
     * @date:2017年12月20日上午17:40:37
	 * @param SurveyType
	 * @return 影响行数
	 */
	Integer deleteSurveyTypeById(Long id);

	/**
	 * 更新一个 SurveyType通过主键id
	 * @author:tyc
     * @date:2017年12月20日上午17:40:37
	 * @param SurveyType
	 * @return 影响行数
	 */
	Integer updateSurveyType(SurveyType surveyType);
	
	/**
	 * 查找SurveyType通过主键id
	 * @author:tyc
     * @date:2017年12月20日上午17:40:37
	 * @param SurveyType
	 * @return 影响行数
	 */
	SurveyType selectSurveyTypeById(Long id);
}
