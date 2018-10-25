package com.bc.pmpheep.back.dao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.ActivityVideo;
import com.bc.pmpheep.back.po.ActivityVideoChain;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActivityVideoDao {
    List<ActivityVideoVO> listActivityVideo(PageParameter<ActivityVideoVO> pageParameter);

    void addActivityVideo(ActivityVideo activityVideo);
    Integer updateActivityVideo(ActivityVideo ActivityVideo);
    Integer addActivityVideochain(ActivityVideoChain activityVideoChain);
    Integer deleteVideoById(Long id);
}
