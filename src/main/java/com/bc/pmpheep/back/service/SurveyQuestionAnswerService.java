package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问题回答业务层接口
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
public interface SurveyQuestionAnswerService {
	
	/**
     * 新增一个SurveyQuestionAnswer
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:11:24
     * @param SurveyQuestionAnswer 实体对象
     * @return 影响行数
     */
	SurveyQuestionAnswer addSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer) 
			throws CheckedServiceException;
    
    /**
     * 更新一个 SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:16:22
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    Integer updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer) 
    		throws CheckedServiceException;

    /**
     * 查找SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:25:17
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    SurveyQuestionAnswer getSurveyQuestionAnswerById(Long id) throws CheckedServiceException;
}
