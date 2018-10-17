package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.vo.SurveyQuestionOptionCategoryVO;
import com.bc.pmpheep.back.vo.SurveyQuestionVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;

/**
 * SurveyQuestionService接口
 * 
 * @author tyc
 * 
 */
public interface MaterialSurveyQuestionService {
    /**
     * 新增一个SurveyQuestion
     *
     * @author:tyc
     * @date:2017年12月21日下午15:54:40
     * @param SurveyQuestion 实体对象
     * @return 影响行数
     */
    SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion) throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：批量插入
     * 使用示范：
     *
     * @param surveyQuestions SurveyQuestion 对象集合
     * @return 影响行数
     * </pre>
     */
    List<Long> batchInsertSurveyQuestion(List<SurveyQuestion> surveyQuestions)
    throws CheckedServiceException;

    /**
     * 更新一个 SurveyQuestion通过主键id
     *
     * @author:tyc
     * @date:2017年12月21日下午16:52:15
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer updateSurveyQuestion(SurveyQuestion surveyQuestion) throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：批量更新
     * 使用示范：
     *
     * @param SurveyQuestion  SurveyQuestion 集合
     * @return 影响行数
     * </pre>
     */
    Integer batchUpdateSurveyQuestion(List<SurveyQuestion> surveyQuestions)
    throws CheckedServiceException;

    /**
     * 查找SurveyQuestion通过主键id
     *
     * @author:tyc
     * @date:2017年12月21日下午16:54:55
     * @param SurveyQuestion
     * @return 影响行数
     */
    SurveyQuestion getSurveyQuestionById(Long id) throws CheckedServiceException;

    /**
     * 逻辑删除SurveyQuestion通过主键id
     *
     * @author:tyc
     * @date:2017年12月21日下午17:01:35
     * @param SurveyQuestion
     * @return 影响行数
     */
    Integer deleteSurveyQuestionById(Long id) throws CheckedServiceException;

    /**
     * 添加SurveyQuestion问题集合
     *
     * @author:tyc
     * @date:2017年12月21日下午17:01:35
     * @param SurveyQuestionListVO
     * @return 影响行数
     */
    Integer addSurveyQuestionListVOList(String jsonSurveyQuestion) throws CheckedServiceException;

    /**
     * 问题表分页列表（同时查询分页数据和总条数）
     *
     * @author:tyc
     * @date:2017年12月25日下午15:57:52
     * @param pageParameter
     * @return
     * @throws CheckedServiceException
     */
    PageResult<SurveyQuestionVO> listSurveyQuestion(PageParameter<SurveyQuestionVO> pageParameter)
    throws CheckedServiceException;

    /**
     *
     * <pre>
     * 功能描述：根据问题id/问题类型Id查询问题选项
     * 使用示范：
     *
     * @param questionId Question主键ID
     * @param categoryId Question_Category主键ID
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    List<SurveyQuestionOptionCategoryVO> getQuestionOptionByQuestionIdOrCategoryId(Long questionId,
                                                                                   Long categoryId) throws CheckedServiceException;

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
    Integer batchDeleteSurveyQuestionByQuestionIds(List<Long> questionIds)
    throws CheckedServiceException;
}