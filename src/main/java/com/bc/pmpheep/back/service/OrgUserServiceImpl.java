package com.bc.pmpheep.back.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgUserDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
		if (null == orgUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
		}
		if (null == orgUser.getUsername()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "用户名不能为空");
		}
		if (null == orgUser.getPassword()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.ILLEGAL_PARAM, "密码不能为空");
		}
		if (null == orgUser.getRealname()) {
			orgUser.setRealname(orgUser.getUsername());
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

	/**
	 * 
	 * 功能描述：分页查询作家用户
	 *
	 * @param page
	 *            传入的查询数据
	 * @return 需要的Page对象
	 */
	@Override
	public PageResult<OrgUserManagerVO> getListOrgUser(PageParameter<OrgUserManagerVO> pageParameter)
			throws CheckedServiceException {
		String username = pageParameter.getParameter().getUsername();
		if (Tools.notEmpty(username)) {
			pageParameter.getParameter().setUsername(username);
		}
		String realname = pageParameter.getParameter().getRealname();
		if (Tools.notEmpty(realname)) {
			pageParameter.getParameter().setRealname(realname);
		}
		String orgName = pageParameter.getParameter().getOrgName();
		if (Tools.notEmpty(orgName)) {
			pageParameter.getParameter().setOrgName(orgName);
		}
		PageResult<OrgUserManagerVO> pageResult = new PageResult<OrgUserManagerVO>();
		Tools.CopyPageParameter(pageParameter, pageResult);
		int total = orgUserDao.getListOrgUserTotal(pageParameter);
		if (total > 0) {
			pageResult.setTotal(total);
			List<OrgUserManagerVO> list = orgUserDao.getListOrgUser(pageParameter);
			pageResult.setRows(list);
		}
		return pageResult;
	}

	@Override
	public String addOrgUserOfBack(OrgUser orgUser) throws CheckedServiceException {
		if (null == orgUser.getUsername()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "机构代码不能为空");
		}
		if (null == orgUser.getRealname()) {
			orgUser.setRealname(orgUser.getUsername());
		}
		orgUser.setPassword(ShiroKit.md5(Const.DEFAULT_PASSWORD, orgUser.getUsername()));// 后台添加用户设置默认密码为123456
		int num = orgUserDao.addOrgUser(orgUser);// 返回的影响行数，如果不是影响0行就是添加成功
		String result = "FAIL";
		if (num > 0) {
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public String updateOrgUserOfBack(OrgUser orgUser) throws CheckedServiceException {
		if (null == orgUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		int num = orgUserDao.updateOrgUser(orgUser);// 返回的影响行数，如果不是影响0行就是添加成功
		String result = "FAIL";
		if (num > 0) {
			result = "SUCCESS";
		}
		return result;
	}
}
