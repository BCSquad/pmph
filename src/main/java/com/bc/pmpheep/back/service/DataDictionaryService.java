package com.bc.pmpheep.back.service;



import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DataDictionaryItem;
import com.bc.pmpheep.back.po.DataDictionaryType;

/**
 *  数据字典接口
 */
public interface DataDictionaryService {

    /**
     * 获取字典类别
     * @param dataDictionaryType
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<DataDictionaryType> getTypeList(DataDictionaryType dataDictionaryType, Integer pageNumber, Integer pageSize);

    /**
     * 新增或修改
     * @param dataDictionaryType
     * @return
     */
    int typeAdd(DataDictionaryType dataDictionaryType);

    /**
     * 获取数据字典项列表
     * @param dataDictionaryItem
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<DataDictionaryItem> getItemList(DataDictionaryItem dataDictionaryItem, Integer pageNumber, Integer pageSize);

    /**
     * 新增或修改数据字典项 （id为空时新增）
     * @param dataDictionaryItem
     * @return
     */
    int itemAdd(DataDictionaryItem dataDictionaryItem);
}
