package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyTemplate;

/**
 * SurveyTemplate模版实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyTemplateDao {

    /**
     * 新增一个SurveyTemplate
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate 实体对象
     * @return 影响行数
     */
    Integer addSurveyTemplate(SurveyTemplate surveyTemplate);

    /**
     * 删除SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer deleteSurveyTemplateById(Long id);

    /**
     * 更新一个 SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer updateSurveyTemplate(SurveyTemplate surveyTemplate);

    /**
     * 查找SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    SurveyTemplate selectSurveyTemplateById(Long id);
}
