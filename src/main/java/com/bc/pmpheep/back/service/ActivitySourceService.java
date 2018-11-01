package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.ActivitySourceChain;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ActivitySourceService {
    /**
     * 添加文件资源
     * @param files
     * @param activitySource
     * @param sessionId
     * @param request
     * @return
     * @throws IOException
     */
    ActivitySource addSource(Long activityId,String[] files, ActivitySource activitySource,String sessionId, HttpServletRequest request) throws IOException;

    /**
     * 获取文件资源列表
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult<ActivitySourceVO> listSource(PageParameter<ActivitySourceVO> pageParameter, String sessionId);

    /**
     * 添加活动与文件资源列表关联
     * @param activityId
     * @param sourceIds
     */
    void addActivitySourceChain(String activityId, String[] sourceIds);

    /**
     * 根据id删除文件资源
     * @param id
     * @return
     */

    Integer deleteSourceByIds(Long id);

    Integer updateSort(Integer id,PageParameter<ActivitySourceVO> pageParameter,String type);

    List<ActivitySourceChain> getSourceChain(Long id);

    public Integer checkedName(String sourceName);

    public PageResult<ActivitySourceVO> getChainList(PageParameter<ActivitySourceVO> pageParameter, String sessionId);
    public Integer delChainSourceById(Long activityId,Long activitySourceId);
}
