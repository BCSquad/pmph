package com.bc.pmpheep.back.controller.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：Shiro登陆
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-9-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("all")
@RequestMapping(value = "/writer")
@Controller
public class WriterLoginController {
    Logger                  logger = LoggerFactory.getLogger(WriterLoginController.class);

    @Autowired
    WriterUserService       writerUserService;
    @Autowired
    WriterPermissionService writerPermissionService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseBean login() {
        return new ResponseBean("login");
    }

    /**
     * 
     * <pre>
     * 功能描述：登陆
     * 使用示范：
     *
     * @param user
     * @param model
     * @return
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseBean login(WriterUser user, HttpServletRequest request) {
        List<Long> permissionsIds = new ArrayList<Long>();// 用户拥有的权限资源ID集合
        List<WriterPermission> permissions = new ArrayList<WriterPermission>();// 权限资源树集合
        String username = user.getUsername();
        String password = user.getPassword();
        logger.debug("username => " + username);
        logger.debug("password => " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        try {
            subject.login(token);
            // 验证成功在Session中保存用户信息
            final WriterUser writerUser = writerUserService.getByUsername(user.getUsername());
            request.getSession().setAttribute(Const.SESSION_WRITER_USER, writerUser);
            // 权限资源树集合
            permissions = writerPermissionService.getListAllParentMenu();
            // 拥有的权限资源
            List<WriterPermission> havePermissions =
            writerUserService.getListAllResource(writerUser.getId());
            for (WriterPermission writerPermission : havePermissions) {
                permissionsIds.add(writerPermission.getId());
            }
            // 设置是否拥有权限
            for (WriterPermission writerPermission : permissions) {
                if (permissionsIds.contains(writerPermission.getId())) {
                    writerPermission.setHasMenu(true);
                    List<WriterPermission> childList = writerPermission.getChildren();
                    for (WriterPermission child : childList) {
                        if (permissionsIds.contains(child.getId())) {
                            child.setHasMenu(true);
                        }
                    }
                }
            }
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = e.getMessage();
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            msg = "密码不匹配(生产环境中应该写:用户名和密码的组合不正确)";
        } catch (LockedAccountException e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return new ResponseBean(permissions);
    }

    /**
     * 
     * <pre>
     * 功能描述：退出
     * 使用示范：
     *
     * @param model
     * @return
     * </pre>
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseBean logout() {
        Map<String, String> returnMap = new HashMap<String, String>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute(Const.SESSION_WRITER_USER);
        subject.logout();
        returnMap.put("url", "/login");
        return new ResponseBean(returnMap);
    }

    /**
     * 无权限
     * 
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/unAuthorization")
    public ResponseBean unAuthorization() {
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        responseBean.setCode(ResponseBean.NO_PERMISSION);
        responseBean.setMsg("没有权限！");
        return responseBean;
    }
}
