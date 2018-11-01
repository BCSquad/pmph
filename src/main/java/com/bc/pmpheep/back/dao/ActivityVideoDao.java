package com.bc.pmpheep.back.dao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.ActivitySourceChain;
import com.bc.pmpheep.back.po.ActivityVideo;
import com.bc.pmpheep.back.po.ActivityVideoChain;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ActivityVideoDao {
    List<ActivityVideoVO> listActivityVideo(PageParameter<ActivityVideoVO> pageParameter);

    void addActivityVideo(ActivityVideo activityVideo);
    Integer updateActivityVideo(ActivityVideo ActivityVideo);
    Integer addActivityVideochain(ActivityVideoChain activityVideoChain);
    Integer deleteVideoById(Long id);
    Integer delChainByVideoId(Map<String,Long> map);
    Integer getMaxSort();
    ActivityVideo getSortById(Integer id);
    Integer updateVideoSort(ActivityVideo activityVideo);
    ActivityVideo getUpSortById(Integer id);
    ActivityVideo getDownSortById(Integer id);
    List<ActivityVideoChain> getVideoChain(Long id);
    Integer delVideoChain(Long id);
    Integer checkedName(String activityName);
    List<ActivityVideoVO> getChainList(PageParameter<ActivityVideoVO> pageParameter);
}
