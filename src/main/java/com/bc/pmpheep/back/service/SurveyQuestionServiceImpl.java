package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionDao;
import com.bc.pmpheep.back.dao.SurveyQuestionOptionDao;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.JsonUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
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
	@Autowired
	private SurveyQuestionOptionDao surveyQuestionOptionDao;
	
	@Override
	public SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion) throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyQuestion)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if (ObjectUtil.isNull(surveyQuestion.getCategoryId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题分类为空");
		}
		if (StringUtil.isEmpty(surveyQuestion.getTitle())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "题目为空");
		}
		if (ObjectUtil.isNull(surveyQuestion.getType())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题类型为空");
		}
		if (ObjectUtil.isNull(surveyQuestion.getSort())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题序号为空");
		}
		if (ObjectUtil.isNull(surveyQuestion.getIsAnswer())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "问题是否必答为空");
		}
		surveyQuestionDao.addSurveyQuestion(surveyQuestion);
		Long id = surveyQuestion.getId();
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "新增id为空");
		}
		return surveyQuestion;
	}

	@Override
	public Integer updateSurveyQuestion(SurveyQuestion surveyQuestion)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(surveyQuestion)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionDao.updateSurveyQuestion(surveyQuestion);
	}

	@Override
	public SurveyQuestion getSurveyQuestionById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionDao.getSurveyQuestionById(id);
	}

	@Override
	public Integer deleteSurveyQuestionById(Long id)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return surveyQuestionDao.deleteSurveyQuestionById(id);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer addSurveyQuestionListVOList(String jsonSurveyQuestion) throws CheckedServiceException {
		if (ObjectUtil.isNull(jsonSurveyQuestion)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		// json字符串转List对象集合
		List<SurveyQuestionListVO> SurveyQuestionListVO = 
				new JsonUtil().getArrayListObjectFromStr(SurveyQuestionListVO.class, jsonSurveyQuestion);
		if (CollectionUtil.isEmpty(SurveyQuestionListVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		for (SurveyQuestionListVO SurveyQuestionLists : SurveyQuestionListVO) { //遍历获取问题的集合
			Long id = SurveyQuestionLists.getId(); // 获取问题id
			if (ObjectUtil.notNull(id)) { // 如果id不为空，则先删除
				surveyQuestionDao.deleteSurveyQuestionById(id);
				surveyQuestionOptionDao.deleteSurveyQuestionOptionByQuestionId(id);
			}
			SurveyQuestion surveyQuestion = new SurveyQuestion(null, SurveyQuestionLists.getCategoryId(), 
					SurveyQuestionLists.getTitle(), SurveyQuestionLists.getType(), 
					SurveyQuestionLists.getSort(), SurveyQuestionLists.getDirection(), 
					SurveyQuestionLists.getIsAnswer()); // 问题实体
			SurveyQuestion surveyQuestions = addSurveyQuestion(surveyQuestion); // 先保存问题
			Long newId = surveyQuestions.getId(); // 获取数据库新生成的问题id
			List<SurveyQuestionOption> surveyQuestionOptionList = 
					SurveyQuestionLists.getSurveyQuestionOptionList(); // 获取问题选项list
			for (SurveyQuestionOption surveyQuestionOptions : surveyQuestionOptionList) { // 遍历问题选项
				SurveyQuestionOption surveyQuestionOption = new SurveyQuestionOption(newId, 
						surveyQuestionOptions.getOptionContent(), surveyQuestionOptions.getIsOther(), 
						surveyQuestionOptions.getRemark()); // 问题选项实体
				surveyQuestionOptionDao.addSurveyQuestionOption(surveyQuestionOption); // 再保存问题选项
			}
		}
		return SurveyQuestionListVO.size();
	}

}
