package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.MaterialSurveyType;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.po.SurveyWordDetailVO;
import com.bc.pmpheep.back.po.SurveyWordMainVO;
import com.bc.pmpheep.back.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Survey问卷实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyDao {

    /**
     * 新增一个Survey
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * @param survey 实体对象
     * @return 影响行数
     */
    Integer addSurvey(SurveyVO survey);

    /**
     * 逻辑删除Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     * Survey
     * @return 影响行数
     */
    Integer deleteSurveyById(Long id);

    /**
     * 更新一个 Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     *  Survey
     * @return 影响行数
     */
    Integer updateSurvey(Survey survey);

    /**
     * 查找Survey通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:55:35
     *  Survey
     * @return 影响行数
     */
    Survey getSurveyById(Long id);

    /**
     * 问卷表分页列表（同时查询分页数据和总条数）
     * 
     * @author:tyc
     * @date:2017年12月25日上午10:28:56
     * @param pageParameter
     * @return
     */
    List<SurveyVO> listSurvey(PageParameter<SurveyVO> pageParameter);

    /**
     * 调研表导出word的主列表
     * @param surveyVO
     * @return
     */
    List<SurveyWordMainVO> wordExMainList(SurveyVO surveyVO);

    List<SurveyWordDetailVO> wordExDetailList();

    /**
     * 
     * <pre>
     * 功能描述：查询问卷和问卷对应的类型
     * 使用示范：
     *
     * @param id 问卷id
     * @return SurveyVO 对象
     * </pre>
     */
    SurveyVO getSurveyAndSurveyTypeById(@Param("id") Long id);

    /**
     * 
     * <pre>
     * 功能描述：根据问卷ID查询问卷已发送对象
     * 使用示范：
     *
     *  surveyId 问卷id
     * @return
     * </pre>
     */
    List<OrgVO> listSendOrgBySurveyId(PageParameter<OrgVO> pageParameter);

    /**
     * 查询问题和选项
     * @param id
     * @return
     */
    List<SurveyQuestionOptionCategoryVO> getSurveyQuestionBySurveyId(@Param("id")Long id);

    /**
     * 查询选项及答案
     * @param paramMap
     * @return
     */
    List<SurveyQuestionOptionCategoryVO> getSurveyResult(Map<String, Object> paramMap);

    /**
     * 查询调研表结果列表
     * @param pageParameter
     * @return
     */
    List<MaterialSurveyCountAnswerVO> toAnswerList(PageParameter<MaterialSurveyCountAnswerVO> pageParameter);

    Integer toAnswerListCount(PageParameter<MaterialSurveyCountAnswerVO> pageParameter);

    /**
     * 物理删除该书籍下所有调研表的中间关联表
     * @param materialId
     * @param textbookId
     * @return
     */
    int deleteSurveyChainByTextbookId(@Param("materialId") Long materialId,@Param("textbookId")  Long textbookId);

    /**
     * 物理删除某调研表的中间关联表
     * @return
     */
    int deleteSurveyChainByMaterialSurveyId(@Param("materialSurveyId")Long materialSurveyId);


    /**
     * 插入书籍和调研表的中间关联
     * @param chain
     * @return
     */
    int insertChain(MaterialSurveyChain chain);

    /**
     * 从模板克隆问题及选项
     * @param templateId
     * @param surveyId
     * @return
     */
    int cloneQuestionAndOptionByTemplateId(@Param("templateId") Long templateId,@Param("surveyId")  Long surveyId);

    /**
     * 获取书籍相关调研
     * @param textbookId
     * @return
     */
    List<SurveyVO> getSurveyByTextbook(@Param("textbookId")Long textbookId);

    List<SurveyVO> getSurveyByMaterial(@Param("materialId")Long materialId);


    SurveyVO getSurveyByMaterialIdAndTemplateId(@Param("materialId")Long materialId, @Param("templateId")Long templateId);


    /**
     * 查询调研表所关联教材下的图书及和图书的关联关系
     * @param paramMap
     * @return
     */
    List<MaterialSurveyChain> chainBookList(Map<String, Object> paramMap);

    /**
     * 批量插入中间表
     * @param checkedTextbookListVO
     * @return
     */
    int insertChainBatch(List<MaterialSurveyChain> checkedTextbookListVO);

    /**
     * 获取调研表（调查对象）分类
     * @return
     */
    List<MaterialSurveyType> getTypeList();


}
