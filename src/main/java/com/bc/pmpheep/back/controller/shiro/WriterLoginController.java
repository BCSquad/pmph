package com.bc.pmpheep.back.controller.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bc.pmpheep.annotation.LogDetail;
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
import com.bc.pmpheep.back.sessioncontext.SessionContext;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DesRun;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：Shiro登陆
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-9-20
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
@SuppressWarnings("all")
@RequestMapping(value = "/writer")
@Controller
public class WriterLoginController {
	Logger logger = LoggerFactory.getLogger(WriterLoginController.class);

	@Autowired
	WriterUserService writerUserService;
	@Autowired
	WriterPermissionService writerPermissionService;

	// 当前业务类型
	private static final String BUSSINESS_TYPE = "个人用户-Shiro登录";

	/**
	 * 
	 * <pre>
	 * 功能描述：登陆
	 * 使用示范：
	 *
	 * &#64;param user
	 * &#64;param model
	 * &#64;return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseBean login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request) {
		logger.info("username => " + username);
		logger.info("password => " + password);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			WriterUser writerUser = writerUserService.login(username, new DesRun("", password).enpsw);
			writerUser.setLoginType(Const.LOGIN_TYPE_WRITER);
			if (!RouteUtil.DEFAULT_USER_AVATAR.equals(writerUser.getAvatar())) {
				writerUser.setAvatar(RouteUtil.userAvatar(writerUser.getAvatar()));
			}
			// 根据用户Id查询对应权限Id
			List<Long> writerUserPermissionIds = writerUserService.getWriterUserPermissionByUserId(writerUser.getId());
			// 验证成功在Session中保存用户信息
			request.getSession().setAttribute(Const.SESSION_WRITER_USER, writerUser);
			// 验证成功在Session中保存用户Token信息
			request.getSession().setAttribute(Const.SEESION_WRITER_USER_TOKEN, new DesRun(password, username).enpsw);
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
	 * &#64;param model
	 * &#64;return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "退出")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseBean logout(HttpServletRequest request, @RequestParam("loginType") Short loginType) {
		String sessionId = CookiesUtil.getSessionId(request);
		HttpSession session = SessionContext.getSession(new DesRun(sessionId).depsw);
		if (ObjectUtil.notNull(session)) {
			if (Const.LOGIN_TYPE_WRITER == loginType) {
				session.removeAttribute(Const.SESSION_WRITER_USER);// 清除User信息
				session.removeAttribute(Const.SEESION_WRITER_USER_TOKEN);// 清除token
			}
		}
		return new ResponseBean();
	}

	/**
	 * 无权限
	 * 
	 * <pre>
	 * 功能描述：
	 * 使用示范：
	 *
	 * &#64;return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "提示无权限")
	@RequestMapping(value = "/unAuthorization", method = RequestMethod.GET)
	public ResponseBean unAuthorization() {
		ResponseBean<Object> responseBean = new ResponseBean<Object>();
		responseBean.setCode(ResponseBean.NO_PERMISSION);
		responseBean.setMsg("没有权限！");
		return responseBean;
	}
}
