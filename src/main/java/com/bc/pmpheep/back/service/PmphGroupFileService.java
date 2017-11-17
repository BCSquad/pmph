package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupFileService 接口
 *
 * @author Mryang
 */
public interface PmphGroupFileService {

	/**
	 * 增加（保存）一个PmphGroupFile对象
	 * 
	 * @param pmphGroupFile
	 *            要保存的实例
	 * @return 返回含主键的被保存实例
	 */
	PmphGroupFile add(PmphGroupFile pmphGroupFile);

	/**
	 *
	 * @param PmphGroupFileVO
	 *            实体对象
	 * @return 上传成功与否提示信息
	 * @throws CheckedServiceException
	 */
	String addPmphGroupFile(Long[] ids, MultipartFile file,  HttpServletRequest request)
			throws CheckedServiceException, IOException;

	/**
	 *
	 * @param id
	 *            主键id
	 * @return PmphGroupFile
	 * @throws CheckedServiceException
	 */
	PmphGroupFile getPmphGroupFileById(Long id) throws CheckedServiceException;

	/**
	 *
	 * @param id
	 *            主键id
	 * @return 删除成功与否状态提示
	 * @throws CheckedServiceException
	 */
	String deletePmphGroupFileById(Long groupId, Long[] ids, HttpServletRequest request) throws CheckedServiceException;

	/**
	 * 全字段更新
	 *
	 * @param pmphGroupFile
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile) throws CheckedServiceException;

	/**
	 *
	 * Description:获取小组共享文件
	 *
	 * @author:lyc
	 * @date:2017年9月30日上午11:31:14
	 * @Param:pageParameter传入的查询条件,若有文件名则为模糊查询
	 * @Return:List<PmphGroupFileVO>查询到的小组文件集合
	 * @throws CheckedServiceException
	 */
	PageResult<PmphGroupFileVO> listGroupFile(PageParameter<PmphGroupFileVO> pageParameter)
			throws CheckedServiceException;

	/**
	 *
	 *
	 * 功能描述：根据小组获取该组所有文件
	 *
	 * @param groupId
	 *            小组id
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	List<PmphGroupFile> listPmphGroupFileByGroupId(Long groupId) throws CheckedServiceException;

	/**
	 *
	 *
	 * 功能描述：下载之后下载次数+1
	 *
	 * @param
	 *
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Integer updatePmphGroupFileOfDown(Long groupId, String fileId) throws CheckedServiceException;

}
