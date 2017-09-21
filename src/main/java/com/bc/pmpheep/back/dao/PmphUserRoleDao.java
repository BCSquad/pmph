package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.PmphUserRole;

/**
 * 
 *PmphUserRole 实体类数据访问层接口 
 *
 * @author Mryang
 *
 * @createDate 2017年9月19日 上午11:42:36
 *
 */
@Repository
public interface PmphUserRoleDao {

	/**
	 * 
	 * @introduction  新增 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:34:12
	 * @param pmphUserRole
	 * @return  影响行数
	 */
	Integer addPmphUserRole(PmphUserRole pmphUserRole) ;
    
	/**
	 * 
	 * @introduction   根据id查询 PmphUserRole 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:33:43
	 * @param id
	 * @return  PmphUserRole
	 */
	PmphUserRole getPmphUserRoleById(Long id)  ;

	/**
	 * 根据id删除一个 PmphUserRole 
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:32:59
	 * @param id
	 * @return 影响行数
	 */
	Integer deletePmphUserRoleById(Long id) ;

	/**
	 * 
	 * @introduction  通过主键id更新PmphUserRole 不为null 的字段
	 * @author Mryang
	 * @createDate 2017年9月19日 上午11:31:17
	 * @param pmphUserRole
	 * @return 影响行数
	 */
	Integer updatePmphUserRole(PmphUserRole pmphUserRole) ;
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getPmphUserRoleCount();
}
