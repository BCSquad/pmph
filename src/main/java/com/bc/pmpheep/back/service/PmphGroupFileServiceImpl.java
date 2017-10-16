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
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
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

	/**
	 * 
	 * @param pmphGroupFile
	 *            实体对象
	 * @return 带主键的 PmphGroupFile
	 * @throws CheckedServiceException
	 * @update Mryang 2017.10.13 15:30
	 */
	@Override
	public String addPmphGroupFile(List<Long> ids, List<MultipartFile> files)
			throws CheckedServiceException, IOException {
		PmphUser pmphUser = (PmphUser) (ShiroSession.getShiroSessionUser().getAttribute(Const.SESSION_PMPH_USER));
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "用户没有登录");
		}
		if (null == ids || ids.size()==0 ){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "参数不能为空");
		}
		if (null == files || files.size()==0 ){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "文件不能为空");
		}
		Long memberId =pmphUser.getId();
		for (Long id : ids) {
			if (null == id) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "小组id不能为空");
			}
			for(MultipartFile file: files){
				String fileId = fileService.save(file,FileType.GROUP_FILE, 0);
				PmphGroupFile pmphGroupFile =new PmphGroupFile(id,memberId,fileId,file.getOriginalFilename(),0,null);
				pmphGroupFileDao.addPmphGroupFile(pmphGroupFile);
			}
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
		PmphUser pmphUser = (PmphUser) (ShiroSession.getShiroSessionUser().getAttribute(Const.SESSION_PMPH_USER));
		if (null == pmphUser || null == pmphUser.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.NULL_PARAM, "该用户为空");
		}
		Long id = pmphUser.getId();
		PmphGroupMember currentUser = pmphGroupMemberService.getPmphGroupMemberByMemberId(id);
	    if (null == ids || ids.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, 
			        		  CheckedExceptionResult.NULL_PARAM, "主键为空");
		  } else {
			  for (Long fileId : ids){
				  Long uploaderId = pmphGroupFileDao.getPmphGroupFileById(fileId).getMemberId();
				  if (uploaderId == currentUser.getGruopId()||currentUser.getIsFounder()
						  ||currentUser.getIsAdmin()){
					  pmphGroupFileDao.deletePmphGroupFileById(fileId);					  
				  }else{
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
		if (null != pageParameter.getParameter().getFileName()){
		    String fileName = pageParameter.getParameter().getFileName().trim();
		    if (!fileName.equals("")) {
			       pageParameter.getParameter().setFileName("%" + fileName + "%");
		    }else{
		    	pageParameter.getParameter().setFileName(fileName);
		    }
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
