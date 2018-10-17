package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyQuestionAnswerDao;
import com.bc.pmpheep.back.dao.SurveyQuestionAnswerDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.SurveyQuestionAnswerCountsVO;
import com.bc.pmpheep.back.vo.SurveyQuestionFillVO;
import com.bc.pmpheep.back.vo.SurveyRecoveryVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class MaterialSurveyQuestionAnswerServiceImpl implements MaterialSurveyQuestionAnswerService {

    @Autowired
    private MaterialSurveyQuestionAnswerDao surveyQuestionAnswerDao;
    @Autowired
    MaterialSurveyService                   surveyService;

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

    @Override
    public Integer addUserToAnswer(List<SurveyQuestionAnswer> answerJosn, String sessionId)
    throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (CollectionUtil.isEmpty(answerJosn)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "未选择问题选项");
        }
        // json字符串转List对象集合
        // List<SurveyQuestionAnswer> surveyQuestionAnswerList =
        // new JsonUtil().getArrayListObjectFromStr(SurveyQuestionAnswer.class, answerJosn);
        // if (CollectionUtil.isEmpty(surveyQuestionAnswerList)) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
        // CheckedExceptionResult.NULL_PARAM, "参数为空");
        // }
        for (SurveyQuestionAnswer surveyQuestionAnswer : answerJosn) { // 设置用户
            surveyQuestionAnswer.setUserId(pmphUser.getId());
        }
        return this.batchAddSurveyQuestionAnswer(answerJosn);
    }

    @Override
    public PageResult<SurveyQuestionAnswerCountsVO> listSurveyQuestionAnswer(
    PageParameter<SurveyQuestionAnswerCountsVO> pageParameter) throws CheckedServiceException {
        PageResult<SurveyQuestionAnswerCountsVO> pageResult =
        new PageResult<SurveyQuestionAnswerCountsVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyQuestionAnswerCountsVO> surveyQuestionAnswerList =
        surveyQuestionAnswerDao.listSurveyQuestionAnswer(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyQuestionAnswerList)) {
            Integer count = surveyQuestionAnswerList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyQuestionAnswerList);
        }
        return pageResult;
    }

    @Override
    public Map<String, Object> getSurveyQuestionAnswerCounts(
    SurveyQuestionAnswerCountsVO questionAnswerCountsVO) throws CheckedServiceException {
        Long surveyId = questionAnswerCountsVO.getSurveyId();
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID为空");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("questionOne",
                      surveyQuestionAnswerDao.getSurveyQuestionAnswerCounts(questionAnswerCountsVO));
        resultMap.put("questionMore", this.listFillQuestionCounts(surveyId));
        return resultMap;
    }

    @Override
    public PageResult<SurveyRecoveryVO> listSurveyRecovery(
    PageParameter<SurveyRecoveryVO> pageParameter) throws CheckedServiceException {
        PageResult<SurveyRecoveryVO> pageResult = new PageResult<SurveyRecoveryVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyRecoveryVO> surveyRecoveryList =
        surveyQuestionAnswerDao.listSurveyRecovery(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyRecoveryList)) {
            Integer count = surveyRecoveryList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyRecoveryList);
        }
        return pageResult;
    }

    @Override
    public Map<String, Object> listSurveyQuestionAnswerBySurveyIdAndUserId(Long surveyId,
    Long userId) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID为空");
        }
        if (ObjectUtil.isNull(userId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("Survey", surveyService.getSurveyAndSurveyTypeById(surveyId));
        resultMap.put("SurveyQuestionAnswer",
                      surveyQuestionAnswerDao.listSurveyQuestionAnswerBySurveyId(surveyId, userId));
        return resultMap;
    }

    @Override
    public PageResult<SurveyQuestionFillVO> listFillQuestion(
    PageParameter<SurveyQuestionFillVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Long surveyId = pageParameter.getParameter().getSurveyId();
        Long questionId = pageParameter.getParameter().getQuestionId();
        if (ObjectUtil.isNull(surveyId) || ObjectUtil.isNull(questionId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID或问题ID为空");
        }
        PageResult<SurveyQuestionFillVO> pageResult = new PageResult<SurveyQuestionFillVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SurveyQuestionFillVO> surveyQuestionFillList =
        surveyQuestionAnswerDao.listFillQuestion(pageParameter);
        if (CollectionUtil.isNotEmpty(surveyQuestionFillList)) {
            Integer count = surveyQuestionFillList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(surveyQuestionFillList);
        }
        return pageResult;
    }

    @Override
    public List<SurveyQuestionFillVO> listFillQuestionCounts(Long surveyId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID为空");
        }
        return surveyQuestionAnswerDao.listFillQuestionCounts(surveyId);
    }

    @Override
    public List<Long> getUserIdBySurveyId(Long surveyId) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷ID为空");
        }
        return surveyQuestionAnswerDao.getUserIdBySurveyId(surveyId);
    }

}
