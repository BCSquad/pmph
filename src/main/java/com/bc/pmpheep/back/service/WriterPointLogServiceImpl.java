package com.bc.pmpheep.back.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterPointLogDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPointLog;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.vo.OrgVO;
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
	public WriterPointLog add(WriterPointLog writerPointLog) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPointLog)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		writerPointLogDao.addWriterPointLog(writerPointLog);
		return writerPointLog;
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

}
