package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterMessageDao;
import com.bc.pmpheep.back.po.WriterMessage;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口实现
 * 
 * @author Mryang
 *
 */
@Service
public class WriterMessageServiceImpl extends BaseService implements WriterMessageService {
	@Autowired
	private WriterMessageDao writerMessageDao;

	/**
	 * 
	 * @param WriterMessage
	 *            实体对象
	 * @return 带主键的WriterMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterMessage addWriterMessage(WriterMessage writerMessage) throws CheckedServiceException {
		if(null == writerMessage ){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		writerMessageDao.addWriterMessage(writerMessage);
		return writerMessage;
	}

	/**
	 * 
	 * @param WriterMessage
	 *            必须包含主键ID
	 * @return WriterMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterMessage getWriterMessageById(WriterMessage writerMessage) throws CheckedServiceException {
		if (null == writerMessage.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键id为空");
		}
		return writerMessageDao.getWriterMessageById(writerMessage);
	}

	/**
	 * 
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteWriterMessageById(WriterMessage writerMessage) throws CheckedServiceException {
		if (null == writerMessage.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键id为空");
		}
		return writerMessageDao.deleteWriterMessageById(writerMessage);
	}

	/**
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateWriterMessageById(WriterMessage writerMessage) throws CheckedServiceException {
		if (null == writerMessage.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键id为空");
		}
		return writerMessageDao.updateWriterMessageById(writerMessage);
	}
}
