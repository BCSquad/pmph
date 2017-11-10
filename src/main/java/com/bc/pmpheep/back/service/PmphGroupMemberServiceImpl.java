package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupMemberDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;
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
		PmphGroupMember pmphGroupMemberVO = pmphGroupMemberDao.getPmphGroupMemberById(id);
		return pmphGroupMemberVO;
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
	public List<PmphGroupMemberVO> listPmphGroupMember(Long groupId, String sessionId) throws CheckedServiceException {

		if (null == groupId || groupId == 0) {
			List<PmphGroupListVO> myPmphGroupListVOList = pmphGroupService.listPmphGroup(new PmphGroup(), sessionId);
			if (null == myPmphGroupListVOList || myPmphGroupListVOList.size() == 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"你没有小组！");
			}
			groupId = myPmphGroupListVOList.get(0).getId();// 初始化页面时没有参数传入则直接调用初始化时小组排序的第一个小组id
		}
		List<PmphGroupMemberVO> list = pmphGroupMemberDao.listPmphGroupMember(groupId);
		for (PmphGroupMemberVO pmphGroupMemberVO : list) {
			if (pmphGroupMemberVO.getIsWriter()) {
				pmphGroupMemberVO.setAvatar(writerUserService.get(pmphGroupMemberVO.getUserId()).getAvatar());
				pmphGroupMemberVO.setUserType(Const.SENDER_TYPE_2);
			} else {
				pmphGroupMemberVO.setAvatar(pmphUserService.get(pmphGroupMemberVO.getUserId()).getAvatar());
				pmphGroupMemberVO.setUserType(Const.SENDER_TYPE_1);
			}
		}
		return list;
	}

	@Override
	public String addPmphGroupMemberOnGroup(Long groupId, List<PmphGroupMember> pmphGroupMembers, String sessionId)
			throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (pmphUser.getIsAdmin() || isFounderOrisAdmin(groupId, sessionId)) {// 是超级管理员或者该小组的创建人和管理员才可以添加成员
			if (pmphGroupMembers.size() > 0) {
				for (PmphGroupMember pmphGroupMember : pmphGroupMembers) {
					if (null == pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, pmphGroupMember.getUserId(),
							pmphGroupMember.getIsWriter())) {
						if (null == pmphGroupMember.getUserId()) {
							throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
									CheckedExceptionResult.ILLEGAL_PARAM, "成员id为空");
						}
						if (pmphGroupMember.getIsWriter()) {
							WriterUser writerUser = writerUserService.get(pmphGroupMember.getUserId());
							pmphGroupMember.setDisplayName(writerUser.getRealname());
						} else {
							PmphUser user = pmphUserService.get(pmphGroupMember.getUserId());
							pmphGroupMember.setDisplayName(user.getRealname());
						}
						pmphGroupMember.setGroupId(groupId);
						pmphGroupMemberDao.addPmphGroupMember(pmphGroupMember);

					}
				}
				result = "SUCCESS";
			} else {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
						"参数为空");
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有此操作权限");
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

	@Override
	public Boolean isFounderOrisAdmin(Long groupId, String sessionId) throws CheckedServiceException {
		boolean flag = false;
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		Long memberId = pmphUser.getId();
		PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, memberId, false);
		if (currentUser.getIsFounder() || currentUser.getIsAdmin()) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Boolean isFounder(Long groupId, String sessionId) throws CheckedServiceException {
		boolean flag = false;
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		Long memberId = pmphUser.getId();
		PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, memberId, false);
		if (currentUser.getIsFounder()) {
			flag = true;
		}
		return flag;
	}

	@Override
	public PageResult<PmphGroupMemberManagerVO> listGroupMemberManagerVOs(
			PageParameter<PmphGroupMemberManagerVO> pageParameter) throws CheckedServiceException {
		if (null == pageParameter.getParameter().getGroupId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id不能为空");
		}
		String name = pageParameter.getParameter().getName();
		if (StringUtil.notEmpty(name)) {
			pageParameter.getParameter().setName(name);
		}
		PageResult<PmphGroupMemberManagerVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = pmphGroupMemberDao.groupMemberTotal(pageParameter);
		if (total > 0) {
			List<PmphGroupMemberManagerVO> list = pmphGroupMemberDao.listGroupMemberManagerVOs(pageParameter);
			for (PmphGroupMemberManagerVO pmphGroupMemberManagerVO : list) {
				if (pmphGroupMemberManagerVO.getIsWriter()) {
					pmphGroupMemberManagerVO.setUserType(Const.SENDER_TYPE_2);
				} else {
					pmphGroupMemberManagerVO.setUserType(Const.SENDER_TYPE_1);
				}
			}
			pageResult.setRows(list);
			pageResult.setTotal(total);
		}
		return pageResult;
	}

	@Override
	public String deletePmphGroupMemberByIds(Long groupId, Long[] ids, String sessionId)
			throws CheckedServiceException {
		String result = "FAIL";
		if (!isFounderOrisAdmin(groupId, sessionId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有操作权限");
		}
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"该用户为空");
		}
		Long userid = pmphUser.getId();
		PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, userid, false);
		if (null == ids || ids.length == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键不能为空");
		} else {
			for (Long id : ids) {
				PmphGroupMember pmphGroupMember = pmphGroupMemberDao.getPmphGroupMemberById(id);
				if (userid == pmphGroupMember.getUserId() && !pmphGroupMember.getIsWriter()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "不能删除自己");
				}
				if (pmphUser.getIsAdmin() || currentUser.getIsFounder()) {// 只有小组创建者和超级管理员可以删除小组成员
					if (pmphGroupMemberDao.getPmphGroupMemberById(id).getIsFounder()) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.ILLEGAL_PARAM, "小组创建者不能删除，请重新选择");
					}
					pmphGroupMemberDao.deletePmphGroupMemberById(id);
				}
				if (currentUser.getIsAdmin() && (pmphGroupMember.getIsFounder() || pmphGroupMember.getIsAdmin())) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "管理员不能删除创建者或其他管理员");
				} else {
					pmphGroupMemberDao.deletePmphGroupMemberById(id);
				}
			}
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public PmphGroupMemberVO getPmphGroupMemberByMemberId(Long groupId, Long userId, Boolean isWriter)
			throws CheckedServiceException {
		if (null == userId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"组员id不能为空");
		}
		PmphGroupMemberVO pmphGroupMemberVO = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, userId,
				isWriter);
		if (ObjectUtil.isNull(pmphGroupMemberVO)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"你不是该小组的组员");
		}
		pmphGroupMemberVO.setAvatar(pmphUserService.get(userId).getAvatar());
		return pmphGroupMemberVO;
	}

	@Override
	public String updateMemberIdentity(Long groupId, List<PmphGroupMember> members, String sessionId)
			throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (pmphUser.getIsAdmin() || isFounder(groupId, sessionId)) {// 只有小组创建者与超级管理员可以修改小组人员权限
			if (null == members || members.size() == 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"参数不能为空");
			} else {
				for (PmphGroupMember pmphGroupMember : members) {
					PmphGroupMember member = pmphGroupMemberDao.getPmphGroupMemberById(pmphGroupMember.getId());
					if (member.getIsFounder()) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.NULL_PARAM, "不要把创建者添加为管理员了");
					}
					if (pmphGroupMember.getIsAdmin()) {
						if (member.getIsAdmin()) {
							throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
									CheckedExceptionResult.NULL_PARAM, "该用户已经是管理员了");
						} else {
							pmphGroupMember.setIsAdmin(true);
						}
					} else {
						if (member.getIsAdmin()) {
							pmphGroupMember.setIsAdmin(false);
						} else {
							throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
									CheckedExceptionResult.NULL_PARAM, "该成员不是小组管理员");
						}

					}
					pmphGroupMemberDao.updatePmphGroupMember(pmphGroupMember);
				}
				result = "SUCCESS";
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"只有创建者有此权限操作");
		}

		return result;

	}

}
