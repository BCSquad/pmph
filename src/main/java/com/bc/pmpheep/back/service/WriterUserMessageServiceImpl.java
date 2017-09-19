package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserMessageDao;
import com.bc.pmpheep.back.po.WriterUserMessage;

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
	 * @param WriterUserMessage
	 *            实体对象
	 * @return 带主键的WriterUserMessage
	 * @throws Exception
	 */
	@Override
	public WriterUserMessage addWriterUserMessage(WriterUserMessage writerUserMessage) throws Exception {
		writerUserMessageDao.addWriterUserMessage(writerUserMessage);
		return writerUserMessage;
	}

	/**
	 * 
	 * @param WriterUserMessage
	 *            必须包含主键ID
	 * @return WriterUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public WriterUserMessage getWriterUserMessageById(WriterUserMessage writerUserMessage) throws Exception {
		if (null == writerUserMessage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerUserMessageDao.getWriterUserMessageById(writerUserMessage);
	}

	/**
	 * 
	 * @param WriterUserMessage
	 * @return 影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteWriterUserMessageById(WriterUserMessage writerUserMessage) throws Exception {
		if (null == writerUserMessage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerUserMessageDao.deleteWriterUserMessageById(writerUserMessage);
	}

	/**
	 * @param WriterUserMessage
	 * @return 影响行数
	 * @throws Exception
	 *             ，NullPointerException(主键为空)
	 */
	@Override
	public Integer updateWriterUserMessageById(WriterUserMessage writerUserMessage) throws Exception {
		if (null == writerUserMessage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerUserMessageDao.updateWriterUserMessageById(writerUserMessage);
	}

}
