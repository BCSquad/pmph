package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgDao;
import com.bc.pmpheep.back.dao.OrgUserDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.ValidatUtil;
import com.bc.pmpheep.back.vo.OrgAndOrgUserVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.SsoHelper;

/**
 * OrgUserServiceImpl 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class OrgUserServiceImpl extends BaseService implements OrgUserService {
	@Autowired
	private OrgUserDao orgUserDao;
	@Autowired
	private OrgDao orgDao;
	@Autowired
	private OrgService orgService;
	@Autowired
	SystemMessageService systemMessageService;

	@Override
	public List<OrgUser> getOrgUserListByOrgIds(List<Long> orgIds) throws CheckedServiceException {
		if (null == orgIds) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return orgUserDao.getOrgUserListByOrgIds(orgIds);
	}

	/**
	 * 
	 * @param orgUser
	 *            实体对象
	 * @return 带主键的 OrgUser
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgUser addOrgUser(OrgUser orgUser) throws CheckedServiceException {
		if (ObjectUtil.isNull(orgUser)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (StringUtil.isEmpty(orgUser.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名不能为空");
		}
		if (StringUtil.isEmpty(orgUser.getPassword())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "密码不能为空");
		}
		if (StringUtil.strLength(orgUser.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名不能超过20个字符");
		}
		if (StringUtil.isEmpty(orgUser.getAvatar())) {
			orgUser.setAvatar(RouteUtil.DEFAULT_USER_AVATAR);
		}
		if (StringUtil.isEmpty(orgUser.getRealname())) {
			orgUser.setUsername(orgService.getOrgById(orgUser.getOrgId()).getOrgName());
		}
		orgUserDao.addOrgUser(orgUser);
		return orgUser;
	}

	/**
	 * 
	 * @param id
	 * @return OrgUser
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgUser getOrgUserById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserDao.getOrgUserById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgUserById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserDao.deleteOrgUserById(id);
	}

	/**
	 * @param orgUser
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updateOrgUser(OrgUser orgUser) throws CheckedServiceException {
		if (null == orgUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserDao.updateOrgUser(orgUser);
	}

	@Override
	public Integer updateOrgUserProgressById(Integer progress, List<Long> orgUserIds)
			throws CheckedServiceException, IOException {
		if (CollectionUtil.isEmpty(orgUserIds) || ObjectUtil.isNull(progress)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SCHOOL_ADMIN_CHECK,
					CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		List<OrgUser> orgUserList = orgUserDao.getOrgUserByIds(orgUserIds);
		Integer count = 0;
		if (CollectionUtil.isNotEmpty(orgUserList)) {
			List<OrgUser> orgUsers = new ArrayList<OrgUser>(orgUserList.size());
			for (OrgUser orgUser : orgUserList) {
				if (Const.ORG_USER_PROGRESS_1 == orgUser.getProgress()
						|| Const.ORG_USER_PROGRESS_2 == orgUser.getProgress()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
							CheckedExceptionResult.NULL_PARAM, "已审核的用户不能再次审核");
				}
				orgUsers.add(new OrgUser(orgUser.getId(), progress));
			}
			if (CollectionUtil.isNotEmpty(orgUsers)) {
				count = orgUserDao.updateOrgUserProgressById(orgUsers);
			}
		}
		Boolean isPass = null;
		if (1 == progress) {
			isPass = true;
		}
		if (2 == progress) {
			isPass = false;
		}
		if (null != isPass) {// 推送机构认证审核信息
			systemMessageService.sendWhenManagerCertificationAudit(orgUserIds, isPass);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：分页查询作家用户
	 * 
	 * @param page
	 *            传入的查询数据
	 * @return 需要的Page对象
	 */
	@Override
	public PageResult<OrgAndOrgUserVO> getListOrgUser(PageParameter<OrgAndOrgUserVO> pageParameter)
			throws CheckedServiceException {
		String username = pageParameter.getParameter().getUsername();
		if (StringUtil.notEmpty(username)) {
			pageParameter.getParameter().setUsername(username);
		}
		String realname = pageParameter.getParameter().getRealname();
		if (StringUtil.notEmpty(realname)) {
			pageParameter.getParameter().setRealname(realname);
		}
		String orgName = pageParameter.getParameter().getOrgName();
		if (StringUtil.notEmpty(orgName)) {
			pageParameter.getParameter().setOrgName(orgName);
		}
		String orgTypeName = pageParameter.getParameter().getOrgTypeName();
		if (StringUtil.notEmpty(orgTypeName)) {
			pageParameter.getParameter().setOrgTypeName(orgTypeName);
		}
		PageResult<OrgAndOrgUserVO> pageResult = new PageResult<OrgAndOrgUserVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = orgUserDao.getListOrgUserTotal(pageParameter);
		if (total > 0) {
			pageResult.setTotal(total);
			List<OrgAndOrgUserVO> list = orgUserDao.getListOrgUser(pageParameter);
			pageResult.setRows(list);
		}
		// if (StringUtil.isEmpty(orgTypeName)) {// 当没有机构类型的时候 学校和医院生效
		// if (pageParameter.getParameter().getIsHospital()) {
		// pageParameter.getParameter().setHospital("医院");
		// PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// int total = orgUserDao.getListOrgUserTotal(pageParameter);
		// if (total > 0) {
		// pageResult.setTotal(total);
		// List<OrgAndOrgUserVO> list = orgUserDao.getListOrgUser(pageParameter);
		// pageResult.setRows(list);
		// }
		// } else {
		// pageParameter.getParameter().setSchool("医院");
		// PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// int total = orgUserDao.getListOrgUserTotal(pageParameter);
		// if (total > 0) {
		// pageResult.setTotal(total);
		// List<OrgAndOrgUserVO> list = orgUserDao.getListOrgUser(pageParameter);
		// pageResult.setRows(list);
		// }
		// }
		// } else {// 当有机构类型的时候 学校和医院失效
		// pageParameter.getParameter().setHospital(null);
		// pageParameter.getParameter().setSchool(null);
		// PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// int total = orgUserDao.getListOrgUserTotal(pageParameter);
		// if (total > 0) {
		// pageResult.setTotal(total);
		// List<OrgAndOrgUserVO> list = orgUserDao.getListOrgUser(pageParameter);
		// pageResult.setRows(list);
		// }
		// }
		return pageResult;
	}

	@Override
	public String addOrgUserOfBack(OrgUser orgUser) throws CheckedServiceException {
		if (StringUtil.isEmpty(orgUser.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "机构代码不能为空");
		}
		if (StringUtil.strLength(orgUser.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名不能超过20个字符");
		}
		if (!StringUtil.isEmpty(orgUser.getNote())) {
			if (StringUtil.strLength(orgUser.getNote()) > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注不能超过100个字符");
			}
		}
		if (StringUtil.isEmpty(orgUser.getRealname())) {
			orgUser.setRealname(orgUser.getUsername());
		}
		orgUser.setPassword(new DesRun(Const.DEFAULT_PASSWORD, "").enpsw);// 后台添加用户设置默认密码为123456
		int num = orgUserDao.addOrgUser(orgUser);// 返回的影响行数，如果不是影响0行就是添加成功
		String result = "FAIL";
		if (num > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public String updateOrgUserOfBack(OrgAndOrgUserVO orgAndOrgUserVO) throws CheckedServiceException {
		OrgUser username = orgUserDao.getOrgUserById(orgAndOrgUserVO.getId());
		if (!username.getUsername().equals(orgAndOrgUserVO.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "机构代码不相同");
		}
		if (ObjectUtil.isNull(orgAndOrgUserVO.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		if (StringUtil.strLength(orgAndOrgUserVO.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名不能超过20个字符");
		}
		if (StringUtil.isEmpty(orgAndOrgUserVO.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "机构代码不能为空");
		}
		if (StringUtil.strLength(orgAndOrgUserVO.getRealname()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "管理员姓名不能超过20个字符");
		}
		if (StringUtil.strLength(orgAndOrgUserVO.getNote()) > 100) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "备注不能超过100个字符");
		}
		if (!StringUtil.isEmpty(orgAndOrgUserVO.getEmail())) {
			if (!ValidatUtil.checkEmail(orgAndOrgUserVO.getEmail())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "邮箱不符合规范");
			}
		}
		if (!StringUtil.isEmpty(orgAndOrgUserVO.getHandphone())) {
			if (!ValidatUtil.checkMobileNumber(orgAndOrgUserVO.getHandphone())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "手机号码不符合规范");
			}
		}
		if (StringUtil.isEmpty(orgAndOrgUserVO.getOrgName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
					"机构名称为空");
		}
		if (StringUtil.strLength(orgAndOrgUserVO.getOrgName()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
					"机构名称过长");
		}
		// 通过id查询机构用户来判断该机构名称是否被使用或者用户名等于数据库的机构
		Org orgname = orgDao.getOrgById(orgAndOrgUserVO.getOrgId());
		if (!orgname.getOrgName().equals(orgAndOrgUserVO.getOrgName())) {
			if (orgDao.getOrgByOrgName(orgAndOrgUserVO.getOrgName()).size() > 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
						"该机构名称已被使用，请重新输入");
			}
		}
		Org org = new Org();
		org.setId(orgAndOrgUserVO.getOrgId());
		org.setOrgName(orgAndOrgUserVO.getOrgName());
		org.setOrgTypeId(orgAndOrgUserVO.getOrgTypeId());
		org.setAreaId(orgAndOrgUserVO.getAreaId());
		if (ObjectUtil.notNull(org)) {
			orgDao.updateOrg(org);
		}
		OrgUser orgUser = new OrgUser();
		orgUser.setId(orgAndOrgUserVO.getId());
		if (StringUtil.isEmpty(orgAndOrgUserVO.getRealname())) {
			orgUser.setRealname(orgAndOrgUserVO.getUsername());
		} else {
			orgUser.setRealname(orgAndOrgUserVO.getRealname());
		}
		orgUser.setIsDisabled(orgAndOrgUserVO.getIsDisabled());
		orgUser.setHandphone(orgAndOrgUserVO.getHandphone());
		orgUser.setEmail(orgAndOrgUserVO.getEmail());
		orgUser.setAddress(orgAndOrgUserVO.getNote());
		String result = "FAIL";
		if (ObjectUtil.notNull(orgUser)) {
			int count = orgUserDao.updateOrgUser(orgUser);// 返回的影响行数，如果不是影响0行就是添加成功
			if (count > 0) {
				result = "SUCCESS";
			}
		}
		return result;
	}

	@Override
	public Object addOrgUserAndOrgOfBack(OrgUser orgUser, Org org) {
		if (orgUserDao.getOrgUsername(orgUser.getUsername()).size() > 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
					"该机构代码已被使用，请重新输入");
		}
		if (StringUtil.isEmpty(orgUser.getUsername())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "机构代码不能为空");
		}
		if (StringUtil.strLength(orgUser.getUsername()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "机构代码不能超过20个字符");
		}
		if (!StringUtil.isEmpty(orgUser.getNote())) {
			if (StringUtil.strLength(orgUser.getNote()) > 100) {
				throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
						CheckedExceptionResult.ILLEGAL_PARAM, "备注不能超过100个字符");
			}
		}
		if (ObjectUtil.isNull(org)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		// if (null == org.getParentId()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
		// CheckedExceptionResult.NULL_PARAM, "上级机构id不能为空");
		// }
		if (StringUtil.isEmpty(org.getOrgName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
					"机构名称为空");
		}
		if (StringUtil.strLength(org.getOrgName()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
					"机构名称过长");
		}
		if (orgDao.getOrgByOrgName(org.getOrgName()).size() > 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.ILLEGAL_PARAM,
					"该机构名称已被使用，请重新输入");
		}
		if (ObjectUtil.isNull(org.getOrgTypeId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"机构类型不能为空");
		}
		if (ObjectUtil.isNull(org.getAreaId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"所属区域不能为空");
		}
		// if (null == org.getContactPerson()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
		// CheckedExceptionResult.NULL_PARAM,
		// "机构联系人不能为空");
		// }
		// if (null == org.getContactPhone()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
		// CheckedExceptionResult.NULL_PARAM,
		// "机构联系电话不能为空");
		// }
		orgDao.addOrg(org);
		if (StringUtil.isEmpty(orgUser.getRealname())) {
			orgUser.setRealname(orgUser.getUsername());
		}
		orgUser.setAvatar(RouteUtil.DEFAULT_USER_AVATAR);// 默认机构用户头像路径
		orgUser.setOrgId(orgDao.getOrgid(org.getOrgName()));
		orgUser.setPassword(new DesRun("", Const.DEFAULT_PASSWORD).enpsw);// 后台添加用户设置默认密码为123456
                SsoHelper ssoHelper = new SsoHelper();
		String result = ssoHelper.createSSOAccount(orgUser);
		if (!result.equals("success")) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.FAILURE_SSO_CALLBACK,
					result);
		}
		int num = orgUserDao.addOrgUser(orgUser);// 返回的影响行数，如果不是影响0行就是添加成功
		result = "FAIL";
		if (num > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public Integer getCount() {
		return orgUserDao.geCount();
	}

	@Override
	public OrgUser getOrgUserByOrgId(Long orgId) throws CheckedServiceException {
		if (ObjectUtil.isNull(orgId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"机构id不能为空");
		}
		OrgUser orgUser = null;
		try {
			orgUser = orgUserDao.getOrgUserByOrgId(orgId);
		} catch (Exception e) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.OBJECT_NOT_FOUND,
					"机构管理员产生了错误");
		}
		return orgUser;
	}

	@Override
	public PageResult<OrgAndOrgUserVO> getListAllOrgUser(PageParameter<OrgAndOrgUserVO> pageParameter)
			throws CheckedServiceException {
		PageResult<OrgAndOrgUserVO> pageResult = new PageResult<OrgAndOrgUserVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// 包含数据总条数的数据集
		List<OrgAndOrgUserVO> list = orgUserDao.getListAllOrgUser(pageParameter);
		if (CollectionUtil.isNotEmpty(list)) {
			Integer count = list.get(0).getCount();
			pageResult.setTotal(count);
			pageResult.setRows(list);
		}
		return pageResult;
	}

	@Override
	public String resetPassword(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		String password = "123456";
		OrgUser orgUser = orgUserDao.getOrgUserById(id);
		DesRun desRun = new DesRun(orgUser.getUsername(), password);
		orgUser.setPassword(desRun.enpsw);
		orgUserDao.updateOrgUser(orgUser);
		return password;
	}
}
