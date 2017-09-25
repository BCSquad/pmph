package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserMessageDao;
import com.bc.pmpheep.back.po.WriterUserMessage;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterUserMessageService 接口实现
 * 
 * @author Mryang
 *
 */
@Service
public class WriterUserMessageServiceImpl extends BaseService implements WriterUserMessageService {
	@Autowired
	private WriterUserMessageDao writerUserMessageDao;

	/**
	 * 
	 * @param writerUserMessage
	 *            实体对象
	 * @return 带主键的WriterUserMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterUserMessage addWriterUserMessage(WriterUserMessage writerUserMessage)  throws CheckedServiceException {
		if(null == writerUserMessage){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		if(null == writerUserMessage.getMsgId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息为空");
		}
		if(null == writerUserMessage.getUserId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息人为空");
		}
		writerUserMessageDao.addWriterUserMessage(writerUserMessage);
		return writerUserMessage;
	}

	/**
	 * 
	 * @param id
	 *            
	 * @return WriterUserMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public WriterUserMessage getWriterUserMessageById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键id为空");
		}
		return writerUserMessageDao.getWriterUserMessageById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteWriterUserMessageById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键id为空");
		}
		return writerUserMessageDao.deleteWriterUserMessageById(id);
	}

	/**
	 * @param writerUserMessage 必须包含主键ID
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateWriterUserMessage(WriterUserMessage writerUserMessage) throws CheckedServiceException {
		if (null == writerUserMessage.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键id为空");
		}
		return writerUserMessageDao.updateWriterUserMessage(writerUserMessage);
	}

}
