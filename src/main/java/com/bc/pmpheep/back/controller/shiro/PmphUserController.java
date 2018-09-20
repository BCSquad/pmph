package com.bc.pmpheep.back.controller.shiro;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;

import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.MaterialService;
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
	@Autowired
	MaterialService materialService;
	@Autowired
	FileService fileService;
	
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "社内用户";
	/**
	 * 获取社内用户
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月11日 下午5:19:15
	 * @param id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据主键 获取用户要更新的信息")
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public ResponseBean getInfo(HttpServletRequest request){
		return new ResponseBean(userService.getInfo(request));
	}
	
	/**
	 * 获取社内用户
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年12月11日 下午5:19:15
	 * @param id
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新密码")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public ResponseBean updatePassword(HttpServletRequest request,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword){
		return new ResponseBean(userService.updatePassword(request,oldPassword, newPassword));
	}

	@ResponseBody
	@RequestMapping(value = "uploadFile" , method = RequestMethod.POST)
	public ResponseBean uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("ids")Long ids , HttpServletRequest request) throws IOException {

		InputStream stream = null;
		String mid = null;
		try {
			stream = file.getInputStream();
			mid = fileService.save(stream, file.getOriginalFilename(), ImageType.PMPH_USER_AVATAR, 0);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}


		PmphUser pmphUser = new PmphUser(ids);
		pmphUser.setAvatar(mid);
		userService.updateUser(pmphUser);


		return new ResponseBean(mid);
	}
	
	
	/**
	 * 修改个人信息
	 * @author Mryang
	 * @createDate 2017年11月28日 下午3:33:44
	 * @param pmphUser
	 * @param file
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改个人信息")
	@RequestMapping(value = "/updatePersonalData", method = RequestMethod.PUT)
	public ResponseBean updatePersonalData(
			HttpServletRequest request, 
			PmphUser pmphUser,
			@RequestParam(value="file", required = false) String newAvatar) {
		ResponseBean rb = new ResponseBean();
		try {
			rb.setData(userService.updatePersonalData(request,pmphUser, newAvatar));
		} catch (IOException e) {
			rb.setData("文件上传失败");
			rb.setCode(0);
		} catch (Exception e) {
			rb.setData(e.getMessage());
			rb.setCode(0);
		}
		return rb;
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询所有的用户对象列表")
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加用户角色关联表数据")
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新单个用户信息")
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新用户数据和角色")
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询指定用户 id 所拥有的权限")
	@RequestMapping(value = "/resources/{id}", method = RequestMethod.GET)
	public ResponseBean resources(@PathVariable("id") Long userId) {
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除用户")
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分页查询社内用户")
	@RequestMapping(value = "/list/pmphUser", method = RequestMethod.GET)
	public ResponseBean pmphUser(Integer pageSize, Integer pageNumber, String name, @RequestParam("path") String path,
			Long departmentId,Long groupId) {
		PageParameter pageParameter = new PageParameter<>();
		PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
		if (StringUtil.notEmpty(name)) {
			pmphUserManagerVO.setName(name.replaceAll(" ", ""));// 去除空格
		}
		pmphUserManagerVO.setPath(path);
		pmphUserManagerVO.setDepartmentId(departmentId);
		pageParameter.setPageNumber(pageNumber);
		pageParameter.setPageSize(pageSize);
		pageParameter.setParameter(pmphUserManagerVO);
		return new ResponseBean(userService.getListPmphUser(pageParameter,groupId));
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改社内用户")
	@RequestMapping(value = "/update/updateUser", method = RequestMethod.PUT)
	public ResponseBean updateUser(PmphUserManagerVO pmphUserManagerVO) {
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
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化获取社内所有部门")
	@RequestMapping(value = "/list/pmphDepartment", method = RequestMethod.GET)
	public ResponseBean pmphDepartment(Long id) {
		return new ResponseBean(pmphDepartmentService.listPmphDepartment(id));
	}
	/**
	 * 个人中心（首页）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "个人中心首页")
	@RequestMapping(value = "/personal/center", method = RequestMethod.GET)
	public ResponseBean center(HttpServletRequest request,String state,String materialName,
			String groupName,String bookname,String name,String title,String authProgress,String topicBookname) {
		return new ResponseBean(userService.getPersonalCenter(request,state,materialName,groupName,
				title,bookname,name,authProgress,topicBookname));
	}
}
