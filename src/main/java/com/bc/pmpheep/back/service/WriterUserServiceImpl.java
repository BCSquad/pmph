package com.bc.pmpheep.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterProfileDao;
import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.ValidatUtil;
import com.bc.pmpheep.back.vo.GroupMemberWriterUserVO;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.SsoHelper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * WriterUserService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class WriterUserServiceImpl implements WriterUserService, ApplicationContextAware {

	@Autowired
	WriterUserDao writerUserDao;
	@Autowired
	WriterRoleDao writerRoleDao;

	@Autowired
	WriterProfileDao writerProfileDao;

	@Autowired
	private DecPositionPublishedService decPositionPublishedService;

	@Autowired
	private PmphGroupService pmphGroupService;
        
        ApplicationContext context;

	@Override
	public List<WriterUser> getWriterUserListByOrgIds(List<Long> orgIds) throws CheckedServiceException {
		if (orgIds.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerUserDao.getWriterUserListByOrgIds(orgIds);
	}

	/**
	 * 返回新插入用户数据的主键
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public WriterUser add(WriterUser user) throws CheckedServiceException {
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
		if (StringUtil.isEmpty(user.getNickname())) {
			user.setNickname(user.getUsername());
		}
		// 使用用户名作为盐值，MD5 算法加密
		user.setPassword(new DesRun("", user.getPassword()).enpsw);
		writerUserDao.add(user);
		return user;
	}

	/**
	 * 为单个用户设置多个角色
	 * 
	 * @param user
	 * @param rids
	 */
	@Override
	public WriterUser add(WriterUser user, List<Long> rids) throws CheckedServiceException {
		Long userId = this.add(user).getId();
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时不能添加角色！");
		}
		writerRoleDao.addUserRoles(userId, rids);
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
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止删除用户！");
		}
		return writerUserDao.delete(id);
	}

	@Override
	public Integer deleteUserAndRole(List<Long> ids) throws CheckedServiceException {
		if (ids.size() < 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "Id为空时不能删除用户！");
		}
		int count = 0;
		// 删除用户列表
		writerUserDao.batchDelete(ids);
		// 依次删除这些用户所绑定的角色
		for (Long userId : ids) {
			writerRoleDao.deleteUserRoles(userId);
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
	public WriterUser update(WriterUser user, List<Long> rids) throws CheckedServiceException {
		Long userId = user.getId();
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止更新用户");
		}
		writerRoleDao.deleteUserRoles(userId);
		writerRoleDao.addUserRoles(userId, rids);
		return update(user);
	}

	/**
	 * 更新单个用户信息
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public WriterUser update(WriterUser user) throws CheckedServiceException {
		if (ObjectUtil.isNull(user)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户属性为空时禁止更新用户");
		} else {
			String password = user.getPassword();
			if (StringUtil.notEmpty(password)) {
				user.setPassword(new DesRun("", user.getPassword()).enpsw);
			}
		}
		writerUserDao.update(user);
		return user;
	}

	/**
	 * 根据主键 id 加载用户对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public WriterUser get(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		return writerUserDao.get(id);
	}

	/**
	 * 根据用户名加载用户对象（用于登录使用）
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public WriterUser getByUsernameAndPassword(String username, String password) throws CheckedServiceException {
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名或密码为空时禁止查询");
		}
		WriterUser writerUser = writerUserDao.getByUsernameAndPassword(username, password);
		if (ObjectUtil.isNull(writerUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.OBJECT_NOT_FOUND, "用户名或密码错误！");
		}
		return writerUser;
	}

	/**
	 * 登录逻辑 1、先根据用户名查询用户对象 2、如果有用户对象，则继续匹配密码 如果没有用户对象，则抛出异常
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public WriterUser login(String username, String password) throws CheckedServiceException {
		WriterUser user = writerUserDao.getByUsernameAndPassword(username, password);
		// 密码匹配的工作交给 Shiro 去完成
		if (ObjectUtil.isNull(user)) {
			// 因为缓存切面的原因,在这里就抛出用户名不存在的异常
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名不存在(生产环境中应该写:用户名和密码的组合不正确)");
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
	public List<WriterUser> getList() throws CheckedServiceException {
		return writerUserDao.getListUser();
	}

	/**
	 * 根据角色 id 查询是这个角色的所有用户
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<WriterUser> getListByRole(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止查询");
		}
		return writerUserDao.getListByRole(id);
	}

	/**
	 * 查询指定用户 id 所拥有的权限
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<WriterPermission> getListAllResource(Long uid) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止查询");
		}
		return writerUserDao.getListAllResources(uid);
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
					CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止查询");
		}
		return writerUserDao.getListRoleSnByUser(uid);
	}

	/**
	 * 查询指定用户所绑定的角色列表
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<WriterRole> getListUserRole(Long uid) throws CheckedServiceException {
		if (ObjectUtil.isNull(uid)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止查询");
		}
		return writerUserDao.getListUserRole(uid);
	}

	/**
	 * 
	 * 功能描述：分页查询作家用户
	 * 
	 * 
	 * @param page
	 *            传入的查询数据
	 * @return 需要的Page对象
	 */
	@Override
	public PageResult<WriterUserManagerVO> getListWriterUser(PageParameter<WriterUserManagerVO> pageParameter,
			Long groupId) throws CheckedServiceException {
		String name = pageParameter.getParameter().getName();
		if (StringUtil.notEmpty(name)) {
			pageParameter.getParameter().setName(name);
		}
		String orgName = pageParameter.getParameter().getOrgName();
		if (StringUtil.notEmpty(orgName)) {
			pageParameter.getParameter().setOrgName(orgName);
		}
		if (!ObjectUtil.isNull(groupId)) {
			pageParameter.getParameter().setGroupId(groupId);
		}
		PageResult<WriterUserManagerVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// 当rank为1的时候 查询教师用户
		int total = 0;
		if (pageParameter.getParameter().getRank() == null || pageParameter.getParameter().getRank() != 1) {
			// 当rank不为1的时候
			total = writerUserDao.getListWriterUserTotal(pageParameter);
			if (total > 0) {
				List<WriterUserManagerVO> list = writerUserDao.getListWriterUser(pageParameter);
				for (int i = 0;i<list.size();i++) {
					WriterUserManagerVO vo = list.get(i);
					String voS = com.alibaba.fastjson.JSON.toJSONString(vo).replaceAll("-","");
					vo = com.alibaba.fastjson.JSON.parseObject(voS,WriterUserManagerVO.class);
					list.remove(i);
					list.add(i,vo);
					switch (vo.getRank()) {
					case 0:
						vo.setRankName("普通用户");
						break;
					case 1:
						vo.setRankName("教师用户");
						break;
					case 2:
						vo.setRankName("作家用户");
						break;
					case 3:
						vo.setRankName("专家用户");
						break;

					default:
						throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_USER_MANAGEMENT,
								CheckedExceptionResult.NULL_PARAM, "该用户没有身份");
					}
				}
				pageResult.setRows(list);
			}
			pageResult.setTotal(total);
		} else {
			total = writerUserDao.getLsitisTeacherTotal(pageParameter);
			if (total > 0) {
				List<WriterUserManagerVO> list = writerUserDao.getLsitisTeacher(pageParameter);
				for (int i = 0;i<list.size();i++) {
					WriterUserManagerVO vo = list.get(i);
					String voS = com.alibaba.fastjson.JSON.toJSONString(vo).replaceAll("-","");
					vo = com.alibaba.fastjson.JSON.parseObject(voS,WriterUserManagerVO.class);
					list.remove(i);
					list.add(i,vo);
					switch (vo.getRank()) {
					case 0:
						vo.setRankName("普通用户");
						break;
					case 1:
						vo.setRankName("教师用户");
						break;
					case 2:
						vo.setRankName("作家用户");
						break;
					case 3:
						vo.setRankName("专家用户");
						break;

					default:
						throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_USER_MANAGEMENT,
								CheckedExceptionResult.NULL_PARAM, "该用户没有身份");
					}
				}
				pageResult.setRows(list);
			}
			pageResult.setTotal(total);
		}
		// 设置职位
		if (null != pageResult.getRows() && pageResult.getRows().size() > 0 && null != groupId) {
			// 清空职位
			for (WriterUserManagerVO writerUserManagerVO : pageResult.getRows()) {
				writerUserManagerVO.setPosition("无");
			}
			// 设置职位
			PmphGroup pmphGroup = pmphGroupService.getPmphGroupById(groupId);
			Long bookId = pmphGroup.getBookId();
			if (null != bookId && bookId.intValue() > 0) {
				// 查询这本书的发布职位
				List<DecPositionPublished> publisheds = decPositionPublishedService
						.getDecPositionPublishedListByBookId(bookId);
				if (null != publisheds && publisheds.size() > 0) {
					Map<Long, String> userIdsAndPostions = new HashMap<Long, String>();
					for (DecPositionPublished published : publisheds) {
						Declaration declaration = declarationService.getDeclarationById(published.getDeclarationId());
						String postiton = "无";
						if (published.getChosenPosition().intValue() == 4 && null != published.getRank()
								&& published.getRank() == 1) {
							postiton = "主编(第一主编)";
						} else if (published.getChosenPosition().intValue() == 4) {
							postiton = "主编";
						} else if (published.getChosenPosition().intValue() == 12 && null != published.getRank()
								&& published.getRank() == 1) {
							postiton = "主编(第一主编)，数字编委";
						} else if (published.getChosenPosition().intValue() == 12) {
							postiton = "主编，数字编委";
						} else if (published.getChosenPosition().intValue() == 2) {
							postiton = "副主编";
						} else if (published.getChosenPosition().intValue() == 10) {
							postiton = "副主编，数字编委";
						} else if (published.getChosenPosition().intValue() == 1) {
							postiton = "编委";
						} else if (published.getChosenPosition().intValue() == 9) {
							postiton = "编委，数字编委";
						}
						userIdsAndPostions.put(declaration.getUserId(), postiton);
					}
					for (WriterUserManagerVO writerUserManagerVO : pageResult.getRows()) {
						String postion = userIdsAndPostions.get(writerUserManagerVO.getId());
						if (null != postion) {
							writerUserManagerVO.setPosition(postion);
						}
					}
				}
			}
		}
		// 设置职位 end
		return pageResult;
	}

	@Override
	public List<WriterUserManagerVO> exportWriterInfo(String name,
													  Integer rank, String orgName, String handphone, String email){
		Map map = new HashMap();
		//if (StringUtil.notEmpty(name)) {
			map.put("name",name);
		//}

		//if (StringUtil.notEmpty(orgName)) {
			map.put("orgName",orgName);
		//}
		map.put("rank",rank);
		map.put("handphone",handphone);
		map.put("email",email);

			List<WriterUserManagerVO> list = writerUserDao.exportWriterInfo(map);
			for (int i = 0;i<list.size();i++) {
				WriterUserManagerVO vo = list.get(i);
				String voS = com.alibaba.fastjson.JSON.toJSONString(vo).replaceAll("-","");
				vo = com.alibaba.fastjson.JSON.parseObject(voS,WriterUserManagerVO.class);
				list.remove(i);
				list.add(i,vo);
				if(!ObjectUtil.isNull(vo.getProgress())){
					switch(vo.getProgress()){// 0=未提交/1=已提交/2=被退回/3=通过
						case 0:vo.setProgressName("未提交");break;
						case 1:vo.setProgressName("已提交");break;
						case 2:vo.setProgressName("被退回");break;
						case 3:vo.setProgressName("通过");break;
						default:vo.setProgressName("未提交");

					}
				}

				switch (vo.getRank()) {
					case 0:
						vo.setRankName("普通用户");
						break;
					case 1:
						vo.setRankName("教师用户");
						break;
					case 2:
						vo.setRankName("作家用户");
						break;
					case 3:
						vo.setRankName("专家用户");
						break;

					default:
						throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_USER_MANAGEMENT,
								CheckedExceptionResult.NULL_PARAM, "该用户没有身份");
				}
			}

			return list;

	}

	@Autowired
	private DeclarationService declarationService;

	@Override
	public PageResult<WriterUserManagerVO> getTeacherCheckList(PageParameter<WriterUserManagerVO> pageParameter)
			throws CheckedServiceException {
		PageResult<WriterUserManagerVO> pageResult = new PageResult<WriterUserManagerVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		List<WriterUserManagerVO> writerUserManagerVOList = writerUserDao.getTeacherCheckList(pageParameter);
		for (WriterUserManagerVO writerUserManagerVO : writerUserManagerVOList) {
			switch (writerUserManagerVO.getRank()) {
			case 0:
				writerUserManagerVO.setRankName("普通用户");
				break;
			case 1:
				writerUserManagerVO.setRankName("教师用户");
				break;
			case 2:
				writerUserManagerVO.setRankName("作家用户");
				break;
			case 3:
				writerUserManagerVO.setRankName("专家用户");
				break;
			default:
				throw new CheckedServiceException(CheckedExceptionBusiness.WRITER_USER_MANAGEMENT,
						CheckedExceptionResult.NULL_PARAM, "该用户没有身份");
			}
		}
		if (CollectionUtil.isNotEmpty(writerUserManagerVOList)) {
			Integer count = writerUserManagerVOList.get(0).getCount();
			pageResult.setTotal(count);
			pageResult.setRows(writerUserManagerVOList);
		}
		return pageResult;
	}

	@Override
	public String addWriterUserOfBack(WriterUser writerUser) throws CheckedServiceException {
		WriterUser username = writerUserDao.getUsername(writerUser.getUsername());
		if (username != null) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户代码已存在");
		}
		if (StringUtil.strLength(writerUser.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名需要小于20字符");
		}
		if (StringUtil.strLength(writerUser.getRealname()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "姓名需要小于20字符");
		}
		if (!StringUtil.isEmpty(writerUser.getHandphone())) {
			if (!ValidatUtil.checkMobileNumber(writerUser.getHandphone())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "电话格式不正确");
			}
		}
		if (!StringUtil.isEmpty(writerUser.getEmail())) {
			if (!ValidatUtil.checkEmail(writerUser.getEmail())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "邮箱格式不正确");
			}
		}
		if (StringUtil.isEmpty(writerUser.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名为空");
		}
		if (StringUtil.isEmpty(writerUser.getRealname())) {
			writerUser.setRealname(writerUser.getUsername());
		}
		if (!StringUtil.isEmpty(writerUser.getNote())) {
			if (StringUtil.strLength(writerUser.getNote()) > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.NULL_PARAM, "备注需要小于100字符");
			}
		}
		writerUser.setPassword(new DesRun("", Const.DEFAULT_PASSWORD).enpsw);// 后台添加用户设置默认密码为123456
		writerUser.setNickname(writerUser.getUsername());
		writerUser.setAvatar("DEFAULT");// 后台添加新用户时，设置为默认头像
		writerUserDao.add(writerUser);
		Long num = writerUser.getId();
		String result = "FAIL";
		if (num > 0) {
			WriterProfile writerProfile = new WriterProfile();
			writerProfile.setUserId(num);
			writerProfileDao.addWriterProfile(writerProfile);
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public String updateWriterUserOfBack(WriterUser writerUser) throws CheckedServiceException {
		WriterUser username = writerUserDao.get(writerUser.getId());
		if (!writerUser.getUsername().equals(username.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户代码不相同");
		}
		if (StringUtil.strLength(writerUser.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户名需要小于20字符");
		}
		if (StringUtil.strLength(writerUser.getRealname()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "姓名需要小于20字符");
		}
		if (!StringUtil.isEmpty(writerUser.getHandphone())) {
			if (!ValidatUtil.checkMobileNumber(writerUser.getHandphone())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.NULL_PARAM, "电话格式不正确");
			}
		}
		if (!StringUtil.isEmpty(writerUser.getEmail())) {
			if (!ValidatUtil.checkEmail(writerUser.getEmail())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.NULL_PARAM, "邮箱格式不正确");
			}
		}
		if (!StringUtil.isEmpty(writerUser.getNote())) {
			if (StringUtil.strLength(writerUser.getNote()) > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.NULL_PARAM, "备注需要小于100字符");
			}
		}
		if (StringUtil.strLength(writerUser.getPosition()) > 36) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "职务需要小于36字符");
		}
		if (StringUtil.strLength(writerUser.getTitle()) > 30) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "职称需要小于30字符");
		}
		int num = writerUserDao.update(writerUser);
		String result = "FAIL";
		if (num > 0) {

			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public List<Long> getWriterUserPermissionByUserId(Long userId) {
		if (ObjectUtil.isNull(userId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
		}
		return writerUserDao.getWriterUserPermissionByUserId(userId);
	}

	@Override
	public PageResult<GroupMemberWriterUserVO> listGroupMemberWriterUsers(
			PageParameter<GroupMemberWriterUserVO> pageParameter) throws CheckedServiceException {
		String bookName = pageParameter.getParameter().getBookname();
		if (StringUtil.notEmpty(bookName)) {
			pageParameter.getParameter().setBookname(bookName);
		}
		String name = pageParameter.getParameter().getName();
		if (StringUtil.notEmpty(name)) {
			pageParameter.getParameter().setRealname(name);
		}
		PageResult<GroupMemberWriterUserVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = writerUserDao.getGroupMemberWriterUserTotal(pageParameter);
		if (total > 0) {
			List<GroupMemberWriterUserVO> list = writerUserDao.listGroupMemberWriterUserVOs(pageParameter);
			for (GroupMemberWriterUserVO vo : list) {
				switch (vo.getChosenPosition()) {
				case 0:
					vo.setChosenPositionName("无职位");
					break;
				case 1:
					vo.setChosenPositionName("主编");
					break;
				case 2:
					vo.setChosenPositionName("副主编");
					break;
				case 3:
					vo.setChosenPositionName("编委");
					break;
				default:
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有身份");
				}
			}
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public Integer updateWriterUserRank(WriterUser writerUsers) {
		if (null == writerUsers) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerUserDao.updateWriterUserRank(writerUsers);
	}

	@Override
	public Integer getCount() {
		return writerUserDao.getCount();
	}

	@Override
	public List<WriterUser> getWriterUserRankList(List<WriterUser> writerUsers) {
		if (null == writerUsers) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerUserDao.getWriterUserRankList(writerUsers);
	}

	@Override
	public Integer updateWriterUser(WriterUser writerUsers) {
		if (null == writerUsers) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerUserDao.updateWriterUser(writerUsers);
	}

	@Override
	public List<WriterUser> getWriterUserList(Long[] userIds) {
		if (null == userIds) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return writerUserDao.getWriterUserList(userIds);
	}

	@Override
	public String resetPassword(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		String password = "123456";
		WriterUser user = writerUserDao.get(id);
                SsoHelper ssoHelper = context.getBean(SsoHelper.class);
                if(!ssoHelper.resetPassword(user.getUsername(), password)){
                    throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT, 
                            CheckedExceptionResult.FAILURE_SSO_CALLBACK, "访问单点登录系统失败");
                }
		DesRun desRun = new DesRun(user.getUsername(), password);
		user.setPassword(desRun.enpsw);
		writerUserDao.update(user);
		return password;
	}


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

	@Override
	public Integer isTop(Long id, Boolean isTop) {
		return writerUserDao.isTop(id,isTop);
	}


}
