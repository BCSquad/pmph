package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionDao;
import com.bc.pmpheep.back.po.SurveyQuestion;
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
public class SurveyQuestionServiceImpl implements SurveyQuestionService{

	@Autowired
	private SurveyQuestionDao surveyQuestionDao;
	
	@Override
	public SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion) throws CheckedServiceException {
		if (ObjectUtil.notNull(surveyQuestion)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.notNull(surveyQuestion.getCategoryId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题分类为空");
		}
		if (StringUtil.notEmpty(surveyQuestion.getTitle())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "题目为空");
		}
		if (ObjectUtil.notNull(surveyQuestion.getType())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题类型为空");
		}
		if (ObjectUtil.notNull(surveyQuestion.getSort())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题序号为空");
		}
		if (ObjectUtil.notNull(surveyQuestion.getIsAnswer())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题是否必答为空");
		}
		Long id = surveyQuestion.getId();
		surveyQuestionDao.addSurveyQuestion(surveyQuestion);
		if (ObjectUtil.notNull(id)) {
			surveyQuestion.setId(id);
		}
		return surveyQuestion;
	}

	@Override
	public Integer updateSurveyQuestion(SurveyQuestion surveyQuestion)
			throws CheckedServiceException {
		if (ObjectUtil.notNull(surveyQuestion)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionDao.updateSurveyQuestion(surveyQuestion);
	}

	@Override
	public SurveyQuestion selectSurveyQuestionById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.notNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionDao.selectSurveyQuestionById(id);
	}

	@Override
	public Integer deleteSurveyQuestionById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.notNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionDao.deleteSurveyQuestionById(id);
	}

}
