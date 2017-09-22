package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgUserDao;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
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

	/**
	 * 
	 * @param orgUser
	 *            实体对象
	 * @return 带主键的 OrgUser
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgUser addOrgUser(OrgUser orgUser) throws CheckedServiceException {
		if (null == orgUser.getRealname()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
					CheckedExceptionResult.NULL_PARAM, "真实名称为空");
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
	 * <pre>
	 * 功能描述：分页查询作家用户
	 * 使用示范：
	 *
	 * &#64;param page 传入的查询数据
	 * &#64;return 需要的Page对象
	 * </pre>
	 */
	@Override
	public Page<OrgUserManagerVO, Map<String, String>> getListOrgUser(Page<OrgUserManagerVO, Map<String, String>> page)
			throws CheckedServiceException {
		if (null != page.getParameter().get("username")) {
			String username = page.getParameter().get("username").trim();
			if (!username.equals("")) {
				page.getParameter().put("username", "%" + username + "%");
			} else {
				page.getParameter().put("username", username);
			}
		}
		if (null != page.getParameter().get("realname")) {
			String realname = page.getParameter().get("realname").trim();
			if (!realname.equals("")) {
				page.getParameter().put("realname", "%" + realname + "%");
			} else {
				page.getParameter().put("realname", realname);
			}

		}
		if (null != page.getParameter().get("orgName")) {
			String orgName = page.getParameter().get("orgName").trim();
			if (!orgName.equals("")) {
				page.getParameter().put("orgName", "%" + orgName + "%");
			} else {
				page.getParameter().put("orgName", orgName);
			}
		}
		int total = orgUserDao.getListOrgUserTotal(page);
		if (total > 0) {
			List<OrgUserManagerVO> list = orgUserDao.getListOrgUser(page);
			page.setRows(list);
		}
		page.setTotal(total);

		return page;
	}

}
