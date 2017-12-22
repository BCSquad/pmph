package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.SurveyTarget;

/**
 * SurveyTarget发起问卷关联实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface SurveyTargetDao {

    /**
     * 新增一个SurveyTarget
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     * @param SurveyTarget 实体对象
     * @return 影响行数
     */
    Integer addSurveyTarget(SurveyTarget surveyTarget);

    /**
     * 删除SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     * @param SurveyTarget
     * @return 影响行数
     */
    Integer deleteSurveyTargetById(Long id);

    /**
     * 更新一个 SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     * @param SurveyTarget
     * @return 影响行数
     */
    Integer updateSurveyTarget(SurveyTarget surveyTarget);

    /**
     * 查找SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     * @param SurveyTarget
     * @return 影响行数
     */
    SurveyTarget selectSurveyTargetById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：批量插入SurveyTarget
     * 使用示范：
     *
     * @param list SurveyTarget对象集合
     * @return 影响行数
     * </pre>
     */
    Integer batchSaveSurveyTargetByList(List<SurveyTarget> list);
}
