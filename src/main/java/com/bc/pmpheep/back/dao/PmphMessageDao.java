package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphMessage;


/**
 * PmphMessage  实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphMessageDao {
	
	/**
	 * 
	 * @param  pmphMessage 实体对象
	 * @return  影响行数
	 */
	Integer addPmphMessage (PmphMessage pmphMessage) ;
	
	/**
	 * 
	 * @param id
	 * @return  PmphMessage
	 */
	PmphMessage getPmphMessageById(Long id) ;
	
	/**
	 * 
	 * @param PmphMessage
	 * @return  影响行数
	 */
	Integer deletePmphMessageById(Long id) ;
	
	/**
	 * @param pmphMessage
	 * @return 影响行数
	 */
	Integer updatePmphMessage(PmphMessage pmphMessage) ;

}
