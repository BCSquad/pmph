package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.DataDictionaryItem;
import com.bc.pmpheep.back.po.DataDictionaryType;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
