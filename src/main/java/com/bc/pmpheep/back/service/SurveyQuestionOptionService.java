package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * SurveyQuestionOptionService接口
 * @author tyc
 *
 */
public interface SurveyQuestionOptionService {
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
     * 更新一个 SurveyOptions通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:05:07
     * @param SurveyOptions
     * @return 影响行数
     */
    Integer updateSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption) throws CheckedServiceException;
    
    /**
     * 查找SurveyQuestionOption通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:06:15
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    SurveyQuestionOption selectSurveyQuestionOptionById(Long id) throws CheckedServiceException;
}
