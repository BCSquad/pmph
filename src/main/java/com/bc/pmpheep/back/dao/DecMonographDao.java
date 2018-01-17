package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.back.po.DecSci;

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
	
	/**
	 * 根据申报表id查询
	 * @author:tyc
     * @date:2018年1月16日下午16:43:54
	 * @param declarationId
	 * @return
	 */
	List<DecMonograph> getListDecMonographByDeclarationId(Long declarationId);
	
	/**
	 * Description: 根据申报表id查询
	 * 
	 * @Param declarationId 申报表id
	 * 
	 * @Return 作家学习经历信息
	 */
	List<DecMonograph> getListDecMonographByDeclarationIds(List<Long> declarationId);
}
