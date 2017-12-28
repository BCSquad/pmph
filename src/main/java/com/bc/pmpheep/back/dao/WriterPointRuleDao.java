package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.WriterPointRule;

/**
 * WriterPointRule实体类的数据访问层接口
 * 
 * @author tyc
 * 
 */
public interface WriterPointRuleDao {

	/**
	 * 添加
	 * @param writerPointRule
	 * @return
	 */
	Integer addWriterPointRule(WriterPointRule writerPointRule);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer deleteWriterPointRule(Long id);
	
	/**
	 * 修改
	 * @param writerPointRule
	 * @return
	 */
	Integer updateWriterPointRule(WriterPointRule writerPointRule);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	WriterPointRule getWriterPointRule(Long id);
}
