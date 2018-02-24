package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupFileDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.general.bean.FileType;
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
	private PmphGroupMemberService pmphGroupMemberService;
	@Autowired
	private FileService fileService;
	@Autowired
	private PmphGroupMessageService pmphGroupMessageService;

	@Override
	public PmphGroupFile add(PmphGroupFile pmphGroupFile) {
		if (ObjectUtil.isNull(pmphGroupFile)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组文件对象为空");
		}
		pmphGroupFileDao.addPmphGroupFile(pmphGroupFile);
		return pmphGroupFile;
	}

	/**
	 *
	 * @param pmphGroupFile
	 *            实体对象
	 * @return 带主键的 PmphGroupFile
	 * @throws CheckedServiceException
	 * @update Mryang 2017.10.13 15:30
	 */
	@Override
	public String addPmphGroupFile(Long[] ids, MultipartFile file, String sessionId)
			throws CheckedServiceException, IOException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.isNull(ids) || ids.length == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数不能为空");
		}
		if (ObjectUtil.isNull(file)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"文件不能为空");
		}
		Long userId = pmphUser.getId();
		List<PmphGroupFile> list = new ArrayList<>();
		for (Long id : ids) {
			if (ObjectUtil.isNull(id)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"小组id不能为空");
			}
			PmphGroupMemberVO pmphGroupMemberVO = pmphGroupMemberService.getPmphGroupMemberByMemberId(id, userId,
					false);
			PmphGroupFile pmphGroupFile = new PmphGroupFile(id, pmphGroupMemberVO.getId(),
					"0" + pmphGroupMemberVO.getId(), file.getOriginalFilename(), 0, null);
			Long fileLenth = file.getSize();
			pmphGroupFile.setFileSize(Double.valueOf(fileLenth / 1024));
			pmphGroupFileDao.addPmphGroupFile(pmphGroupFile);
			list.add(pmphGroupFile);

		}
		String fileId = fileService.save(file, FileType.GROUP_FILE, list.get(0).getId());// list.get(0).getId()获取上传的第一条数据的id
		for (PmphGroupFile pmphGroupFile : list) {
			pmphGroupFile.setFileId(fileId);
			pmphGroupFileDao.updatePmphGroupFile(pmphGroupFile);
			PmphGroupMemberVO pmphGroupMemberVO = pmphGroupMemberService
					.getPmphGroupMemberByMemberId(pmphGroupFile.getGroupId(), userId, false);
			pmphGroupMessageService.addGroupMessage(
					pmphGroupMemberVO.getDisplayName() + "上传了文件" + file.getOriginalFilename(),
					pmphGroupFile.getGroupId(), sessionId, Const.SENDER_TYPE_0);
		}
		return "success";
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
		if (ObjectUtil.isNull(id)) {
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
	public String deletePmphGroupFileById(Long groupId, Long[] ids, String sessionId) throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		Long userId = pmphUser.getId();
		PmphGroupMemberVO currentUser = new PmphGroupMemberVO();
		if (ObjectUtil.isNull(ids) || ids.length == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		} else {
			if (!pmphUser.getIsAdmin()) {
				currentUser = pmphGroupMemberService.getPmphGroupMemberByMemberId(groupId, userId, false);
			}
			for (Long id : ids) {
				Long uploaderId = pmphGroupFileDao.getPmphGroupFileById(id).getMemberId();
				if (pmphUser.getIsAdmin() || uploaderId.equals(currentUser.getId()) || currentUser.getIsFounder()
						|| currentUser.getIsAdmin()) {// 超级管理员、小组创建者、小组管理者、文件上传人才可以删除文件
					PmphGroupFile pmphGroupFile = pmphGroupFileDao.getPmphGroupFileById(id);
					Integer num = pmphGroupFileDao.getPmphGroupFileTotalByFileId(pmphGroupFile.getFileId());
					if (1 == num) {
						fileService.remove(pmphGroupFile.getFileId());
					}
					pmphGroupFileDao.deletePmphGroupFileById(id);
				} else {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有此操作权限");
				}
			}
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
		if (ObjectUtil.isNull(pmphGroupFile.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupFileDao.updatePmphGroupFile(pmphGroupFile);
	}

	@Override
	public PageResult<PmphGroupFileVO> listGroupFile(PageParameter<PmphGroupFileVO> pageParameter) {
		if (ObjectUtil.isNull(pageParameter.getParameter().getGroupId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id不能为空");
		}
		String fileName = pageParameter.getParameter().getFileName();
		if (StringUtil.notEmpty(fileName)) {
			pageParameter.getParameter().setFileName(fileName);
		}
		PageResult<PmphGroupFileVO> pageResult = new PageResult<PmphGroupFileVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = pmphGroupFileDao.getGroupFileTotal(pageParameter);
		if (total > 0) {
			List<PmphGroupFileVO> list = pmphGroupFileDao.listGroupFile(pageParameter);
			for (PmphGroupFileVO pmphGroupFileVO : list) {
				Double fileSize = pmphGroupFileVO.getFileSize();
				if (ObjectUtil.notNull(fileSize)) {
					if (fileSize > 0) {
						pmphGroupFileVO.setFileLenth(String.format("%.2f", fileSize) + " kb");
					} else {
						pmphGroupFileVO.setFileLenth("0 KB");
					}
					if (fileSize > 1024) {
						pmphGroupFileVO.setFileLenth(String.format("%.2f", fileSize / 1024) + " M");
					}
					if (fileSize > 1024 * 1024) {
						pmphGroupFileVO.setFileLenth(String.format("%.2f", fileSize / 1024 / 1024) + " G");
					}
				} else {
					pmphGroupFileVO.setFileLenth("大小不详");
				}
				pmphGroupFileVO.setFileId(RouteUtil.MONGODB_GROUP_FILE + pmphGroupFileVO.getFileId());
			}
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public List<PmphGroupFile> listPmphGroupFileByGroupId(Long groupId) throws CheckedServiceException {
		if (ObjectUtil.isNull(groupId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id不能为空");
		}
		return pmphGroupFileDao.listPmphGroupFileByGroupId(groupId);
	}

	@Override
	public Integer updatePmphGroupFileOfDown(Long groupId, String fileId) throws CheckedServiceException {
		if (ObjectUtil.isNull(groupId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id不能为空");
		}
		if (StringUtil.isEmpty(fileId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"文件id不能为空");
		}
		return pmphGroupFileDao.updatePmphGroupFileOfDownload(groupId, fileId);
	}

}
