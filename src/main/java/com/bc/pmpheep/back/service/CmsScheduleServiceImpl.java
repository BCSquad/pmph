package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsScheduleDao;
import com.bc.pmpheep.back.po.CmsSchedule;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsScheduleService 接口实现
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
public class CmsScheduleServiceImpl implements CmsScheduleService {
    @Autowired
    CmsScheduleDao cmsScheduleDao;

    @Override
    public CmsSchedule addCmsSchedule(CmsSchedule cmsSchedule) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsSchedule)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        cmsScheduleDao.addCmsSchedule(cmsSchedule);
        return cmsSchedule;
    }

    @Override
    public Integer updateCmsSchedule(CmsSchedule cmsSchedule) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsSchedule)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsScheduleDao.updateCmsSchedule(cmsSchedule);
    }

    @Override
    public List<CmsSchedule> getCmsScheduleList(CmsSchedule cmsSchedule)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsSchedule.getScheduledTime())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsScheduleDao.getCmsScheduleList(cmsSchedule);
    }

    @Override
    public CmsSchedule getCmsScheduleById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsScheduleDao.getCmsScheduleById(id);
    }

    @Override
    public CmsSchedule getCmsScheduleByContentId(Long contentId) throws CheckedServiceException {
        if (ObjectUtil.isNull(contentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "contentId参数为空");

        }
        return cmsScheduleDao.getCmsScheduleByContentId(contentId);
    }

    @Override
    public Integer getCmsScheduleCount() throws CheckedServiceException {
        return cmsScheduleDao.getCmsScheduleCount();
    }

    @Override
    public Integer deleteCmsScheduleById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsScheduleDao.deleteCmsScheduleById(id);
    }

    @Override
    public Integer deleteCmsScheduleByContentId(Long contentId) throws CheckedServiceException {
        if (ObjectUtil.isNull(contentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "contentId参数为空");

        }
        return cmsScheduleDao.deleteCmsScheduleByContentId(contentId);
    }

    @Override
    public Integer deleteCmsScheduleByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsScheduleDao.deleteCmsScheduleByIds(ids);
    }

}
