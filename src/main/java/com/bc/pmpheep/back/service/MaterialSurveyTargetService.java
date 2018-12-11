package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyTarget;
import com.bc.pmpheep.back.vo.SurveyTargetVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.io.IOException;
import java.util.List;

/**
 * 
 * <pre>
 * 功能描述：调研表发起调研业务层
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
public interface MaterialSurveyTargetService {

    /**
     * 新增一个SurveyTarget
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:42:11
     *  SurveyTarget 实体对象
     * @return 影响行数
     */
    SurveyTarget addSurveyTarget(SurveyTarget surveyTarget) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：发起调研
     * 使用示范：
     *
     * @param message 系统消息
     *  title 消息标题
     *  surveyId 调研表Id
     *  orgIds 机构id
     *  sessionId
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer batchSaveSurveyTargetByList(Message message, SurveyTargetVO surveyTargetVO,
                                        String sessionId) throws Exception;

    /**
     * 删除SurveyTarget通过主键id
     * 
     * @author:tyc
     * 
     * @date:2017年12月22日下午16:45:02
     * 
     *  SurveyTarget
     * 
     * @return 影响行数
     */
    Integer deleteSurveyTargetById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:48:28
     *  SurveyTarget
     * @return 影响行数
     */
    Integer updateSurveyTarget(SurveyTarget surveyTarget) throws CheckedServiceException;

    /**
     * 查找SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:51:25
     *  SurveyTarget
     * @return 影响行数
     */
    SurveyTarget getSurveyTargetById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按调研ID查询，已经发布的学校
     * 使用示范：
     *
     * @param surveyId 调研ID
     * @return OrgId 集合
     * </pre>
     */
    List<Long> listOrgIdBySurveyId(Long surveyId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：调研表补发消息
     * 使用示范：
     *
    * @param message 消息对象
     * @param title 调研表名称
     * @param surveyId 调研ID
     * @param sessionId
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    Integer reissueSurveyMessage(Message message, String title, Long surveyId, String sessionId)
    throws CheckedServiceException, IOException;
}
