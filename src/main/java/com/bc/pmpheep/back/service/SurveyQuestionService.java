package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * SurveyQuestionService接口
 * @author tyc
 *
 */
public interface SurveyQuestionService {
	/**
     * 新增一个SurveyQuestion
     * 
     * @author:tyc
     * @date:2017年12月21日下午15:54:40
     * @param SurveyQuestion 实体对象
     * @return 影响行数
     */
	SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion) throws CheckedServiceException;
	
	/**
     * 更新一个 SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月21日下午16:52:15
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer updateSurveyQuestion(SurveyQuestion surveyQuestion) throws CheckedServiceException;
    
    /**
     * 查找SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月21日下午16:54:55
     * @param SurveyQuestion
     * @return 影响行数
     */
    SurveyQuestion getSurveyQuestionById(Long id) throws CheckedServiceException;
    
    /**
     * 逻辑删除SurveyQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月21日下午17:01:35
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer deleteSurveyQuestionById(Long id) throws CheckedServiceException;
    
    /**
     * 添加SurveyQuestion问题集合
     * 
     * @author:tyc
     * @date:2017年12月21日下午17:01:35
     * @param SurveyQuestionListVO
     * @return 影响行数
     */
    Integer addSurveyQuestionListVOList(String jsonSurveyQuestion) throws CheckedServiceException;
}