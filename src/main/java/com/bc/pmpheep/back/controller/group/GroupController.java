package com.bc.pmpheep.back.controller.group;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupMessageService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;
import com.bc.pmpheep.back.vo.PmphGroupMessageVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author MrYang
 * @CreateDate 2017年9月21日 下午3:59:34
 * 
 * 
 */
@Controller
@RequestMapping(value = "/group")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GroupController {

	@Autowired
	private PmphGroupService pmphGroupService;
	@Autowired
	PmphGroupMemberService pmphGroupMemberService;
	@Autowired
	private PmphGroupFileService pmphGroupFileService;
	@Autowired
	PmphGroupMessageService pmphGroupMessageService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "小组";

	/**
	 * 根据小组名称模糊查询获取当前用户的小组
	 * 
	 * @author Mryang
	 * @param pmphGroup
	 * @return
	 * @createDate 2017年9月21日 下午4:02:57
	 */
	@RequestMapping(value = "/list/pmphGroup", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据小组名称模糊查询获取当前用户的小组")
	@ResponseBody
	public ResponseBean pmphGroup(@RequestParam(name = "groupName", defaultValue = "") String groupName,
			HttpServletRequest request) {
		/*
		 * --------- 以下是正确的示例 ---------
		 * 
		 * 在ResponseBean初始化时，通过ResponseBeanAop对其构造函数进行切面编程，
		 * 因此返回时<务必>要使用ResponseBean的构造函数即 new ResponseBean(anything)
		 */
		PmphGroup pmphGroup = new PmphGroup();
		if (ObjectUtil.notNull(groupName)) {
			pmphGroup.setGroupName(groupName.trim());
		}
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupService.listPmphGroup(pmphGroup, sessionId));
	}

	/**
	 * 上传文件时的小组列表
	 * 
	 * @author Mryang
	 * @param pmphGroup
	 * @return
	 * @createDate 2017年9月21日 下午4:02:57
	 */
	@RequestMapping(value = "/list/pmphGroupFile", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "上传文件时的小组列表")
	@ResponseBody
	public ResponseBean pmphGroupFile(HttpServletRequest request) {
		PmphGroup pmphGroup = new PmphGroup();
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupService.listPmphGroupFile(pmphGroup, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：根据小组id查询小组成员
	 * 
	 * @param groupId
	 *            小组id
	 * @return 该小组的小组成员
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据小组id查询小组成员")
	@RequestMapping(value = "/list/pmphGroupMember", method = RequestMethod.GET)
	public ResponseBean pmphGroupMember(Long groupId, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupMemberService.listPmphGroupMember(groupId, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：新建小组
	 * 
	 * @param file
	 *            上传的头像
	 * @param pmphGroup
	 *            新增的小组信息
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新建小组")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseBean add(String file, PmphGroup pmphGroup, HttpServletRequest request) {
		try {
			String sessionId = CookiesUtil.getSessionId(request);
			return new ResponseBean(pmphGroupService.addPmphGroupOnGroup(file, pmphGroup, sessionId));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 
	 * 功能描述：小组头像上传
	 *
	 * @param request
	 * @param files
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "上传附件")
	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public ResponseBean files(@RequestParam("file") MultipartFile file) {
		return new ResponseBean(pmphGroupService.msgUploadFiles(file));
	}

	/**
	 * 
	 * 
	 * 功能描述：添加小组成员
	 * 
	 * @param pmphGroupMembers
	 *            需要小组Id 成员Id 是否作家用户
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加小组成员")
	@RequestMapping(value = "/add/groupMember", method = RequestMethod.POST)
	public ResponseBean groupMember(Long groupId, String pmphGroupMembers, HttpServletRequest request) {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<PmphGroupMember>>() {
		}.getType();
		List<PmphGroupMember> list = gson.fromJson(pmphGroupMembers, type);
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupMemberService.addPmphGroupMemberOnGroup(groupId, list, sessionId));

	}

	/**
	 * 
	 * 
	 * 功能描述：修改小组头像或小组名称
	 * 
	 * @param file
	 *            修改后上传的头像
	 * @param pmphGroup
	 *            小组id 与 小组新名称
	 * @return 是否成功
	 * @update Mryang 2017.10.13 15:20
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改小组头像或小组名称")
	@RequestMapping(value = "/update/pmphGroupDetail", method = RequestMethod.POST)
	public ResponseBean pmphGroupDetail(String file, PmphGroup pmphGroup, HttpServletRequest request) {
		try {
			String sessionId = CookiesUtil.getSessionId(request);
			return new ResponseBean(pmphGroupService.updatePmphGroupOnGroup(file, pmphGroup, sessionId));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 
	 * 
	 * 功能描述： 根据小组id解散小组
	 * 
	 * @param pmphGroup
	 *            要删除的小组
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "解散小组")
	@RequestMapping(value = "/delete/group", method = RequestMethod.DELETE)
	public ResponseBean group(PmphGroup pmphGroup, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupService.deletePmphGroupById(pmphGroup, sessionId));
	}

	/**
	 * 
	 * Description:上传小组文件
	 * 
	 * @author:lyc
	 * @date:2017年9月30日下午4:50:04
	 * @Param:上传的文件以及小组、上传人信息
	 * @Return:是否成功
	 * @update Mryang 2017.10.13 15:30
	 */
	@RequestMapping(value = "/add/pmphGroupFile", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "上传小组文件")
	@ResponseBody
	public ResponseBean pmphGroupFile(Long[] ids, MultipartFile file, HttpServletRequest request) {
		try {
			String sessionId = CookiesUtil.getSessionId(request);
			return new ResponseBean(pmphGroupFileService.addPmphGroupFile(ids, file, sessionId));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 
	 * Description:分页查询小组共享文件
	 * 
	 * @author:lyc
	 * @date:2017年9月30日下午4:53:26
	 * @Param:Page传入的查询条件，若文件名不为空则为模糊查询功能
	 * @Return:小组共享文件分页集合
	 */
	@RequestMapping(value = "/list/groupFile", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询小组共享文件")
	@ResponseBody
	public ResponseBean groupFile(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize") Integer pageSize, PmphGroupFileVO pmphGroupFileVO) {
		PageParameter<PmphGroupFileVO> pageParameter = new PageParameter<PmphGroupFileVO>(pageNumber, pageSize,
				pmphGroupFileVO);
		return new ResponseBean(pmphGroupFileService.listGroupFile(pageParameter));
	}

	/**
	 * 
	 * Description:删除小组共享文件
	 * 
	 * @author:lyc
	 * @date:2017年9月30日下午4:58:50
	 * @Param:文件id
	 * @Return:是否成功
	 */
	@RequestMapping(value = "/delete/file", method = RequestMethod.DELETE)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除小组共享文件")
	@ResponseBody
	public ResponseBean file(Long groupId, Long[] ids, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupFileService.deletePmphGroupFileById(groupId, ids, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：批量删除小组成员
	 * 
	 * @param listPar
	 *            小组成员id
	 * @param sessionId
	 *            当前操作者
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除小组成员")
	@RequestMapping(value = "/delete/pmphGroupMembers", method = RequestMethod.PUT)
	public ResponseBean pmphGroupMembers(Long groupId, Long[] ids, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupMemberService.updateGroupMemberByIds(groupId, ids, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：批量修改小组成员权限
	 * 
	 * @param listPar
	 *            成员id
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量修改小组成员权限")
	@RequestMapping(value = "/update/identity", method = RequestMethod.PUT)
	public ResponseBean identity(Long groupId, String pmphGroupMembers, HttpServletRequest request) {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<PmphGroupMember>>() {
		}.getType();
		List<PmphGroupMember> list = gson.fromJson(pmphGroupMembers, type);
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupMemberService.updateMemberIdentity(groupId, list, sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：分页查询小组成员管理界面小组成员信息
	 * 
	 * @param pageSize
	 *            当页条数
	 * @param pageNumber
	 *            当前页数
	 * @param groupId
	 *            小组id
	 * @param name
	 *            姓名/帐号
	 * @return
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "分页查询小组成员管理界面小组成员信息")
	@RequestMapping(value = "/list/manager", method = RequestMethod.GET)
	public ResponseBean manager(Integer pageSize, Integer pageNumber, Long groupId, String name) {
		PageParameter pageParameter = new PageParameter<>(pageNumber, pageSize);
		PmphGroupMemberManagerVO pmphGroupMemberManagerVO = new PmphGroupMemberManagerVO();
		pmphGroupMemberManagerVO.setName(name);
		pmphGroupMemberManagerVO.setGroupId(groupId);
		pageParameter.setParameter(pmphGroupMemberManagerVO);
		return new ResponseBean(pmphGroupMemberService.listGroupMemberManagerVOs(pageParameter));
	}

	/**
	 * 
	 * 
	 * 功能描述：发送小组消息
	 * 
	 * @param msgConrent
	 *            消息内容
	 * @param groupId
	 *            小组id
	 * @param sessionId
	 *            当前用户session id
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "发送小组消息")
	@RequestMapping(value = "/add/groupMessage", method = RequestMethod.POST)
	public ResponseBean groupMessage(String msgConrent, Long groupId, HttpServletRequest request) {
		try {
			String sessionId = CookiesUtil.getSessionId(request);
			return new ResponseBean(
					pmphGroupMessageService.addGroupMessage(msgConrent, groupId, sessionId, Const.SENDER_TYPE_1));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：撤回消息
	 * 
	 * @param id
	 *            该消息id
	 * @param sessionId
	 *            当前用户session id
	 * @return 是否成功
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "撤回小组消息")
	@RequestMapping(value = "/delete/pmphGroupMessage", method = RequestMethod.DELETE)
	public ResponseBean pmphGroupMessage(Long id, HttpServletRequest request) {
		try {
			String sessionId = CookiesUtil.getSessionId(request);
			return new ResponseBean(pmphGroupMessageService.deletePmphGroupMessageById(id, sessionId));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：进入小组的时候获取历史消息
	 * 
	 * @param pageSize
	 *            获取的条数
	 * @param pageNumber
	 *            当前第几页
	 * @param groupId
	 *            小组id
	 * @param nowTime
	 *            进入小组的时间节点
	 * @return 消息结果集
	 * 
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取历史消息")
	@RequestMapping(value = "/list/message", method = RequestMethod.GET)
	public ResponseBean message(Integer pageSize, Integer pageNumber, Long groupId, Long baseTime) {
		PageParameter<PmphGroupMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		PmphGroupMessageVO pmphGroupMessageVO = new PmphGroupMessageVO();
		pmphGroupMessageVO.setGmtCreate(new Timestamp(baseTime));
		pmphGroupMessageVO.setGroupId(groupId);
		pageParameter.setParameter(pmphGroupMessageVO);
		return new ResponseBean(pmphGroupMessageService.listPmphGroupMessage(pageParameter));
	}

	/**
	 * 功能描述：职位遴选页面新建小组
	 * 
	 * @param textbookId
	 * @param pmphGroupMembers
	 * @param request
	 * @return 是否成功
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "职位遴选页面新建小组")
	@RequestMapping(value = "/addEditorGroup", method = RequestMethod.POST)
	public ResponseBean addEditorGroup(Long textbookId, String pmphGroupMembers, HttpServletRequest request) {
		try {
			String sessionId = CookiesUtil.getSessionId(request);
			Gson gson = new Gson();
			Type type = new TypeToken<ArrayList<PmphGroupMember>>() {
			}.getType();
			List<PmphGroupMember> list = gson.fromJson(pmphGroupMembers, type);
			return new ResponseBean(pmphGroupService.addEditorSelcetionGroup(sessionId, list, textbookId));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 职位遴选页面更新小组成员
	 * 
	 * @param textbookId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "职位遴选页面更新小组成员")
	@RequestMapping(value = "/addEditors", method = RequestMethod.POST)
	public ResponseBean addEditors(Long textbookId, HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphGroupMemberService.addEditorBookGroup(textbookId, sessionId));
	}
}
