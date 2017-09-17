package com.bc.pmpheep.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.dao.WriterRoleDao;
import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.util.Tools;
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
    WriterUserDao userDao;
    @Autowired
    WriterRoleDao roleDao;

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
        userDao.add(user);
        return user;
    }

    /**
     * 为单个用户设置多个角色
     * 
     * @param user
     * @param rids
     */
    @Transactional
    @Override
    public WriterUser add(WriterUser user, List<Long> rids) throws CheckedServiceException {
        Long userId = this.add(user).getId();
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时不能添加角色！");
        }
        roleDao.addUserRoles(userId, rids);
        return user;
    }

    /**
     * 根据 user_id 删除用户数据
     * 
     * @param id
     */
    @Override
    public void delete(Long id) throws CheckedServiceException {
        if (1 == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "不能删除管理员用户！");
        }
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteUserAndRole(List<Long> ids) throws CheckedServiceException {
        if (ids.contains(1)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "不能删除管理员用户！");
        }
        // 删除用户列表
        userDao.batchDelete(ids);
        // 依次删除这些用户所绑定的角色
        for (Long userId : ids) {
            roleDao.deleteUserRoles(userId);
        }

    }

    /**
     * 
     * 更新用户数据 1、更新用户基本信息 2、更新用户所属角色 （1）先删除所有的角色 （2）再添加绑定的角色
     * 
     * @param user
     * @param rids
     */
    @Transactional
    @Override
    public WriterUser update(WriterUser user, List<Long> rids) throws CheckedServiceException {
        Long userId = user.getId();
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户ID为空时禁止更新用户");
        }
        roleDao.deleteUserRoles(userId);
        roleDao.addUserRoles(userId, rids);
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
    public WriterUser update(WriterUser user) throws CheckedServiceException {
        String password = user.getPassword();
        if (null == password) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户密码为空时禁止更新用户");
        }
        if (password != null) {
            user.setPassword(ShiroKit.md5(user.getPassword(), user.getUsername()));
        }
        userDao.update(user);
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
        return userDao.get(id);
    }

    /**
     * 根据用户名加载用户对象（用于登录使用）
     * 
     * @param username
     * @return
     */
    @Override
    public WriterUser getByUsername(String username) throws CheckedServiceException {
        if (null == username) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "用户名为空时禁止查询");
        }
        return userDao.getByUserName(username);
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
        WriterUser user = userDao.getByUserName(username);
        // 密码匹配的工作交给 Shiro 去完成
        if (user == null) {
            // 因为缓存切面的原因,在这里就抛出用户名不存在的异常
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM,
                                              "用户名不存在(生产环境中应该写:用户名和密码的组合不正确)");
        } else {
            Integer isdisable = user.getIsDisabled();
            if (Tools.isNullOrEmpty(isdisable)) {
                isdisable = 0;
            } else if (isdisable == 1) {
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
        return userDao.getListUser();
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
        return userDao.getListByRole(id);
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
        return userDao.getListAllResources(uid);
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
        return userDao.getListRoleSnByUser(uid);
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
        return userDao.getListUserRole(uid);
    }

}
