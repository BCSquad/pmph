package com.bc.pmpheep.back.controller.shiro;

import java.util.ArrayList;
import java.util.List;

import com.bc.pmpheep.back.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：PmphRole 
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
@RequestMapping("role/pmph")
public class PmphRoleController {
    Logger                      logger         = LoggerFactory.getLogger(PmphRoleController.class);
    @Autowired
    PmphRoleService             roleService;
    @Autowired
    PmphPermissionService       pmphPermissionService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "角色";

    /**
     * 
     * <pre>
	 * 功能描述：跳转到查询所有角色的页面
	 * 使用示范：
	 *
	 * @param model
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询角色列表信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list(@RequestParam("roleName") String roleName) {
        List<PmphRole> roleList = roleService.getList(StringUtil.toAllCheck(roleName));
        return new ResponseBean(roleList);
    }

    /**
     * 
     * <pre>
	 * 功能描述：添加用户角色的后台方法
	 * 使用示范：
	 *
	 * @param role 
	 *        ids 角色默认权限的id
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增角色")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseBean add(PmphRole role, Long[] ids) {
        logger.debug(role.toString());
        if (ArrayUtil.isEmpty(ids)) {
            ids = new Long[] { 1L };// 默认权限（1：个人中心首页）
        }
        return new ResponseBean(roleService.add(role, ids));
    }

    /**
     * 
     * <pre>
	 * 功能描述：跳转到更新角色的页面
	 * 使用示范：
	 *
	 * @param id
	 * @param model
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "跳转到更新角色的页面")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ResponseBean update(@PathVariable("id") Long id) {
        PmphRole role = roleService.get(id);
        return new ResponseBean(role);
    }

    /**
     * 
     * <pre>
	 * 功能描述：修改角色对象的方法
	 * 使用示范：
	 *
	 * @param role
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改角色")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseBean update(PmphRole role) {
        logger.debug(role.toString());
        return new ResponseBean(roleService.update(role));
    }

    /**
     * 
     * <pre>
	 * 功能描述：获取资源
	 * 使用示范：
	 *
	 * @param id
	 * @param model
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询角色拥有的资源菜单列表")
    @RequestMapping(value = "/{roleId}/resources", method = RequestMethod.GET)
    public ResponseBean resources(@PathVariable("roleId") Long roleId) {
        return new ResponseBean(roleService.getListPmphRolePermission(roleId));
    }

    /**
     * 设置用户权限
     * 
     * <pre>
	 * 功能描述：
	 * 使用示范：
	 *
	 * @param roleId
	 * @param permissionIds
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新角色拥有的资源菜单")
    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public ResponseBean resource(@RequestParam("roleId") Long roleId,
    @RequestParam("permissionIds") String permissionIds) {
        String[] ids = permissionIds.split(",");
        List<Long> idLists = new ArrayList<Long>(ids.length);
        for (String id : ids) {
            idLists.add(Long.valueOf(id));
        }
        return new ResponseBean(roleService.addRoleResource(roleId, idLists));
    }

    /**
     * 
     * <pre>
	 * 功能描述：角色删除
	 * 使用示范：
	 *
	 * @param roleIds
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除角色")
    @RequestMapping(value = "/{roleIds}/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@PathVariable("roleIds") String roleIds) {
        logger.debug(roleIds);
        String[] ids = roleIds.split(",");
        List<Long> idLists = new ArrayList<Long>(ids.length);
        for (String id : ids) {
            idLists.add(Long.valueOf(id));
        }
        // 先批量删除角色,再从角色资源表中删除角色资源数据
        roleService.deleteRoleAndResource(idLists);
        // 用户绑定到这个角色上,也应该删除
        Integer count = roleService.deleteRoleAndUser(idLists);
        return new ResponseBean(count);
    }

    /**
     * 
     * 
     * 功能描述：社内用户修改时获取所有角色
     * 
     * @return 获取到角色的id与名称
     * 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
    @RequestMapping(value = "/list/role", method = RequestMethod.GET)
    public ResponseBean role() {
        return new ResponseBean(roleService.getListRole());
    }
}
