package com.bc.pmpheep.back.service;



import com.bc.pmpheep.back.po.Area;

/**
 * AreaService 接口
 * @author Mryang
 *
 */
public interface AreaService {
	
	/**
	 * 新增一个Area 
	 * @param area 实体对象
	 * @return 带主键的Area 
	 */
	Area addArea(Area area) throws Exception;
	
	/**
	 *  查询一个 Area 通过主键id
	 * @param area 必须包含主键ID
	 * @return  area
	 * @throws Exception
	 */
	Area findAreaById(Area area) throws Exception;
	
	/**
	 * 删除Area 通过主键id
	 * @param area
	 * @return  影响行数
	 * @throws Exception，NullPointerException
	 */
	Integer deleteAreaById(Area area) throws Exception;
	
	/**
	 * 更新一个 Area通过主键id
	 * @param area
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateAreaById(Area area) throws Exception;
}
