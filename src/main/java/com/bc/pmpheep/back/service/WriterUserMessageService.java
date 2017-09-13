package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterUserMessage;

/**
 * WriterUserMessageService 接口
 * @author Mryang
 */
public interface WriterUserMessageService {

	/**
	 * 
	 * @param  WriterUserMessage 实体对象
	 * @return  带主键的WriterUserMessage
	 * @throws Exception 
	 */
	WriterUserMessage addWriterUserMessage (WriterUserMessage writerUserMessage) throws Exception;
	
	/**
	 * 
	 * @param WriterUserMessage 必须包含主键ID
	 * @return  WriterUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterUserMessage getWriterUserMessageById(WriterUserMessage writerUserMessage) throws Exception;
	
	/**
	 * 
	 * @param WriterUserMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterUserMessageById(WriterUserMessage writerUserMessage) throws Exception;
	
	/**
	 * @param WriterUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateWriterUserMessageById(WriterUserMessage writerUserMessage) throws Exception;
}
