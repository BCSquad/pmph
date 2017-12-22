package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionOptionDao;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * SurveyQuestionService接口实现
 *
 * @author tyc
 *
 */
@Service
public class SurveyQuestionOptionServiceImpl implements SurveyQuestionOptionService{
	
	@Autowired
	private SurveyQuestionOptionDao surveyQuestionOptionDao;

	@Override
	public SurveyQuestionOption addSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption) 
			throws CheckedServiceException {
		if (ObjectUtil.notNull(surveyQuestionOption)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.notNull(surveyQuestionOption.getQuestionId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题对应为空");
		}
		if (StringUtil.notEmpty(surveyQuestionOption.getOptionContent())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "选项内容为空");
		}
		if (ObjectUtil.notNull(surveyQuestionOption.getIsOther())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "是否有其他为空");
		}
		surveyQuestionOptionDao.addSurveyQuestionOption(surveyQuestionOption);
		return surveyQuestionOption;
	}

	@Override
	public Integer updateSurveyQuestionOption(SurveyQuestionOption surveyQuestionOption)
			throws CheckedServiceException {
		if (ObjectUtil.notNull(surveyQuestionOption)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionOptionDao.updateSurveyQuestionOption(surveyQuestionOption);
	}

	@Override
	public SurveyQuestionOption getSurveyQuestionOptionById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.notNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionOptionDao.getSurveyQuestionOptionById(id);
	}
	
}
