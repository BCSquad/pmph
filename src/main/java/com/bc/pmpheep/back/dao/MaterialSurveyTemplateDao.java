package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.MaterialSurveyTemplate;
import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.vo.SurveyQuestionOptionCategoryVO;
import com.bc.pmpheep.back.vo.SurveyTemplateGetVO;
import com.bc.pmpheep.back.vo.SurveyTemplateListVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyTemplate模版实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyTemplateDao {

    /**
     * 新增一个SurveyTemplate
     *
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate 实体对象
     * @return 影响行数
     */
    Integer addSurveyTemplate(MaterialSurveyTemplate surveyTemplate) throws CheckedServiceException;

    /**
     * 逻辑删除SurveyTemplate通过主键id
     *
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer deleteSurveyTemplateById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyTemplate通过主键id
     *
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    Integer updateSurveyTemplate(SurveyTemplate surveyTemplate) throws CheckedServiceException;

    /**
     * 查找SurveyTemplate通过主键id
     *
     * @author:tyc
     * @date:2017年12月20日下午17:03:01
     * @param SurveyTemplate
     * @return 影响行数
     */
    SurveyTemplate getSurveyTemplateById(Long id) throws CheckedServiceException;

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
    List<SurveyQuestionOptionCategoryVO> getSurveyTemplateQuestionByTemplateId(
            @Param("templateId") Long templateId);

    /**
     * 根据模版id获取问卷表标题和简介
     *
     * @author:tyc
     * @date:2017年12月22日上午09:34:40
     * @param
     * @return 影响行数
     */
    SurveyTemplateGetVO getSurveyTemplateGetVOById(Long id);

    /**
     * 模版表分页列表（同时查询分页数据和总条数）
     *
     * @author:tyc
     * @date:2017年12月25日下午16:18:46
     * @param pageParameter
     * @return
     */
    List<SurveyTemplateListVO> listSurveyTemplateList(
            PageParameter<SurveyTemplateListVO> pageParameter);
}
