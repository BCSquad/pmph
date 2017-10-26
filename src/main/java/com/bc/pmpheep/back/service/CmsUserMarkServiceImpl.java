package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsUserMarkDao;
import com.bc.pmpheep.back.po.CmsUserMark;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsUserMarkService 接口实现
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
public class CmsUserMarkServiceImpl implements CmsUserMarkService {
    @Autowired
    CmsUserMarkDao cmsUserMarkDao;

    @Override
    public CmsUserMark addCmsUserMark(CmsUserMark cmsUserMark) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsUserMark)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        cmsUserMarkDao.addCmsUserMark(cmsUserMark);
        return cmsUserMark;
    }

    @Override
    public Integer updateCmsUserMark(CmsUserMark cmsUserMark) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsUserMark)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserMarkDao.updateCmsUserMark(cmsUserMark);
    }

    @Override
    public List<CmsUserMark> getCmsUserMarkList(CmsUserMark cmsUserMark)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsUserMark)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserMarkDao.getCmsUserMarkList(cmsUserMark);
    }

    @Override
    public CmsUserMark getCmsUserMarkById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserMarkDao.getCmsUserMarkById(id);
    }

    @Override
    public Integer getCmsUserMarkCount() throws CheckedServiceException {
        return cmsUserMarkDao.getCmsUserMarkCount();
    }

    @Override
    public Integer deleteCmsUserMarkById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserMarkDao.deleteCmsUserMarkById(id);
    }

    @Override
    public Integer deleteCmsUserMarkByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsUserMarkDao.deleteCmsUserMarkByIds(ids);
    }

}
