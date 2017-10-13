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
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
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
@RequestMapping(value = "/users/writer")
public class WriterUserController {
    Logger            logger = LoggerFactory.getLogger(WriterUserController.class);
    @Autowired
    WriterUserService writerUserService;
    @Autowired
    WriterRoleService writerRoleService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list() {
        return new ResponseBean(writerUserService.getList());
    }

    /**
     * 
     * <pre>
	 * 功能描述：
	 * 使用示范：
	 *
	 * @param model
	 * @return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseBean add() {
        logger.debug("跳转到添加用户的页面");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("user", new WriterUser());
        result.put("roles", writerRoleService.getList());
        return new ResponseBean(result);
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
    public ResponseBean add(WriterUser user, @FormParam("roleIds") String roleIds) {
        logger.debug("添加用户 post 方法");
        logger.debug(user.toString());
        String[] ids = roleIds.split(",");
        List<Long> roleIdList = new ArrayList<>(ids.length);
        for (String roleId : ids) {
            roleIdList.add(Long.valueOf(roleId));
        }
        return new ResponseBean(writerUserService.add(user, roleIdList));
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
    public ResponseBean updateStatus(WriterUser user) {
        WriterUser writerUser = writerUserService.update(user);
        return new ResponseBean(writerUser);
    }

    /**
     * 
     * <pre>
	 * 功能描述：更新用户
	 * 使用示范：
	 *
	 * @param id
	 * @param model
	 * @return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ResponseBean update(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 要从数据库查询对象进行回显
        WriterUser user = writerUserService.get(id);
        result.put("user", user);
        // 所有的角色列表
        result.put("roles", writerRoleService.getList());
        // 根据用户 id 查询用户的所有角色
        List<WriterRole> hasRoles = writerUserService.getListUserRole(id);
        // 将用户的所有角色 id 添加到一个字符串中
        List<Long> rids = new ArrayList<>(hasRoles.size());
        for (WriterRole r : hasRoles) {
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
	 * @param user
	 * @param request
	 * @return
	 * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseBean update(WriterUser user, @FormParam("roleIds") String roleIds) {
        logger.debug("user => " + user.toString());
        String[] ids = roleIds.split(",");
        List<Long> roleIdList = new ArrayList<>(ids.length);
        for (String roleId : ids) {
            roleIdList.add(Long.valueOf(roleId));
        }
        return new ResponseBean(writerUserService.update(user, roleIdList));
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
        List<WriterPermission> resourceList = writerUserService.getListAllResource(userId);
        WriterUser user = writerUserService.get(userId);
        result.put("resources", resourceList);
        result.put("user", user);
        return new ResponseBean(result);
    }

    /**
     * 
     * <pre>
	 * 功能描述：批量删除用户 1、删除用户数据 2、删除用户绑定的角色数据
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
        return new ResponseBean(writerUserService.deleteUserAndRole(list));
    }

    /**
     * 
     * 
     * 功能描述：分页查询作家用户
     * 
     * @param page 分页条件
     * @param writerUserManagerVO 查询条件
     * @return 分好页的结果集
     * 
     */
    @ResponseBody
    @RequestMapping(value = "/list/writeruser", method = RequestMethod.GET)
    public ResponseBean listWriterUser(@RequestParam("pageSize") Integer pageSize,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("name") String name,
    @RequestParam("rank") Integer rank, @RequestParam("orgName") String orgName) {
        PageParameter pageParameter = new PageParameter<>();
        WriterUserManagerVO writerUserManagerVO = new WriterUserManagerVO();
        writerUserManagerVO.setName(name);
        writerUserManagerVO.setOrgName(orgName);
        writerUserManagerVO.setRank(rank);
        pageParameter.setPageNumber(pageNumber);
        pageParameter.setPageSize(pageSize);
        pageParameter.setParameter(writerUserManagerVO);
        return new ResponseBean(writerUserService.getListWriterUser(pageParameter));
    }

    /**
     * 
     * 
     * 功能描述：在作家用户管理页面添加用户
     * 
     * @param writerUser 添加的用户
     * @return 是否成功
     * 
     */
    @ResponseBody
    @RequestMapping(value = "/add/writeruserofback", method = RequestMethod.POST)
    public ResponseBean addWriterUserOfBack(WriterUser writerUser) {
        return new ResponseBean(writerUserService.addWriterUserOfBack(writerUser));
    }

    /**
     * 
     * 
     * 功能描述：在作家用户管理页面修改作家用户
     * 
     * @param writerUser 修改的作家用户（必须要有id）
     * @return 是否成功
     * 
     */
    @ResponseBody
    @RequestMapping(value = "/update/writeruserofback", method = RequestMethod.PUT)
    public ResponseBean updateWriterUserOfBack(WriterUser writerUser) {
        return new ResponseBean(writerUserService.updateWriterUserOfBack(writerUser));
    }
}
