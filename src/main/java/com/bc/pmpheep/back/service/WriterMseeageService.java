package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterMseeage;

/**
 * WriterMseeageService 接口
 * @author Mryang
 */
public interface WriterMseeageService {
	
	/**
	 * 
	 * @param  WriterMseeage 实体对象
	 * @return  带主键的WriterMseeage
	 * @throws Exception 
	 */
	WriterMseeage addWriterMseeage (WriterMseeage writerMseeage) throws Exception;
	
	/**
	 * 
	 * @param WriterMseeage 必须包含主键ID
	 * @return  WriterMseeage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterMseeage getWriterMseeageById(WriterMseeage writerMseeage) throws Exception;
	
	/**
	 * 
	 * @param WriterMseeage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterMseeageById(WriterMseeage writerMseeage) throws Exception;
	
	/**
	 * @param WriterMseeage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateWriterMseeageById(WriterMseeage writerMseeage) throws Exception;
}
