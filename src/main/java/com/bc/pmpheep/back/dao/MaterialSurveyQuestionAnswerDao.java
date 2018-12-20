package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerCountsVO;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerListVO;
import com.bc.pmpheep.back.vo.SurveyQuestionFillVO;
import com.bc.pmpheep.back.vo.SurveyRecoveryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyQuestionAnswer问题回答实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyQuestionAnswerDao {

    /**
     * 新增一个SurveyQuestionAnswer
     *
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswerListVO 实体对象
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
     * @param SurveyQuestionAnswerListVO
     * @return 影响行数
     */
    Integer deleteSurveyQuestionAnswerById(Long id);

    /**
     * 更新一个 SurveyQuestionAnswer通过主键id
     *
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswerListVO
     * @return 影响行数
     */
    Integer updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

    /**
     * 查找SurveyQuestionAnswer通过主键id
     *
     * @author:tyc
     * @date:2017年12月20日下午16:40:37
     * @param SurveyQuestionAnswerListVO
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
    List<SurveyQuestionAnswerCountsVO> listSurveyQuestionAnswer(
            PageParameter<SurveyQuestionAnswerCountsVO> pageParameter);

    /**
     *
     * <pre>
     * 功能描述：问卷结果统计详情
     * 使用示范：
     *
     * @param questionAnswerCountsVO SurveyQuestionAnswerCountsVO对象
     * @return
     * </pre>
     */
    List<SurveyQuestionAnswerCountsVO> getSurveyQuestionAnswerCounts(
            SurveyQuestionAnswerCountsVO questionAnswerCountsVO);

    /**
     *
     * <pre>
     * 功能描述：填空题详情
     * 使用示范：
     *
     * @param pageParameter
     * @return 分页数据集
     * </pre>
     */
    List<SurveyQuestionFillVO> listFillQuestion(PageParameter<SurveyQuestionFillVO> pageParameter);

    /**
     *
     * <pre>
     * 功能描述：填空题统计
     * 使用示范：
     *
     * @param surveyId 问卷ID
     * @return
     * </pre>
     */
    List<SurveyQuestionFillVO> listFillQuestionCounts(@Param("surveyId") Long surveyId);

    /**
     *
     * <pre>
     * 功能描述：问卷回收列表
     * 使用示范：
     *
     * @param pageParameter
     * @return
     * </pre>
     */
    List<SurveyRecoveryVO> listSurveyRecovery(PageParameter<SurveyRecoveryVO> pageParameter);

    /**
     *
     * <pre>
     * 功能描述：查询问卷详情
     * 使用示范：
     *
     * @param surveyId 问卷ID
     * @param userId 用户ID
     * @return  SurveyQuestionAnswerListVO 对象集合
     * </pre>
     */
    List<SurveyQuestionAnswerListVO> listSurveyQuestionAnswerBySurveyId(
            @Param("surveyId") Long surveyId, @Param("userId") Long userId);

    /**
     * 
     * <pre>
     * 功能描述：根据问卷ID查询已回答问卷的用户ID
     * 使用示范：
     *
     * @param surveyId 问卷Id
     * @return 已回答问卷的用户ID集合
     * </pre>
     */
    List<Long> getUserIdBySurveyId(Long surveyId);
}
