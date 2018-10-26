package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Activity;
import com.bc.pmpheep.back.vo.ActivityVO;
import com.bc.pmpheep.back.vo.CmsContentVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface ActivityManagementService {

    /**
     * 查询列表
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult<ActivityVO> listActivity(PageParameter<ActivityVO> pageParameter, String sessionId);

    /**
     * 添加活动,活动简介
     * @param activity
     * @param content
     * @param sessionId
     * @param request
     * @return
     */
    Activity addActivity(Activity activity, String content, String sessionId, HttpServletRequest request);

    /**
     * 获取活动详情
     * @param id
     * @return
     */
    Map<String, Object> getActivitDetailsyById(Long id);

    /**
     * 更新活动信息,与活动介绍
     * @param activity
     * @param content
     * @param sessionId
     * @param request
     * @return
     */
    Integer updateActivity(Activity activity, String content, String sessionId, HttpServletRequest request);

    /**
     * 更新状态
     * @param activity
     */
    void setActivityStatus(Activity activity);

    /**
     * 置顶
     * @param activity
     */
    void setActivitySetTop(Activity activity);
}
