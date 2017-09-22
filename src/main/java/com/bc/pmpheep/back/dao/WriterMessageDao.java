package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterMessage;

/**
 * WriterMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface WriterMessageDao {

	/**
	 * 
	 * @param WriterMessage
	 *            实体对象
	 * @return 带主键的WriterMessage
	 * @throws Exception
	 */
	Integer addWriterMessage(WriterMessage writerMessage);

	/**
	 * 
	 * @param WriterMessage
	 *            必须包含主键ID
	 * @return WriterMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterMessage getWriterMessageById(WriterMessage writerMessage);

	/**
	 * 
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterMessageById(WriterMessage writerMessage);

	/**
	 * @param WriterMessage
	 * @return 影响行数
	 * @throws Exception
	 *             ，NullPointerException(主键为空)
	 */
	Integer updateWriterMessageById(WriterMessage writerMessage);
	
	   /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getWriterMessageCount();
}
