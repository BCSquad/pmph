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

    PageResult<ActivityVO> listActivity(PageParameter<ActivityVO> pageParameter, String sessionId);
    Activity addActivity(Activity activity, String content, String sessionId, HttpServletRequest request);
    Activity addActivity(Activity activity);

    Map<String, Object> getActivitDetailsyById(Long id);

    Integer updateActivity(Activity activity, String content, String sessionId, HttpServletRequest request);
    void setActivityStatus(Activity activity);
    void setActivitySetTop(Activity activity);
}
