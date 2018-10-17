package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyQuestionDao;
import com.bc.pmpheep.back.dao.SurveyQuestionDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
import com.bc.pmpheep.back.vo.SurveyQuestionOptionCategoryVO;
import com.bc.pmpheep.back.vo.SurveyQuestionVO;
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
public class MaterialSurveyQuestionServiceImpl implements MaterialSurveyQuestionService {

    @Autowired
    private MaterialSurveyQuestionDao surveyQuestionDao;
    @Autowired
    private MaterialSurveyQuestionOptionService surveyQuestionOptionService;

    @Override
    public SurveyQuestion addSurveyQuestion(SurveyQuestion surveyQuestion)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyQuestion)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        // if (ObjectUtil.isNull(surveyQuestion.getCategoryId())) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
        // CheckedExceptionResult.NULL_PARAM, "问题分类为空");
        // }
        if (StringUtil.isEmpty(surveyQuestion.getTitle())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "题目为空");
        }
        if (ObjectUtil.isNull(surveyQuestion.getType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题类型为空");
        }
        // if (ObjectUtil.isNull(surveyQuestion.getSort())) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
        // CheckedExceptionResult.NULL_PARAM, "问题序号为空");
        // }
        // if (ObjectUtil.isNull(surveyQuestion.getIsAnswer())) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
        // CheckedExceptionResult.NULL_PARAM, "问题是否必答为空");
        // }
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
    public SurveyQuestion getSurveyQuestionById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionDao.getSurveyQuestionById(id);
    }

    @Override
    public Integer deleteSurveyQuestionById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionDao.deleteSurveyQuestionById(id);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Integer addSurveyQuestionListVOList(String jsonSurveyQuestion)
    throws CheckedServiceException {
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
        int count = 0;
        for (SurveyQuestionListVO SurveyQuestionLists : SurveyQuestionListVO) { // 遍历获取问题的集合
            Long id = SurveyQuestionLists.getId(); // 获取问题id
            if (ObjectUtil.notNull(id)) { // 如果id不为空，则先删除
                deleteSurveyQuestionById(id);
                surveyQuestionOptionService.deleteSurveyQuestionOptionByQuestionId(id);
            }
            SurveyQuestion surveyQuestion =
            new SurveyQuestion(null, SurveyQuestionLists.getCategoryId(),
                               SurveyQuestionLists.getTitle(), SurveyQuestionLists.getType(),
                               SurveyQuestionLists.getSort(), SurveyQuestionLists.getDirection(),
                               SurveyQuestionLists.getIsAnswer()); // 问题实体
            SurveyQuestion surveyQuestions = addSurveyQuestion(surveyQuestion); // 先保存问题
            if (ObjectUtil.isNull(surveyQuestions)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                                  CheckedExceptionResult.NULL_PARAM, "新增数据为空");
            }
            Long newId = surveyQuestions.getId(); // 获取数据库新生成的问题id
            List<SurveyQuestionOption> surveyQuestionOptionList =
            SurveyQuestionLists.getSurveyQuestionOptionList(); // 获取问题选项list
            for (SurveyQuestionOption surveyQuestionOptions : surveyQuestionOptionList) { // 遍历问题选项
                SurveyQuestionOption surveyQuestionOption =
                new SurveyQuestionOption(newId, surveyQuestionOptions.getOptionContent(),
                                         surveyQuestionOptions.getIsOther(),
                                         surveyQuestionOptions.getRemark()); // 问题选项实体
                surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption); // 再保存问题选项
            }
            count++;
        }
        return count;
    }

    @Override
    public PageResult<SurveyQuestionVO> listSurveyQuestion(
    PageParameter<SurveyQuestionVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        PageResult<SurveyQuestionVO> pageResult = new PageResult<SurveyQuestionVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyQuestionVO> surveyQuestionList =
        surveyQuestionDao.listSurveyQuestion(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyQuestionList)) {
            Integer count = surveyQuestionList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyQuestionList);
        }
        return pageResult;
    }

    @Override
    public List<SurveyQuestionOptionCategoryVO> getQuestionOptionByQuestionIdOrCategoryId(
    Long questionId, Long categoryId) throws CheckedServiceException {
        return surveyQuestionDao.getQuestionOptionByQuestionIdOrCategoryId(questionId, categoryId);
    }

    @Override
    public Integer batchUpdateSurveyQuestion(List<SurveyQuestion> surveyQuestions)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(surveyQuestions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionDao.batchUpdateSurveyQuestion(surveyQuestions);
    }

    @Override
    public List<Long> batchInsertSurveyQuestion(List<SurveyQuestion> surveyQuestions)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(surveyQuestions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        List<Long> newIds = surveyQuestionDao.batchInsertSurveyQuestion(surveyQuestions);
        if (CollectionUtil.isEmpty(newIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问题批量新增失败");
        }
        return newIds;
    }

    @Override
    public Integer batchDeleteSurveyQuestionByQuestionIds(List<Long> questionIds)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(questionIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionDao.batchDeleteSurveyQuestionByQuestionIds(questionIds);
    }

}
