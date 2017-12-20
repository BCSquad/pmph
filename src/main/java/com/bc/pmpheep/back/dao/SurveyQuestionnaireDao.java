package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestionnaire;
/**
 * SurveyQuestionnaire实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionnaireDao {

	/**
	 * 新增一个SurveyQuestionnaire
	 * @author:tyc
     * @date:2017年12月20日下午16:55:35
	 * @param SurveyQuestionnaire
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addSurveyQuestionnaire(SurveyQuestionnaire surveyQuestionnaire);

	/**
	 * 删除SurveyQuestionnaire通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:55:35
	 * @param SurveyQuestionnaire
	 * @return 影响行数
	 */
	Integer deleteSurveyQuestionnaireById(Long id);

	/**
	 * 更新一个 SurveyQuestionnaire通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:55:35
	 * @param SurveyQuestionnaire
	 * @return 影响行数
	 */
	Integer updateSurveyQuestionnaire(SurveyQuestionnaire surveyQuestionnaire);
	
	/**
	 * 查找SurveyQuestionnaire通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:55:35
	 * @param SurveyQuestionnaire
	 * @return 影响行数
	 */
	SurveyQuestionnaire selectSurveyQuestionnaireById(Long id);
}
