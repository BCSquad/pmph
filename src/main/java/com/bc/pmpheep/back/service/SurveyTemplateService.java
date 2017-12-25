package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.vo.SurveyQuestionOptionCategoryVO;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查模版业务层
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
public interface SurveyTemplateService {
    /**
     * 新增一个SurveyTemplate
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:51:22
     * @param SurveyTemplate 实体对象
     * @return 影响行数
     */
    SurveyTemplate addSurveyTemplate(SurveyTemplate surveyTemplate) throws CheckedServiceException;

    /**
     * 逻辑删除SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:53:06
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer deleteSurveyTemplateById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:57:43
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer updateSurveyTemplate(SurveyTemplate surveyTemplate) throws CheckedServiceException;

    /**
     * 查找SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午17:59:13
     * @param SurveyTemplate
     * @return 影响行数
     */
    SurveyTemplate getSurveyTemplateById(Long id) throws CheckedServiceException;

    /**
     * 添加SurveyTemplate模版
     * 
     * @author:tyc
     * @date:2017年12月24日下午14:23:07
     * @param SurveyTemplateVO
     * @return 影响行数
     */
    Integer addSurveyTemplateVO(SurveyTemplateVO surveyTemplateVO) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据模版Id查询下面的所有问题
     * 使用示范：
     *
     * @param templateId SurveyTemplate主键
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    List<SurveyQuestionOptionCategoryVO> getSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException;
}
