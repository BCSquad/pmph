package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterMessageService 接口
 * @author Mryang
 */
public interface WriterMessageService {
	
	/**
	 * 
	 * @param  WriterMessage 实体对象
	 * @return  带主键的WriterMessage
	 * @throws CheckedServiceException 
	 */
	WriterMessage addWriterMessage (WriterMessage writerMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param WriterMessage 必须包含主键ID
	 * @return  WriterMessage
	 * @throws CheckedServiceException
	 */
	WriterMessage getWriterMessageById(WriterMessage writerMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param WriterMessage
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteWriterMessageById(WriterMessage writerMessage) throws CheckedServiceException;
	
	/**
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateWriterMessageById(WriterMessage writerMessage) throws CheckedServiceException;
}
