package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestionAnswer;

/**
 * SurveyQuestionAnswer问题回答实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionAnswerDao {

    /**
     * 新增一个SurveyQuestionAnswer
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

    /**
     * 删除SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    Integer deleteSurveyQuestionAnswerById(Long id);

    /**
     * 更新一个 SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    Integer updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

    /**
     * 查找SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    SurveyQuestionAnswer getSurveyQuestionAnswerById(Long id);
}
