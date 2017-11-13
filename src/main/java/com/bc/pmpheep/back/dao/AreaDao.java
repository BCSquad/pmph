package com.bc.pmpheep.back.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.vo.AreaTreeVO;

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
	 * 获取area集通过parentId (返回的只有一层List)
	 * @author Mryang
	 * @createDate 2017年9月25日 上午11:57:29
	 * @param parentId
	 * @return
	 */
	List<AreaTreeVO> getAreaByParentId(Long parentId);
	
	/**
	 * 根据ids批量删除
	 */
	Integer deleteAreaBatch(List<Long> ids);
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
	 
	 /**
	  * 功能描述：根据区域名称获取区域id
	  * @param areaName 区域名称
	  * @return id 区域id
	  */
	 Long getAreaId(String areaName);
	 
	 /**
	  * 功能描述：获取区域机构id的数量
	  * @param id
	  * @return
	  */
	 //Long getAreaByOrgId(Long id);
	 Long getOrgId(Long id);
}
