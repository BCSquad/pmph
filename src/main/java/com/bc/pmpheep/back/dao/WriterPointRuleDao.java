package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.vo.WriterPointRuleVO;

/**
 * WriterPointRule实体类的数据访问层接口
 * 
 * @author tyc
 * 
 */
public interface WriterPointRuleDao {

	/**
	 * 添加
     * @author:tyc
     * @date:2017年12月28日上午09:32:09
	 * @param writerPointRule
	 * @return
	 */
	Integer addWriterPointRule(WriterPointRule writerPointRule);
	
	/**
	 * 删除
     * @author:tyc
     * @date:2017年12月28日上午09:37:43
	 * @param id
	 * @return
	 */
	Integer deleteWriterPointRule(Long id);
	
	/**
	 * 修改
     * @author:tyc
     * @date:2017年12月28日上午09:53:08
	 * @param writerPointRule
	 * @return
	 */
	Integer updateWriterPointRule(WriterPointRule writerPointRule);
	
	/**
	 * 查询
     * @author:tyc
     * @date:2017年12月28日上午09:59:21
	 * @param id
	 * @return
	 */
	WriterPointRule getWriterPointRule(Long id);
	
	/**
     * 积分规则表分页列表（同时查询分页数据和总条数）积分规则管理
     * @author:tyc
     * @date:2017年12月28日下午14:30:50 
	 * @param pageParameter
	 * @return
	 */
	List<WriterPointRuleVO> listWriterPointRule(PageParameter<WriterPointRuleVO> pageParameter);
	
	/**
     * 积分规则表分页列表（同时查询分页数据和总条数）积分兑换规则
     * @author:tyc
     * @date:2017年12月28日下午14:49:12 
	 * @param pageParameter
	 * @return
	 */
	List<WriterPointRuleVO> listWriterPointRulePoint(PageParameter<WriterPointRuleVO> pageParameter);
}
