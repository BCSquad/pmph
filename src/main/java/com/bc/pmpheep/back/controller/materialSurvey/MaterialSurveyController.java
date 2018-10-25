package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.service.MaterialSurveyService;
import com.bc.pmpheep.back.service.SurveyService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <pre>
 * 功能描述：教材调研控制器
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
@RequestMapping(value = "/materialSurvey")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MaterialSurveyController {
    @Autowired
    MaterialSurveyService surveyService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "教材调研";

    /**
     * 
     * <pre>
     * 功能描述：获取教材调研列表
     * 使用示范：
     *
     * @param surveyVO SurveyVO
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取教材调研列表")
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增教材调研信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(Survey survey, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyService.addSurvey(survey, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：修改教材调研信息
     * 使用示范：
     *
     * @param questionAnswerJosn 问题Json字符串
     * @param templateId 模版Id
     * @param title 教材调研名称
     * @param typeId 调查对象
     * @param intro 教材调研概述
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改教材调研信息")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public ResponseBean modify(String questionAnswerJosn, SurveyVO surveyVO) {
        return new ResponseBean(surveyService.updateSurveyAndTemplate(questionAnswerJosn, surveyVO));
    }

    /**
     * 
     * <pre>
     * 功能描述：按主键ID删除教材调研信息
     * 使用示范：
     *
     * @param id Survey主键
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "按主键ID删除教材调研信息")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public ResponseBean remove(@PathVariable("id") Long id) {
        return new ResponseBean(surveyService.deleteSurveyById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：根据教材调研ID查询教材调研已发送对象
     * 使用示范：
     *
     * @param surveyId 教材调研ID
     * @return 已发送对象(学校)集合
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询教材调研已发送对象")
    @GetMapping(value = "/send/org")
    public ResponseBean org(OrgVO orgVO, @RequestParam("pageNumber") Integer pageNumber,
    @RequestParam("pageSize") Integer pageSize) {
        PageParameter<OrgVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(orgVO);
        return new ResponseBean(surveyService.listSendOrgBySurveyId(pageParameter));
    }

}
