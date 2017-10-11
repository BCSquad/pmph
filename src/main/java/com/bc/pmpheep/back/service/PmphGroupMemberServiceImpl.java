package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupMemberDao;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupMemberService 接口实现
 * 
 * @author Mryang
 *
 */
@Service
public class PmphGroupMemberServiceImpl extends BaseService implements PmphGroupMemberService {
	@Autowired
	private PmphGroupMemberDao pmphGroupMemberDao;
	@Autowired
	private PmphGroupService pmphGroupService;
	@Autowired
	private PmphUserService pmphUserService;
	@Autowired
	private WriterUserService writerUserService;

	/**
	 * 
	 * @param pmphGroupMember
	 *            实体对象
	 * @return 带主键的 PmphGroupMember
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupMember addPmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException {
		if (null == pmphGroupMember.getDisplayName()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组内显示名称为空");
		}
		pmphGroupMemberDao.addPmphGroupMember(pmphGroupMember);
		return pmphGroupMember;
	}

	/**
	 * 
	 * @param 主键id
	 * @return PmphGroupMember
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupMember getPmphGroupMemberById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMemberDao.getPmphGroupMemberById(id);
	}

	/**
	 * 
	 * @param 主键id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphGroupMemberById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMemberDao.deletePmphGroupMemberById(id);
	}

	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updatePmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException {
		if (null == pmphGroupMember.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMemberDao.updatePmphGroupMember(pmphGroupMember);
	}

	@Override
	public List<PmphGroupMemberVO> listPmphGroupMember(Long groupId) throws CheckedServiceException {
		List<PmphGroupMemberVO> list = new ArrayList<>();
		if (null == groupId || groupId == 0) {
			List<PmphGroupListVO> myPmphGroupListVOList =pmphGroupService.listPmphGroup(new PmphGroup());
			if(null == myPmphGroupListVOList || myPmphGroupListVOList.size() == 0){
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "你没有小组！");
			}
			groupId = myPmphGroupListVOList.get(0).getId();// 初始化页面时没有参数传入则直接调用初始化时小组排序的第一个小组id
		}
		list = pmphGroupMemberDao.listPmphGroupMember(groupId);
		for (PmphGroupMemberVO pmphGroupMemberVO : list) {
			if (pmphGroupMemberVO.isIsWriter()) {
				pmphGroupMemberVO.setAvatar(writerUserService.get(pmphGroupMemberVO.getMemberId()).getAvatar());
			} else {
				pmphGroupMemberVO.setAvatar("");
			}
		}
		return list;
	}

	@Override
	public String addPmphGroupMemberOnGroup(List<PmphGroupMember> pmphGroupMembers) throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = (PmphUser) (ShiroSession.getShiroSessionUser().getAttribute(Const.SESSION_PMPH_USER));
		if (null == pmphUser||null == pmphUser.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		Long id = pmphUser.getId();
		PmphGroupMember currentUser = pmphGroupMemberDao.getPmphGroupMemberById(id);
		if (currentUser.isIsFounder()|currentUser.isIsAdmin()){
		  if (pmphGroupMembers.size() > 0) {
			 for (PmphGroupMember pmphGroupMember : pmphGroupMembers) {
				if (null == pmphGroupMember.getGruopId()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "成员小组id为空");
				}
				if (null == pmphGroupMember.getMemberId()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "成员id为空");
				}
				if (null == pmphGroupMember.getDisplayName()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "成员名称为空");
				}
				pmphGroupMemberDao.addPmphGroupMember(pmphGroupMember);

			}
			result = "SUCCESS";
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"参数为空");
		}
	   }else{
		   throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
				   CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有此操作权限");
	   }
		return result;
	}

	@Override
	public String deletePmphGroupMemberOnGroup(Long groupId) throws CheckedServiceException {
		String result = "FAIL";
		if (null == groupId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"小组id为空");
		}
		int num = pmphGroupMemberDao.deletePmphGroupMemberOnGroup(groupId);
		if (num > 0) {
			result = "SUCCESS";
		}

		return result;
	}
}
