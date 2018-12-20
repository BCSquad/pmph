package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.SurveyQuestionCategory;
import org.springframework.stereotype.Repository;

/**
 * SurveyQuestionCategory问题分类实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyQuestionCategoryDao {

    /**
     * 新增一个SurveyQuestionCategory
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:43:11
     * @param SurveyQuestionCategory 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestionCategory(SurveyQuestionCategory surveyQuestionCategory);

    /**
     * 删除SurveyQuestionCategory通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:43:11
     * @param SurveyQuestionCategory
     * @return 影响行数
     */
    Integer deleteSurveyQuestionCategoryById(Long id);

    /**
     * 更新一个 SurveyQuestionCategory通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:43:11
     * @param SurveyQuestionCategory
     * @return 影响行数
     */
    Integer updateSurveyQuestionCategory(SurveyQuestionCategory surveyQuestionCategory);

    /**
     * 查找SurveyQuestionCategory通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:43:11
     * @param SurveyQuestionCategory
     * @return 影响行数
     */
    SurveyQuestionCategory getSurveyQuestionCategoryById(Long id);
}
