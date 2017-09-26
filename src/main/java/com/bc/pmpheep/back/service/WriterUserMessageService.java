package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterUserMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterUserMessageService 接口
 * @author Mryang
 */
public interface WriterUserMessageService {

	/**
	 * 
	 * @param  WriterUserMessage 实体对象
	 * @return  带主键的WriterUserMessage
	 * @throws CheckedServiceException 
	 */
	WriterUserMessage addWriterUserMessage (WriterUserMessage writerUserMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  WriterUserMessage
	 * @throws CheckedServiceException
	 */
	WriterUserMessage getWriterUserMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteWriterUserMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * @param WriterUserMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateWriterUserMessage(WriterUserMessage writerUserMessage) throws CheckedServiceException;
}
