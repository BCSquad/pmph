package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.po.SurveyTarget;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyTarget发起调研表关联实体类数据访问层接口
 * 
 * @author tyc
 */
@Repository
public interface MaterialSurveyTargetDao {

    /**
     * 新增一个SurveyTarget
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     *  SurveyTarget 实体对象
     * @return 影响行数
     */
    Integer addSurveyTarget(SurveyTarget surveyTarget);

    /**
     * 删除SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     *  SurveyTarget
     * @return 影响行数
     */
    Integer deleteSurveyTargetById(Long id);

    /**
     * 更新一个 SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     *  SurveyTarget
     * @return 影响行数
     */
    Integer updateSurveyTarget(SurveyTarget surveyTarget);

    /**
     * 查找SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月20日下午16:58:31
     *  SurveyTarget
     * @return 影响行数
     */
    SurveyTarget getSurveyTargetById(Long id);

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

    /**
     * 根据发起人id查询调研表表
     * 
     * @author:tyc
     * @date:2017年12月26日上午09:47:56
     *  surveyTargetList
     * @return
     */
    List<Survey> getSurveyTargetByUserId(Long userId);

    /**
     * 根据机构id查询调研表表
     * 
     * @author:tyc
     * @date:2017年12月26日上午10:36:30
     * @param orgId
     * @return
     */
    List<Survey> getSurveyTargetByOrgId(Long orgId);

    /**
     * 
     * <pre>
     * 功能描述：按调研表ID查询，已经发布的学校
     * 使用示范：
     *
     * @param surveyId 调研表ID
     * @return orgid 集合
     * </pre>
     */
    List<Long> listOrgIdBySurveyId(Long surveyId);
}
