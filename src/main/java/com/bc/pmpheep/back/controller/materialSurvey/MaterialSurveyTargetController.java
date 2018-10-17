package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.SurveyTargetService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.SurveyTargetVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
@RequestMapping(value = "/materialSurvey/target")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MaterialSurveyTargetController {
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
    HttpServletRequest request) throws Exception {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyTargetService.batchSaveSurveyTargetByList(message,
                                                                                surveyTargetVO,
                                                                                sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：问卷调查补发消息
     * 使用示范：
     *
     * @param message 消息对象
     * @param title 问卷调查名称
     * @param surveyId 问卷ID
     * @param request
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "问卷调查补发消息")
    @RequestMapping(value = "/send/message", method = RequestMethod.POST)
    public ResponseBean message(Message message, @RequestParam("title") String title,
    @RequestParam("surveyId") Long surveyId, HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(surveyTargetService.reissueSurveyMessage(message,
                                                                             title,
                                                                             surveyId,
                                                                             sessionId));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

}
