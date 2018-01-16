package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterUserTrendstDao;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：作家动态业务实现层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018年1月16日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Service
public class WriterUserTrendstServiceImpl implements WriterUserTrendstService {
	@Autowired
	WriterUserTrendstDao writerUserTrendstDao;

	@Override
	public WriterUserTrendst addWriterUserTrendst(WriterUserTrendst writerUserTrendst) throws CheckedServiceException {
		if (ObjectUtil.isNull(writerUserTrendst)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_USER_TRENDST,
					CheckedExceptionResult.NULL_PARAM, "参数对象为空");
		}
		writerUserTrendstDao.addWriterUserTrendst(writerUserTrendst);
		return writerUserTrendst;
	}
}
