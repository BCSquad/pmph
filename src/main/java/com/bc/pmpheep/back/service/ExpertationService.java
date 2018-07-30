package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.ExpertationVO;

public interface ExpertationService {


    /**
     * 查找临床决策申报列表
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult<ExpertationVO> list4Audit(PageParameter<ExpertationVO> pageParameter,String sessionId);

}
