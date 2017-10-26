package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsExtraDao;
import com.bc.pmpheep.back.po.CmsExtra;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsExtraService 接口实现
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
public class CmsExtraServiceImpl implements CmsExtraService {
    @Autowired
    CmsExtraDao cmsExtraDao;

    @Override
    public CmsExtra addCmsExtra(CmsExtra cmsExtra) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsExtra)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        cmsExtraDao.addCmsExtra(cmsExtra);
        return cmsExtra;
    }

    @Override
    public Integer updateCmsExtra(CmsExtra cmsExtra) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsExtra)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsExtraDao.updateCmsExtra(cmsExtra);
    }

    @Override
    public List<CmsExtra> getCmsExtraList(CmsExtra cmsExtra) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsExtra)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsExtraDao.getCmsExtraList(cmsExtra);
    }

    @Override
    public CmsExtra getCmsExtraById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsExtraDao.getCmsExtraById(id);
    }

    @Override
    public Integer getCmsExtraCount() throws CheckedServiceException {
        return cmsExtraDao.getCmsExtraCount();
    }

    @Override
    public Integer deleteCmsExtraById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsExtraDao.deleteCmsExtraById(id);
    }

    @Override
    public Integer deleteCmsExtraByIds(List<Long> ids) throws CheckedServiceException {
        if (ObjectUtil.isNull(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsExtraDao.deleteCmsExtraByIds(ids);
    }

}
