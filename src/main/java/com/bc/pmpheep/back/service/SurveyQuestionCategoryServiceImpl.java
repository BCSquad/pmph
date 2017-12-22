package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionCategoryDao;
import com.bc.pmpheep.back.po.SurveyQuestionCategory;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查问题分类业务层接口实现类
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
public class SurveyQuestionCategoryServiceImpl implements SurveyQuestionCategoryService {

	@Autowired
	private SurveyQuestionCategoryDao surveyQuestionCategoryDao;
	
	@Override
	public SurveyQuestionCategory addSurveyQuestionCategory(SurveyQuestionCategory surveyQuestionCategory)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyQuestionCategory)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(surveyQuestionCategory.getCategoryName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "分类名称为空");
		}
		surveyQuestionCategoryDao.addSurveyQuestionCategory(surveyQuestionCategory);
		return surveyQuestionCategory;
	}

	@Override
	public Integer deleteSurveyQuestionCategoryById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionCategoryDao.deleteSurveyQuestionCategoryById(id);
	}

	@Override
	public Integer updateSurveyQuestionCategory(SurveyQuestionCategory surveyQuestionCategory)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyQuestionCategory)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionCategoryDao.updateSurveyQuestionCategory(surveyQuestionCategory);
	}

	@Override
	public SurveyQuestionCategory getSurveyQuestionCategoryById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionCategoryDao.getSurveyQuestionCategoryById(id);
	}

}
