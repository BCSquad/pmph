package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.dao.PmphDepartmentDao;
import com.bc.pmpheep.back.dao.PmphPermissionDao;
import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.dao.PmphUserDao;
import com.bc.pmpheep.back.dao.PmphUserRoleDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.ValidatUtil;
import com.bc.pmpheep.back.vo.BookCorrectionAuditVO;
import com.bc.pmpheep.back.vo.BookUserCommentVO;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.back.vo.MaterialListVO;
import com.bc.pmpheep.back.vo.PmphEditorVO;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.back.vo.PmphIdentity;
import com.bc.pmpheep.back.vo.PmphRoleVO;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.back.vo.TopicDeclarationVO;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphUserService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class PmphUserServiceImpl implements PmphUserService {

	@Autowired
	PmphUserDao pmphUserDao;
	@Autowired
	PmphRoleDao pmphRoleDao;
	@Autowired
	PmphDepartmentDao pmphDepartmentDao;
	@Autowired
	PmphPermissionDao permissionDao;
	@Autowired
	PmphUserRoleDao pmphUserRoleDao;
	@Autowired
	private FileService fileService;
	@Autowired
	MaterialService materialService;
	@Autowired
	private PmphGroupService pmphGroupService;
	@Autowired
	CmsContentService cmsContentService;
	@Autowired
	BookCorrectionService bookCorrectionService;
	@Autowired
	BookUserCommentService bookUserCommentService;
	@Autowired
	WriterUserService writerUserService;
	@Autowired
	OrgUserService orgUserService;
	@Autowired
	TopicService topicService;
	@Autowired
    PmphRoleService roleService;
	@Autowired
	PmphUserService pmphUserService;
	
	@Override
	public boolean updatePersonalData(PmphUser pmphUser, MultipartFile file) throws IOException {
		Long id = pmphUser.getId();
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止更新用户");
		}
		// 头像文件不为空
		if (null != file) {
			if (StringUtil.notEmpty(pmphUser.getAvatar())) {
				fileService.remove(pmphUser.getAvatar());
			}
			String newAvatar = fileService.save(file, ImageType.PMPH_USER_AVATAR, id);
			pmphUser.setAvatar(newAvatar);
		}
		pmphUserDao.update(pmphUser);
		return true;
	}

	/**
	 * 返回新插入用户数据的主键
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public PmphUser add(PmphUser user) throws CheckedServiceException {
		if (StringUtil.isEmpty(user.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止新增用户");
		}
		if (StringUtil.isEmpty(user.getPassword())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "密码为空时禁止新增用户");
		}
		if (StringUtil.isEmpty(user.getRealname())) {
			user.setRealname(user.getUsername());
		}
		if (StringUtil.isEmpty(user.getAvatar())) {
			user.setAvatar(RouteUtil.DEFAULT_USER_AVATAR);
		}
		// 使用用户名作为盐值，MD5 算法加密
		user.setPassword(new DesRun("", user.getPassword()).enpsw);
		pmphUserDao.add(user);
		return user;
	}

	/**
	 * 为单个用户设置多个角色
	 * 
	 * @param user
	 * @param rids
	 */
	@Override
	public PmphUser add(PmphUser user, List<Long> rids) throws CheckedServiceException {
		Long userId = this.add(user).getId();
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时不能添加角色！");
		}
		pmphRoleDao.addUserRoles(userId, rids);
		return user;
	}

	/**
	 * 根据 user_id 删除用户数据
	 * 
	 * @param id
	 */
	@Override
	public Integer delete(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除用户");
		}
		return pmphUserDao.delete(id);
	}

	@Override
	public Integer deleteUserAndRole(List<Long> ids) throws CheckedServiceException {
		if (ids.size() < 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除用户！");
		}
		Integer count = 0;
		// 删除用户列表
		pmphUserDao.batchDelete(ids);
		// 依次删除这些用户所绑定的角色
		for (Long userId : ids) {
			pmphRoleDao.deleteUserRoles(userId);
			count++;
		}
		return count;
	}

	/**
	 * 
	 * 更新用户数据 1、更新用户基本信息 2、更新用户所属角色 （1）先删除所有的角色 （2）再添加绑定的角色
	 * 
	 * @param user
	 * @param rids
	 */
	@Override
	public PmphUser update(PmphUser user, List<Long> rids) throws CheckedServiceException {
		Long userId = user.getId();
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止更新用户");
		}
		pmphRoleDao.deleteUserRoles(userId);
		pmphRoleDao.addUserRoles(userId, rids);
		this.update(user);
		return user;
	}

	/**
	 * 更新单个用户信息
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public PmphUser update(PmphUser user) throws CheckedServiceException {
		if (ObjectUtil.isNull(user)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户属性为空时禁止更新用户");
		} else {
			String password = user.getPassword();
			if (StringUtil.notEmpty(password)) {
				user.setPassword(new DesRun("", user.getPassword()).enpsw);
			}
		}
		pmphUserDao.update(user);
		return user;
	}

	/**
	 * 根据主键 id 加载用户对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public PmphUser get(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		return pmphUserDao.get(id);
	}

	/**
	 * 根据主键 id 加载用户对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public PmphUser getInfo(HttpServletRequest request) throws CheckedServiceException {
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser sessionPmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == sessionPmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		Long id = sessionPmphUser.getId();
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		PmphUser pmphUser = pmphUserDao.getInfo(id);
		if (null == pmphUser) {
			pmphUser = new PmphUser();
		}
		String avatar = pmphUser.getAvatar();
		pmphUser.setAvatar(RouteUtil.userAvatar(avatar));
		return pmphUser;
	}

	@Override
	public Integer updatePassword(HttpServletRequest request, String oldPassword, String newPassword) {
		// 获取当前用户
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser sessionPmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == sessionPmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		Long id = sessionPmphUser.getId();
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		if (StringUtil.isEmpty(oldPassword)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "原密码为空");
		}
		if (StringUtil.isEmpty(newPassword)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "新密码为空");
		}
		oldPassword = oldPassword.trim();
		newPassword = newPassword.trim();
		if (newPassword.length() > 50) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "新密码长度不能超过50");
		}
		if (oldPassword.equals(newPassword)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "新旧密码不能一致");
		}
		// 先修改SSO
		// ---------------------------------
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		// 加密密码
		map.put("oldPassword", new DesRun("", oldPassword).enpsw);
		map.put("newPassword", new DesRun("", newPassword).enpsw);
		Integer res = pmphUserDao.updatePassword(map);
		if (null == res || res == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "原密码错误");
		}
		return 1;
	}

	/**
	 * 根据用户名加载用户对象（用于登录使用）
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public PmphUser getByUsernameAndPassword(String username, String password) throws CheckedServiceException {
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名或密码为空时禁止查询");
		}
		PmphUser pmphUser = pmphUserDao.getByUsernameAndPassword(username, password);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.OBJECT_NOT_FOUND, "用户名或密码错误！");
		}
		return pmphUser;
	}

	@Override
	public PageResult<PmphUserManagerVO> getListByUsernameAndRealname(String name, int number, int size)
			throws CheckedServiceException {
		if (null == name || "".equals(name)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "模糊查询条件为空");
		}
		List<PmphUser> pmphUsers = pmphUserDao.getListByUsernameAndRealname(name, (number - 1) * size, size);
		PageResult<PmphUserManagerVO> page = new PageResult<>();
		page.setFirst(true);
		page.setLast(true);
		page.setPageNumber(number);
		page.setPageSize(size);
		if (!pmphUsers.isEmpty()) {
			List<PmphUserManagerVO> list = new ArrayList<>();
			for (PmphUser user : pmphUsers) {
				PmphUserManagerVO userVO = new PmphUserManagerVO();
				PmphDepartment department = pmphDepartmentDao.getPmphDepartmentById(user.getDepartmentId());
				if (null != department) {
					userVO.setDepartmentName(department.getDpName());
				}
				try {
					BeanUtils.copyProperties(userVO, user);
				} catch (BeansException ex) {
					throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
							CheckedExceptionResult.VO_CONVERSION_FAILED, ex.getMessage());
				}
				list.add(userVO);
			}
			page.setRows(list);
		}
		return page;
	}

	/**
	 * 登录逻辑
	 * 
	 * 1、先根据用户名查询用户对象
	 * 
	 * 2、如果有用户对象，则继续匹配密码 如果没有用户对象，则抛出异常
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public PmphUser login(String username, String password) throws CheckedServiceException {
		PmphUser user = pmphUserDao.getByUsernameAndPassword(username, password);
		// 密码匹配的工作交给 Shiro 去完成
		if (ObjectUtil.isNull(user)) {
			// 因为缓存切面的原因,在这里就抛出用户名不存在的异常
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名或密码不正确！");
		} else {
			if (user.getIsDisabled()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "用户已经被禁用，请联系管理员启用该账号");
			}
		}
		return user;
	}

	/**
	 * 查询所有的用户对象列表
	 * 
	 * @return
	 */
	@Override
	public List<PmphUser> getList() throws CheckedServiceException {
		return pmphUserDao.getListUser();
	}

	/**
	 * 根据角色 id 查询是这个角色的所有用户
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<PmphUser> getListByRole(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止查询");
		}
		return pmphUserDao.getListByRole(id);
	}

	/**
	 * 查询指定用户 id 所拥有的权限
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<PmphPermission> getListAllResource(Long uid) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		List<PmphPermission> permissions = pmphUserDao.getListAllResources(uid);
		// List<PmphPermission> perList = permissions;
		// for (PmphPermission permission : permissions) {
		// List<PmphPermission> subList =
		// permissionDao.getListChildMenuByParentId(permission.getId());
		// permission.setChildren(subList);
		// if (!perList.contains(permission)) {
		// permissions.remove(permission);
		// }
		// }
		return permissions;
	}

	/**
	 * 查询指定用户所指定的角色字符串列表
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<String> getListRoleSnByUser(Long uid) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		return pmphUserDao.getListRoleSnByUser(uid);
	}

	/**
	 * 查询指定用户所绑定的角色列表
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<PmphRole> getListUserRole(Long uid) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		return pmphUserDao.getListUserRole(uid);
	}

	@Override
	public PageResult<PmphUserManagerVO> getListPmphUser(PageParameter<PmphUserManagerVO> pageParameter)
			throws CheckedServiceException {
		String name = pageParameter.getParameter().getName();
		if (StringUtil.notEmpty(name)) {
			pageParameter.getParameter().setName(name);
		}
		String path = pageParameter.getParameter().getPath();
		Long departmentId = pageParameter.getParameter().getDepartmentId();
		if (StringUtil.notEmpty(path) && ObjectUtil.notNull(departmentId)) {
			pageParameter.getParameter().setPath(path + "-" + java.lang.String.valueOf(departmentId) + '-');
		}
		PageResult<PmphUserManagerVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = pmphUserDao.getListPmphUserTotal(pageParameter);
		if (total > 0) {
			List<PmphUserManagerVO> list = pmphUserDao.getListPmphUser(pageParameter);
			for (PmphUserManagerVO pmphUserManagerVO : list) {
				List<PmphRoleVO> pmphRoles = pmphRoleDao.listPmphUserRoleByUserId(pmphUserManagerVO.getId());
				pmphUserManagerVO.setPmphRoles(pmphRoles);
			}
			pageResult.setRows(list);
		}

		pageResult.setTotal(total);

		return pageResult;
	}

	@Override
	public String updatePmphUserOfBack(PmphUserManagerVO pmphUserManagerVO) throws CheckedServiceException {
		PmphUser username = pmphUserDao.get(pmphUserManagerVO.getId());
		if (!username.getUsername().equals(pmphUserManagerVO.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户账号不相同");
		}
		if (ObjectUtil.isNull(pmphUserManagerVO.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止更新用户");
		}
		if (StringUtil.strLength(pmphUserManagerVO.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名需要小于20字符");
		}
		if (StringUtil.strLength(pmphUserManagerVO.getRealname()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "姓名需要小于20字符");
		}
		if (!StringUtil.isEmpty(pmphUserManagerVO.getHandphone())) {
			if (!ValidatUtil.checkMobileNumber(pmphUserManagerVO.getHandphone())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "电话格式不正确");
			}
		}
		if (!StringUtil.isEmpty(pmphUserManagerVO.getEmail())) {
			if (!ValidatUtil.checkEmail(pmphUserManagerVO.getEmail())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "邮箱格式不正确");
			}
		}
		if (StringUtil.isEmpty(pmphUserManagerVO.getRealname())) {
			pmphUserManagerVO.setRealname(pmphUserManagerVO.getUsername());
		}
		int num = pmphUserDao.updatePmphUserOfBack(pmphUserManagerVO);
		String result = "FAIL";
		if (num > 0) {
			pmphUserRoleDao.deletePmphUserRoleByUserId(pmphUserManagerVO.getId());
			String pmphRoles = pmphUserManagerVO.getRoleIds();
			if (!StringUtil.isEmpty(pmphRoles)) {
				String[] roleIds = pmphRoles.split(",");
				for (String roleId : roleIds) {
					PmphUserRole pmphUserRole = new PmphUserRole(pmphUserManagerVO.getId(), Long.valueOf(roleId));
					pmphUserRoleDao.addPmphUserRole(pmphUserRole);
				}
			}

			result = "SUCCESS";
		}
		return result;

	}

	@Override
	public List<Long> getPmphUserPermissionByUserId(Long userId) {
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		List<Long> list = pmphUserDao.getPmphUserPermissionByUserId(userId);
		if (CollectionUtil.isEmpty(list)) {
			list.add(1L);
		}
		return list;
	}

	@Override
	public String getMaterialPermissionByUserId(Long userId) {
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		String materialPermission = null;
		List<Integer> integers = pmphUserDao.getMaterialPermissionByUserId(userId);
		if (CollectionUtil.isEmpty(integers)) {
			materialPermission = "00000000";
		}
		if (integers.size() == 1) {
			materialPermission = StringUtil.tentToBinary(integers.get(0));
		}
		if (integers.size() >= 2) {
			Integer integer = integers.get(0);
			for (int i = 1; i < integers.size(); i++) {
				if (null == integers.get(i)) {
					integer = integer | 0;
				} else {
					integer = integer | integers.get(i);
				}
			}
			materialPermission = StringUtil.tentToBinary(integer);
		}
		return materialPermission;
	}

	@Override
	public PageResult<PmphEditorVO> listEditors(PageParameter<PmphEditorVO> pageParameter)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter().getDepartmentId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"部门id为空");
		}
		PageResult<PmphEditorVO> pageResult = new PageResult<>();
		Long departmentId = pageParameter.getParameter().getDepartmentId();
		String path = pmphDepartmentDao.getPmphDepartmentById(departmentId).getPath();
		path = path + "-" + departmentId + "-";
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = pmphUserDao.totalEditors(path, departmentId, pageParameter.getParameter().getRealName());
		if (total > 0) {
			List<PmphEditorVO> list = pmphUserDao.listEditors(path, departmentId,
					pageParameter.getParameter().getRealName(), pageParameter.getPageSize(), pageParameter.getStart());
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public Map<String, Object> getPersonalCenter(HttpServletRequest request, String state, String materialName,
			String groupName, String title, String bookname, String name, String authProgress, String topicBookname,Boolean booleans) {
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser sessionPmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == sessionPmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"请求用户不存在");
		}
		// 用于装所有的数据map
		Map<String, Object> map = new HashMap<>();
		// 教师认证总数量
		Integer writerUserCount = writerUserService.getCount();
		// 机构认证数量orgList
		Integer orgerCount = orgUserService.getCount();
		// 教材申报数量
//		Integer materialCount = materialService.getCount(sessionPmphUser.getId());
		// 选题申报数量
//		Integer topicCount = topicService.getCount(sessionPmphUser.getId());
		// 小组
		PmphGroupListVO pmphGroup = new PmphGroupListVO();
		if (ObjectUtil.notNull(groupName)) {
			pmphGroup.setGroupName(groupName.trim());
		}
		PageParameter<PmphGroupListVO> pageParameterPmphGroup = new PageParameter<>();
		pageParameterPmphGroup.setParameter(pmphGroup);
		// 小组结果
		PageResult<PmphGroupListVO> pageResultPmphGroup = pmphGroupService.getlistPmphGroup(pageParameterPmphGroup,
				sessionId);
		// 教材申报
		PageParameter<MaterialListVO> pageParameter2 = new PageParameter<>();
		MaterialListVO materialListVO = new MaterialListVO();
		materialListVO.setState(state);
		materialListVO.setMaterialName(materialName);
		pageParameter2.setParameter(materialListVO);
		// 教材申报的结果
		PageResult<MaterialListVO> pageResultMaterialListVO = materialService.listMaterials(pageParameter2, sessionId);
		if(booleans){
			// 文章审核
			PageParameter<CmsContentVO> pageParameter1 = new PageParameter<>();
			CmsContentVO cmsContentVO = new CmsContentVO();
			cmsContentVO.setTitle(title);
			cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_1);
			pageParameter1.setParameter(cmsContentVO);
			//文章审核的结果
			PageResult<CmsContentVO> pageResultCmsContentVO = cmsContentService.listCmsContent(pageParameter1, sessionId);
			map.put("cmsContent", pageResultCmsContentVO);
		}
		// 图书纠错审核
		PageResult<BookCorrectionAuditVO> pageResultBookCorrectionAuditVO = bookCorrectionService
				.listBookCorrectionAudit(request, Const.PAGE_NUMBER, Const.PAGE_SIZE, bookname, null, null);
		// 图书评论审核
		PageParameter<BookUserCommentVO> pageParameter = new PageParameter<>();
		BookUserCommentVO bookUserCommentVO = new BookUserCommentVO();
		bookUserCommentVO.setName(name.replaceAll(" ", ""));// 去除空格
		pageParameter.setParameter(bookUserCommentVO);
		PageResult<BookUserCommentVO> pageResultBookUserCommentVO = bookUserCommentService
				.listBookUserComment(pageParameter);
		// 图书附件审核 暂时没有
		
		// 选题申报
		PageParameter<TopicDeclarationVO> pageParameter3 = new PageParameter<>();
		TopicDeclarationVO topicDeclarationVO = new TopicDeclarationVO();
		String[] strs = authProgress.split(",");
		List<Long> progress = new ArrayList<>();
		for (String str : strs) {
			progress.add(Long.valueOf(str));
		}
		topicDeclarationVO.setBookname(topicBookname);
		pageParameter3.setParameter(topicDeclarationVO);
		PageResult<TopicDeclarationVO> pageResultTopicDeclarationVO = topicService.listCheckTopic(progress,
				pageParameter3);
		//选题申报当前用户角色
		PmphIdentity pmphIdentity=pmphUserService.identity(sessionId);
		//获取用户角色
		List<PmphRole> rolelist=roleService.getPmphRoleByUserId(sessionPmphUser.getId());
		// 把其他模块的数据都装入map中返回给前端
		map.put("topicList", pageResultTopicDeclarationVO);
		map.put("materialList", pageResultMaterialListVO);
		map.put("bookCorrectionAudit", pageResultBookCorrectionAuditVO);
		map.put("bookUserComment", pageResultBookUserCommentVO);
		map.put("pmphGroup", pageResultPmphGroup);
		map.put("writerUserCount", writerUserCount);
		map.put("orgUserCount", orgerCount);
		//把用户信息存入map
		map.put("pmphUser", sessionPmphUser);
		//把用户角色存入map
		map.put("pmphRole", rolelist);
		//把选题申报的当前身份存入map
		map.put("pmphIdentity", pmphIdentity);
		return map;
	}

	@Override
	public PmphUser getPmphUser(String username) {
		if (StringUtil.isEmpty(username)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空");
		}
		PmphUser pmphUser = pmphUserDao.getPmphUser(username);
		return pmphUser;
	}

	@Override
	public List<PmphUser> listPmphUserByDepartmentId(Long departmentId) throws CheckedServiceException {
		if (ObjectUtil.isNull(departmentId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"部门id为空");
		}
		return pmphUserDao.listPmphUserByDepartmentId(departmentId);
	}

	@Override
	public PmphIdentity identity(String sessionId) throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TOPIC, CheckedExceptionResult.NULL_PARAM,
					"用户为空！");
		}
		Long id = pmphUser.getId();
		PmphIdentity identity = new PmphIdentity(pmphUser.getId(), pmphUser.getUsername(), pmphUser.getRealname());
		identity.setIsAdmin(pmphUser.getIsAdmin());
		identity.setIsDirector(pmphUser.getIsDirector());
		List<PmphUser> list = new ArrayList<>();
		list = pmphUserDao.isEditor(id);
		if (!list.isEmpty()) {
			identity.setIsEditor(true);
		}
		list = pmphUserDao.isOpts(id);
		if (!list.isEmpty()) {
			identity.setIsOpts(true);
		}
		return identity;
	}

}
