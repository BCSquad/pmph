package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.ActivitySourceChain;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitySourceDao {
    void addSource(ActivitySource activitySource);
    void updateSource(ActivitySource activitySource);
    List<ActivitySourceVO> listActivitySource(PageParameter<ActivitySourceVO> pageParameter);
    void addActivitySourceChain(ActivitySourceChain activitySourceChain);
    Integer deleteSourceById(Long id);
}
