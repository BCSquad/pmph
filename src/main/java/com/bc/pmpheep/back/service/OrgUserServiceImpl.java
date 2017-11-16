package com.bc.pmpheep.back.service;

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
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.ValidatUtil;
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
    @Autowired
	private OrgDao orgDao;

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
     * @param orgUser 实体对象
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

        if (StringUtil.isEmpty(orgUser.getRealname())) {
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

    @Override
    public Integer updateOrgUserProgressById(Integer progress, List<Long> orgUserIds)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(orgUserIds) || ObjectUtil.isNull(progress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SCHOOL_ADMIN_CHECK,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        List<OrgUser> orgUserList = orgUserDao.getOrgUserByIds(orgUserIds);
        List<OrgUser> orgUsers = new ArrayList<OrgUser>(orgUserList.size());
        for (OrgUser orgUser : orgUserList) {
            if (Const.ORG_USER_PROGRESS_0 == orgUser.getProgress()) {
                orgUsers.add(new OrgUser(orgUser.getId(), progress));
            }
        }
        return orgUserDao.updateOrgUserProgressById(orgUsers);
    }

    /**
     * 
     * 功能描述：分页查询作家用户
     * 
     * @param page 传入的查询数据
     * @return 需要的Page对象
     */
    @Override
    public PageResult<OrgUserManagerVO> getListOrgUser(PageParameter<OrgUserManagerVO> pageParameter)
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
        PageResult<OrgUserManagerVO> pageResult = new PageResult<OrgUserManagerVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
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
                                                  CheckedExceptionResult.ILLEGAL_PARAM,
                                                  "备注不能超过100个字符");
            }
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
        OrgUser username = orgUserDao.getOrgUserById(orgUser.getId());
        if (!username.getUsername().equals(orgUser.getUsername())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "机构代码不相同");
        }
        if (ObjectUtil.isNull(orgUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        if (StringUtil.strLength(orgUser.getUsername()) > 20) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "用户名不能超过20个字符");
        }
        if (StringUtil.isEmpty(orgUser.getUsername())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "机构代码不能为空");
        }
        if (StringUtil.strLength(orgUser.getRealname()) > 20) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM,
                                              "管理员姓名不能超过20个字符");
        }
        if (StringUtil.strLength(orgUser.getNote()) > 100) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "备注不能超过100个字符");
        }
        if (!StringUtil.isEmpty(orgUser.getEmail())) {
            if (!ValidatUtil.checkEmail(orgUser.getEmail())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.ILLEGAL_PARAM, "邮箱不符合规范");
            }
        }
        if (!StringUtil.isEmpty(orgUser.getHandphone())) {
            if (!ValidatUtil.checkMobileNumber(orgUser.getHandphone())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.ILLEGAL_PARAM, "手机号码不符合规范");
            }
        }
        int num = orgUserDao.updateOrgUser(orgUser);// 返回的影响行数，如果不是影响0行就是添加成功
        String result = "FAIL";
        if (num > 0) {
            result = "SUCCESS";
        }
        return result;
    }

	@Override
	public Object addOrgUserAndOrgOfBack(OrgUser orgUser, Org org) {
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
                		CheckedExceptionResult.ILLEGAL_PARAM,"备注不能超过100个字符");
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
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"机构名称为空");
		}
		if (StringUtil.strLength(org.getOrgName()) > 20) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"机构名称过长");
		}
		if (orgDao.getOrgByOrgName(org.getOrgName()).size() > 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM,
					"机构名称重复了");
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
		// if (null == org.getSort()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
		// CheckedExceptionResult.NULL_PARAM,
		// "机构显示顺序为空");
		// }
		// if (null == org.getNote()) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.ORG,
		// CheckedExceptionResult.NULL_PARAM, "备注为空");
		// }
		Long id = org.getId();
		orgDao.addOrg(org);
		if (null != id) {
			org.setId(id);
		}
		if (ObjectUtil.isNull(orgUser.getRealname())) {
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

}
