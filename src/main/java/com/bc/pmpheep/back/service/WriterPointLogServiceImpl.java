package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterPointLogDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.po.WriterPointLog;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.WriterPointLogVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 积分日志记录接口实现
 * 
 * @author mr
 * 
 */
@Service
public class WriterPointLogServiceImpl implements WriterPointLogService {

    @Autowired
    WriterPointLogDao      writerPointLogDao;
    @Autowired
    WriterPointService     writerPointService;
    @Autowired
    WriterPointRuleService writerPointRuleService;

    @Override
    public WriterPointLog getWriterPointLog(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return writerPointLogDao.getWriterPointLog(id);
    }

    @Override
    public Integer update(WriterPointLog writerPointLog) throws CheckedServiceException {
        if (ObjectUtil.isNull(writerPointLog)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return writerPointLogDao.updateWriterPointLog(writerPointLog);
    }

    @Override
    public Integer add(WriterPointLog writerPointLog) throws CheckedServiceException {
        if (ObjectUtil.isNull(writerPointLog)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }

        return writerPointLogDao.addWriterPointLog(writerPointLog);
    }

    @Override
    public Integer delete(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return writerPointLogDao.deleteWriterPointLog(id);
    }

    @Override
    public PageResult<WriterPointLogVO> getListWriterPointLog(
    PageParameter<WriterPointLogVO> pageParameter) {
        PageResult<WriterPointLogVO> pageResult = new PageResult<WriterPointLogVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<WriterPointLogVO> writerPointLogVOs =
        writerPointLogDao.listWriterPointLogVO(pageParameter);
        if (CollectionUtil.isNotEmpty(writerPointLogVOs)) {
            Integer count = writerPointLogVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(writerPointLogVOs);
        }
        return pageResult;

    }

    @Override
    public List<WriterPointLog> getWriterPointLogByUserId(Long userId)
    throws CheckedServiceException {
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return writerPointLogDao.getWriterPointLogByUserId(userId);
    }

    @Override
    public void addWriterPointLogByRuleName(String ruleName, Long userId)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(ruleName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "规则名称为空");
        }
        if (ObjectUtil.isNull(userId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "作者Id为空");
        }
        // 获取积分规则
        WriterPointRule writerPointRuleVOs =
        writerPointRuleService.getWriterPointRuleByName(ruleName);
        if (null != writerPointRuleVOs) {
            if (writerPointRuleVOs.getIsDisabled() == false) {
                // 查询用户之前的积分值
                List<WriterPointLog> writerPointLog2 = this.getWriterPointLogByUserId(userId);
                WriterPointLog writerPointLog = new WriterPointLog();
                // 现在的规则的积分值+以前的积分
                Integer temp = 0; // 获取的总积分
                if (!writerPointLog2.isEmpty()) {
                    Integer newTemp = 0; // 以前的总积分
                    // 遍历以前获取的积分
                    for (WriterPointLog writerPointLogNew : writerPointLog2) {
                        newTemp += writerPointLogNew.getPoint();
                    }
                    temp = writerPointRuleVOs.getPoint() + newTemp;
                } else {
                    temp = writerPointRuleVOs.getPoint();
                }
                writerPointLog.setPoint(writerPointRuleVOs.getPoint());
                // 积分规则id
                writerPointLog.setRuleId(writerPointRuleVOs.getId());
                writerPointLog.setUserId(userId);
                // 增加积分记录
                this.add(writerPointLog);
                // 查询用户积分
                WriterPoint point = writerPointService.getWriterPointByUserId(userId);
                if (ObjectUtil.notNull(point)) {
                    writerPointService.updateWriterPoint(new WriterPoint(point.getId(), userId,
                                                                         temp + point.getLoss(),
                                                                         temp,
                                                                         point.getLoss()));
                } else {
                    writerPointService.addWriterPoint(new WriterPoint(userId, temp, temp, 0));
                }

            }
        }
    }
}
