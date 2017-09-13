package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroupFile;

/**
 * PmphGroupFileService 接口
 * @author Mryang
 */
public interface PmphGroupFileService {
	
	/**
	 * 
	 * @param  PmphGroupFile 实体对象
	 * @return  带主键的 PmphGroupFile
	 * @throws Exception 
	 */
	PmphGroupFile addPmphGroupFile (PmphGroupFile pmphGroupFile) throws Exception;
	
	/**
	 * 
	 * @param PmphGroupFile 必须包含主键ID
	 * @return  PmphGroupFile
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroupFile getPmphGroupFileById(PmphGroupFile pmphGroupFile) throws Exception;
	
	/**
	 * 
	 * @param PmphGroupFile
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupFileById(PmphGroupFile pmphGroupFile) throws Exception;
	
	/**
	 * @param PmphGroupFile
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupFileById(PmphGroupFile pmphGroupFile) throws Exception;
}
