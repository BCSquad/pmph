package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.ProductType;

import java.util.List;

public interface ProductTypeService {


    PageResult getTypeList(PageParameter<ProductType> pageParameter, String sessionId);
}
