package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.WriterPoint;

/**
 * WriterPoint实体类的数据访问层接口
 * 
 * @author tyc
 * 
 */
public interface WriterPointDao {

	/**
	 * 添加
	 * @param writerPoint
	 * @return
	 */
	Integer addWriterPoint(WriterPoint writerPoint);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer deleteWriterPoint(Long id);
	
	/**
	 * 修改
	 * @param writerPoint
	 * @return
	 */
	Integer updateWriterPoint(WriterPoint writerPoint);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	WriterPoint getWriterPoint(Long id);
}
