package com.bc.pmpheep.back.controller.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：PMPH-Shiro登陆
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
@RequestMapping(value = "/pmph")
@Controller
public class PmphLoginController {
    Logger                logger = LoggerFactory.getLogger(PmphLoginController.class);

    @Autowired
    PmphUserService       pmphUserService;
    @Autowired
    PmphPermissionService pmphPermissionService;

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
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseBean login(@RequestParam("username") String username,
    @RequestParam("password") String password, HttpServletRequest request) {
        // List<Long> permissionsIds = new ArrayList<Long>();// 用户拥有的权限资源ID集合
        // List<PmphPermission> permissions = new ArrayList<PmphPermission>();// 权限资源树集合
        Map<String, Object> resultMap = new HashMap<String, Object>();
        logger.info("username => " + username);
        logger.info("password => " + password);
        try {
            PmphUser pmphUser = pmphUserService.login(username, new DesRun("", password).enpsw);
            // 根据用户Id查询对应权限Id
            List<Long> pmphUserPermissionIds =
            pmphUserService.getPmphUserPermissionByUserId(pmphUser.getId());
            // 验证成功在Session中保存用户信息
            request.getSession().setAttribute(Const.SESSION_PMPH_USER, pmphUser);
            // 验证成功在Session中保存用户Token信息
            request.getSession().setAttribute(Const.SEESION_PMPH_USER_TOKEN,
                                              new DesRun(password, username).enpsw);
            // pmphUserSessionId
            resultMap.put(Const.PMPH_USER_SEESION_ID,
                          new DesRun("", request.getSession().getId()).enpsw);
            resultMap.put(Const.SESSION_PMPH_USER, pmphUser);
            resultMap.put(Const.SEESION_PMPH_USER_TOKEN, new DesRun(password, username).enpsw);
            resultMap.put("pmphUserPermissionIds", pmphUserPermissionIds);
            // 权限资源树集合
            // permissions = pmphPermissionService.getListAllParentMenu();
            // 拥有的权限资源
            // List<PmphPermission> havePermissions =
            // pmphUserService.getListAllResource(pmphUser.getId());
            // for (PmphPermission pmphPermission : havePermissions) {
            // permissionsIds.add(pmphPermission.getId());
            // }
            // 设置是否拥有权限
            // for (PmphPermission permission : permissions) {
            // if (permissionsIds.contains(permission.getId())) {
            // permission.setHasMenu(true);
            // List<PmphPermission> childList = permission.getChildren();
            // for (PmphPermission child : childList) {
            // if (permissionsIds.contains(child.getId())) {
            // child.setHasMenu(true);
            // }
            // }
            // }
            // }
            return new ResponseBean(resultMap);
        } catch (CheckedServiceException cException) {
            return new ResponseBean(cException);
        }
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
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseBean logout(HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
        session.removeAttribute(Const.SESSION_PMPH_USER);// 清除User信息
        session.removeAttribute(Const.SEESION_PMPH_USER_TOKEN);// 清除token
        returnMap.put("url", "/pmph/login");
        return new ResponseBean(returnMap);
    }

    /**
     * 
     * <pre>
     * 功能描述：无权限
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/unAuthorization", method = RequestMethod.GET)
    public ResponseBean unAuthorization() {
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        responseBean.setCode(ResponseBean.NO_PERMISSION);
        responseBean.setMsg("没有权限！");
        return responseBean;
    }
}
