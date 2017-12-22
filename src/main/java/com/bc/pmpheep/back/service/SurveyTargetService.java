package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查发起问卷业务层
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
public interface SurveyTargetService {

    /**
     * 
     * <pre>
     * 功能描述：批量插入SurveyTarget
     * 使用示范：
     *
     * @param surveyId 问卷表Id
     * @param orgIds 机构id
     * @param sessionId 
     * @return 影响行数
     * </pre>
     */
    Integer batchSaveSurveyTargetByList(Long surveyId, List<Long> orgIds, String sessionId)
    throws CheckedServiceException;

}
