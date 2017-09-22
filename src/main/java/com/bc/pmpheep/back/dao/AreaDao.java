package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.Page;
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
	 * @return 影响行数
	 */
	Integer addArea(Area area);

	/**
	 * 删除Area 通过主键id
	 * 
	 * @param area
	 * @return 影响行数
	 */
	Integer deleteAreaById(Long id);

	/**
	 * 更新一个 Area通过主键id
	 * 
	 * @param area
	 * @return 影响行数
	 */
	Integer updateArea(Area area);

	/**
	 * 查询一个 Area 通过主键id
	 * 
	 * @param area
	 *            必须包含主键ID
	 * @return area
	 */
	Area getAreaById(Long id);
	
	/**
	 * 
	 * <pre>
	 * 功能描述：删除整张表数据
	 * 使用示范：
	 *
	 * </pre>
	 */
	 void deleteAllArea();
	 
	 /**
	  * 
	  * <pre>
	  * 功能描述：查询表单总条数
	  * 使用示范：
	  *
	  * @return 表单的总条数
	  * </pre>
	  */
	 Long getAreacount();

	// 测试
	List<Area> getTest(Page<Area,Area> p);

	Integer getTestTotal(Page<Area,Area> p);
}
