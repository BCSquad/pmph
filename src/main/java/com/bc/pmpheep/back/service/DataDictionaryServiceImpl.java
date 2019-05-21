package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.DataDictionaryDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DataDictionaryItem;
import com.bc.pmpheep.back.po.DataDictionaryType;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Alias("com.bc.pmpheep.back.service.DataDictionaryServiceImpl")
public class DataDictionaryServiceImpl implements DataDictionaryService{

    @Autowired
    DataDictionaryDao dicDao;

    @Override
    public PageResult<DataDictionaryType> getTypeList(DataDictionaryType dataDictionaryType, Integer pageNumber, Integer pageSize) {
        PageResult pageResult = new PageResult();
        PageParameter<DataDictionaryType> pageParameter = new PageParameter(pageNumber,pageSize);
        pageParameter.setParameter(dataDictionaryType);


        int total = dicDao.getTypeListCount(pageParameter);
        List<DataDictionaryType> list = new ArrayList<>();
        if(total >0) {
            list = dicDao.getTypeList(pageParameter);
        }

        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }

    @Override
    public int typeAdd(DataDictionaryType dataDictionaryType) throws CheckedServiceException{

        DataDictionaryType checkVO = new DataDictionaryType();
        PageParameter<DataDictionaryType> pageParameter = new PageParameter(1,1);
        pageParameter.setParameter(checkVO);

        if(StringUtil.isEmpty(dataDictionaryType.getTypeName())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.NULL_PARAM,"分类名称不可为空。");
        }

        if(StringUtil.isEmpty(dataDictionaryType.getTypeCode())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.NULL_PARAM,"分类编码不可为空。");
        }

        checkVO.setTypeCode(dataDictionaryType.getTypeCode());
        List<DataDictionaryType> checkList = dicDao.getTypeListStrict(pageParameter);



        if(CollectionUtil.isNotEmpty(checkList) && !checkList.get(0).getId().equals(dataDictionaryType.getId())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.ILLEGAL_PARAM,"分类编码已存在。");
        }

        int result = dicDao.typeAdd(dataDictionaryType);

        return result;
    }

    @Override
    public PageResult<DataDictionaryItem> getItemList(DataDictionaryItem dataDictionaryItem, Integer pageNumber, Integer pageSize) {
        PageResult pageResult = new PageResult();
        PageParameter<DataDictionaryItem> pageParameter = new PageParameter(pageNumber,pageSize);
        pageParameter.setParameter(dataDictionaryItem);


        int total = dicDao.getItemListCount(pageParameter);
        List<DataDictionaryItem> list = new ArrayList<>();
        if(total >0) {
            list = dicDao.getItemList(pageParameter);
        }

        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }

    @Override
    public int itemAdd(DataDictionaryItem dataDictionaryItem) {
        DataDictionaryItem checkVO = new DataDictionaryItem();
        PageParameter<DataDictionaryItem> pageParameter = new PageParameter(1,1);
        pageParameter.setParameter(checkVO);

        if(StringUtil.isEmpty(dataDictionaryItem.getName())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.NULL_PARAM,"字典项名称不可为空。");
        }

        if(StringUtil.isEmpty(dataDictionaryItem.getCode())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.NULL_PARAM,"字典项业务编码不可为空。");
        }

        checkVO.setName(dataDictionaryItem.getName());
        checkVO.setTypeId(dataDictionaryItem.getTypeId());
        List<DataDictionaryItem> checkList = dicDao.getItemListStrict(pageParameter);

        if(CollectionUtil.isNotEmpty(checkList) && !checkList.get(0).getId().equals(dataDictionaryItem.getId())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.ILLEGAL_PARAM,"字典项名称已存在。");
        }
        checkVO = new DataDictionaryItem();
        pageParameter.setParameter(checkVO);
        checkVO.setCode(dataDictionaryItem.getCode());
        checkVO.setTypeId(dataDictionaryItem.getTypeId());
        checkList = dicDao.getItemListStrict(pageParameter);

        if(CollectionUtil.isNotEmpty(checkList) && !checkList.get(0).getId().equals(dataDictionaryItem.getId())){
            throw new CheckedServiceException(CheckedExceptionBusiness.DATA_DICTIONARY, CheckedExceptionResult.ILLEGAL_PARAM,"字典项业务编码已存在。");
        }

        int result = dicDao.itemAdd(dataDictionaryItem);

        return result;
    }

    @Override
    public List<Map<String, Object>> getListByCode(String code) {
        return  dicDao.getListByCode(code) ;
    }
}
