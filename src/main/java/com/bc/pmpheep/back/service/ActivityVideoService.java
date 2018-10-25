package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.ActivityVideo;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ActivityVideoService {
    PageResult<ActivityVideoVO> listVideo(PageParameter<ActivityVideoVO> pageParameter, String sessionId);

    Integer addActivityVideo(String sessionId, ActivityVideo activityVideo, MultipartFile cover) throws IOException;

    Integer addActivityVideoChain(String activityId, String[] ids);

    Integer deleteVideoByIds(Long id);
}
