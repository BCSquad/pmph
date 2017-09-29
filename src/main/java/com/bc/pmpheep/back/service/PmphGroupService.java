package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口
 * 
 * @author Mryang
 */
public interface PmphGroupService {
	/**
	 * 
	 * @param pmphGroup
	 *            实体对象
	 * @return 带主键的PmphGroup
	 * @throws CheckedServiceException
	 */
	PmphGroup addPmphGroup(PmphGroup pmphGroup) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 *            主键ID
	 * @return PmphGroup
	 * @throws CheckedServiceException
	 */
	PmphGroup getPmphGroupById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 *            主键ID
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphGroupById(Long id) throws CheckedServiceException;

	/**
	 * PmphGroup全字段更新
	 * 
	 * @param pmphGroup
	 *            必须包含主键
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroup(PmphGroup pmphGroup) throws CheckedServiceException;

	/**
	 * 
	 * @introduction 根据小组名称模糊查询获取当前用户的小组
	 * @author Mryang
	 * @createDate 2017年9月20日 下午4:45:48
	 * @param pmphGroup
	 * @return List<PmphGroupListVO>
	 * @throws CheckedServiceException
	 */
	List<PmphGroupListVO> getList(PmphGroup pmphGroup) throws CheckedServiceException;
	
	/**
	 * 
	 *  
	 * 功能描述：新增小组
	 *
	 * @param file 上传的头像
	 * @param pmphGroup 新增的小组信息
	 * @return 带主键的PmphGroup
	 * @throws CheckedServiceException
	 *
	 */
	PmphGroup addPmphGroupOnGroup(MultipartFile file, PmphGroup pmphGroup) throws CheckedServiceException, IOException;
}
