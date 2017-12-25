package com.bc.pmpheep.back.controller.survey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.SurveyTemplateService;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

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
@RequestMapping(value = "/survey/template")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SurveyTemplateController {
    @Autowired
    SurveyTemplateService       surveyTemplateService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "问卷模版";

    /**
     * 
     * <pre>
     * 功能描述：问卷模版添加
     * 使用示范：
     *
     * @param surveyId 问卷表Id
     * @param orgIds 学校集合
     * @param request
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增问卷模版")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseBean save(SurveyTemplateVO surveyTemplateVO, HttpServletRequest request) {
        return new ResponseBean(surveyTemplateService.addSurveyTemplateVO(surveyTemplateVO));
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
    @RequestMapping(value = "/question/look", method = RequestMethod.POST)
    public ResponseBean look(Long templateId) {
        return new ResponseBean(
                                surveyTemplateService.getSurveyTemplateQuestionByTemplateId(templateId));
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
