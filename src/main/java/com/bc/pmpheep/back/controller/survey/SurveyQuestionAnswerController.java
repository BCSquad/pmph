package com.bc.pmpheep.back.controller.survey;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.service.SurveyQuestionAnswerService;
import com.bc.pmpheep.back.service.SurveyService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerCountsVO;
import com.bc.pmpheep.back.vo.SurveyQuestionFillVO;
import com.bc.pmpheep.back.vo.SurveyRecoveryVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：问卷回答控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-25
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/survey/question/answer")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SurveyQuestionAnswerController {
    @Autowired
    SurveyQuestionAnswerService surveyQuestionAnswerService;
    @Autowired
    SurveyService               surveyService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "问卷回收";

    /**
     * 
     * <pre>
     * 功能描述：用户回答问卷
     * 使用示范：
     *
     * @param answerJson SurveyQuestionAnswer Json字符串
     * @param request
     * @return 影响行数 
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷回答")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(@RequestBody List<SurveyQuestionAnswer> answerJosn,
    HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyQuestionAnswerService.addUserToAnswer(answerJosn, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：问卷回收列表 
     * 使用示范：
     *
     * @param title 问卷标题
     * @param userName 提交人
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷回收列表")
    @RequestMapping(value = "/recovery", method = RequestMethod.GET)
    public ResponseBean recovery(SurveyRecoveryVO surveyRecoveryVO,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        surveyRecoveryVO.setUserName(StringUtil.toAllCheck(surveyRecoveryVO.getUserName()));// 发起人
        surveyRecoveryVO.setTitle(StringUtil.toAllCheck(surveyRecoveryVO.getTitle()));// 问卷标题
        PageParameter<SurveyRecoveryVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(surveyRecoveryVO);
        return new ResponseBean(surveyQuestionAnswerService.listSurveyRecovery(pageParameter));
    }

    /**
     * 
     * <pre>
     * 功能描述：查询问卷回收详情
     * 使用示范：
     *
     * @param surveyId 问卷ID
     * @return  SurveyQuestionAnswer 对象集合
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷回收列表详情")
    @RequestMapping(value = "/recovery/detail", method = RequestMethod.GET)
    public ResponseBean detail(@RequestParam("surveyId") Long surveyId) {
        return new ResponseBean(
                                surveyQuestionAnswerService.listSurveyQuestionAnswerBySurveyId(surveyId));
    }

    /**
     * 
     * <pre>
     * 功能描述：问卷结果统计
     * 使用示范：
     *
     * @param surveyQuestionAnswerCountsVO
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷结果统计")
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ResponseBean result(SurveyQuestionAnswerCountsVO surveyQuestionAnswerCountsVO,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        surveyQuestionAnswerCountsVO.setSurveyTitle(StringUtil.toAllCheck(surveyQuestionAnswerCountsVO.getTitle()));// 问卷标题
        PageParameter<SurveyQuestionAnswerCountsVO> pageParameter =
        new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(surveyQuestionAnswerCountsVO);
        return new ResponseBean(surveyQuestionAnswerService.listSurveyQuestionAnswer(pageParameter));
    }

    /**
     * 
     * <pre>
     * 功能描述：问卷结果统计详情
     * 使用示范：
     *
     * @param questionAnswerCountsVO
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷结果统计详情")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseBean count(SurveyQuestionAnswerCountsVO questionAnswerCountsVO) {
        return new ResponseBean(
                                surveyQuestionAnswerService.getSurveyQuestionAnswerCounts(questionAnswerCountsVO));
    }

    /**
     * 
     * <pre>
     * 功能描述：主观题详情
     * 使用示范：
     *
     * @param surveyQuestionFillVO 
     * @param pageNumber
     * @param pageSize
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "主观题详情")
    @RequestMapping(value = "/fill", method = RequestMethod.GET)
    public ResponseBean fill(SurveyQuestionFillVO surveyQuestionFillVO,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        PageParameter<SurveyQuestionFillVO> pageParameter =
        new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(surveyQuestionFillVO);
        return new ResponseBean(surveyQuestionAnswerService.listFillQuestion(pageParameter));
    }
}
