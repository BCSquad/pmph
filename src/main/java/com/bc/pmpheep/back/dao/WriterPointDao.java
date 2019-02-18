package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.WriterPoint;
import com.bc.pmpheep.back.vo.WriterPointVO;

/**
 * WriterPoint实体类的数据访问层接口
 * 
 * @author tyc
 * 
 */
public interface WriterPointDao {

	/**
	 * 添加
     * @author:tyc
     * @date:2017年12月28日上午09:04:37
	 * @param writerPoint
	 * @return
	 */
	Integer addWriterPoint(WriterPoint writerPoint);
	
	/**
	 * 删除
     * @author:tyc
     * @date:2017年12月28日上午09:07:23
	 * @param id
	 * @return
	 */
	Integer deleteWriterPoint(Long id);
	
	/**
	 * 修改
     * @author:tyc
     * @date:2017年12月28日上午09:12:53
	 * @param writerPoint
	 * @return
	 */
	Integer updateWriterPoint(WriterPoint writerPoint);
	
	/**
	 * 查询
     * @author:tyc
     * @date:2017年12月28日上午09:15:11
	 * @param id
	 * @return
	 */
	WriterPoint getWriterPoint(Long id);
	
	/**
     * 用户积分表分页列表（同时查询分页数据和总条数）
     * @author:tyc
     * @date:2017年12月28日下午14:13:56 
	 * @param pageParameter
	 * @return
	 */
	List<WriterPointVO> listWriterPointVO(PageParameter<WriterPointVO> pageParameter);
	
	/**
	 * 通过用户id查询积分
	 * @param userId
	 * @return
	 */
	WriterPoint getWriterPointByUserId(Long userId);
	List<WriterPointVO> getAllWriterPoint();
	
}