package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问题模版关联业务层
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface MaterialSurveyTemplateQuestionService {
    /**
     * 新增一个SurveyTemplateQuestion
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:55:11
     * @param SurveyTemplateQuestion 实体对象
     * @return 影响行数
     */
    SurveyTemplateQuestion addSurveyTemplateQuestion(SurveyTemplateQuestion surveyTemplateQuestion)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：批量新增
     * 使用示范：
     *
     * @param surveyTemplateQuestions SurveyTemplateQuestion对象集合
     * @return 影响行数
     * </pre>
     */
    Integer batchInsertSurveyTemplateQuestion(List<SurveyTemplateQuestion> surveyTemplateQuestions)
    throws CheckedServiceException;

    /**
     * 删除SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:57:53
     * @param SurveyTemplateQuestion
     * @return 影响行数
     */
    Integer deleteSurveyTemplateQuestionById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:00:23
     * @param SurveyTemplateQuestion
     * @return 影响行数
     */
    Integer updateSurveyTemplateQuestion(SurveyTemplateQuestion surveyTemplateQuestion)
    throws CheckedServiceException;

    /**
     * 查找SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:05:08
     * @param SurveyTemplateQuestion
     * @return 影响行数
     */
    SurveyTemplateQuestion getSurveyTemplateQuestionById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过templateId查询对象集合
     * 使用示范：
     *
     * @param templateId 模版ID
     * @return SurveyTemplateQuestion 对象集合
     * </pre>
     */
    List<SurveyTemplateQuestion> getSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过templateId删除
     * 使用示范：
     *
     * @param templateId 模版ID
     * @return 影响行数
     * </pre>
     */
    Integer deleteSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException;
}
