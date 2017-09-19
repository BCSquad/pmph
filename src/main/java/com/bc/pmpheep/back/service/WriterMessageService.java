package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterMessage;

/**
 * WriterMessageService 接口
 * @author Mryang
 */
public interface WriterMessageService {
	
	/**
	 * 
	 * @param  WriterMessage 实体对象
	 * @return  带主键的WriterMessage
	 * @throws Exception 
	 */
	WriterMessage addWriterMessage (WriterMessage writerMessage) throws Exception;
	
	/**
	 * 
	 * @param WriterMessage 必须包含主键ID
	 * @return  WriterMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterMessage getWriterMessageById(WriterMessage writerMessage) throws Exception;
	
	/**
	 * 
	 * @param WriterMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterMessageById(WriterMessage writerMessage) throws Exception;
	
	/**
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateWriterMessageById(WriterMessage writerMessage) throws Exception;
}
