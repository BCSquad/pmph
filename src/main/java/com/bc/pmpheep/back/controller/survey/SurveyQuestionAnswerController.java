package com.bc.pmpheep.back.controller.survey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.SurveyQuestionAnswerService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerCountsVO;
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

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "问卷回答";

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
    public ResponseBean create(@RequestParam("answerJosn") String answerJson,
    HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyQuestionAnswerService.addUserToAnswer(answerJson, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：问卷结果统计
     * 使用示范：
     *
     * @param questionAnswerCountsVO
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷结果统计")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseBean count(SurveyQuestionAnswerCountsVO questionAnswerCountsVO) {
        return new ResponseBean(
                                surveyQuestionAnswerService.getSurveyQuestionAnswerCounts(questionAnswerCountsVO));
    }
}
