package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyTemplateDao;
import com.bc.pmpheep.back.dao.MaterialSurveyTemplateQuestionDao;
import com.bc.pmpheep.back.dao.SurveyTemplateDao;
import com.bc.pmpheep.back.dao.SurveyTemplateQuestionDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 功能描述：问卷调查模版业务层接口实现类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
public class MaterialSurveyTemplateServiceImpl implements MaterialSurveyTemplateService {

    @Autowired
    private MaterialSurveyTemplateDao surveyTemplateDao;
    @Autowired
    private MaterialSurveyService             surveyService;
    @Autowired
    private MaterialSurveyTemplateQuestionDao surveyTemplateQuestionDao;
    @Autowired
    MaterialSurveyQuestionService             surveyQuestionService;
    @Autowired
    MaterialSurveyQuestionOptionService       surveyQuestionOptionService;

    @Override
    public SurveyTemplate addSurveyTemplate(SurveyTemplate surveyTemplate)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTemplate)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(surveyTemplate.getTemplateName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版名称为空");
        }
        if (ObjectUtil.isNull(surveyTemplate.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版创建人为空");
        }
        surveyTemplateDao.addSurveyTemplate(surveyTemplate);
        Long id = surveyTemplate.getId();
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "新增失败");
        }
        return surveyTemplate;
    }

    @Override
    public Integer deleteSurveyTemplateById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateDao.deleteSurveyTemplateById(id);
    }

    @Override
    public Integer updateSurveyTemplate(SurveyTemplate surveyTemplate)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTemplate)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateDao.updateSurveyTemplate(surveyTemplate);
    }

    @Override
    public SurveyTemplate getSurveyTemplateById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateDao.getSurveyTemplateById(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public SurveyTemplate addSurveyTemplateVO(String questionAnswerJosn,
    SurveyTemplateVO surveyTemplateVO, String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyTemplateVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(questionAnswerJosn)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题及问题选项为空");
        }
        // json字符串转List对象集合
        List<SurveyQuestionListVO> surveyQuestionListVO;
        try {
            surveyQuestionListVO =
            new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, questionAnswerJosn);
        } catch (Exception e) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.VO_CONVERSION_FAILED,
                                              "json字符串转List对象失败");
        }
        if (CollectionUtil.isEmpty(surveyQuestionListVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        String templateName = surveyTemplateVO.getTemplateName();// 问卷名称
        if (templateName.length() > 50) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷标题不能超过50个字符");
        }
        String intro = surveyTemplateVO.getIntro();// 问卷概述
        if (templateName.length() > 200) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷概述不能超过200个字符");
        }
        if (templateName.length() > 200) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷概述不能超过200个字符");
        }
        Long typeId = surveyTemplateVO.getTypeId();// 问卷调查类型
        Long userId = pmphUser.getId();// 问卷创建人
        SurveyTemplate surveyTemplate =
        addSurveyTemplate(new SurveyTemplate(templateName, intro, typeId, userId)); // 添加模版表
        Long templateId = surveyTemplate.getId(); // 获取模版id
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "新增模版失败");
        }
        Survey survey =
        surveyService.addSurvey(new Survey(templateName, intro, templateId, typeId, userId));// 添加问卷表
        if (ObjectUtil.isNull(survey.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "新增问卷失败");
        }
        // 添加问题及问题选项
        List<Long> newIds = addQuestionAndOption(surveyQuestionListVO);
        // 模版问题中间表
        List<SurveyTemplateQuestion> surveyTemplateQuestions =
        new ArrayList<SurveyTemplateQuestion>(newIds.size());
        for (Long questionId : newIds) {
            surveyTemplateQuestions.add(new SurveyTemplateQuestion(templateId, questionId));
        }
        surveyTemplateQuestionDao.batchInsertSurveyTemplateQuestion(surveyTemplateQuestions); // 添加模版问题中间表
        System.out.println(surveyTemplate.toString());
        return surveyTemplate;
    }

    @Override
    public Map<String, Object> getSurveyTemplateQuestionByTemplateId(Long surveyId, Long templateId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版ID为空");
        }
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("survey", surveyService.getSurveyById(surveyId));
        //SurveyTemplate s = surveyTemplateDao.getSurveyTemplateById(templateId);
        resultMap.put("qestionAndOption",
                      surveyTemplateDao.getSurveyTemplateQuestionByTemplateId(templateId));
        return resultMap;
    }

    @Override
    public PageResult<SurveyTemplateListVO> listSurveyTemplateList(
    PageParameter<SurveyTemplateListVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        PageResult<SurveyTemplateListVO> pageResult = new PageResult<SurveyTemplateListVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyTemplateListVO> surveyTemplateList =
        surveyTemplateDao.listSurveyTemplateList(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyTemplateList)) {
            Integer count = surveyTemplateList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyTemplateList);
        }
        return pageResult;
    }

    /**
     * 
     * <pre>
     * 功能描述：添加问题及问题选项
     * 使用示范：
     *
     * @param surveyQuestionListVO
     * @return
     * </pre>
     */
    private List<Long> addQuestionAndOption(List<SurveyQuestionListVO> surveyQuestionListVO) {
        List<Long> questionIds = new ArrayList<Long>(surveyQuestionListVO.size());
        for (SurveyQuestionListVO surveyQuestionLists : surveyQuestionListVO) { // 遍历获取问题的集合
            SurveyQuestion surveyQuestion =
            new SurveyQuestion(surveyQuestionLists.getTitle(), surveyQuestionLists.getType(),
                               surveyQuestionLists.getSort(), surveyQuestionLists.getDirection()); // 问题实体
            SurveyQuestion surveyQuestions =
            surveyQuestionService.addSurveyQuestion(surveyQuestion); // 先保存问题
            if (ObjectUtil.isNull(surveyQuestions)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                                  CheckedExceptionResult.NULL_PARAM, "新增数据为空");
            }
            Long newId = surveyQuestions.getId(); // 获取数据库新生成的问题id
            questionIds.add(newId);
            if (Const.SURVEY_QUESTION_TYPE_1 == surveyQuestionLists.getType()
                || Const.SURVEY_QUESTION_TYPE_2 == surveyQuestionLists.getType()) {
                List<SurveyQuestionOption> surveyQuestionOptionList =
                surveyQuestionLists.getSurveyQuestionOptionList(); // 获取问题选项list
                for (SurveyQuestionOption surveyQuestionOptions : surveyQuestionOptionList) { // 遍历问题选项
                    SurveyQuestionOption surveyQuestionOption =
                    new SurveyQuestionOption(newId, surveyQuestionOptions.getOptionContent(),
                                             surveyQuestionOptions.getIsOther(),
                                             surveyQuestionOptions.getRemark()); // 问题选项实体
                    surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption); // 再保存问题选项
                }
            }
        }
        return questionIds;
    }
}
