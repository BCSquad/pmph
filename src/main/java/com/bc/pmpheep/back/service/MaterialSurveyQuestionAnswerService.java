package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerCountsVO;
import com.bc.pmpheep.back.vo.SurveyQuestionFillVO;
import com.bc.pmpheep.back.vo.SurveyRecoveryVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;
import java.util.Map;

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
public interface MaterialSurveyQuestionAnswerService {

    /**
     * 新增一个SurveyQuestionAnswer
     *
     * @author:tyc
     * @date:2017年12月22日下午16:11:24
     * @param SurveyQuestionAnswerListVO 实体对象
     * @return 影响行数
     */
    SurveyQuestionAnswer addSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer)
    throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：批量新增SurveyQuestionAnswer
     * 使用示范：
     *
     * @param surveyQuestionAnswers
     * @return 影响行数
     * </pre>
     */
    Integer batchAddSurveyQuestionAnswer(List<SurveyQuestionAnswer> surveyQuestionAnswers);

    /**
     * 更新一个 SurveyQuestionAnswer通过主键id
     *
     * @author:tyc
     * @date:2017年12月22日下午16:16:22
     * @param SurveyQuestionAnswerListVO
     * @return 影响行数
     */
    Integer updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer)
    throws CheckedServiceException;

    /**
     * 查找SurveyQuestionAnswer通过主键id
     *
     * @author:tyc
     * @date:2017年12月22日下午16:25:17
     * @param SurveyQuestionAnswerListVO
     * @return 影响行数
     */
    SurveyQuestionAnswer getSurveyQuestionAnswerById(Long id) throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：用户回答
     * 使用示范：
     *
     * @param answerJosn 问答问题Json字符串
     * @param sessionId
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer addUserToAnswer(List<SurveyQuestionAnswer> answerJosn, String sessionId)
    throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：问卷结果统计
     * 使用示范：
     *
     * @param questionAnswerCountsVO SurveyQuestionAnswerCountsVO对象
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<SurveyQuestionAnswerCountsVO> listSurveyQuestionAnswer(
            PageParameter<SurveyQuestionAnswerCountsVO> pageParameter) throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：问卷结果统计详情
     * 使用示范：
     *
     * @param questionAnswerCountsVO SurveyQuestionAnswerCountsVO对象
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    Map<String, Object> getSurveyQuestionAnswerCounts(
            SurveyQuestionAnswerCountsVO questionAnswerCountsVO) throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：问卷回收列表
     * 使用示范：
     *
     * @param pageParameter
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<SurveyRecoveryVO> listSurveyRecovery(PageParameter<SurveyRecoveryVO> pageParameter)
    throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：查询问卷详情
     * 使用示范：
     *
     * @param surveyId 问卷ID
     * @param userId 用户ID
     * @return  SurveyQuestionAnswer 对象集合
     * @throws CheckedServiceException
     * </pre>
     */
    Map<String, Object> listSurveyQuestionAnswerBySurveyIdAndUserId(Long surveyId, Long userId)
    throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：填空题详情
     * 使用示范：
     *
     * @param pageParameter
     * @return 分页数据集
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<SurveyQuestionFillVO> listFillQuestion(
            PageParameter<SurveyQuestionFillVO> pageParameter) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：填空题统计
     * 使用示范：
     *
     * @param surveyId 问卷ID
     * @throws CheckedServiceException
     * @return
     * </pre>
     */
    List<SurveyQuestionFillVO> listFillQuestionCounts(Long surveyId) throws CheckedServiceException;

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
    List<Long> getUserIdBySurveyId(Long surveyId) throws CheckedServiceException;
}
