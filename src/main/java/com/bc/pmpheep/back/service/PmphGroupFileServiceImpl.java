package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupFileDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphGroupFileServiceImpl extends BaseService implements PmphGroupFileService {
	@Autowired
	private PmphGroupFileDao pmphGroupFileDao;

	@Autowired
	private FileService fileService;

	/**
	 * 
	 * @param pmphGroupFile
	 *            实体对象
	 * @return 带主键的 PmphGroupFile
	 * @throws CheckedServiceException
	 */
	@Override
	public String addPmphGroupFile(List<PmphGroupFile> pmphGroupFiles, MultipartFile file)
			throws CheckedServiceException, IOException {
		String result = "FAIL";
		if (pmphGroupFiles.size() > 0) {
			for (PmphGroupFile pmphGroupFile : pmphGroupFiles) {
				if (null == pmphGroupFile.getGroupId()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
							"小组id不能为空");
				}
				if (null == pmphGroupFile.getMemberId()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
							"成员id不能为空");
				}
				if (null == pmphGroupFile.getFileName()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
							"文件名称不能为空");
				}
				pmphGroupFile.setFileId(fileService.save(file));
				pmphGroupFileDao.addPmphGroupFile(pmphGroupFile);
			}
			result = "SUCCESS";
		}
		return result;
	}

	/**
	 * 
	 * @param id
	 *            主键id
	 * @return PmphGroupFile
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupFile getPmphGroupFileById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupFileDao.getPmphGroupFileById(id);
	}

	/**
	 * 
	 * @param id
	 *            主键id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public String deletePmphGroupFileById(List<Long> ids) throws CheckedServiceException {
		String result = "FAIL";
		if (null == ids || ids.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		} else {
			pmphGroupFileDao.deletePmphGroupFileById(ids);
			result = "SUCCESS";
		}
		return result;
	}

	/**
	 * @param PmphGroupFileVO
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile) throws CheckedServiceException {
		if (null == pmphGroupFile.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupFileDao.updatePmphGroupFile(pmphGroupFile);
	}

	@Override
	public PageResult<PmphGroupFileVO> listGroupFile(PageParameter<PmphGroupFileVO> pageParameter) {
		if (null == pageParameter.getParameter().getGroupId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id不能为空");
		}
		String fileName = pageParameter.getParameter().getFileName();
		if (!fileName.equals("")) {
			pageParameter.getParameter().setFileName("%" + fileName + "%");
		}
		PageResult<PmphGroupFileVO> pageResult = new PageResult<PmphGroupFileVO>();
		Tools.CopyPageParameter(pageParameter, pageResult);
		int total = pmphGroupFileDao.getGroupFileTotal(pageParameter);
		if (total > 0) {
			pageResult.setTotal(total);
			List<PmphGroupFileVO> list = pmphGroupFileDao.listGroupFile(pageParameter);
			pageResult.setRows(list);
		}
		return pageResult;
	}

}
