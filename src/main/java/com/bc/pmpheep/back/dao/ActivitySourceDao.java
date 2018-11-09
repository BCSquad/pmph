package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.ActivitySourceChain;
import com.bc.pmpheep.back.po.ActivityVideo;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActivitySourceDao {
    void addSource(ActivitySource activitySource);
    void updateSource(ActivitySource activitySource);
    List<ActivitySourceVO> listActivitySource(PageParameter<ActivitySourceVO> pageParameter);
    void addActivitySourceChain(ActivitySourceChain activitySourceChain);
    Integer deleteSourceById(Long id);
    Integer delChainBySourceId(Map<String,Long> map);
    Integer getMaxSort();
    ActivitySource getSortById(Integer id);
    Integer updateSourceSort(ActivitySource ActivitySource);
    ActivitySource getUpSortById(Integer id);
    ActivitySource getDownSortById(Integer id);
    List<ActivitySourceChain> getSourceChain(Long id);
    Integer delsourceChin(Long id);
    Integer checkedName(String title);
    List<ActivitySourceVO> getChainList(PageParameter<ActivitySourceVO> pageParameter);
    Long getChainSortMax(long activityId);
    Integer updateChainSort(ActivitySourceChain activitySourceChain);
    ActivitySourceChain getUpChianById(Map<String,Long> map);
    ActivitySourceChain getDownChainById(Map<String,Long> map) ;
    Long getSourceChainSortMax(Long activityId);

}
