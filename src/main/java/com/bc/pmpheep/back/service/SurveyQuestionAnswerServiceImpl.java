package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionAnswerDao;
import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问题回答业务层接口实现类
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
public class SurveyQuestionAnswerServiceImpl implements SurveyQuestionAnswerService {
	
	@Autowired
	private SurveyQuestionAnswerDao surveyQuestionAnswerDao;

	@Override
	public SurveyQuestionAnswer addSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyQuestionAnswer)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(surveyQuestionAnswer.getUserId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if (ObjectUtil.isNull(surveyQuestionAnswer.getQuestionId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题为空");
		}
		if (ObjectUtil.isNull(surveyQuestionAnswer.getOptionId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题选项为空");
		}
		surveyQuestionAnswerDao.addSurveyQuestionAnswer(surveyQuestionAnswer);
		return surveyQuestionAnswer;
	}

	@Override
	public Integer updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyQuestionAnswer)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionAnswerDao.updateSurveyQuestionAnswer(surveyQuestionAnswer);
	}

	@Override
	public SurveyQuestionAnswer getSurveyQuestionAnswerById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionAnswerDao.getSurveyQuestionAnswerById(id);
	}

}
