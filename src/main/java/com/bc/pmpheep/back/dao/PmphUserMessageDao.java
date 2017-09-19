package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.PmphUserMessage;


/**
 * PmphUserMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphUserMessageDao {

	/**
	 * 新增 一个         pmphUserMessage 
	 * @param  pmphUserMessage 
	 * @return  影响行数
	 */
	Integer addPmphUserMessage (PmphUserMessage pmphUserMessage) ;
	
	/**
	 * 根据id查询 pmphUserMessage
	 * @param id
	 * @return  PmphUserMessage
	 */
	PmphUserMessage getPmphUserMessageById(Long id) ;
	
	/**
	 * 根据id删除PmphUserMessage
	 * @param id
	 * @return  影响行数
	 */
	Integer deletePmphUserMessageById(Long id) ;
	
	/**
	 * 根据带主键修改 pmphUserMessage（必须带主键）
	 * @param pmphUserMessage 
	 * @return 影响行数
	 */
	Integer updatePmphUserMessage(PmphUserMessage pmphUserMessage) ;
}
