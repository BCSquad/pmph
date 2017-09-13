package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterMseeage;

/**
 * WriterMseeage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface WriterMseeageDao {

	/**
	 * 
	 * @param  WriterMseeage 实体对象
	 * @return  带主键的WriterMseeage
	 * @throws Exception 
	 */
	WriterMseeage addWriterMseeage (WriterMseeage writerMseeage) ;
	
	/**
	 * 
	 * @param WriterMseeage 必须包含主键ID
	 * @return  WriterMseeage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterMseeage getWriterMseeageById(WriterMseeage writerMseeage)  ;
	
	/**
	 * 
	 * @param WriterMseeage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterMseeageById(WriterMseeage writerMseeage)  ;
	
	/**
	 * @param WriterMseeage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateWriterMseeageById(WriterMseeage writerMseeage)  ;
}
