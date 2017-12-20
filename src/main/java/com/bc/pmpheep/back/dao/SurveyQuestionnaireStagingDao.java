package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestionnaireStaging;
/**
 * SurveyQuestionnaireStaging实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionnaireStagingDao {

	/**
	 * 新增一个SurveyQuestionnaireStaging
	 * @author:tyc
     * @date:2017年12月20日下午16:58:31
	 * @param SurveyQuestionnaireStaging
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addSurveyQuestionnaireStaging(SurveyQuestionnaireStaging surveyQuestionnaireStaging);

	/**
	 * 删除SurveyQuestionnaireStaging通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:58:31
	 * @param SurveyQuestionnaireStaging
	 * @return 影响行数
	 */
	Integer deleteSurveyQuestionnaireStagingById(Long id);

	/**
	 * 更新一个 SurveyQuestionnaireStaging通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:58:31
	 * @param SurveyQuestionnaireStaging
	 * @return 影响行数
	 */
	Integer updateSurveyQuestionnaireStaging(SurveyQuestionnaireStaging surveyQuestionnaireStaging);
	
	/**
	 * 查找SurveyQuestionnaireStaging通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:58:31
	 * @param SurveyQuestionnaireStaging
	 * @return 影响行数
	 */
	SurveyQuestionnaireStaging selectSurveyQuestionnaireStagingById(Long id);
}
