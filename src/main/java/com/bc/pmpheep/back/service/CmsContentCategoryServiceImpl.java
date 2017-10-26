package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsContentCategoryDao;
import com.bc.pmpheep.back.po.CmsContentCategory;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentCategoryService 接口实现
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
public class CmsContentCategoryServiceImpl implements CmsContentCategoryService {
    @Autowired
    CmsContentCategoryDao cmsContentCategoryDao;

    @Override
    public CmsContentCategory addCmsContentCategory(CmsContentCategory cmsContentCategory)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContentCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        cmsContentCategoryDao.addCmsContentCategory(cmsContentCategory);
        return cmsContentCategory;
    }

    @Override
    public Integer updateCmsContentCategory(CmsContentCategory cmsContentCategory)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContentCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentCategoryDao.updateCmsContentCategory(cmsContentCategory);
    }

    @Override
    public List<CmsContentCategory> getCmsContentCategoryList(CmsContentCategory cmsContentCategory)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContentCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentCategoryDao.getCmsContentCategoryList(cmsContentCategory);
    }

    @Override
    public CmsContentCategory getCmsContentCategoryById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentCategoryDao.getCmsContentCategoryById(id);
    }

    @Override
    public Integer getCmsContentCategoryCount() throws CheckedServiceException {
        return cmsContentCategoryDao.getCmsContentCategoryCount();
    }

    @Override
    public Integer deleteCmsContentCategoryById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentCategoryDao.deleteCmsContentCategoryById(id);
    }

    @Override
    public Integer deleteCmsContentCategoryByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentCategoryDao.deleteCmsContentCategoryByIds(ids);
    }

}
