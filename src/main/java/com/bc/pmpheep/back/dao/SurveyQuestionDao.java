package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestion;

/**
 * SurveyQuestion问题实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionDao {

    /**
     * 新增一个SurveyQuestion
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestion(SurveyQuestion surveyQuestion);

    /**
     * 逻辑删除SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer deleteSurveyQuestionById(Long id);

    /**
     * 更新一个 SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer updateSurveyQuestion(SurveyQuestion surveyQuestion);

    /**
     * 查找SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:47:44
     * @param SurveyQuestion
     * @return 影响行数
     */
    SurveyQuestion selectSurveyQuestionById(Long id);
}
