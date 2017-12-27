package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyTemplateQuestion;

/**
 * SurveyTemplateQuestion问题模版关联实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyTemplateQuestionDao {

    /**
     * 新增一个SurveyTemplateQuestion
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @param SurveyTemplateQuestion 实体对象
     * @return 影响行数
     */
    Integer addSurveyTemplateQuestion(SurveyTemplateQuestion surveyTemplateQuestion);

    /**
     * 删除SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @param SurveyTemplateQuestion
     * @return 影响行数
     */
    Integer deleteSurveyTemplateQuestionById(Long id);

    /**
     * 更新一个 SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @param SurveyTemplateQuestion
     * @return 影响行数
     */
    Integer updateSurveyTemplateQuestion(SurveyTemplateQuestion surveyTemplateQuestion);

    /**
     * 查找SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @param SurveyTemplateQuestion
     * @return 影响行数
     */
    SurveyTemplateQuestion getSurveyTemplateQuestionById(Long id);
}
