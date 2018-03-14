package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.vo.DepartmentOptsVO;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;

/**
 * PmphDepartment 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphDepartmentDao {
	/**
	 * 
	 * @param PmphDepartment
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addPmphDepartment(PmphDepartment pmphDepartment);

	/**
	 * 
	 * @param id
	 * @return PmphDepartment
	 */
	PmphDepartment getPmphDepartmentById(Long id);
	
	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	PmphDepartment getPmphDepartmentByName(@Param("name") String name);

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 */
	Integer deletePmphDepartmentById(Long id);

	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 */
	Integer updatePmphDepartment(PmphDepartment pmphDepartment);

	/**
	 * 
	 * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * &#64;return 表单的总条数
	 * </pre>
	 */
	Long getPmphDepartmentCount();

	/**
	 * 
	 * 功能描述：获取全部出版社部门 使用示范：
	 * 
	 * @param parentId
	 *            父级id
	 * @return 出版社部门
	 */
	List<PmphUserDepartmentVO> listPmphDepartment(Long parentId);

	/**
	 * 
	 * 功能描述：模糊查询部门
	 * 
	 * @param dpName
	 *            部门名称
	 * @return 查询的结果集
	 */
	List<PmphUserDepartmentVO> listPmphDepartmentByDpName(String dpName);

	/**
	 * 根据ids批量删除PmphDepartment
	 */
	Integer deletePmphDepartmentBatch(List<Long> ids);

	/**
	 * 
	 * 
	 * 功能描述：根据父级id 和 名称查询
	 *
	 * @return
	 *
	 */
	List<PmphDepartment> getPmphDepartmentByDpNameAndParentId(PmphDepartment pmphDepartment);

	/**
	 * 根据主键id查询
	 * 
	 * @param id
	 * @return
	 */
	List<PmphUserDepartmentVO> getDepartmentId(Long id);

	/**
	 * 
	 * 
	 * 功能描述：选题申报获取分配的部门以及主任名称
	 *
	 * @param dpName
	 *            部门名称
	 * @return
	 *
	 */
	List<DepartmentOptsVO> listOpts(@Param("dpName") String dpName, @Param("pageSize") Integer pageSize,
			@Param("start") Integer start);

	/**
	 * 
	 * 
	 * 功能描述：选题申报获取分配的部门以及主任名称
	 *
	 * @param dpName
	 *            部门名称
	 * @return
	 *
	 */
	Integer listOptsTotal(@Param("dpName") String dpName);
}
