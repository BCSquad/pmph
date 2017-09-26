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
	 * @param writerUserMessage
	 *            实体对象
	 * @return 带主键的WriterUserMessage
	 */
	Integer addWriterUserMessage(WriterUserMessage writerUserMessage);

	/**
	 * 
	 * @param id
	 * @return WriterUserMessage
	 */
	WriterUserMessage getWriterUserMessageById(Long  id);

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteWriterUserMessageById(Long id);

	/**
	 * @param writerUserMessage 必须带主键
	 * @return 影响行数
	 * @throws Exception
	 */
	Integer updateWriterUserMessage(WriterUserMessage writerUserMessage);
	
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
