package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterUserMessage;

/**
 * WriterUserMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface WriterUserMessageDao {
	/**
	 * 
	 * @param WriterUserMessage
	 *            实体对象
	 * @return 带主键的WriterUserMessage
	 * @throws Exception
	 */
	Integer addWriterUserMessage(WriterUserMessage writerUserMessage);

	/**
	 * 
	 * @param WriterUserMessage
	 *            必须包含主键ID
	 * @return WriterUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	WriterUserMessage getWriterUserMessageById(WriterUserMessage writerUserMessage);

	/**
	 * 
	 * @param WriterUserMessage
	 * @return 影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteWriterUserMessageById(WriterUserMessage writerUserMessage);

	/**
	 * @param WriterUserMessage
	 * @return 影响行数
	 * @throws Exception
	 *             ，NullPointerException(主键为空)
	 */
	Integer updateWriterUserMessageById(WriterUserMessage writerUserMessage);
	
	   /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getWriterUserMessageCount();
}
