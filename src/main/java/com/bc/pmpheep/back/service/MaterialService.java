package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface MaterialService {

    /**
     * 新增一个Material
     * 
     * @param Material 实体对象
     * @return 带主键的 Material thorws CheckedServiceException
     */
    Material addMaterial(Material material) throws CheckedServiceException;

    /**
     * 查询一个 Material 通过主键id
     * 
     * @param id
     * @return Material
     * @throws CheckedServiceException
     */
    Material getMaterialById(Long id) throws CheckedServiceException;

    /**
     * 删除Material 通过主键id
     * 
     * @param Material
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteMaterialById(Long id) throws CheckedServiceException;

    /**
     * 通过主键id更新material 不为null 的字段
     * 
     * @param Material
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateMaterial(Material material) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取教材集合
     * 使用示范：
     *
     * @param materialName 教材名称
     * @return
     * </pre>
     */
    List<Material> getListMaterial(String materialName);

}
