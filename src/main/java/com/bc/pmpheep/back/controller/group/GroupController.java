package com.bc.pmpheep.back.controller.group;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupMessageService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.ListPar;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;
import com.bc.pmpheep.back.vo.PmphGroupMessageVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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

	/**
	 * 根据小组名称模糊查询获取当前用户的小组
	 * 
	 * @author Mryang
	 * @param pmphGroup
	 * @return
	 * @createDate 2017年9月21日 下午4:02:57
	 */
	@RequestMapping(value = "/list/pmphgroup", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean listPmphGroup(@RequestParam(name = "groupName", defaultValue = "") String groupName,
			@RequestParam("sessionId") String sessionId) {
		/*
		 * --------- 以下是正确的示例 ---------
		 * 
		 * 在ResponseBean初始化时，通过ResponseBeanAop对其构造函数进行切面编程，
		 * 因此返回时<务必>要使用ResponseBean的构造函数即 new ResponseBean(anything)
		 */
		PmphGroup pmphGroup = new PmphGroup();
		if (Tools.isNotNullOrEmpty(groupName)) {
			pmphGroup.setGroupName(groupName);
		}
		return new ResponseBean(pmphGroupService.listPmphGroup(pmphGroup, sessionId));
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
	@RequestMapping(value = "/list/pmphgroupmember", method = RequestMethod.GET)
	public ResponseBean listPmphGroupMember(Long groupId, @RequestParam("sessionId") String sessionId) {
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
	@RequestMapping(value = "/add/pmphgroup", method = RequestMethod.POST)
	public ResponseBean addPmphGroupOnGroup(MultipartFile file, PmphGroup pmphGroup,
			@RequestParam("sessionId") String sessionId) {
		try {
			return new ResponseBean(pmphGroupService.addPmphGroupOnGroup(file, pmphGroup, sessionId));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
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
	@RequestMapping(value = "/add/pmphgroupmember", method = RequestMethod.POST)
	public ResponseBean addPmphGroupMemberOnGroup(Long gruopId, ListPar listPar, String sessionId) {
		return new ResponseBean(
				pmphGroupMemberService.addPmphGroupMemberOnGroup(gruopId, listPar.getPmphGroupMembers(), sessionId));
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
	@RequestMapping(value = "/update/pmphgroup", method = RequestMethod.PUT)
	public ResponseBean updatePmphGroupOnGroup(MultipartFile file, PmphGroup pmphGroup) {
		try {
			return new ResponseBean(pmphGroupService.updatePmphGroup(file, pmphGroup));
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
	@RequestMapping(value = "/delete/pmphgroup", method = RequestMethod.DELETE)
	public ResponseBean deletePmphGroupById(PmphGroup pmphGroup, String sessionId) {
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
	@RequestMapping(value = "/add/pmphgroupfile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean addPmphGroupFile(ListPar listPar, String sessionId) {
		try {
			return new ResponseBean(
					pmphGroupFileService.addPmphGroupFile(listPar.getIds(), listPar.getFiles(), sessionId));
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
	@RequestMapping(value = "/list/groupfile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseBean listGroupFile(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
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
	@RequestMapping(value = "/delete/pmphgroupfile", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseBean deletePmphGroupFileById(Long groupId, ListPar listPar, String sessionId) {
		return new ResponseBean(pmphGroupFileService.deletePmphGroupFileById(groupId, listPar.getIds(), sessionId));
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
	@RequestMapping(value = "/delete/pmphgroupmember", method = RequestMethod.DELETE)
	public ResponseBean deletePmphGroupMemberByIds(Long groupId, ListPar listPar, String sessionId) {
		return new ResponseBean(
				pmphGroupMemberService.deletePmphGroupMemberByIds(groupId, listPar.getIds(), sessionId));
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
	@RequestMapping(value = "/update/identity", method = RequestMethod.PUT)
	public ResponseBean updateMemberIdentity(Long groupId, ListPar listPar, String sessionId) {
		PmphGroupMember pmphGroupMember = new PmphGroupMember();
		List<PmphGroupMember> list = new ArrayList<>();
		for (Long id : listPar.getIds()) {
			pmphGroupMember.setId(id);
			list.add(pmphGroupMember);
		}
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
	@RequestMapping(value = "/list/manager")
	public ResponseBean listGroupMemberManagerVOs(Integer pageSize, Integer pageNumber, Long groupId, String name) {
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
	@RequestMapping(value = "/add/groupmessage", method = RequestMethod.POST)
	public ResponseBean addGroupMessage(String msgConrent, Long groupId, String sessionId) {
		try {
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
	@RequestMapping(value = "/delete/pmphgroupmessage", method = RequestMethod.DELETE)
	public ResponseBean deletePmphGroupMessageById(Long id, String sessionId) {
		try {
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
	@RequestMapping(value = "/list/message", method = RequestMethod.GET)
	public ResponseBean listPmphGroupMessage(Integer pageSize, Integer pageNumber, Long groupId, Timestamp baseTime) {
		PageParameter<PmphGroupMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		PmphGroupMessageVO pmphGroupMessageVO = new PmphGroupMessageVO();
		pmphGroupMessageVO.setGmtCreate(baseTime);
		pmphGroupMessageVO.setGroupId(groupId);
		pageParameter.setParameter(pmphGroupMessageVO);
		return new ResponseBean(pmphGroupMessageService.listPmphGroupMessage(pageParameter));
	}
}
