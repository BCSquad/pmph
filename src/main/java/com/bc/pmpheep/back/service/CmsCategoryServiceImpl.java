package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsCategoryDao;
import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsCategoryService 接口实现
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-25
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
public class CmsCategoryServiceImpl implements CmsCategoryService {
    @Autowired
    CmsCategoryDao categoryDao;

    @Override
    public CmsCategory addCmsCategory(CmsCategory cmsCategory) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        categoryDao.addCmsCategory(cmsCategory);
        return cmsCategory;
    }

    @Override
    public Integer updateCmsCategory(CmsCategory cmsCategory) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.updateCmsCategory(cmsCategory);
    }

    @Override
    public List<CmsCategory> getCmsCategoryList(CmsCategory cmsCategory)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.getCmsCategoryList(cmsCategory);
    }

    @Override
    public CmsCategory getCmsCategoryById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.getCmsCategoryById(id);
    }

    @Override
    public Integer getCmsCategoryCount() throws CheckedServiceException {
        return categoryDao.getCmsCategoryCount();
    }

    @Override
    public Integer deleteCmsCategoryById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.deleteCmsCategoryById(id);
    }

    @Override
    public Integer deleteCmsCategoryByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.deleteCmsCategoryByIds(ids);
    }

}
