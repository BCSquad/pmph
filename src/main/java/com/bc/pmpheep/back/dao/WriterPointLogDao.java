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
     * @author:tyc
     * @date:2017年12月28日上午09:19:01
	 * @param writerPointLog
	 * @return
	 */
	Integer addWriterPointLog(WriterPointLog writerPointLog);
	
	/**
	 * 删除
     * @author:tyc
     * @date:2017年12月28日上午09:23:22
	 * @param id
	 * @return
	 */
	Integer deleteWriterPointLog(Long id);
	
	/**
	 * 修改
     * @author:tyc
     * @date:2017年12月28日上午09:26:15
	 * @param writerPointLog
	 * @return
	 */
	Integer updateWriterPointLog(WriterPointLog writerPointLog);
	
	/**
	 * 查询
     * @author:tyc
     * @date:2017年12月28日上午09:30:09
	 * @param id
	 * @return
	 */
	WriterPointLog getWriterPointLog(Long id);
}
