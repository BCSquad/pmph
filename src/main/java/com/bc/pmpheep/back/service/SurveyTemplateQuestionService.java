package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
public interface SurveyTemplateQuestionService {
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
}
