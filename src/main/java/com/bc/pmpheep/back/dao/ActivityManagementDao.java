package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Activity;
import com.bc.pmpheep.back.vo.ActivityVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityManagementDao {
    List<ActivityVO> listActivity(PageParameter<ActivityVO> pageParameter);
    void addActivity(Activity activity);
    Activity getActivityById(Long id);
    Integer updateActivity(Activity activity);
    void updateActivityStatusById(Activity activity);
    void updateActivitySetTopById(Activity activity);
}
