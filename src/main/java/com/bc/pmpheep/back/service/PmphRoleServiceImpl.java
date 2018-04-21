package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.dao.PmphRolePermissionDao;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphRoleVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphRoleService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class PmphRoleServiceImpl implements PmphRoleService {

	@Autowired
	PmphRoleDao roleDao;
	@Autowired
	PmphRolePermissionDao pmphRolePermissionDao;
	@Autowired
	PmphRoleMaterialService pmphRoleMaterialService;

	@Override
	public PmphRole get(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
		}
		return roleDao.get(id);
	}
	
	  /**
     * 根据 roleName加载角色对象
     * 
     * @param id 角色ID
     * @return
     */
	@Override
	public  PmphRole getByName(String roleName) throws CheckedServiceException{
		if (StringUtil.isEmpty(roleName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,CheckedExceptionResult.NULL_PARAM, "角色名称为空时禁止查询");
		}
		return roleDao.getByName(roleName);
	}

	@Override
	public List<PmphRole> getPmphRoleByUserId(Long userId) throws CheckedServiceException {
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		return roleDao.getPmphRoleByUserId(userId);
	}

	@Override
	public List<PmphRole> getList(String roleName) throws CheckedServiceException {
		List<PmphRole> pmphRoles = roleDao.getListRole(roleName);
		for (PmphRole pmphRole : pmphRoles) {
			List<Long> subList = this.getListPmphRolePermissionIdByRoleId(pmphRole.getId());
			pmphRole.setPmphRolePermissionChild(subList);
			if (ObjectUtil.isNull(pmphRoleMaterialService.getPmphRoleMaterialByRoleId(pmphRole.getId()))) {
				pmphRole.setMaterialPermission("00000000");
			} else {
				pmphRole.setMaterialPermission(StringUtil.tentToBinary(
						pmphRoleMaterialService.getPmphRoleMaterialByRoleId(pmphRole.getId()).getPermission()));
			}
		}

		return pmphRoles;
	}

	@Override
	public List<PmphRoleVO> getListRole() throws CheckedServiceException {
		List<PmphRoleVO> list = roleDao.listRole();
		return list;
	}

	@Override
	public PmphUserRole getUserRole(Long uid, Long roleId) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid) || ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止查询");
		}
		return roleDao.getUserRole(uid, roleId);
	}

	@Override
	public List<PmphPermission> getListRoleResource(Long roleId) throws CheckedServiceException {
		if (ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
		}
		return roleDao.getListRoleResource(roleId);
	}

	@Override
	public PmphRolePermission getResourceRole(Long roleId, Long resId) throws CheckedServiceException {
		if (ObjectUtil.isNull(roleId) || ObjectUtil.isNull(resId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止查询");
		}
		return roleDao.getResourceRole(roleId, resId);
	}

	@Override
	public List<PmphRolePermission> getListPmphRolePermission(Long roleId) {
		if (ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
		}
		return roleDao.getPmphRolePermissionByRoleId(roleId);
	}

	@Override
	public List<Long> getListPmphRolePermissionIdByRoleId(Long roleId) {
		if (ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止查询");
		}
		return roleDao.getPmphRolePermissionIdByRoleId(roleId);
	}

	@Override
	public PmphRole add(PmphRole role, Long[] ids) throws CheckedServiceException {
		if (ObjectUtil.isNull(role)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增角色");
		}
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色默认权限为空时禁止新增角色");
		}
		if (StringUtil.getLength(role.getRoleName()) > 10) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "角色名称不能超过20个字符");
		}
		if (ObjectUtil.notNull(roleDao.getPmphRoleId(role.getRoleName()))) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "角色名称重复了");
		}
		if (!StringUtil.isEmpty(role.getNote()) && StringUtil.getLength(role.getNote()) > 10) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "备注不能超过20个字符");
		}
		this.addPmphRole(role);
		// roleDao.add(role);
		for (Long id : ids) {
			pmphRolePermissionDao.addPmphRolePermission(new PmphRolePermission(role.getId(), id));
		}
		return role;
	}

	@Override
	public Integer addUserRole(Long uid, Long roleId) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid) || ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止新增");
		}
		return roleDao.addUserRole(uid, roleId);
	}

	@Override
	public Integer addRoleResource(Long roleId, List<Long> permissionIds) throws CheckedServiceException {
		// 添加时先删除当前权限
		deleteRoleResourceByRoleId(roleId);
		if (CollectionUtil.isEmpty(permissionIds)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止新增");
		}
		// PmphRoleMaterial pmphRoleMaterial =
		// new PmphRoleMaterial(roleId, Integer.valueOf(materailId, 2));
		// if
		// (ObjectUtil.isNull(pmphRoleMaterialService.getPmphRoleMaterialByRoleId(roleId)))
		// {
		// pmphRoleMaterialService.addPmphRoleMaterial(pmphRoleMaterial);
		// } else {
		// pmphRoleMaterialService.updatePmphRoleMaterialByRoleId(pmphRoleMaterial);
		// }
		List<PmphRolePermission> lists = new ArrayList<PmphRolePermission>(permissionIds.size());
		PmphRolePermission pmphRolePermission;
		for (Long permissionId : permissionIds) {
			pmphRolePermission = new PmphRolePermission();
			pmphRolePermission.setRoleId(roleId);
			pmphRolePermission.setPermissionId(permissionId);
			lists.add(pmphRolePermission);
		}
		return roleDao.addRoleResource(lists);
	}

	@Override
	public Integer update(PmphRole role) throws CheckedServiceException {
		if (ObjectUtil.isNull(role.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止更新");
		}
		if (StringUtil.getLength(role.getRoleName()) > 10) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "角色名称不能超过20个字符");
		}
		if ("系统管理员".equals(role.getRoleName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "系统管理员不能修改");
		}
		if ("编辑".equals(role.getRoleName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "编辑不能修改");
		}
		Long roleId = roleDao.getPmphRoleId(role.getRoleName());
		if (ObjectUtil.notNull(roleDao.getPmphRoleId(role.getRoleName()))) {
			if (!role.getId().equals(roleId)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "角色名称重复了");
			}
		}
		if (!StringUtil.isEmpty(role.getNote()) && StringUtil.getLength(role.getNote()) > 10) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "备注不能超过20个字符");
		}
		return roleDao.update(role);
	}

	@Override
	public Integer delete(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
		}
		return roleDao.delete(id);
	}

	@Override
	public Integer deleteRoleAndResource(List<Long> ids) throws CheckedServiceException {
		if (0 == ids.size()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
		}
		roleDao.batchDelete(ids);
		Integer count = roleDao.batchDeleteRoleResource(ids);
		return count;
	}

	@Override
	public Integer deleteUserRole(Long uid, Long roleId) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid) || ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止删除");
		}
		return roleDao.deleteUserRole(uid, roleId);
	}

	@Override
	public Integer deleteUserRoles(Long uid) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除");
		}
		return roleDao.deleteUserRoles(uid);
	}

	@Override
	public Integer deleteRoleResource(Long roleId, Long resId) throws CheckedServiceException {
		if (ObjectUtil.isNull(roleId) || ObjectUtil.isNull(resId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或资源ID为空时禁止删除");
		}
		return roleDao.deleteRoleResource(roleId, resId);
	}

	@Override
	public Integer deleteRoleAndUser(List<Long> ids) throws CheckedServiceException {
		if (0 == ids.size()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除");
		}
		return roleDao.deleteRoleAndUser(ids);
	}

	@Override
	public Integer deleteRoleResourceByRoleId(Long roleId) throws CheckedServiceException {
		if (ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID为空时禁止删除");
		}
		return roleDao.deleteRoleResourceByRoleId(roleId);
	}

	@Override
	public PmphRole addPmphRole(PmphRole role) throws CheckedServiceException {
		if (ObjectUtil.isNull(role)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色属性为空时禁止新增角色");
		}
		roleDao.add(role);
		return role;
	}

	@Override
	public List<PmphUserRole> getUserRoleList(Long userId, Long roleId) {
		if (ObjectUtil.isNull(userId) || ObjectUtil.isNull(roleId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ROLE_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "角色ID或用户ID为空时禁止查询");
		}
		return roleDao.getUserRoleList(userId, roleId);
	}


	@Override
	public List<Map<String, Object>> userPermission(Long id) {
		return roleDao.userPermission(id);
	}

}
