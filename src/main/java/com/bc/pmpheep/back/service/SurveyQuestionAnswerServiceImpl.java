package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyQuestionAnswerDao;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.JsonUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
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
    public Integer batchAddSurveyQuestionAnswer(List<SurveyQuestionAnswer> surveyQuestionAnswers) {
        if (CollectionUtil.isEmpty(surveyQuestionAnswers)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionAnswerDao.batchAddSurveyQuestionAnswer(surveyQuestionAnswers);
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
    public SurveyQuestionAnswer getSurveyQuestionAnswerById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyQuestionAnswerDao.getSurveyQuestionAnswerById(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Integer addUserToAnswer(String answerJosn, String sessionId)
    throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.isEmpty(answerJosn)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "未选择问题选项");
        }
        // json字符串转List对象集合
        List<SurveyQuestionAnswer> surveyQuestionAnswerList =
        new JsonUtil().getArrayListObjectFromStr(SurveyQuestionAnswer.class, answerJosn);
        if (CollectionUtil.isEmpty(surveyQuestionAnswerList)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (SurveyQuestionAnswer surveyQuestionAnswer : surveyQuestionAnswerList) { // 设置用户
            surveyQuestionAnswer.setUserId(pmphUser.getId());
        }
        Integer count = 0;
        count = this.batchAddSurveyQuestionAnswer(surveyQuestionAnswerList);
        return count;
    }
}
