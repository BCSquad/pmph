package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyTypeDao;
import com.bc.pmpheep.back.dao.SurveyTypeDao;
import com.bc.pmpheep.back.po.SurveyType;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <pre>
 * 功能描述：问卷调查类型业务层接口实现类
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
public class MaterialSurveyTypeServiceImpl implements MaterialSurveyTypeService {

    @Autowired
    private MaterialSurveyTypeDao surveyTypeDao;

    @Override
    public SurveyType addSurveyType(SurveyType surveyType) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(surveyType.getSurveyName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷调查类型名称为空");
        }
        SurveyType st = this.getSurveyTypeByName(surveyType.getSurveyName());
        if (ObjectUtil.notNull(st)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "对象名称已存在");
        }
        surveyTypeDao.addSurveyType(surveyType);
        return surveyType;
    }

    @Override
    public Integer deleteSurveyTypeById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTypeDao.deleteSurveyTypeById(id);
    }

    @Override
    public Integer updateSurveyType(SurveyType surveyType) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTypeDao.updateSurveyType(surveyType);
    }

    @Override
    public SurveyType getSurveyTypeById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTypeDao.getSurveyTypeById(id);
    }

    @Override
    public List<SurveyType> listSurveyType() throws CheckedServiceException {
        return surveyTypeDao.listSurveyType();
    }

    @Override
    public SurveyType getSurveyTypeByName(String surveyName) throws CheckedServiceException {
        if (StringUtil.isEmpty(surveyName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTypeDao.getSurveyTypeByName(surveyName);
    }
}
