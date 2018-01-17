package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecEduExp;
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
	 * @param decSci
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
	 * @param decSci
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
	
	/**
	 * 根据申报表id查询
	 * @author:tyc
     * @date:2018年1月16日下午16:56:17
	 * @param declarationId
	 * @return
	 */
	List<DecSci> getListDecSciByDeclarationId(Long declarationId);
	
	
	/**
	 * Description: 根据申报表id查询
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecSci> getListDecSciByDeclarationIds(List<Long> declarationId);
}
