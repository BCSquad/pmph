package com.bc.pmpheep.back.service;



import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialExtensionService 接口
 * @author 曾庆峰
 *
 */
public interface MaterialExtensionService {
	
	/**
	 * 新增一个MaterialExtension 
	 * @param MaterialExtension 实体对象
	 * @return 带主键的 MaterialExtension 
	 * thorws CheckedServiceException
	 */
	MaterialExtension addMaterialExtension(MaterialExtension materialExtension) throws CheckedServiceException;
	
	/**
	 *  查询一个 MaterialExtension 通过主键id
	 * @param id
	 * @return  MaterialExtension
	 * @throws CheckedServiceException
	 */
	MaterialExtension getMaterialExtensionById(Long id) throws CheckedServiceException;
	
	/**
	 * 删除MaterialExtension 通过主键id
	 * @param MaterialExtension
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialExtensionById(Long id) throws CheckedServiceException;
	
	/**
	 * 通过主键更新materialExtension不为null的字段
	 * @param MaterialExtension
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateMaterialExtension(MaterialExtension materialExtension) throws CheckedServiceException;
}
