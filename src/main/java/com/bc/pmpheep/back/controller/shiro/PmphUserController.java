package com.bc.pmpheep.back.controller.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：PmphUserController
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
@Controller
@RequestMapping(value = "/users/pmph")
public class PmphUserController {
	Logger logger = LoggerFactory.getLogger(PmphUserController.class);
	@Autowired
	PmphUserService userService;
	@Autowired
	PmphRoleService roleService;
	@Autowired
	PmphDepartmentService pmphDepartmentService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list() {
		return new ResponseBean(userService.getList());
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：添加用户保存的方法
	 * 使用示范：
	 *
	 * @param user
	 * @param request
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseBean add(PmphUser user, @FormParam("roleIds") String roleIds) {
		logger.debug("添加用户 post 方法");
		logger.debug(user.toString());
		String[] ids = roleIds.split(",");
		List<Long> roleIdList = new ArrayList<>(ids.length);
		for (String roleId : ids) {
			roleIdList.add(Long.valueOf(roleId));
		}
		return new ResponseBean(userService.add(user, roleIdList));
	}

	/**
	 * 更新用户
	 * 
	 * <pre>
	 * 功能描述：
	 * 使用示范：
	 *
	 * @param user
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
	public ResponseBean updateStatus(PmphUser user) {
		PmphUser pmphUser = userService.update(user);
		return new ResponseBean(pmphUser);
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：更新用户的信息（包括更新用户绑定的角色）
	 * 使用示范：
	 *
	 * @param user
	 * @param request
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseBean update(PmphUser user, @FormParam("roleIds") String roleIds) {
		logger.debug("user => " + user.toString());
		String[] ids = roleIds.split(",");
		List<Long> roleIdList = new ArrayList<>(ids.length);
		for (String roleId : ids) {
			roleIdList.add(Long.valueOf(roleId));
		}
		return new ResponseBean(userService.update(user, roleIdList));
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：根据用户 id 跳转到用户权限的列表页面
	 * 使用示范：
	 *
	 * @param userId
	 * @param model
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping(value = "/resources/{id}", method = RequestMethod.GET)
	public ResponseBean listResources(@PathVariable("id") Long userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PmphPermission> resourceList = userService.getListAllResource(userId);
		PmphUser user = userService.get(userId);
		result.put("resources", resourceList);
		result.put("user", user);
		return new ResponseBean(result);
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：批量删除用户 
	 * 
	 * 1、删除用户数据 
	 * 
	 * 2、删除用户绑定的角色数据
	 * 使用示范：
	 *
	 * @param userIds
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{userIds}", method = RequestMethod.DELETE)
	public ResponseBean delete(@PathVariable("userIds") String userIds) {
		String[] ids = userIds.split(",");
		List<Long> list = new ArrayList<Long>(ids.length);
		for (String str : ids) {
			list.add(Long.valueOf(str));
		}
		return new ResponseBean(userService.deleteUserAndRole(list));
	}

	/**
	 * 
	 * 
	 * 功能描述：分页查询社内用户
	 * 
	 * @param page
	 *            分页条件
	 * @param pmphUserManagerVO
	 *            查询条件
	 * @return 分好页的结果集
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/list/pmphuser", method = RequestMethod.GET)
	public ResponseBean listPmphUser(Integer pageSize, Integer pageNumber, String name,
			@RequestParam("path") String path, Long departmentId) {
		PageParameter pageParameter = new PageParameter<>();
		PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
		pmphUserManagerVO.setName(name.replaceAll(" ", ""));//去除空格
		pmphUserManagerVO.setPath(path);
		pmphUserManagerVO.setDepartmentId(departmentId);
		pageParameter.setPageNumber(pageNumber);
		pageParameter.setPageSize(pageSize);
		pageParameter.setParameter(pmphUserManagerVO);
		return new ResponseBean(userService.getListPmphUser(pageParameter));
	}

	/**
	 * 
	 * 
	 * 功能描述：在社内用户管理中修改社内用户
	 * 
	 * @param pmphUserManagerVO
	 *            修改的社内用户（必须要有id）
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/update/pmphuserofback", method = RequestMethod.PUT)
	public ResponseBean updatePmphUserOfBack(PmphUserManagerVO pmphUserManagerVO) {
		return new ResponseBean(userService.updatePmphUserOfBack(pmphUserManagerVO));
	}

	/**
	 * 
	 * 
	 * 功能描述：初始化获取社内所有部门
	 * 
	 * @return 已经分好级的社内部门
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/list/pmphdepartment", method = RequestMethod.GET)
	public ResponseBean listPmphDepartment() {
		return new ResponseBean(pmphDepartmentService.listPmphDepartment(null));
	}
}
