package com.bc.pmpheep.back.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SysOperationDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.SysOperation;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：SysOperationServiceImpl 接口实现
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-15
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
public class SysOperationServiceImpl implements SysOperationService {

    @Autowired
    SysOperationDao sysOperationDao;

    @Override
    public SysOperation addSysOperation(SysOperation sysOperation) throws CheckedServiceException {
        if (ObjectUtil.isNull(sysOperation)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.OPERATION,
                                              CheckedExceptionResult.NULL_PARAM, "传递参数都为空");
        }
        if (ObjectUtil.isNull(sysOperation.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.OPERATION,
                                              CheckedExceptionResult.NULL_PARAM, "操作用户ID为空");
        }
        if (StringUtil.isEmpty(sysOperation.getUserName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.OPERATION,
                                              CheckedExceptionResult.NULL_PARAM, "操作用户名为空");
        }
        if (ObjectUtil.isNull(sysOperation.getOperateDate())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.OPERATION,
                                              CheckedExceptionResult.NULL_PARAM, "操作时间为空");
        }
        if (ObjectUtil.isNull(sysOperation.getOperateText())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.OPERATION,
                                              CheckedExceptionResult.NULL_PARAM, "操作内容为空");
        }
        sysOperationDao.addSysOperation(sysOperation);
        return sysOperation;
    }

    @Override
    public PageResult<SysOperation> getListSysOperation(PageParameter<SysOperation> pageParameter)
    throws CheckedServiceException {
        PageResult<SysOperation> pageResult = new PageResult<SysOperation>();
        String startTime = pageParameter.getParameter().getStartTime();
        String endTime = pageParameter.getParameter().getEndTime();
        // 查询近一个月操作日志
        String startDate = DateUtil.date2Str(new Date());
        String endDate = DateUtil.getAfterDayDate("-30");
        if (StringUtil.isEmpty(startTime) && StringUtil.isEmpty(endTime)) {
            pageParameter.getParameter().setStartTime(endDate);
            pageParameter.getParameter().setEndTime(startDate);
        } else {
            if (StringUtil.isEmpty(startTime)) {
                pageParameter.getParameter()
                             .setStartTime(DateUtil.date2Str(DateUtil.getDateBefore(DateUtil.str2Date(endTime),
                                                                                    30)));
                pageParameter.getParameter().setEndTime(endTime);
            }
            if (StringUtil.isEmpty(endTime)) {
                pageParameter.getParameter().setStartTime(startTime);
                pageParameter.getParameter()
                             .setEndTime(DateUtil.date2Str(DateUtil.getDateBefore(DateUtil.str2Date(startTime),
                                                                                  -30)));
            }
        }
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<SysOperation> sysOperations = sysOperationDao.getListSysOperation(pageParameter);
        if (CollectionUtil.isNotEmpty(sysOperations)) {
            Integer count = sysOperations.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(sysOperations);
        }
        return pageResult;
    }

    @Override
    public List<SysOperation> getSysOperation(Long userId) {
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.OPERATION,
                                              CheckedExceptionResult.NULL_PARAM, "用户id为空");
        }
        return sysOperationDao.getSysOperation(userId);
    }
}
