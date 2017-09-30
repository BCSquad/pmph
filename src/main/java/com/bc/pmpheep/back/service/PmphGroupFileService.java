package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * PmphGroupFileService 接口
 * @author Mryang
 */
public interface PmphGroupFileService {
	
	/**
	 * 
	 * @param  PmphGroupFileVO 实体对象
	 * @return  上传成功与否提示信息
	 * @throws  CheckedServiceException
	 */
	String addPmphGroupFile (List<PmphGroupFile> pmphGroupFiles , MultipartFile file) 
			throws  CheckedServiceException , IOException;
	
	/**
	 * 
	 * @param id 主键id
	 * @return  PmphGroupFile
	 * @throws CheckedServiceException
	 */
	PmphGroupFile getPmphGroupFileById(Long  id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id 主键id
	 * @return  删除成功与否状态提示
	 * @throws CheckedServiceException
	 */
	String deletePmphGroupFileById(List<Long> ids) throws CheckedServiceException;
	
	/**
	 * 全字段更新
	 * @param pmphGroupFile
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile) throws CheckedServiceException;
	
	/**
	 * 
	 * Description:获取小组共享文件
	 * @author:lyc
	 * @date:2017年9月30日上午11:31:14
	 * @Param:page传入的查询条件,若有文件名则为模糊查询
	 * @Return:List<PmphGroupFileVO>查询到的小组文件集合
	 * @throws CheckedServiceException
	 */
	Page<PmphGroupFileVO , PmphGroupFileVO> getGroupFileList(Page<PmphGroupFileVO, PmphGroupFileVO> page) 
			throws CheckedServiceException;
	
}
