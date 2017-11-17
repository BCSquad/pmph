package com.bc.pmpheep.back.controller.shiro;

import java.util.ArrayList;
import java.util.List;

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
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.back.service.WriterRoleService;
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
@RequestMapping("/role/writer")
public class WriterRoleController {
	Logger logger = LoggerFactory.getLogger(WriterRoleController.class);
	@Autowired
	WriterRoleService writerRoleService;
	@Autowired
	WriterPermissionService writerPermissionService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "作家用户角色";

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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询所有角色")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseBean list(@RequestParam("roleName") String roleName) {
		List<WriterRole> roleList = writerRoleService.getListRole(roleName);
		return new ResponseBean(roleList);
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：添加用户角色的后台方法
	 * 使用示范：
	 *
	 * @param role
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加用户角色")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseBean add(WriterRole role) {
		logger.debug(role.toString());
		return new ResponseBean(writerRoleService.add(role));
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新角色")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ResponseBean update(@PathVariable("id") Long id) {
		WriterRole role = writerRoleService.get(id);
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改角色对象")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseBean update(WriterRole role) {
		logger.debug(role.toString());
		writerRoleService.update(role);
		return new ResponseBean();
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：获取资源
	 * 使用示范：
	 *
	 * @param roleId
	 * @return
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据角色ID查询对应权限对象集合")
	@RequestMapping(value = "/resources/{roleId}", method = RequestMethod.GET)
	public ResponseBean resources(@PathVariable("roleId") Long roleId) {
		return new ResponseBean(writerRoleService.getListWriterRolePermission(roleId));
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据角色 id 和权限 id 增加一条用户权限关联数据")
	@RequestMapping(value = "/resources", method = RequestMethod.POST)
	public ResponseBean resource(@RequestParam("roleId") Long roleId,
			@RequestParam("permissionIds") String permissionIds) {
		String[] ids = permissionIds.split(",");
		List<Long> idLists = new ArrayList<Long>(ids.length);
		for (String id : ids) {
			idLists.add(Long.valueOf(id));
		}
		return new ResponseBean(writerRoleService.addRoleResource(roleId, idLists));
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "角色删除")
	@RequestMapping(value = "/delete/{roleIds}", method = RequestMethod.DELETE)
	public ResponseBean delete(@PathVariable("roleIds") String roleIds) {
		logger.debug(roleIds);
		String[] ids = roleIds.split(",");
		List<Long> idLists = new ArrayList<Long>(ids.length);
		for (String id : ids) {
			idLists.add(Long.valueOf(id));
		}
		// 先批量删除角色,再从角色资源表中删除角色资源数据
		writerRoleService.deleteRoleAndResource(idLists);
		// 用户绑定到这个角色上,也应该删除
		Integer count = writerRoleService.deleteRoleAndUser(idLists);
		return new ResponseBean(count);
	}
}
