package com.bc.pmpheep.back.dao;


import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Area;

/**
 * AreaDao实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface AreaDao {

	/**
	 * 新增一个Area
	 * 
	 * @param area
	 *            实体对象
	 * @return 带主键的Area 
	 */
	Area addArea(Area area);

	/**
	 * 删除Area 通过主键id
	 * 
	 * @param area
	 * @return 影响行数
	 */
	Integer deleteAreaById(Area area);

	/**
	 * 更新一个 Area通过主键id
	 * 
	 * @param area
	 * @return 影响行数
	 */
	Integer updateAreaById(Area area);

	/**
	 * 查询一个 Area 通过主键id
	 * 
	 * @param area
	 *            必须包含主键ID
	 * @return area
	 */
	Area getAreaById(Area area);

}
