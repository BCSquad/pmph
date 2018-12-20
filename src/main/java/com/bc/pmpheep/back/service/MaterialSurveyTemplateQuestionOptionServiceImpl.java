package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyQuestionOptionDao;
import com.bc.pmpheep.back.dao.MaterialSurveyTemplateQuestionOptionDao;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
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
 * SurveyQuestionService接口实现
 * 
 * @author tyc
 * 
 */
@Service
public class MaterialSurveyTemplateQuestionOptionServiceImpl implements MaterialSurveyTemplateQuestionOptionService {

    @Autowired
    private MaterialSurveyTemplateQuestionOptionDao surveyQuestionOptionDao;

    @Override
    public SurveyQuestionOption addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyQuestionOption)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(surveyQuestionOption.getQuestionId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题对应为空");
        }
        if (StringUtil.isEmpty(surveyQuestionOption.getOptionContent())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "选项内容为空");
        }
        // if (ObjectUtil.isNull(surveyQuestionOption.getIsOther())) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
        // CheckedExceptionResult.NULL_PARAM, "是否有其他为空");
        // }
        surveyQuestionOptionDao.addSurveyQuestionOption(surveyQuestionOption);
        return surveyQuestionOption;
    }

    @Override
    public Integer updateSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyQuestionOption)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionOptionDao.updateSurveyQuestionOption(surveyQuestionOption);
    }

    @Override
    public SurveyQuestionOption getSurveyQuestionOptionById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionOptionDao.getSurveyQuestionOptionById(id);
    }

    @Override
    public Integer deleteSurveyQuestionOptionByQuestionId(Long questionId) {
        if (ObjectUtil.isNull(questionId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionOptionDao.deleteSurveyQuestionOptionByQuestionId(questionId);
    }

    @Override
    public Integer batchUpdateSurveyOption(List<SurveyQuestionOption> surveyQuestionOptions)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(surveyQuestionOptions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionOptionDao.batchUpdateSurveyOption(surveyQuestionOptions);
    }

    @Override
    public Integer batchInsertSurveyQuestionOption(List<SurveyQuestionOption> surveyQuestionOptions)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(surveyQuestionOptions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionOptionDao.batchInsertSurveyQuestionOption(surveyQuestionOptions);
    }

    @Override
    public Integer batchDeleteSurveyQuestionOptionByQuestionIds(List<Long> questionIds)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(questionIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionOptionDao.batchDeleteSurveyQuestionOptionByQuestionIds(questionIds);
    }

}
