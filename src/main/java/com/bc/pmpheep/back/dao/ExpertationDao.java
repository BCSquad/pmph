package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.vo.ExpertationCountnessVO;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.back.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpertationDao {

    /**
     * 查找临床决策申报列表 总数
     * @param pageParameter
     * @return
     */
    int queryExpertationCount(PageParameter<ExpertationVO> pageParameter);

    /**
     * 查找临床决策申报列表
     * @param pageParameter
     * @return
     */
    List<ExpertationVO> queryExpertation(PageParameter<ExpertationVO> pageParameter);

    /**
     * 按内容分类统计 列表
     * @param pageParameter
     * @return
     */
    List<ExpertationCountnessVO> getCountListGroupByContentType(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 按内容分类统计 列表总数
     * @param pageParameter
     * @return
     */
    int getCountListGroupByContentTypeCount(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 按学科分类统计 列表总数
     * @param pageParameter
     * @return
     */
    int getCountListGroupBySubjectTypeCount(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 按学科分类统计 列表
     * @param pageParameter
     * @return
     */
    List<ExpertationCountnessVO> getCountListGroupBySubjectType(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 按专业分类统计  列表
     * @param pageParameter
     * @return
     */
    List<ExpertationCountnessVO> getCountListGroupByProfessionType(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 按专业分类统计总数
     * @param pageParameter
     * @return
     */
    int getCountListGroupByProfessionTypeCount(PageParameter<Map<String, Object>> pageParameter);

    /**
     * 查询申报详情
     * @param id
     * @return
     */
    ExpertationVO getExpertationById(Long id);

    void updateOnlineProgress(@Param("id") Long id,@Param("onlineProgress") Integer onlineProgress,@Param("returnCause") String returnCause);

    List<ProductType> queryProductSubjectTypeListByExpertationId(Long id);

    List<ProductType> queryProductContentTypeListByExpertationId(Long id);

    List<ProductType> queryProductProfessionTypeListByExpertationId(Long id);

    /*
    教育经历
     */
    List<DecEduExp> queryDecEduExp(Long id);

    /*
    工作经历
     */
    List<DecWorkExp> queryDecWorkExp(Long id);

    /*
    学术经历
     */
    List<DecAcade> queryDecAcade(Long id);


    /*
    人卫社教材
     */
    List<DecTextbookPmph> queryDecTextbookPmph(Long id);

    /*
   主编学术专著情况
    */
    List<DecMonograph> queryDecMonograph(Long id);

    /**
     * 主编或参编图书情况
     * @param id
     * @return
     */
    List<DecEditorBook> queryDecEditorBook(Long id);

    /**
     * 结果统计--控制显示tabs
     * @param productType
     * @return
     */
    Map showTabs(Long productType);

    /**
     * 本专业获奖情况
     * @param id
     * @return
     */
    List<DecProfessionAward> queryDecProfessionAward(Long id);

    /**
     * 文章发表情况
     * @param id
     * @return
     */
    List<DecArticlePublished> queryDecArticlePublished(Long id);

    Boolean queryAmIAnAuditor(@Param("user_id") Long user_id,@Param("expertation_id") Long expertation_id);

    /**
     * 获取申报专业
     * @param id
     * @return
     */
    List<ProductProfessionType> queryProfession(Long id);

    /**
     * 获取产品
     * @param expert_type
     * @return
     */
    ProductVO getProductByProductType(Long expert_type);
}
