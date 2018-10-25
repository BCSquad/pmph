package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyTemplateQuestion问题模版关联实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyTemplateQuestionDao {

    /**
     * 新增一个SurveyTemplateQuestion
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @param surveyQuestion 实体对象
     * @return 影响行数
     */
    Integer addSurveyTemplateQuestion(SurveyQuestion surveyQuestion);

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
    Integer batchInsertSurveyTemplateQuestion(List<SurveyQuestion> surveyTemplateQuestions);

    /**
     * 删除SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @return 影响行数
     */
    Integer deleteSurveyTemplateQuestionById(Long id);

    /**
     * 更新一个 SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer updateSurveyTemplateQuestion(SurveyQuestion SurveyQuestion);

    /**
     * 查找SurveyTemplateQuestion通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:08:22
     * @return 影响行数
     */
    SurveyQuestion getSurveyTemplateQuestionById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：通过templateId查询对象集合
     * 使用示范：
     *
     * @param templateId 模版ID
     * @return SurveyQuestion 对象集合
     * </pre>
     */
    List<SurveyQuestion> getSurveyTemplateQuestionByTemplateId(Long templateId);

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
    Integer deleteSurveyTemplateQuestionByTemplateId(Long templateId);
}
