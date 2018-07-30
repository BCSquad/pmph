package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

import java.util.Map;

public interface ExpertationService {


    /**
     * 查找临床决策申报列表
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult<ExpertationVO> list4Audit(PageParameter<ExpertationVO> pageParameter,String sessionId);

    /**
     * 查询 临床决策申报-结果统计
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult getCountListGroupByType(PageParameter<Map<String, Object>> pageParameter, String sessionId);
}
