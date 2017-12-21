package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyAnswer;
/**
 * SurveyAnswer实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyAnswerDao {

	/**
	 * 新增一个SurveyAnswer
	 * @author:tyc
     * @date:2017年12月20日下午16:40:37
	 * @param SurveyAnswer 实体对象
	 * @return 影响行数
	 */
	Integer addSurveyAnswer(SurveyAnswer surveyAnswer);

	/**
	 * 删除SurveyAnswer通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:40:37
	 * @param SurveyAnswer
	 * @return 影响行数
	 */
	Integer deleteSurveyAnswerById(Long id);

	/**
	 * 更新一个 SurveyAnswer通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:40:37
	 * @param SurveyAnswer
	 * @return 影响行数
	 */
	Integer updateSurveyAnswer(SurveyAnswer surveyAnswer);
	
	/**
	 * 查找SurveyAnswer通过主键id
	 * @author:tyc
     * @date:2017年12月20日下午16:40:37
	 * @param SurveyAnswer
	 * @return 影响行数
	 */
	SurveyAnswer selectSurveyAnswerById(Long id);
}
