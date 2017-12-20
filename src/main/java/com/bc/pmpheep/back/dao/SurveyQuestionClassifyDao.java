package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestionClassify;
/**
 * SurveyQuestionClassify实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionClassifyDao {

	/**
	 * 新增一个SurveyQuestionClassify
	 * @author:tyc
     * @date:2017年12月20日下午16:48:21
	 * @param SurveyQuestionClassify
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addSurveyQuestionClassify(SurveyQuestionClassify surveyQuestionClassify);

	/**
	 * 删除SurveyQuestionClassify通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:48:21
	 * @param SurveyQuestionClassify
	 * @return 影响行数
	 */
	Integer deleteSurveyQuestionClassifyById(Long id);

	/**
	 * 更新一个 SurveyQuestionClassify通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:48:21
	 * @param SurveyQuestion
	 * @return 影响行数
	 */
	Integer updateSurveyQuestionClassify(SurveyQuestionClassify surveyQuestionClassify);
	
	/**
	 * 查找SurveyQuestionClassify通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:48:21
	 * @param SurveyQuestionClassify
	 * @return 影响行数
	 */
	SurveyQuestionClassify selectSurveyQuestionClassifyById(Long id);
}
