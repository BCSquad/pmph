package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecSci;

/**
 * SCI论文投稿及影响因子情况表数据访问层接口
 * @author tyc
 * 2018年1月16日 10:48
 */
@Repository
public interface DecSciDao {

	/**
	 * 新增
	 * @author:tyc
     * @date:2018年1月16日下午14:11:09
	 * @param decMonograph
	 * @return
	 */
	Integer addDecSci(DecSci decSci);
	
	/**
	 * 删除
	 * @author:tyc
     * @date:2018年1月16日下午14:12:15
	 * @param id
	 * @return
	 */
	Integer deleteDecSci(Long id);
	
	/**
	 * 更新
	 * @author:tyc
     * @date:2018年1月16日下午14:11:47
	 * @param decMonograph
	 * @return
	 */
	Integer updateDecSci(DecSci decSci);
	
	/**
	 * 查询
	 * @author:tyc
     * @date:2018年1月16日下午14:12:14
	 * @param id
	 * @return
	 */
	DecSci getDecSci(Long id);
}
