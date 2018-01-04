package com.bc.pmpheep.back.controller.survey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.SurveyTargetService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.SurveyTargetVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;

/**
 * 
 * <pre>
 * 功能描述：发起问卷控制器
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
@RequestMapping(value = "/survey/target")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SurveyTargetController {
    @Autowired
    SurveyTargetService         surveyTargetService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "发起问卷";

    /**
     * 
     * <pre>
     * 功能描述：发起问卷
     * 使用示范：
     *
     * @param message 系统消息
     * @param title 消息标题
     * @param surveyId 问卷表Id
     * @param orgIds 机构id
     * @param sessionId 
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "发起问卷")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseBean send(Message message, SurveyTargetVO surveyTargetVO,
    HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyTargetService.batchSaveSurveyTargetByList(message,
                                                                                surveyTargetVO,
                                                                                sessionId));
    }
}
