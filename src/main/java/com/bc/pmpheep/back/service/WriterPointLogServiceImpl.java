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
 * @author mr
 *
 */
@Service
public class WriterPointLogServiceImpl implements WriterPointLogService{
	
	@Autowired
	WriterPointLogDao writerPointLogDao;
	@Autowired
	WriterPointService writerPointService;
	@Autowired
	WriterPointRuleService writerPointRuleService;

	@Override
	public WriterPointLog getWriterPointLog(Long id) throws CheckedServiceException {
		if(ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointLogDao.getWriterPointLog(id);
	}

	@Override
	public Integer update(WriterPointLog writerPointLog) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPointLog)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointLogDao.updateWriterPointLog(writerPointLog);
	}

	@Override
	public Integer add(WriterPointLog writerPointLog) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPointLog)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		
		return writerPointLogDao.addWriterPointLog(writerPointLog);
	}

	@Override
	public Integer delete(Long id) throws CheckedServiceException {
		if(ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointLogDao.deleteWriterPointLog(id);
	}

	@Override
	public PageResult<WriterPointLogVO> getListWriterPointLog(PageParameter<WriterPointLogVO> pageParameter) {
		PageResult<WriterPointLogVO> pageResult = new PageResult<WriterPointLogVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<WriterPointLogVO> writerPointLogVOs = writerPointLogDao.listWriterPointLogVO(pageParameter);
        if (CollectionUtil.isNotEmpty(writerPointLogVOs)) {
            Integer count = writerPointLogVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(writerPointLogVOs);
        }
        return pageResult;
	    
	}

	@Override
	public List<WriterPointLog> getWriterPointLogByUserId(Long userId) throws CheckedServiceException {
		if(null==userId){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointLogDao.getWriterPointLogByUserId(userId);
	}

	@Override
	public void addWriterPointLogByRuleName(String ruleName, Long userId) throws CheckedServiceException {
		if(StringUtil.isEmpty(ruleName)){
			 throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                     CheckedExceptionResult.NULL_PARAM, "参数为空1");
		}
		if(ObjectUtil.isNull(userId)){
			 throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		//获取积分规则
		WriterPointRule writerPointRuleVOs =  writerPointRuleService.getWriterPointRuleByName(ruleName);
		if (null != writerPointRuleVOs && null != writerPointRuleVOs.getIsDisabled() &&  writerPointRuleVOs.getIsDisabled() == false) {
			//查询用户之前的积分值
			List<WriterPointLog> writerPointLog2 = this.getWriterPointLogByUserId (userId);
			WriterPointLog writerPointLog = new WriterPointLog();
			//现在的规则的积分值+以前的积分
			Integer temp   = writerPointRuleVOs.getPoint();
			if (null != writerPointLog2 && writerPointLog2.size() > 0) {
				for (WriterPointLog writerPointLogNew : writerPointLog2) {
            		temp += writerPointLogNew.getPoint();
            	}
            } 
			writerPointLog.setPoint(writerPointRuleVOs.getPoint());
			//积分规则id
			writerPointLog.setRuleId(writerPointRuleVOs.getId());
			writerPointLog.setUserId(userId);
			//增加积分记录
			this.add(writerPointLog);
			WriterPoint point = writerPointService.getWriterPointByUserId(userId);
			if(null != point) {
				WriterPoint writerPoint = new WriterPoint();
				//当前获取的总积分=评论积分+以前的积分
				writerPoint.setGain(temp);
				writerPoint.setUserId(userId);
				writerPoint.setTotal(writerPoint.getGain() + point.getLoss());
				writerPoint.setLoss(point.getLoss());
				writerPoint.setId(point.getId());
				writerPointService.updateWriterPoint(writerPoint);
			}else {
				WriterPoint writerPoint = new WriterPoint();
				//当前获取的总积分=评论积分+以前的积分
				writerPoint.setGain(temp);
				writerPoint.setUserId(userId);
				writerPoint.setTotal(writerPoint.getGain());
				writerPoint.setLoss(0);
				writerPointService.addWriterPoint(writerPoint);
			}
		}
	}
}











