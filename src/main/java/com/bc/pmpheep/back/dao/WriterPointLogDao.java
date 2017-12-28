package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.WriterPointLog;

/**
 * WriterPointLog实体类的数据访问层接口
 * 
 * @author tyc
 * 
 */
public interface WriterPointLogDao {

	/**
	 * 添加
	 * @param writerPointLog
	 * @return
	 */
	Integer addWriterPointLog(WriterPointLog writerPointLog);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer deleteWriterPointLog(Long id);
	
	/**
	 * 修改
	 * @param writerPointLog
	 * @return
	 */
	Integer updateWriterPointLog(WriterPointLog writerPointLog);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	WriterPointLog getWriterPointLog(Long id);
}
