package com.bc.pmpheep.back.controller.materialSurvey;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialSurveyTemplate;
import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.service.MaterialSurveyTemplateService;
import com.bc.pmpheep.back.service.SurveyTemplateService;
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * @param request
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增教材调研模版")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean create(@RequestParam("questionAnswerJosn") String questionAnswerJosn,
                               @RequestParam("del_question")String del_question,
                               @RequestParam("del_question_option")String del_question_option,
                               MaterialSurveyTemplate surveyTemplateVO, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(surveyTemplateService.addSurveyTemplateVO(questionAnswerJosn,
                                                                        del_question,
                                                                        del_question_option,
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
    public ResponseBean look(/*@RequestParam("surveyId") Long surveyId,*/
    @RequestParam("templateId") Long templateId) {
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

    @ResponseBody
    @RequestMapping(value = "/switchActive",method = RequestMethod.GET)
    public ResponseBean switchActive(@RequestParam(value = "templateId" ,required =true) Long templateId
            ,@RequestParam(value = "isActive",defaultValue = "true")Boolean isActive){
        SurveyTemplate updateTemplate = new SurveyTemplate();
        updateTemplate.setId(templateId);
        updateTemplate.setActive(isActive);
        return new ResponseBean(surveyTemplateService.updateSurveyTemplate(updateTemplate));
    }


    /**
     * 获取已存在的模板名称
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTemplateName",method = RequestMethod.GET)
    public ResponseBean getTemplateNameList(HttpServletRequest request){
        List<SurveyTemplateListVO> result = surveyTemplateService.getTemplateNameList();
        return new ResponseBean(result);
    }

    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "调研表模板导入Excel文件")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public ResponseBean importExcel(@RequestParam(name = "file")MultipartFile file
            , HttpServletRequest request){
        ResponseBean responseBean = null;
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)){
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                    CheckedExceptionResult.NULL_PARAM, "用户登陆超时，请重新登陆再试");
        }
        HttpSession session = SessionContext.getSession(sessionId);
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        try {
            responseBean = surveyTemplateService.importExcel(file,sessionId);
        } catch (CheckedServiceException e) {
            return new ResponseBean(e);
        }
        return responseBean;
    }
}
