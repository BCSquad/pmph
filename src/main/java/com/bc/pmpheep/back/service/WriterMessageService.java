package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterMessage;

/**
 * WriterMseeageService 接口
 * @author Mryang
 */
public interface WriterMessageService {
	
	/**
	 * 
	 * @param  WriterMseeage 实体对象
	 * @return  带主键的WriterMseeage
	 * @throws Exception 
	 */
	WriterMessage addWriterMseeage (WriterMessage writerMseeage) throws Exception;
	
	/**
	 * 
	 * @param WriterMseeage 必须包含主键ID
	 * @return  WriterMseeage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterMessage getWriterMseeageById(WriterMessage writerMseeage) throws Exception;
	
	/**
	 * 
	 * @param WriterMseeage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterMseeageById(WriterMessage writerMseeage) throws Exception;
	
	/**
	 * @param WriterMseeage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateWriterMseeageById(WriterMessage writerMseeage) throws Exception;
}
