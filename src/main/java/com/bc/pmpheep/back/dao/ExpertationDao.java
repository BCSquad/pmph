package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.vo.ExpertationVO;

import java.util.List;

public interface ExpertationDao {

    /**
     * 查找临床决策申报列表 总数
     * @param pageParameter
     * @return
     */
    int queryExpertationCount(PageParameter<ExpertationVO> pageParameter);

    /**
     * 查找临床决策申报列表
     * @param pageParameter
     * @return
     */
    List<ExpertationVO> queryExpertation(PageParameter<ExpertationVO> pageParameter);

}
