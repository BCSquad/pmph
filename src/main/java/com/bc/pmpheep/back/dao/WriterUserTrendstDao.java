package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterUserTrendst;

@Repository
public interface WriterUserTrendstDao {
	/**
	 * 
	 * 
	 * 功能描述：添加作家动态
	 *
	 * @param writerUserTrendst
	 * @return
	 *
	 */
	Long addWriterUserTrendst(WriterUserTrendst writerUserTrendst);

	/**
	 * 
	 * 
	 * 功能描述：修改作家动态
	 *
	 * @param writerUserTrendst
	 * @return
	 *
	 */
	Integer updateWriterUserTrendst(WriterUserTrendst writerUserTrendst);

	/**
	 * 
	 * 
	 * 功能描述：根据id删除作家动态
	 *
	 * @param id
	 * @return
	 *
	 */
	Integer deleteWriterUserTrendstById(Long id);

	/**
	 * 
	 * 
	 * 功能描述：通过id查询作家动态
	 *
	 * @param id
	 * @return
	 *
	 */
	WriterUserTrendst getWriterUserTrendstById(Long id);

	/**
	 * 
	 * 
	 * 功能描述：获取作家动态总数
	 *
	 * @return
	 *
	 */
	Long getWriterUserTrendstCount();
}
