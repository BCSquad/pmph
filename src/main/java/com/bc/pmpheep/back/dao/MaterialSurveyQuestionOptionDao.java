package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.SurveyQuestionOption;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyQuestionOption问题选项实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyQuestionOptionDao {

    /**
     * 新增一个SurveyQuestionOption
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption);

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
    Integer batchInsertSurveyQuestionOption(List<SurveyQuestionOption> surveyQuestionOptions);

    /**
     * 删除SurveyQuestionOption通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    Integer deleteSurveyQuestionOptionById(Long id);

    /**
     * 更新一个 SurveyOptions通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyOptions
     * @return 影响行数
     */
    Integer updateSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption);

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
    Integer batchUpdateSurveyOption(List<SurveyQuestionOption> surveyQuestionOptions);

    /**
     * 查找SurveyQuestionOption通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:45:57
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    SurveyQuestionOption getSurveyQuestionOptionById(Long id);

    /**
     * 删除SurveyQuestionOption通过问题id
     * 
     * @author:tyc
     * @date:2017年12月24日下午14:06:34
     * @param SurveyQuestionOption
     * @return 影响行数
     */
    Integer deleteSurveyQuestionOptionByQuestionId(Long questionId);

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
    Integer batchDeleteSurveyQuestionOptionByQuestionIds(List<Long> questionIds);

}
