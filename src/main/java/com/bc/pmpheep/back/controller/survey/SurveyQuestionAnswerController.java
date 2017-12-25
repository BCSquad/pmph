package com.bc.pmpheep.back.controller.survey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.SurveyQuestionAnswerService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;

@Controller
@RequestMapping(value = "/survey/question")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SurveyQuestionAnswerController {
    @Autowired
    SurveyQuestionAnswerService surveyQuestionAnswerService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "问卷回答";

    /**
     * 
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @param answerJosn SurveyQuestionAnswer Json字符串
     * @param request
     * @return 影响行数 
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷回答")
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ResponseBean answer(String answerJosn, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyQuestionAnswerService.addUserToAnswer(answerJosn, sessionId));
    }
}
