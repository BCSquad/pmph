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
	 * @param WriterMseeage
	 *            实体对象
	 * @return 带主键的WriterMseeage
	 * @throws Exception
	 */
	@Override
	public WriterMessage addWriterMseeage(WriterMessage writerMseeage) throws Exception {
		return writerMessageDao.addWriterMseeage(writerMseeage);
	}

	/**
	 * 
	 * @param WriterMseeage
	 *            必须包含主键ID
	 * @return WriterMseeage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public WriterMessage getWriterMseeageById(WriterMessage writerMseeage) throws Exception {
		if (null == writerMseeage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerMessageDao.getWriterMseeageById(writerMseeage);
	}

	/**
	 * 
	 * @param WriterMseeage
	 * @return 影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteWriterMseeageById(WriterMessage writerMseeage) throws Exception {
		if (null == writerMseeage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerMessageDao.deleteWriterMseeageById(writerMseeage);
	}

	/**
	 * @param WriterMseeage
	 * @return 影响行数
	 * @throws Exception
	 *             ，NullPointerException(主键为空)
	 */
	@Override
	public Integer updateWriterMseeageById(WriterMessage writerMseeage) throws Exception {
		if (null == writerMseeage.getId()) {
			throw new NullPointerException("主键id为空");
		}
		return writerMessageDao.updateWriterMseeageById(writerMseeage);
	}
}
