package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphUser;
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

    /**
     * 查询临床决策申报详情 带登录校验
     * @param id 申报表主键
     * @param sessionId
     * @return
     */
    ExpertationVO getExpertationById(Long id, String sessionId);

    /**
     * 查询临床决策申报详情 不带登录校验
     * @param expertationId
     * @return
     */
    ExpertationVO getExpertationById(Long expertationId);

    Boolean onlineProgress(Long id, Integer onlineProgress, String returnCause, PmphUser pmphUser);

    Map showTabs(Long productType);


}
