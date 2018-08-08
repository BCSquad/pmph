package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 将输入的excel文件转行成list 返回
     * @param file
     * @param typeType
     * @return
     */
    List<ProductType> importExcel(MultipartFile file, int typeType);

    /**
     * 插入分类树
     * @param list 顶层分类列表，子类列表在其childType列表中
     * @param typeType 1.学科分类 2.内容分类
     */
    void insertProductTypeTree(List<ProductType> list, int typeType);
}
