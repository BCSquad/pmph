package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestionOption;

/**
 * SurveyQuestionOption问题选项实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionOptionDao {

    /**
     * 新增一个SurveyQuestionOption
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption);

    /**
     * 删除SurveyQuestionOption通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    Integer deleteSurveyQuestionOptionById(Long id);

    /**
     * 更新一个 SurveyOptions通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyOptions
     * @return 影响行数
     */
    Integer updateSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption);

    /**
     * 查找SurveyQuestionOption通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    SurveyQuestionOption selectSurveyQuestionOptionById(Long id);
}
