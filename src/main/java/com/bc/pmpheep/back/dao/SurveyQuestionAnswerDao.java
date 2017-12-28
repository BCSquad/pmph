package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerCountsVO;

/**
 * SurveyQuestionAnswer问题回答实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyQuestionAnswerDao {

    /**
     * 新增一个SurveyQuestionAnswer
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer 实体对象
     * @return 影响行数
     */
    Integer addSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

    /**
     * 
     * <pre>
     * 功能描述：批量保存SurveyQuestionAnswer
     * 使用示范：
     *
     * @param surveyQuestionAnswers
     * @return  影响行数
     * </pre>
     */
    Integer batchAddSurveyQuestionAnswer(List<SurveyQuestionAnswer> surveyQuestionAnswers);

    /**
     * 删除SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    Integer deleteSurveyQuestionAnswerById(Long id);

    /**
     * 更新一个 SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    Integer updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

    /**
     * 查找SurveyQuestionAnswer通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswer
     * @return 影响行数
     */
    SurveyQuestionAnswer getSurveyQuestionAnswerById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：问卷结果统计
     * 使用示范：
     *
     * @param questionAnswerCountsVO SurveyQuestionAnswerCountsVO对象
     * @return 
     * </pre>
     */
    List<SurveyQuestionAnswerCountsVO> getSurveyQuestionAnswerCounts(
    SurveyQuestionAnswerCountsVO questionAnswerCountsVO);

}
