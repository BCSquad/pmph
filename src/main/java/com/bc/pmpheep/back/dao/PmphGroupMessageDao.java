package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;


import com.bc.pmpheep.back.po.PmphGroupMessage;


/**
 * PmphGroupMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface  PmphGroupMessageDao {
	/**
	 * 
	 * @param  pmphGroupMessage 实体对象
	 * @return  影响行数
	 */
	Integer addPmphGroupMessage (PmphGroupMessage pmphGroupMessage) ;
	
	/**
	 * 
	 * @param PmphGroupMessage 必须包含主键ID
	 * @return  PmphGroupMessage
	 */
	PmphGroupMessage getPmphGroupMessageById(Long  id) ;
	
	/**
	 * 
	 * @param id 
	 * @return  影响行数
	 */
	Integer deletePmphGroupMessageById(Long  id) ;
	
	/**
	 * @param pmphGroupMessage 
	 * @return 影响行数
	 */
	Integer updatePmphGroupMessage (PmphGroupMessage  pmphGroupMessage) ;
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphGroupMessageCount();
}
