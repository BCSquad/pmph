package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.vo.SurveyQuestionOptionCategoryVO;

/**
 * SurveyTemplate模版实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyTemplateDao {

    /**
     * 新增一个SurveyTemplate
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate 实体对象
     * @return 影响行数
     */
    Integer addSurveyTemplate(SurveyTemplate surveyTemplate);

    /**
     * 逻辑删除SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer deleteSurveyTemplateById(Long id);

    /**
     * 更新一个 SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer updateSurveyTemplate(SurveyTemplate surveyTemplate);

    /**
     * 查找SurveyTemplate通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    SurveyTemplate selectSurveyTemplateById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：根据模版Id查询下面的所有问题
     * 使用示范：
     *
     * @param templateId SurveyTemplate主键
     * @return
     * </pre>
     */
    List<SurveyQuestionOptionCategoryVO> getSurveyTemplateQuestionByTemplateId(Long templateId);
}
