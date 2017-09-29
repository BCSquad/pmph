package com.bc.pmpheep.back.controller.group;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年9月21日 下午3:59:34
 *
 *
 */
@Controller
@RequestMapping(value = "/groups")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GroupController {

	@Autowired
	private PmphGroupService pmphGroupService;
	@Autowired
	PmphGroupMemberService pmphGroupMemberService;

	/**
	 * 根据小组名称模糊查询获取当前用户的小组
	 *
	 * @author Mryang
	 * @param pmphGroup
	 * @return
	 * @createDate 2017年9月21日 下午4:02:57
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ResponseBean getList(PmphGroup pmphGroup) {
		/*
		 * --------- 以下是正确的示例 ---------
		 *
		 * 在ResponseBean初始化时，通过ResponseBeanAop对其构造函数进行切面编程，
		 * 因此返回时<务必>要使用ResponseBean的构造函数即 new ResponseBean(anything)
		 */
		return new ResponseBean(pmphGroupService.getList(pmphGroup));
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
	@RequestMapping(value = "/getListPmphGroupMember")
	public ResponseBean getListPmphGroupMember(Long groupId) {
		return new ResponseBean(pmphGroupMemberService.getListPmphGroupMember(groupId));
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
	@RequestMapping(value = "/addPmphGroupOnGroup")
	public ResponseBean addPmphGroupOnGroup(MultipartFile file, PmphGroup pmphGroup) {
		try {
			return new ResponseBean(pmphGroupService.addPmphGroupOnGroup(file, pmphGroup));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：添加小组成员
	 *
	 * @param pmphGroupMembers 需要小组Id 成员Id 是否作家用户
	 * @return 是否成功
	 *
	 */
	public ResponseBean addPmphGroupMemberOnGroup(List<PmphGroupMember> pmphGroupMembers) {
		return new ResponseBean(pmphGroupMemberService.addPmphGroupMemberOnGroup(pmphGroupMembers));
	}
}
