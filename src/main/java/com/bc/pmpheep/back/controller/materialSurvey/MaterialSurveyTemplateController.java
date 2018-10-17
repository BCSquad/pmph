package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.service.MaterialSurveyTemplateService;
import com.bc.pmpheep.back.service.SurveyTemplateService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.SurveyTemplateListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <pre>
 * 功能描述：问卷模版控制器
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
@RequestMapping(value = "/materialSurvey/template")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MaterialSurveyTemplateController {
    @Autowired
    MaterialSurveyTemplateService surveyTemplateService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "教材调研-调研模版";

    /**
     * 
     * <pre>
     * 功能描述：查询问卷模版列表
     * 使用示范：
     *
     * @param surveyTemplateListVO 
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询教材调研模版列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list(SurveyTemplateListVO surveyTemplateListVO,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        surveyTemplateListVO.setTemplateName(StringUtil.toAllCheck(surveyTemplateListVO.getTemplateName()));// 模版名称
        PageParameter<SurveyTemplateListVO> pageParameter =
        new PageParameter<>(pageNumber, pageSize);
        pageParameter.setParameter(surveyTemplateListVO);
        return new ResponseBean(surveyTemplateService.listSurveyTemplateList(pageParameter));
    }

    /**
     * 
     * <pre>
     * 功能描述：教材调研模版添加
     * 使用示范：
     *
     * @param surveyId 教材调研表Id
     * @param orgIds 学校集合
     * @param request
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增教材调研模版")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(@RequestParam("questionAnswerJosn") String questionAnswerJosn,
    SurveyTemplateVO surveyTemplateVO, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyTemplateService.addSurveyTemplateVO(questionAnswerJosn,
                                                                          surveyTemplateVO,
                                                                          sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：查询模版下的所有问题
     * 使用示范：
     *
     * @param templateId SurveyTemplate模版ID
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询模版下的所有问题")
    @RequestMapping(value = "/question/look", method = RequestMethod.GET)
    public ResponseBean look(@RequestParam("surveyId") Long surveyId,
    @RequestParam("templateId") Long templateId) {
        return new ResponseBean(
                                surveyTemplateService.getSurveyTemplateQuestionByTemplateId(surveyId,
                                                                                            templateId));
    }

    /**
     * 
     * <pre>
     * 功能描述：删除模版
     * 使用示范：
     *
     * @param templateId SurveyTemplate主键ID
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除模版")
    @RequestMapping(value = "/{templateId}/remove", method = RequestMethod.DELETE)
    public ResponseBean remove(@PathVariable("templateId") Long templateId) {
        return new ResponseBean(surveyTemplateService.deleteSurveyTemplateById(templateId));
    }
}
