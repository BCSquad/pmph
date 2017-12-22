package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyTargetDao;
import com.bc.pmpheep.back.po.SurveyTarget;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查发起问卷业务层实现类
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
public class SurveyTargetServiceImpl implements SurveyTargetService {

	@Autowired
	private SurveyTargetDao surveyTargetDao;
	
	@Override
	public SurveyTarget addSurveyTarget(SurveyTarget surveyTarget)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyTarget)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(surveyTarget.getUserId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "发起人为空");
		}
		if (ObjectUtil.isNull(surveyTarget.getSurveyId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问卷为空");
		}
		if (ObjectUtil.isNull(surveyTarget.getOrgId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "机构为空");
		}
		surveyTargetDao.addSurveyTarget(surveyTarget);
		return surveyTarget;
	}

	@Override
	public Integer deleteSurveyTargetById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyTargetDao.deleteSurveyTargetById(id);
	}

	@Override
	public Integer updateSurveyTarget(SurveyTarget surveyTarget)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyTarget)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyTargetDao.updateSurveyTarget(surveyTarget);
	}

	@Override
	public SurveyTarget getSurveyTargetById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyTargetDao.getSurveyTargetById(id);
	}

}
