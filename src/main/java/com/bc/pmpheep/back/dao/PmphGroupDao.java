package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.vo.PmphGroupListVO;

/**
 * PmphGroup 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphGroupDao {
	/**
	 * 
	 * @param  pmphGroup 实体对象
	 * @return   影响行数
	 */
	Integer addPmphGroup (PmphGroup pmphGroup) ;
	
	/**
	 * 
	 * @param id 主键ID
	 * @return  PmphGroup
	 */
	PmphGroup getPmphGroupById(Long  id) ;
	
	/**
	 * 
	 * @param id 主键ID
	 * @return  影响行数
	 */
	Integer deletePmphGroupById(Long  id) ;
	
	/**
	 * PmphGroup全字段更新
	 * @param pmphGroup 必须包含主键
	 * @return 影响行数
	 */
	Integer updatePmphGroup(PmphGroup pmphGroup) ;
	
	/**
	 * 根据小组名称模糊查询获取当前用户的小组
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年9月20日 下午5:27:54
	 * @param pmphGroup ,id当前用户id
	 * @return  List<PmphGroupListVO>
	 */
	List<PmphGroupListVO> getList    (@Param("pmphGroup")PmphGroup pmphGroup,@Param("id")Long id) ;
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphGroupCount();
}
