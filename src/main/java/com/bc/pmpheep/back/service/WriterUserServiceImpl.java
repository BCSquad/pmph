package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.WriterProfileDao;
import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.GroupMemberWriterUserVO;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterUserService 实现
 * 
 * @author 曾庆峰
 * 
 */
@Service
public class WriterUserServiceImpl implements WriterUserService {

    @Autowired
    WriterUserDao    writerUserDao;
    @Autowired
    WriterRoleDao    writerRoleDao;
    @Autowired
    WriterProfileDao writerProfileDao;

    @Override
    public List<WriterUser> getWriterUserListByOrgIds(List<Long> orgIds)
    throws CheckedServiceException {
        if (null == orgIds) {
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
        if (null == user.getUsername()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止新增用户");
        }
        if (null == user.getPassword()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "密码为空时禁止新增用户");
        }
        if (null == user.getRealname()) {
            user.setRealname(user.getUsername());
        }
        // 使用用户名作为盐值，MD5 算法加密
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getUsername()));
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
        if (null == userId) {
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
        if (null == id) {
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
        if (null == userId) {
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
        String password = user.getPassword();
        if (null == password) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户密码为空时禁止更新用户");
        }
        if (password != null) {
            user.setPassword(new DesRun("", user.getPassword()).enpsw);
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
        if (null == id) {
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
    public WriterUser getByUsernameAndPassword(String username, String password)
    throws CheckedServiceException {
        if (null == username || null == password) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户名或密码为空时禁止查询");
        }
        WriterUser writerUser = writerUserDao.getByUsernameAndPassword(username, password);
        if (Tools.isNullOrEmpty(writerUser)) {
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
        if (user == null) {
            // 因为缓存切面的原因,在这里就抛出用户名不存在的异常
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM,
                                              "用户名不存在(生产环境中应该写:用户名和密码的组合不正确)");
        } else {
            if (user.getIsDisabled()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.ILLEGAL_PARAM,
                                                  "用户已经被禁用，请联系管理员启用该账号");
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
        if (null == id) {
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
        if (null == uid) {
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
        if (null == uid) {
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
        if (null == uid) {
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
     * @param page 传入的查询数据
     * @return 需要的Page对象
     */
    @Override
    public PageResult<WriterUserManagerVO> getListWriterUser(
    PageParameter<WriterUserManagerVO> pageParameter) throws CheckedServiceException {
        if (null != pageParameter.getParameter().getName()) {
            String name = pageParameter.getParameter().getName().trim();
            if (!name.equals("")) {
                pageParameter.getParameter().setName("%" + name + "%");
            } else {
                pageParameter.getParameter().setName(name);
            }
        }
        if (null != pageParameter.getParameter().getOrgName()) {
            String orgName = pageParameter.getParameter().getOrgName().trim();
            if (!orgName.equals("")) {
                pageParameter.getParameter().setOrgName("%" + orgName + "%");
            } else {
                pageParameter.getParameter().setOrgName(orgName);
            }
        }
        PageResult<WriterUserManagerVO> pageResult = new PageResult<>();
        Tools.CopyPageParameter(pageParameter, pageResult);
        int total = writerUserDao.getListWriterUserTotal(pageParameter);
        if (total > 0) {
            List<WriterUserManagerVO> list = writerUserDao.getListWriterUser(pageParameter);
            for (WriterUserManagerVO vo : list) {
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
                    throw new CheckedServiceException(
                                                      CheckedExceptionBusiness.WRITER_USER_MANAGEMENT,
                                                      CheckedExceptionResult.NULL_PARAM, "该用户没有身份");
                }
            }
            pageResult.setRows(list);
        }
        pageResult.setTotal(total);

        return pageResult;
    }

    @Override
    public String addWriterUserOfBack(WriterUser writerUser) throws CheckedServiceException {
        writerUser.setPassword(ShiroKit.md5(Const.DEFAULT_PASSWORD, writerUser.getUsername()));// 后台添加用户设置默认密码为123456
        writerUser.setNickname(writerUser.getUsername());
        writerUser.setAvatar(Const.DEFAULT_USER_AVATAR);// 后台添加新用户时，设置为默认头像
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
        int num = writerUserDao.update(writerUser);
        String result = "FAIL";
        if (num > 0) {

            result = "SUCCESS";
        }
        return result;
    }

    @Override
    public List<Long> getWriterUserPermissionByUserId(Long userId) {
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止查询");
        }
        return writerUserDao.getWriterUserPermissionByUserId(userId);
    }

	@Override
	public PageResult<GroupMemberWriterUserVO> getListGroupMemberWriterUsers(
			PageParameter<GroupMemberWriterUserVO> pageParameter)
			throws CheckedServiceException {
		if (null == pageParameter.getParameter().getBookname()){
			String bookname = pageParameter.getParameter().getBookname().trim();
			if (!bookname.equals("")){
				pageParameter.getParameter().setBookname("%" + bookname + "%");
			}else{
				pageParameter.getParameter().setBookname(bookname);
			}
		}
		if (null == pageParameter.getParameter().getRealname()){
			String realname = pageParameter.getParameter().getRealname().trim();
			if (!realname.equals("")){
				pageParameter.getParameter().setRealname("%" + realname + "%");
			}else{
				pageParameter.getParameter().setRealname(realname);
			}
		}
		if (null == pageParameter.getParameter().getUsername()){
			String username = pageParameter.getParameter().getUsername().trim();
			if (!username.equals("")){
				pageParameter.getParameter().setUsername("%" + username + "%");
			}else{
				pageParameter.getParameter().setUsername(username);
			}
		}
		PageResult<GroupMemberWriterUserVO> pageResult = new PageResult<>();
		Tools.CopyPageParameter(pageParameter, pageResult);
		int total = writerUserDao.getGroupMemberWriterUserTotal(pageParameter);
		if (total > 0){
			List<GroupMemberWriterUserVO> list = writerUserDao.getListGroupMemberWriterUserVOs(pageParameter);
			for (GroupMemberWriterUserVO vo : list){
				switch (vo.getChosenPosition()) {
				case 0:
					vo.setChosenPositionName("无职位");;
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

    // /**
    // *
    // * <pre>
    // * 功能描述：分页查询作家用户
    // * 使用示范：
    // *
    // * @param page 传入的查询数据
    // * @return 需要的Page对象
    // * </pre>
    // * @throws ReflectiveOperationException
    // */
    // @Override
    // public Page<WriterUserManagerVO, Map<String, String>>
    // getListWriter(Page<WriterUser,
    // Map<String, String>> page) throws CheckedServiceException,
    // ReflectiveOperationException {
    // if (null != page.getParameter().get("username")) {
    // page.getParameter().put("username", "%" +
    // page.getParameter().get("username") + "%");
    // }
    // if (null != page.getParameter().get("realname")) {
    // page.getParameter().put("realname", "%" +
    // page.getParameter().get("realname") + "%");
    // }
    // if (null != page.getParameter().get("orgName")) {
    // page.getParameter().put("orgName", "%" +
    // page.getParameter().get("orgName") + "%");
    // }
    // List<Org> orgs =
    // orgDao.getListOrgByOrgName(page.getParameter().get("orgName"));
    // String orgId = "";
    // Map<Long, String> map = new HashMap<>();
    // for (Org org : orgs) {
    // orgId += "," + org.getId();
    // map.put(org.getId(), org.getOrgName());
    // }
    // if (orgId.equals("")) {
    // throw new
    // CheckedServiceException(CheckedExceptionBusiness.WRITER_USER_MANAGEMENT,
    // CheckedExceptionResult.NULL_PARAM, "没有找到该机构");
    // } else {
    // orgId = orgId.substring(1);
    // page.getParameter().put("orgId", orgId);
    // }
    // Page<WriterUserManagerVO, Map<String, String>> pageVO = new Page<>();
    // int total = writerUserDao.getListTotal(page);
    // if (total > 0) {
    // List<WriterUserManagerVO> writerUserManagerVOs = new ArrayList<>();
    // List<WriterUser> writerUsers = writerUserDao.getList(page);
    // for(WriterUser writerUser : writerUsers){
    // WriterUserManagerVO writerUserManagerVO = new WriterUserManagerVO();
    // BeanUtils.copyProperties(writerUserManagerVO, writerUser);
    // writerUserManagerVO.setOrgName(map.get(writerUser.getOrgId()));
    // writerUserManagerVOs.add(writerUserManagerVO);
    // }
    //
    // pageVO.setFirst(page.isFirst());
    // pageVO.setLast(page.isLast());
    // pageVO.setPageTotal(total);
    // pageVO.setRows(writerUserManagerVOs);
    // }
    // return pageVO;
    // }

}
