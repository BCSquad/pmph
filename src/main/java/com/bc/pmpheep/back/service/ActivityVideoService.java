package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.ActivitySourceChain;
import com.bc.pmpheep.back.po.ActivityVideo;
import com.bc.pmpheep.back.po.ActivityVideoChain;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    Integer addActivityVideo(Long activityId,String sessionId, ActivityVideo activityVideo, MultipartFile cover) throws IOException;

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

    Integer updateSort(Integer id,PageParameter<ActivityVideoVO> pageParameter,String type);

    public List<ActivityVideoChain> getVideoChain(Long id);
    public Integer checkedName(String title);

    public PageResult<ActivityVideoVO> getChainList(PageParameter<ActivityVideoVO> pageParameter, String sessionId);

    Integer delChainVideoByid(Long activityId,Long activityVideoId);

    public Integer updateChainSort(ActivityVideoChain activityVideoChain);
    public ActivityVideoChain getUpChainById(Map<String,Long> map);
    public  ActivityVideoChain getDownChainnById(Map<String,Long> map);
}
