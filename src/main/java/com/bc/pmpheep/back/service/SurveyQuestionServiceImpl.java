package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionDao;
import com.bc.pmpheep.back.dao.SurveyQuestionOptionDao;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
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
		surveyQuestionDao.addSurveyQuestion(surveyQuestion);
		Long id = surveyQuestion.getId();
		if (ObjectUtil.notNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "id不能为空");
		}
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

	@Override
	public List<SurveyQuestionListVO> addSurveyQuestionListVOList(List<SurveyQuestionListVO> surveyQuestionListVO) {
		if (ObjectUtil.notNull(surveyQuestionListVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		for (SurveyQuestionListVO SurveyQuestionLists : surveyQuestionListVO) { //遍历获取的集合
			Long categoryId = SurveyQuestionLists.getCategoryId();
			String title = SurveyQuestionLists.getTitle();
			Short type = SurveyQuestionLists.getType();
			Integer sort = SurveyQuestionLists.getSort();
			String direction = SurveyQuestionLists.getDirection();
			Boolean isAnswer = SurveyQuestionLists.getIsAnswer();
			String optionContent = SurveyQuestionLists.getOptionContent();
			Boolean isOther = SurveyQuestionLists.getIsOther();
			String remark = SurveyQuestionLists.getRemark();
			SurveyQuestion surveyQuestion = new SurveyQuestion(); // 问题实体
			surveyQuestion.setCategoryId(categoryId);
			surveyQuestion.setTitle(title);
			surveyQuestion.setType(type);
			surveyQuestion.setSort(sort);
			surveyQuestion.setDirection(direction);
			surveyQuestion.setIsAnswer(isAnswer);
			SurveyQuestion surveyQuestions = addSurveyQuestion(surveyQuestion); // 先保存问题
			Long id = surveyQuestions.getId(); // 获取问题id
			SurveyQuestionOption surveyQuestionOption = new SurveyQuestionOption(); // 问题选项实体
			surveyQuestionOption.setQuestionId(id);
			surveyQuestionOption.setOptionContent(optionContent);
			surveyQuestionOption.setIsOther(isOther);
			surveyQuestionOption.setRemark(remark);
			surveyQuestionOptionDao.addSurveyQuestionOption(surveyQuestionOption); // 再保存问题选项
		}
		return surveyQuestionListVO;
	}

}
