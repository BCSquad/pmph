package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsContentDao;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 接口实现
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
public class CmsContentServiceImpl implements CmsContentService {
    @Autowired
    CmsContentDao cmsContentDao;

    @Override
    public CmsContent addCmsContent(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        cmsContentDao.addCmsContent(cmsContent);
        return cmsContent;
    }

    @Override
    public Integer updateCmsContent(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.updateCmsContent(cmsContent);
    }

    @Override
    public List<CmsContent> getCmsContentList(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.getCmsContentList(cmsContent);
    }

    @Override
    public CmsContent getCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.getCmsContentById(id);
    }

    @Override
    public Integer getCmsContentCount() throws CheckedServiceException {
        return cmsContentDao.getCmsContentCount();
    }

    @Override
    public Integer deleteCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.deleteCmsContentById(id);
    }

    @Override
    public Integer deleteCmsContentByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.deleteCmsContentByIds(ids);
    }

}
