package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyQuestionCategory;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问题分类业务层接口
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
public interface SurveyQuestionCategoryService {
	/**
     * 新增一个SurveyQuestionCategory
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:33:43
     * @param SurveyQuestionCategory 实体对象
     * @return 影响行数
     */
	SurveyQuestionCategory addSurveyQuestionCategory(SurveyQuestionCategory surveyQuestionCategory) 
			throws CheckedServiceException;

    /**
     * 删除SurveyQuestionCategory通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:36:15
     * @param SurveyQuestionCategory
     * @return 影响行数
     */
    Integer deleteSurveyQuestionCategoryById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyQuestionCategory通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:38:34
     * @param SurveyQuestionCategory
     * @return 影响行数
     */
    Integer updateSurveyQuestionCategory(SurveyQuestionCategory surveyQuestionCategory) 
    		throws CheckedServiceException;

    /**
     * 查找SurveyQuestionCategory通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:41:57
     * @param SurveyQuestionCategory
     * @return 影响行数
     */
    SurveyQuestionCategory getSurveyQuestionCategoryById(Long id) throws CheckedServiceException;
}
