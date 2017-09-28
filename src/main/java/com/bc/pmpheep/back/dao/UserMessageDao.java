package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.vo.MessageStateVO;



/**
 * 
 * UserMessage 实体类数据访问层接口
 *
 * @author Mryang
 *
 * @createDate 2017年9月27日 下午2:33:53
 *
 */
@Repository
public interface UserMessageDao {
	
	/**
	 * 
	 * 获取MessageState列表（同时查询分页数据和总条数）
	 * @author Mryang
	 * @createDate 2017年9月27日 上午10:36:10
	 * @param page
	 * @return page
	 */
	List<MessageStateVO> getMessageStateList(Page<MessageStateVO,MessageStateVO> page);
	
	
}
