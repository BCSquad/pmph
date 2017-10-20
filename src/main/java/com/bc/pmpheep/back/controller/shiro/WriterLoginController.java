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

import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
    public ResponseBean login(@RequestParam("username") String username,
    @RequestParam("password") String password, HttpServletRequest request) {
        logger.info("username => " + username);
        logger.info("password => " + password);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            WriterUser writerUser =
            writerUserService.login(username, new DesRun("", password).enpsw);
            writerUser.setLoginType(Const.LOGIN_TYPE_WRITER);
            // 根据用户Id查询对应权限Id
            List<Long> writerUserPermissionIds =
            writerUserService.getWriterUserPermissionByUserId(writerUser.getId());
            // 验证成功在Session中保存用户信息
            request.getSession().setAttribute(Const.SESSION_WRITER_USER, writerUser);
            // 验证成功在Session中保存用户Token信息
            request.getSession().setAttribute(Const.SEESION_WRITER_USER_TOKEN,
                                              new DesRun(password, username).enpsw);
            resultMap.put(Const.USER_SEESION_ID, new DesRun("", request.getSession().getId()).enpsw);
            resultMap.put(Const.SESSION_WRITER_USER, writerUser);
            resultMap.put(Const.SEESION_WRITER_USER_TOKEN, new DesRun(password, username).enpsw);
            resultMap.put("writerUserPermissionIds", writerUserPermissionIds);
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
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseBean logout(HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
        session.removeAttribute(Const.SESSION_WRITER_USER);// 清除User信息
        session.removeAttribute(Const.SEESION_WRITER_USER_TOKEN);// 清除token
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
    @RequestMapping(value = "/unAuthorization", method = RequestMethod.GET)
    public ResponseBean unAuthorization() {
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        responseBean.setCode(ResponseBean.NO_PERMISSION);
        responseBean.setMsg("没有权限！");
        return responseBean;
    }
}
