package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.dao.WriterPointDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 用户积分接口实现
 * @author mr
 *	2017-12-28
 */
@Service
public class WriterPointServiceImpl implements WriterPointService{
	
	@Autowired
	WriterPointDao writerPointDao;
	
	@Override
	public PageResult<WriterPoint> getListWriterPoint(PageParameter<WriterPoint> pageParameter)
			throws CheckedServiceException {
		PageResult<WriterPoint> pageResult = new PageResult<WriterPoint>();
	    PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
//	    int total = writerPointDao.getWriterPoint();
//	    if (total > 0) {
//	    	pageResult.setTotal(total);
//	        List<WriterPoint> list = writerPointDao.getListOrgUser(pageParameter);
//	        pageResult.setRows(list);
//	    }
	    return pageResult;
	}

	@Override
	public WriterPoint addWriterPoint(WriterPoint writerPoint) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPoint)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(ObjectUtil.isNull(writerPoint.getUserId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户id为空");
		}
		writerPointDao.addWriterPoint(writerPoint);
		return writerPoint;
	}

	@Override
	public Integer updateWriterPoint(WriterPoint writerPoint) throws CheckedServiceException {
		if(ObjectUtil.isNull(writerPoint)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(ObjectUtil.isNull(writerPoint.getUserId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户id为空");
		}
		if(ObjectUtil.isNull(writerPoint.getId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointDao.updateWriterPoint(writerPoint);
	}

	@Override
	public Integer deleteWriterPoint(Long id) throws CheckedServiceException {
		if(ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointDao.deleteWriterPoint(id);
	}

	@Override
	public WriterPoint getWriterPoint(Long id) throws CheckedServiceException {
		if(ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_POINT_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerPointDao.getWriterPoint(id);
	}

}
