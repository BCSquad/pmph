package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.controller.bean.ResponseBean;

import java.util.List;

public interface ProductTypeService {


    /**
     * 分页查询 产品分类
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult getTypeList(PageParameter<ProductType> pageParameter, String sessionId);

    /**
     * 根据id删除分类
     * @param productType
     * @return
     */
    ResponseBean deleteTypeById(ProductType productType,String sessionId);
}
