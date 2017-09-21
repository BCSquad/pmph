package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * AreaService 接口
 * 
 * @author Mryang
 *
 */
public interface AreaService {

    /**
     * 新增一个Area
     * 
     * @param area 实体对象
     * @return 带主键的 area thorws CheckedServiceException
     */
    Area addArea(Area area) throws CheckedServiceException;

    /**
     * 查询一个 Area 通过主键id
     * 
     * @param id
     * @return area
     * @throws CheckedServiceException
     */
    Area getAreaById(Long id) throws CheckedServiceException;

    /**
     * 删除Area 通过主键id
     * 
     * @param area
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteAreaById(Long id) throws CheckedServiceException;
  
    /**
     * 
     * <pre>
     * 功能描述：删除整张表数据
     * 使用示范：
     *
     * @throws CheckedServiceException
     * </pre>
     */
    void deleteAllArea() throws CheckedServiceException;

    /**
     * 更新一个 Area通过主键id
     * 
     * @param area
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateArea(Area area) throws CheckedServiceException;

    Page getTest();
}
