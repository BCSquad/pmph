package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;

/**
 * PmphGroupFile 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface  PmphGroupFileDao {
	/**
	 * 
	 * @param  pmphGroupFile 实体对象
	 * @return  影响行数
	 */
	Integer addPmphGroupFile (PmphGroupFile pmphGroupFile);
	
	/**
	 * 
	 * @param id 主键id
	 * @return  PmphGroupFile
	 */
	PmphGroupFile getPmphGroupFileById(Long  id) ;
	
	/**
	 * 
	 * @param id 主键id
	 * @return  影响行数
	 */
	Integer deletePmphGroupFileById(Long  id) ;
	
	/**
	 * 全字段更新
	 * @Param： pmphGroupFile
	 * @Return： 影响行数
	 */
	Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile) ;
	
	/**
	 * 
	 * Description:TODO
	 * @author:lyc
	 * @date:2017年9月30日上午9:20:07
	 * @Param:Page
	 * @Return:List<PmphGroupFileVO>
	 */
	List<PmphGroupFileVO> getFileList(Page<PmphGroupFileVO, PmphGroupFileVO> page);

    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphGroupFileCount();
}
