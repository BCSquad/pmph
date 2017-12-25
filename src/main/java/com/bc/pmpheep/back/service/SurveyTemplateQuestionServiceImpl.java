package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyTemplateQuestionDao;
import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
public class SurveyTemplateQuestionServiceImpl implements SurveyTemplateQuestionService {

	@Autowired
	private SurveyTemplateQuestionDao surveyTemplateQuestionDao;
	
	@Override
	public SurveyTemplateQuestion addSurveyTemplateQuestion(SurveyTemplateQuestion surveyTemplateQuestion) 
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyTemplateQuestion)) {
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
		return surveyTemplateQuestion;
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
	public Integer updateSurveyTemplateQuestion(SurveyTemplateQuestion surveyTemplateQuestion) 
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyTemplateQuestion)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyTemplateQuestionDao.updateSurveyTemplateQuestion(surveyTemplateQuestion);
	}

	@Override
	public SurveyTemplateQuestion getSurveyTemplateQuestionById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyTemplateQuestionDao.getSurveyTemplateQuestionById(id);
	}

}
