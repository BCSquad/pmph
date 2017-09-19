package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterMessageDao;
import com.bc.pmpheep.back.po.WriterMessage;

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
	 * @throws Exception
	 */
	@Override
	public WriterMessage addWriterMessage(WriterMessage writerMessage) throws Exception {
		writerMessageDao.addWriterMessage(writerMessage);
		return writerMessage;
	}

	/**
	 * 
	 * @param WriterMessage
	 *            必须包含主键ID
	 * @return WriterMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public WriterMessage getWriterMessageById(WriterMessage writerMessage) throws Exception {
		if (null == writerMessage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerMessageDao.getWriterMessageById(writerMessage);
	}

	/**
	 * 
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteWriterMessageById(WriterMessage writerMessage) throws Exception {
		if (null == writerMessage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerMessageDao.deleteWriterMessageById(writerMessage);
	}

	/**
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws Exception
	 *             ，NullPointerException(主键为空)
	 */
	@Override
	public Integer updateWriterMessageById(WriterMessage writerMessage) throws Exception {
		if (null == writerMessage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerMessageDao.updateWriterMessageById(writerMessage);
	}
}
