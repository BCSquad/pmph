package com.bc.pmpheep.back.controller.survey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.service.SurveyService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：问卷调查控制器
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
@RequestMapping(value = "/survey")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SurveyController {
    @Autowired
    SurveyService               surveyService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "问卷调查";

    /**
     * 
     * <pre>
     * 功能描述：获取问卷调查列表
     * 使用示范：
     *
     * @param surveyVO SurveyVO
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取问卷调查列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list(SurveyVO surveyVO, @RequestParam("pageNumber") Integer pageNumber,
    @RequestParam("pageSize") Integer pageSize) {
        surveyVO.setTitle(StringUtil.toAllCheck(surveyVO.getTitle()));// 问卷标题
        PageParameter<SurveyVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(surveyVO);
        return new ResponseBean(surveyService.listSurvey(pageParameter));
    }

    /**
     * 
     * <pre>
     * 功能描述：新增问卷信息
     * 使用示范：
     *
     * @param survey Survey问卷对象
     * @param request
     * @return Survey问卷对象
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增问卷信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(Survey survey, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyService.addSurvey(survey, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：修改问卷信息
     * 使用示范：
     *
     * @param questionAnswerJosn 问题Json字符串
     * @param templateId 模版Id
     * @param title 问卷名称
     * @param typeId 调查对象
     * @param intro 问卷概述
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改问卷信息")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseBean modify(String questionAnswerJosn, SurveyVO surveyVO) {
        return new ResponseBean(surveyService.updateSurveyAndTemplate(questionAnswerJosn, surveyVO));
    }

    /**
     * 
     * <pre>
     * 功能描述：按主键ID删除问卷信息
     * 使用示范：
     *
     * @param id Survey主键
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "按主键ID删除问卷信息")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public ResponseBean remove(@PathVariable("id") Long id) {
        return new ResponseBean(surveyService.deleteSurveyById(id));
    }
}
