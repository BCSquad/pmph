package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.po.ProductAttachment;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public ProductVO getProductByType(Long product_type, String sessionId) {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }

        ProductVO productVO = productDao.queryProductByProductType(product_type, Const.CLINICAL_DECISION_FILE_DOWNLOAD);
        if(productVO==null){
            productVO = new ProductVO();
            productVO.setProduct_type(product_type);
        }

        return productVO;
    }

    @Override
    public int updateProductAttachmenDownLoadCountsByAttachment(String id) {
        int count = productDao.updateProductAttachmenDownLoadCountsByAttachment(id);
        return count;
    }
}
