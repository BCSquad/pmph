package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.ActivitySourceChain;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ActivitySourceService {
    ActivitySource addSource(String[] imgFile, ActivitySource activitySource,String sessionId, HttpServletRequest request) throws IOException;
    PageResult<ActivitySourceVO> listSource(PageParameter<ActivitySourceVO> pageParameter, String sessionId);
    void addActivitySourceChain(String activityId, String[] sourceIds);

    Integer deleteSourceByIds(Long id);
}
