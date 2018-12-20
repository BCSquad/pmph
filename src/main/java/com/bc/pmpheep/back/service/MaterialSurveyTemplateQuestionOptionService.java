package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;

/**
 * SurveyQuestionOptionService接口
 * 
 * @author tyc
 * 
 */
public interface MaterialSurveyTemplateQuestionOptionService {
    /**
     * 新增一个SurveyQuestionOption
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption 实体对象
     * @return 影响行数
     */
    SurveyQuestionOption addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：批量插入
     * 使用示范：
     *
     * @param surveyQuestionOptions  SurveyQuestionOption对象集合
     * @return 影响行数
     * </pre>
     */
    Integer batchInsertSurveyQuestionOption(List<SurveyQuestionOption> surveyQuestionOptions)
    throws CheckedServiceException;

    /**
     * 更新一个 SurveyOptions通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:05:07
     * @param SurveyOptions
     * @return 影响行数
     */
    Integer updateSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：批量更新
     * 使用示范：
     *
     * @param surveyQuestionOptions  SurveyQuestionOption 集合
     * @return 影响行数
     * </pre>
     */
    Integer batchUpdateSurveyOption(List<SurveyQuestionOption> surveyQuestionOptions)
    throws CheckedServiceException;

    /**
     * 查找SurveyQuestionOption通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:06:15
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    SurveyQuestionOption getSurveyQuestionOptionById(Long id) throws CheckedServiceException;

    /**
     * 删除SurveyQuestionOption通过问题id
     * 
     * @author:tyc
     * @date:2017年12月25日上午11:19:03
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    Integer deleteSurveyQuestionOptionByQuestionId(Long questionId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按问题Id批量删除
     * 使用示范：
     *
     * @param questionIds  问题id
     * @return 影响行数 
     * </pre>
     */
    Integer batchDeleteSurveyQuestionOptionByQuestionIds(List<Long> questionIds)
    throws CheckedServiceException;
}
