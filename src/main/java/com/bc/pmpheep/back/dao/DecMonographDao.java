package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecMonograph;

/**
 * 主编学术专著情况表数据访问层接口
 * @author tyc
 * 2018年1月16日 09:28
 */
@Repository
public interface DecMonographDao {
	
	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日上午09:29:27
	 * @param decMonograph
	 * @return
	 */
	Integer addDecMonograph(DecMonograph decMonograph);
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日上午09:31:11
	 * @param id
	 * @return
	 */
	Integer deleteDecMonograph(Long id);
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日上午09:33:56
	 * @param decMonograph
	 * @return
	 */
	Integer updateDecMonograph(DecMonograph decMonograph);
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日上午09:37:25
	 * @param id
	 * @return
	 */
	DecMonograph getDecMonograph(Long id);
}
