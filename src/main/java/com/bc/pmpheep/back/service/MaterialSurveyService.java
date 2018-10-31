package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialSurveyTemplate;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.Map;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问卷业务层接口
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
public interface MaterialSurveyService {
    /**
     * 新增一个Survey
     * 
     * @author:tyc
     * @date:2017年12月22日下午15:51:35
     * @param survey 实体对象
     * @return 影响行数
     */
    SurveyVO addSurvey(SurveyVO survey) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：新增一个Survey
     * 使用示范：
     *
     * @param survey Survey 实体对象
     * @param sessionId
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    SurveyVO addSurvey(SurveyVO survey, String sessionId) throws CheckedServiceException;

    /**
     * 更新一个 Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:00:11
     * @return 影响行数
     */
    Integer updateSurvey(Survey survey) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改问卷信息
     * 使用示范：
     *
     *  answerJosn 问题Json字符串
     *  templateId 模版Id
     *  title 问卷名称
     *  typeId 调查对象
     *  intro 问卷概述
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateSurveyAndTemplate(String questionAnswerJosn, SurveyVO surveyVO)
    throws CheckedServiceException;

    /**
     * 查找Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:07:54
     * @return 影响行数
     */
    Survey getSurveyById(Long id) throws CheckedServiceException;

    /**
     * 问卷表分页列表（同时查询分页数据和总条数）
     * 
     * @author:tyc
     * @date:2017年12月25日下午15:02:14
     * @param pageParameter
     * @return
     */
    PageResult<SurveyVO> listSurvey(PageParameter<SurveyVO> pageParameter)
    throws CheckedServiceException;

    /**
     * 逻辑删除Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * @return 影响行数
     */
    Integer deleteSurveyById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询问卷和问卷对应的类型
     * 使用示范：
     *
     * @param id 问卷id
     * @return SurveyVO 对象
     * </pre>
     */
    SurveyVO getSurveyAndSurveyTypeById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据问卷ID查询问卷已发送对象
     * 使用示范：
     *
     *  surveyId 问卷id
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    PageResult<OrgVO> listSendOrgBySurveyId(PageParameter<OrgVO> pageParameter)
    throws CheckedServiceException;

    /**
     * 更新调研表
     * @param questionAnswerJosn
     * @param del_question
     * @param del_question_option
     * @param surveyVO
     * @param sessionId
     * @return
     */
    SurveyVO addSurvey(String questionAnswerJosn, String del_question, String del_question_option, SurveyVO surveyVO, String sessionId,Boolean tempReCreat);

    /**
     * 获取调研表详情
     * @param id
     * @return
     */
    Map<String,Object> getSurveyAndQuestionById(Long id);


}
