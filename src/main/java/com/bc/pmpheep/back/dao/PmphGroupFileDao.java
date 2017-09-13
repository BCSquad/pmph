package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroupFile;

/**
 * PmphGroupFile 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface  PmphGroupFileDao {
	/**
	 * 
	 * @param  PmphGroupFile 实体对象
	 * @return  带主键的 PmphGroupFile
	 * @throws Exception 
	 */
	PmphGroupFile addPmphGroupFile (PmphGroupFile pmphGroupFile) ;
	
	/**
	 * 
	 * @param PmphGroupFile 必须包含主键ID
	 * @return  PmphGroupFile
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroupFile getPmphGroupFileById(PmphGroupFile pmphGroupFile) ;
	
	/**
	 * 
	 * @param PmphGroupFile
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupFileById(PmphGroupFile pmphGroupFile) ;
	
	/**
	 * @param PmphGroupFile
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupFileById(PmphGroupFile pmphGroupFile) ;
}
