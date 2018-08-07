package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ProductTypeDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeDao productTypeDao;

    @Override
    public PageResult getTypeList(PageParameter<ProductType> pageParameter, String sessionId) {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }

        PageResult pageResult = new PageResult();

        int count = 0;
        List<ProductType> list = new ArrayList<>();
        int typeType = pageParameter.getParameter().getTypeType();
        if (typeType==1){ //学科分类
            count = productTypeDao.getSubjectTypeCount(pageParameter);
            if(count>0){
                list = productTypeDao.getSubjectTypeList(pageParameter);
            }
        }else if(typeType==2){ //内容分类
            count = productTypeDao.getLeafContentTypeCount(pageParameter);
            if(count>0){
                list = productTypeDao.getLeafContentTypeList(pageParameter);
            }
        }else{
            //TODO 如果后续有新增其他分类...
        }

        pageResult.setTotal(count);
        pageResult.setRows(list);
        pageResult.setPageNumber(pageParameter.getPageNumber());
        pageResult.setPageSize(pageParameter.getPageSize());

        return pageResult;
    }

    @Override
    public ResponseBean deleteTypeById(ProductType productType,String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }
        ResponseBean responseBean = new ResponseBean();
        int typeType = productType.getTypeType();
        int count = 0;
        int experCount=0;
        if (typeType==1){ //学科分类
            experCount = productTypeDao.getSubjectTypeExpertationCount(productType.getId());
            if(experCount>0){
                throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.ILLEGAL_PARAM,
                        "该分类已被临床决策申报引用，无法删除！");
            }
            count = productTypeDao.deleteSubjectTypeById(productType.getId());
        }else if(typeType==2){ //内容分类
            experCount = productTypeDao.getContentTypeExpertationCount(productType.getId());
            if(experCount>0){
                throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.ILLEGAL_PARAM,
                        "该分类已被临床决策申报引用，无法删除！");
            }
            count = productTypeDao.deleteLeafContentTypeById(productType.getId());
            //productTypeDao.refreshLeafOfContentType();
        }else{
            //TODO 如果后续有新增其他分类...
        }

        return responseBean;
    }
}
