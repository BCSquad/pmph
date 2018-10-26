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

    /**
     * 获取视频列表
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult<ActivityVideoVO> listVideo(PageParameter<ActivityVideoVO> pageParameter, String sessionId);

    /**
     * 添加活动视频
     * @param sessionId
     * @param activityVideo
     * @param cover
     * @return
     * @throws IOException
     */

    Integer addActivityVideo(String sessionId, ActivityVideo activityVideo, MultipartFile cover) throws IOException;

    /**
     * 活动与活动视频关联
     * @param activityId
     * @param ids
     * @return
     */
    Integer addActivityVideoChain(String activityId, String[] ids);

    /**
     * 删除活动视频
     * @param id
     * @return
     */
    Integer deleteVideoByIds(Long id);
}
