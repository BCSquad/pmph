package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.SurveyType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyType问卷调查类型实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyTypeDao {

    /**
     * 新增一个SurveyType
     * 
     * @author:tyc
     * @date:2017年12月20日上午17:40:37
     * @param SurveyType 实体对象
     * @return 影响行数
     */
    Integer addSurveyType(SurveyType surveyType);

    /**
     * 删除SurveyType通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日上午17:40:37
     * @param SurveyType
     * @return 影响行数
     */
    Integer deleteSurveyTypeById(Long id);

    /**
     * 更新一个 SurveyType通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日上午17:40:37
     * @param SurveyType
     * @return 影响行数
     */
    Integer updateSurveyType(SurveyType surveyType);

    /**
     * 查找SurveyType通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日上午17:40:37
     * @param SurveyType
     * @return 影响行数
     */
    SurveyType getSurveyTypeById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：获取所有问卷调查类型
     * 使用示范：
     *
     * @return SurveyType对象集合
     * </pre>
     */
    List<SurveyType> listSurveyType();

    /**
     * 
     * <pre>
     * 功能描述：按调查对象名称查询
     * 使用示范：
     *
     * @param surveyName 调查对象名称
     * @return SurveyType
     * </pre>
     */
    SurveyType getSurveyTypeByName(String surveyName);
}
