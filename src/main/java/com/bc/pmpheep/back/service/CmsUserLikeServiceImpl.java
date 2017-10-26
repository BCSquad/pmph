package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsUserLikeDao;
import com.bc.pmpheep.back.po.CmsUserLike;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsUserLikeService 接口实现
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
public class CmsUserLikeServiceImpl implements CmsUserLikeService {
    @Autowired
    CmsUserLikeDao cmsUserLikeDao;

    @Override
    public CmsUserLike addCmsUserLike(CmsUserLike cmsUserLike) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsUserLike)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        cmsUserLikeDao.addCmsUserLike(cmsUserLike);
        return cmsUserLike;
    }

    @Override
    public Integer updateCmsUserLike(CmsUserLike cmsUserLike) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsUserLike)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserLikeDao.updateCmsUserLike(cmsUserLike);
    }

    @Override
    public List<CmsUserLike> getCmsUserLikeList(CmsUserLike cmsUserLike)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsUserLike)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserLikeDao.getCmsUserLikeList(cmsUserLike);
    }

    @Override
    public CmsUserLike getCmsUserLikeById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserLikeDao.getCmsUserLikeById(id);
    }

    @Override
    public Integer getCmsUserLikeCount() throws CheckedServiceException {
        return cmsUserLikeDao.getCmsUserLikeCount();
    }

    @Override
    public Integer deleteCmsUserLikeById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserLikeDao.deleteCmsUserLikeById(id);
    }

    @Override
    public Integer deleteCmsUserLikeByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserLikeDao.deleteCmsUserLikeByIds(ids);
    }

}
