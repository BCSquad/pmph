package com.bc.pmpheep.back.controller.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：PmphUserController
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
@Controller
@RequestMapping(value = "/pmph/user")
public class PmphUserController {
    Logger                logger = LoggerFactory.getLogger(PmphUserController.class);
    @Autowired
    PmphUserService       userService;
    @Autowired
    PmphRoleService       roleService;
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
	 * &#64;param model
	 * &#64;return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseBean add() {
        logger.debug("跳转到添加用户的页面");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("user", new PmphUser());
        result.put("roles", roleService.getList());
        return new ResponseBean(result);
    }

    /**
     * 
     * <pre>
	 * 功能描述：添加用户保存的方法
	 * 使用示范：
	 *
	 * &#64;param user
	 * &#64;param request
	 * &#64;return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseBean add(PmphUser user, HttpServletRequest request) {
        logger.debug("添加用户 post 方法");
        logger.debug(user.toString());
        Map<String, Object> result = new HashMap<String, Object>();
        List<Long> roleIdList = new ArrayList<>();
        String[] roldIds = request.getParameterValues("roleId");
        for (String roleId : roldIds) {
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
	 * &#64;param user
	 * &#64;return
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
	 * 功能描述：更新用户
	 * 使用示范：
	 *
	 * &#64;param id
	 * &#64;param model
	 * &#64;return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ResponseBean update(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 要从数据库查询对象进行回显
        PmphUser user = userService.get(id);
        result.put("user", user);
        // 所有的角色列表
        result.put("roles", roleService.getList());
        // 根据用户 id 查询用户的所有角色
        List<PmphRole> hasRoles = userService.getListUserRole(id);
        // 将用户的所有角色 id 添加到一个字符串中
        List<Long> rids = new ArrayList<>();
        for (PmphRole r : hasRoles) {
            rids.add(r.getId());
        }
        // 指定用户拥有的角色信息
        result.put("hasRole", rids);
        return new ResponseBean(result);
    }

    /**
     * 
     * <pre>
	 * 功能描述：更新用户的信息（包括更新用户绑定的角色）
	 * 使用示范：
	 *
	 * &#64;param user
	 * &#64;param request
	 * &#64;return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseBean update(PmphUser user, HttpServletRequest request) {
        logger.debug("user => " + user.toString());
        String[] roleIds = request.getParameterValues("roleId");
        List<Long> roleIdList = new ArrayList<>();
        for (String roleId : roleIds) {
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
	 * &#64;param userId
	 * &#64;param model
	 * &#64;return
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
	 * &#64;param userIds
	 * &#64;return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@RequestParam("userIds[]") List<Long> userIds) {
        return new ResponseBean(userService.deleteUserAndRole(userIds));
    }

    /**
     * 
     * 
     * 功能描述：分页查询社内用户
     * 
     * @param page 分页条件
     * @param pmphUserManagerVO 查询条件
     * @return 分好页的结果集
     * 
     */
    @ResponseBody
    @RequestMapping(value = "/user/Pmph")
    public ResponseBean listPmphUser(@RequestParam("pageSize") Integer pageSize,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("name") String name,
    @RequestParam("path") String path) {
        PageParameter pageParameter = new PageParameter<>();
        PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
        pmphUserManagerVO.setName(name);
        pmphUserManagerVO.setPath(path);
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
     * @param pmphUserManagerVO 修改的社内用户（必须要有id）
     * @return 是否成功
     * 
     */
    @ResponseBody
    @RequestMapping(value = "/updatePmphUserOfBack")
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
    @RequestMapping(value = "/getListPmphDepartment")
    public ResponseBean getListPmphDepartment() {
        return new ResponseBean(pmphDepartmentService.getListPmphDepartment(null));
    }
}
