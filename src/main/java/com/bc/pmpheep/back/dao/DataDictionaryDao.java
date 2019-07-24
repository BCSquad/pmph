package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.DataDictionaryItem;
import com.bc.pmpheep.back.po.DataDictionaryType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataDictionaryDao {

    List<DataDictionaryType> getTypeList(PageParameter<DataDictionaryType> dataDictionaryType);

    List<DataDictionaryType> getTypeListStrict(PageParameter<DataDictionaryType> dataDictionaryType);

    int getTypeListCount(PageParameter<DataDictionaryType> dataDictionaryType);

    int typeAdd(DataDictionaryType dataDictionaryType);



    List<DataDictionaryItem> getItemListStrict(PageParameter<DataDictionaryItem> pageParameter);

    int getItemListCount(PageParameter<DataDictionaryItem> pageParameter);

    List<DataDictionaryItem> getItemList(PageParameter<DataDictionaryItem> pageParameter);

    int itemAdd(DataDictionaryItem dataDictionaryItem);

    /**
     * 获取某分类的数据字典项列表
     * @param type_code
     * @return
     */
    List<Map<String,Object>> getDataDictionaryListByType(String type_code);


    /**
     * 获取某分类的code的name
     * @param type_code
     * @return
     */
    String getDataDictionaryNameByTypeAndCode(Map<String,Object> map);

    /**
     * 获取某分类某项的数据字典项名称
     * @param type_code
     * @param code
     * @return
     */
    String getDataDictionaryItemNameByCode(@Param("type_code") String type_code, @Param("code") String code);
    String getDataDictionaryItemNameByCode2(@Param("type_code") String type_code, @Param("code") String code);

    List<Map<String, Object>> getListByCode(String type_code);

}
