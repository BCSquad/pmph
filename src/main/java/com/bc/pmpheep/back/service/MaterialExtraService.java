package com.bc.pmpheep.back.service;



import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialExtraService 接口
 * @author 曾庆峰
 *
 */
public interface MaterialExtraService {
	
	/**
	 * 新增一个MaterialExtra 
	 * @param MaterialExtra 实体对象
	 * @return 带主键的 MaterialExtra 
	 * thorws CheckedServiceException
	 */
	MaterialExtra addMaterialExtra(MaterialExtra materialExtra) throws CheckedServiceException;
	
	/**
	 *  查询一个 MaterialExtra 通过主键id
	 * @param id
	 * @return  MaterialExtra
	 * @throws CheckedServiceException
	 */
	MaterialExtra getMaterialExtraById(Long id) throws CheckedServiceException;
	
	/**
	 * 删除MaterialExtra 通过主键id
	 * @param MaterialExtra
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialExtraById(Long id) throws CheckedServiceException;
	
	/**
	 * 更新一个 MaterialExtra通过主键id
	 * @param MaterialExtra
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateMaterialExtra(MaterialExtra materialExtra) throws CheckedServiceException;
}
