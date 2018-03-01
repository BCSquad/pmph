package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphUserWechatDao;
import com.bc.pmpheep.back.po.PmphUserWechat;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：PmphUserWechatService接口实现类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-2-28
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
public class PmphUserWechatServiceImpl implements PmphUserWechatService {
    @Autowired
    PmphUserWechatDao pmphUserWechatDao;

    @Override
    public PmphUserWechat add(PmphUserWechat user) throws CheckedServiceException {
        if (ObjectUtil.isNull(user)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数对象为空");
        }
        if (StringUtil.isEmpty(user.getWechatId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "企业微信账号为空");
        }
        pmphUserWechatDao.add(user);
        return user;
    }

    @Override
    public Integer update(PmphUserWechat user) throws CheckedServiceException {
        if (ObjectUtil.isNull(user)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数对象为空");
        }
        if (ObjectUtil.isNull(user.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphUserWechatDao.update(user);
    }

    @Override
    public Integer delete(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return pmphUserWechatDao.delete(id);
    }

    @Override
    public Integer deleteByWechatId(String wechatId) throws CheckedServiceException {
        if (StringUtil.isEmpty(wechatId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "企业微信账号为空");
        }
        return pmphUserWechatDao.deleteByWechatId(wechatId);
    }

    @Override
    public PmphUserWechat getPmphUserWechatByWechatId(String wechatId)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(wechatId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "企业微信账号为空");
        }
        return pmphUserWechatDao.getPmphUserWechatByWechatId(wechatId);
    }

}
