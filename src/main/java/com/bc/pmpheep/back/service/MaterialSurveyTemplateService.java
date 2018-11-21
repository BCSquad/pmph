package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.MaterialSurveyTemplate;
import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.vo.SurveyTemplateListVO;
import com.bc.pmpheep.back.vo.SurveyTemplateVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.util.List;
import java.util.Map;

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
public interface MaterialSurveyTemplateService {
    /**
     * 新增一个SurveyTemplate
     *
     * @author:tyc
     * @date:2017年12月22日下午17:51:22
     * @return 影响行数
     */
    MaterialSurveyTemplate addSurveyTemplate(MaterialSurveyTemplate surveyTemplate) throws CheckedServiceException;

    /**
     * 逻辑删除SurveyTemplate通过主键id
     *
     * @author:tyc
     * @date:2017年12月22日下午17:53:06
     * @return 影响行数
     */
    Integer deleteSurveyTemplateById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyTemplate通过主键id
     *
     * @author:tyc
     * @date:2017年12月22日下午17:57:43
     * @return 影响行数
     */
    Integer updateSurveyTemplate(SurveyTemplate surveyTemplate) throws CheckedServiceException;

    /**
     * 查找SurveyTemplate通过主键id
     *
     * @author:tyc
     * @date:2017年12月22日下午17:59:13
     * @return 影响行数
     */
    SurveyTemplate getSurveyTemplateById(Long id) throws CheckedServiceException;

    /**
     * 添加SurveyTemplate模版
     *
     * @author:tyc
     * @date:2017年12月24日下午14:23:07
     * @return 影响行数
     */
    MaterialSurveyTemplate addSurveyTemplateVO(String questionAnswerJosn,String del_question,
                                               String del_question_option,
                                               MaterialSurveyTemplate surveyTemplateVO, String sessionId) throws CheckedServiceException;

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
    Map<String, Object> getSurveyTemplateQuestionByTemplateId(Long templateId)
    throws CheckedServiceException;

    /**
     * 模版表分页列表（同时查询分页数据和总条数）
     *
     * @author:tyc
     * @date:2017年12月25日下午16:38:12
     * @param pageParameter
     * @return
     * @throws CheckedServiceException
     */
    PageResult<SurveyTemplateListVO> listSurveyTemplateList(
            PageParameter<SurveyTemplateListVO> pageParameter) throws CheckedServiceException;

    /**
     * 获取已存在的模板名称
     * @return
     */
    List<SurveyTemplateListVO> getTemplateNameList();
}
