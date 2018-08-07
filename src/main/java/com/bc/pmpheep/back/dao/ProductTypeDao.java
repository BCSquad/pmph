package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.vo.ProductType;

import java.util.List;

public interface ProductTypeDao {

    List<ProductType> getSubjectTypeList(PageParameter<ProductType> pageParameter);

    int getSubjectTypeCount(PageParameter<ProductType> pageParameter);

    List<ProductType> getLeafContentTypeList(PageParameter<ProductType> pageParameter);

    int getLeafContentTypeCount(PageParameter<ProductType> pageParameter);

    int deleteSubjectTypeById(Long id);

    int deleteLeafContentTypeById(Long id);

    void refreshLeafOfContentType();

    int getSubjectTypeExpertationCount(Long id);

    int getContentTypeExpertationCount(Long id);
}
