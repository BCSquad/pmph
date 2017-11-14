package com.bc.pmpheep.back.service;

import java.util.List;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.vo.AreaTreeVO;
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
     * 新增一个Area数据迁移
     * 
     * @param area 实体对象
     * @return 带主键的 area thorws CheckedServiceException
     */
    Area addAreaStage(Area area) throws CheckedServiceException;

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
    
    /**
     * 
     * 根据父级id获取下面的所有区域 
     * @author Mryang
     * @createDate 2017年9月25日 上午11:47:08
     * @param parentId
     * @return
     * @throws CheckedServiceException
     */
    List<AreaTreeVO> getAreaTreeVO(Long parentId) throws CheckedServiceException;
    
    /**
     * 根据id删除当前area和他下面的area
     * @author Mryang
     * @createDate 2017年9月25日 下午2:39:10
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    Integer deleteAreaBatch(Long id) throws CheckedServiceException;
    
    /**
     * 根据父级id获取下一级子节点
     * @author Mryang
     * @createDate 2017年9月30日 下午2:57:38
     * @param parentId
     * @return
     * @throws CheckedServiceException
     */
    List<AreaTreeVO> getAreaChirldren(Long parentId) throws CheckedServiceException;
    
}
