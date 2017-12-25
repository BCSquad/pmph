package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
public class SurveyServiceImpl implements SurveyService {
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public Survey addSurvey(Survey survey) throws CheckedServiceException {
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

}
