package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupMemberDao;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
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
	PmphGroupService pmphGroupService;

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
	public List<PmphGroupMemberVO> getListPmphGroupMember(Long groupId) throws CheckedServiceException {
		List<PmphGroupMemberVO> list = new ArrayList<>();
		if (null == groupId || groupId == 0) {
			groupId = pmphGroupService.getList(new PmphGroup()).get(0).getId();// 初始化页面时没有参数传入则直接调用初始化时小组排序的第一个小组id
		}
		list = pmphGroupMemberDao.getListPmphGroupMember(groupId);
		return list;
	}
}
