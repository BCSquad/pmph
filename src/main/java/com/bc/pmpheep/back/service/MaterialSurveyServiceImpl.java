package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.MaterialSurveyCountAnswerVO;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mchange.lang.LongUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问卷业务层接口实现类
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
public class MaterialSurveyServiceImpl implements MaterialSurveyService {

    @Autowired
    MaterialSurveyDao                     surveyDao;
    @Autowired
    MaterialSurveyTemplateService         surveyTemplateService;
    @Autowired
    MaterialSurveyQuestionService         surveyQuestionService;
    @Autowired
    MaterialSurveyQuestionOptionService   surveyQuestionOptionService;
    @Autowired
    MaterialSurveyTemplateQuestionService surveyTemplateQuestionService;

    @Override
    public SurveyVO addSurvey(SurveyVO survey) throws CheckedServiceException {
        if (ObjectUtil.isNull(survey)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(survey.getTitle())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷标题为空");
        }
        if (ObjectUtil.isNull(survey.getTypeId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷调查类型为空");
        }
        if (ObjectUtil.isNull(survey.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷创建人为空");
        }
        surveyDao.addSurvey(survey);
        return survey;
    }

    @Override
    public SurveyVO addSurvey(SurveyVO survey, String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(survey)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        survey.setUserId(pmphUser.getId());
        return this.addSurvey(survey);
    }

    @Override
    public Integer updateSurvey(Survey survey) throws CheckedServiceException {
        if (ObjectUtil.isNull(survey)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyDao.updateSurvey(survey);
    }

    @Override
    public Survey getSurveyById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyDao.getSurveyById(id);
    }

    @Override
    public PageResult<SurveyVO> listSurvey(PageParameter<SurveyVO> pageParameter)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        String startDate = pageParameter.getParameter().getStartTime();
        if (StringUtil.notEmpty(startDate)) {
            pageParameter.getParameter().setStartTime(startDate + " 00:00:00");
        }
        String endDate = pageParameter.getParameter().getEndTime();
        if (StringUtil.notEmpty(endDate)) {
            pageParameter.getParameter().setEndTime(endDate + " 23:59:59");
        }
        PageResult<SurveyVO> pageResult = new PageResult<SurveyVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyVO> surveyList = surveyDao.listSurvey(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyList)) {
            Integer count = surveyList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyList);
        }
        return pageResult;
    }

    @Override
    public Integer deleteSurveyById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷id为空");
        }
        return surveyDao.deleteSurveyById(id);
    }

    @Override
    public SurveyVO getSurveyAndSurveyTypeById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷id为空");

        }
        return surveyDao.getSurveyAndSurveyTypeById(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Integer updateSurveyAndTemplate(String questionAnswerJosn, SurveyVO surveyVO)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(questionAnswerJosn)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题为空");
        }
        if (ObjectUtil.isNull(surveyVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "对象为空");
        }
        if (ObjectUtil.isNull(surveyVO.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID为空");
        }
        if (ObjectUtil.isNull(surveyVO.getTemplateId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版ID为空");
        }
        // json字符串转List对象集合
        List<SurveyQuestionListVO> SurveyQuestionListVO =
        new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, questionAnswerJosn);
        if (CollectionUtil.isEmpty(SurveyQuestionListVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Long surveyId = surveyVO.getId();// 问卷ID
        String title = surveyVO.getTemplateName();// 问卷名称
        String intro = surveyVO.getIntro();// 问卷概述
        Long typeId = surveyVO.getTypeId();// 问卷调查对象
        Long templateId = surveyVO.getTemplateId();// 模版ID
        Integer count = 0;
        count = this.updateSurvey(new Survey(surveyId, title, intro, typeId));// 更新问卷表
        surveyTemplateService.updateSurveyTemplate(new SurveyTemplate(templateId, title, intro,
                                                                      typeId));// 更新模版表

        // 查询模版下的所有问题
        List<SurveyQuestion> lists =
        surveyTemplateQuestionService.getSurveyTemplateQuestionByTemplateId(templateId);
        List<Long> questionIdList = new ArrayList<Long>(lists.size());
        for (SurveyQuestion surveyTemplateQuestion : lists) {
            questionIdList.add(surveyTemplateQuestion.getId());
        }
        // 删除模版问题中间表下模版对应的所有问题
        surveyTemplateQuestionService.deleteSurveyTemplateQuestionByTemplateId(templateId);
        // 删除问题表
        surveyQuestionService.batchDeleteSurveyQuestionByQuestionIds(questionIdList);
        // 删除问题选项表
        surveyQuestionOptionService.batchDeleteSurveyQuestionOptionByQuestionIds(questionIdList);
        // 重新添加问题
        List<Long> newIds = this.addQuestionAndOption(SurveyQuestionListVO);
        // 模版问题中间表
        List<SurveyQuestion> surveyTemplateQuestions =
        new ArrayList<SurveyQuestion>(newIds.size());
        /*for (Long questionId : newIds) {
            surveyTemplateQuestions.add(new SurveyQuestion(templateId, questionId));
        }*/
        // 重新添加模版问题中间表
        count =
        surveyTemplateQuestionService.batchInsertSurveyTemplateQuestion(surveyTemplateQuestions);
        return count;
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

    @Override
    public PageResult<OrgVO> listSendOrgBySurveyId(PageParameter<OrgVO> pageParameter)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        PageResult<OrgVO> pageResult = new PageResult<OrgVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<OrgVO> orgList = surveyDao.listSendOrgBySurveyId(pageParameter);
        if (CollectionUtil.isNotEmpty(orgList)) {
            Integer count = orgList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(orgList);
        }
        return pageResult;
    }

    @Override
    public SurveyVO addSurvey(String questionAnswerJosn, String del_question, String del_question_option, SurveyVO surveyVO, String sessionId,Boolean tempReCreat) {
        //若新增为模板
        if(tempReCreat){
            MaterialSurveyTemplate surveyTemplateVO = new MaterialSurveyTemplate();
            surveyTemplateVO.setId(null);
            surveyTemplateVO.setIntro(surveyVO.getIntro());
            surveyTemplateVO.setTemplateName(surveyVO.getTemplateName());
            surveyTemplateVO.setTypeId(surveyVO.getTypeId());
            surveyTemplateVO.setPreVersionMaterialId(surveyVO.getPreVersionMaterialId());
            surveyTemplateVO.setPreVersionMaterialRound(surveyVO.getPreVersionMaterialRound());
            //去掉json字符串中的id属性，考虑了前逗号，后逗号，后]，后}
            String questionAnswerJosn_temp = questionAnswerJosn.replaceAll(",?\\s*?\"id\"\\s*?:.*?(?=[,\\]\\}])\\s*?,?","");
            surveyTemplateService.addSurveyTemplateVO(questionAnswerJosn_temp,"[]","[]",surveyTemplateVO,sessionId);
        }

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);

        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(questionAnswerJosn)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题及问题选项为空");
        }

        // json字符串转List对象集合
        List<SurveyQuestionListVO> surveyQuestionListVO;
        List<SurveyQuestionListVO> delQuestionList;
        List<SurveyQuestionOption> delQuestionOption;
        try {
            surveyQuestionListVO =
                    new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, questionAnswerJosn);
            delQuestionList =
                    new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, del_question);
            delQuestionOption =
                    new JsonUtil().getArrayListObjectFromStr(SurveyQuestionOption.class, del_question_option);
        } catch (Exception e) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.VO_CONVERSION_FAILED,
                    "json字符串转List对象失败");
        }
        if (CollectionUtil.isEmpty(surveyQuestionListVO)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题参数为空");
        }
        String templateName = surveyVO.getTitle();// 调研表名称
        if (templateName.length() > 50) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "调研表标题不能超过50个字符");
        }
        String intro = surveyVO.getIntro();// 调研表概述
        if (intro.length() > 200) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "调研表概述不能超过200个字符");
        }
        Long typeId = surveyVO.getTypeId();// 调研表类型
        if (ObjectUtil.isNull(typeId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "调研对象未选择");
        }
        Long userId = pmphUser.getId();// 调研表创建人
        surveyVO.setUserId(userId);

        surveyDao.addSurvey(surveyVO); // 添加调研表

        Long newId = surveyVO.getId(); // 获取模版id
        if (ObjectUtil.isNull(newId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "新增调研表失败");
        }

        // 添加问题及问题选项
        List<Long> newIds = addQuestionAndOption(surveyQuestionListVO,delQuestionList,delQuestionOption,newId);

        return surveyVO;
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
    private List<Long> addQuestionAndOption(List<SurveyQuestionListVO> surveyQuestionListVO,
                                            List<SurveyQuestionListVO> delQuestionList,
                                            List<SurveyQuestionOption> delQuestionOption,Long templateId) {
        List<Long> questionIds = new ArrayList<Long>(surveyQuestionListVO.size());

        if(!CollectionUtil.isEmpty(delQuestionList)){
            List<SurveyQuestion> delQuestionSurveyList = new ArrayList<SurveyQuestion>(delQuestionList.size());
            for (SurveyQuestionListVO delQuestion:delQuestionList) {
                delQuestionSurveyList.add(new SurveyQuestion(delQuestion.getId(),
                        delQuestion.getTitle(),
                        delQuestion.getDeleted(),
                        delQuestion.getType(),
                        delQuestion.getDirection(),
                        delQuestion.getSort()!=null?delQuestion.getSort():999,
                        templateId
                ));
            }
            //TODO 1
            surveyQuestionService.batchInsertSurveyQuestion(delQuestionSurveyList);
        }

        if(!CollectionUtil.isEmpty(delQuestionOption)){
            for (SurveyQuestionOption delOption : delQuestionOption) {
                delOption.setDeleted(true);
                delOption.setQuestionId(0L);
            }
            surveyQuestionOptionService.batchInsertSurveyQuestionOption(delQuestionOption);
        }

        for (SurveyQuestionListVO surveyQuestionLists : surveyQuestionListVO) { // 遍历获取问题的集合
            SurveyQuestion surveyQuestion =
                    new SurveyQuestion(surveyQuestionLists.getId(),surveyQuestionLists.getCategoryId(),surveyQuestionLists.getTitle(), surveyQuestionLists.getType(),
                            surveyQuestionLists.getSort(), surveyQuestionLists.getDirection(),surveyQuestionLists.getIsAnswer()); // 问题实体
            surveyQuestion.setSurveyId(templateId);
            //TODO 2
            SurveyQuestion surveyQuestion_saved =
                    surveyQuestionService.addSurveyQuestion(surveyQuestion); // 先保存问题
            if (ObjectUtil.isNull(surveyQuestion_saved)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.NULL_PARAM, "新增数据为空");
            }
            Long newId = surveyQuestion_saved.getId(); // 获取数据库新生成的问题id
            questionIds.add(newId);
            if (Const.SURVEY_QUESTION_TYPE_1 == surveyQuestionLists.getType()
                    || Const.SURVEY_QUESTION_TYPE_2 == surveyQuestionLists.getType()) {
                List<SurveyQuestionOption> surveyQuestionOptionList =
                        surveyQuestionLists.getSurveyQuestionOptionList(); // 获取问题选项list
                for (SurveyQuestionOption surveyQuestionOptions : surveyQuestionOptionList) { // 遍历问题选项
                    surveyQuestionOptions.setQuestionId(newId);
                    surveyQuestionOptions.setDeleted(false);
                    //surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOptions); // 再保存问题选项
                }
                surveyQuestionOptionService.batchInsertSurveyQuestionOption(surveyQuestionOptionList);
            }
        }
        return questionIds;
    }

    @Override
    public Map<String, Object> getSurveyAndQuestionById(Long id) {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "调研表ID为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("survey", surveyDao.getSurveyById(id));

        resultMap.put("qestionAndOption",
                surveyDao.getSurveyQuestionBySurveyId(id));
        return resultMap;
    }

    @Override
    public PageResult<MaterialSurveyCountAnswerVO> toAnswerList(PageParameter<MaterialSurveyCountAnswerVO> pageParameter) {

        if(ObjectUtil.isNull(pageParameter.getParameter())){
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,CheckedExceptionResult.NULL_PARAM,"参数为空");
        }
        if(ObjectUtil.isNull(pageParameter.getParameter().getSurveyId())){
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,CheckedExceptionResult.NULL_PARAM,"调研表ID为空");
        }

        PageResult<MaterialSurveyCountAnswerVO> pageResult = new PageResult<MaterialSurveyCountAnswerVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);

        List<MaterialSurveyCountAnswerVO> result = surveyDao.toAnswerList(pageParameter);
        Integer count = surveyDao.toAnswerListCount(pageParameter);
        if(CollectionUtil.isNotEmpty(result)){
            pageResult.setRows(result);
            pageResult.setTotal(count);
        }

        return pageResult;
    }
}
