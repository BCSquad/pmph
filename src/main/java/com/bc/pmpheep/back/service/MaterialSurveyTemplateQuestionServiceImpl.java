package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyTemplateQuestionDao;
import com.bc.pmpheep.back.dao.SurveyTemplateQuestionDao;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问题模版关联业务层实现类
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
public class MaterialSurveyTemplateQuestionServiceImpl implements MaterialSurveyTemplateQuestionService {

    @Autowired
    private MaterialSurveyTemplateQuestionDao surveyTemplateQuestionDao;

    @Override
    public SurveyQuestion addSurveyTemplateQuestion(
            SurveyQuestion surveyQuestion) throws CheckedServiceException {
        {
            if (ObjectUtil.isNull(surveyQuestion)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.NULL_PARAM, "参数为空");
            }
            // if (ObjectUtil.isNull(surveyQuestion.getCategoryId())) {
            // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
            // CheckedExceptionResult.NULL_PARAM, "问题分类为空");
            // }
            if (StringUtil.isEmpty(surveyQuestion.getTitle())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.NULL_PARAM, "题目为空");
            }
            if (ObjectUtil.isNull(surveyQuestion.getType())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.NULL_PARAM, "问题类型为空");
            }
            // if (ObjectUtil.isNull(surveyQuestion.getSort())) {
            // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
            // CheckedExceptionResult.NULL_PARAM, "问题序号为空");
            // }
            // if (ObjectUtil.isNull(surveyQuestion.getIsAnswer())) {
            // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
            // CheckedExceptionResult.NULL_PARAM, "问题是否必答为空");
            // }
            surveyTemplateQuestionDao.addSurveyTemplateQuestion(surveyQuestion);
            Long id = surveyQuestion.getId();
            if (ObjectUtil.isNull(id)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                        CheckedExceptionResult.NULL_PARAM, "新增id为空");
            }
            return surveyQuestion;
        }



        /*if (ObjectUtil.isNull(surveyTemplateQuestion)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(surveyTemplateQuestion.getTemplateId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "模版为空");
        }
        if (ObjectUtil.isNull(surveyTemplateQuestion.getQuestionId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题为空");
        }
        surveyTemplateQuestionDao.addSurveyTemplateQuestion(surveyTemplateQuestion);
        return surveyTemplateQuestion;*/
    }

    @Override
    public Integer deleteSurveyTemplateQuestionById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateQuestionDao.deleteSurveyTemplateQuestionById(id);
    }

    @Override
    public Integer updateSurveyTemplateQuestion(SurveyQuestion surveyTemplateQuestion)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTemplateQuestion)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateQuestionDao.updateSurveyTemplateQuestion(surveyTemplateQuestion);
    }

    @Override
    public SurveyQuestion getSurveyTemplateQuestionById(Long id)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateQuestionDao.getSurveyTemplateQuestionById(id);
    }

    @Override
    public List<SurveyQuestion> getSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateQuestionDao.getSurveyTemplateQuestionByTemplateId(templateId);
    }

    @Override
    public Integer deleteSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(templateId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateQuestionDao.deleteSurveyTemplateQuestionByTemplateId(templateId);
    }

    @Override
    public Integer batchInsertSurveyTemplateQuestion(
    List<SurveyQuestion> surveyTemplateQuestions) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(surveyTemplateQuestions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTemplateQuestionDao.batchInsertSurveyTemplateQuestion(surveyTemplateQuestions);
    }

}
