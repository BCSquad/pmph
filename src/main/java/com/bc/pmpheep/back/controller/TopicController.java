package com.bc.pmpheep.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.po.TopicLog;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.TopicService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphEditorVO;
import com.bc.pmpheep.back.vo.TopicDeclarationVO;
import com.bc.pmpheep.back.vo.TopicDirectorVO;
import com.bc.pmpheep.back.vo.TopicEditorVO;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * 
 * 功能描述：选题申报控制层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Controller
@RequestMapping(value = "/topic")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TopicController {
	@Autowired
	TopicService topicService;
	@Autowired
	PmphUserService pmphUserService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "选题申报";

	/**
	 * 
	 * 
	 * 功能描述：维护人员选题申报审核页面
	 *
	 * @param request
	 * @param pageSize
	 *            当前页条数
	 * @param pageNumber
	 *            当前页数
	 * @param bookname
	 *            选题名称
	 * @param submitTime
	 *            提交时间
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "维护人员查询可操作的选题")
	@RequestMapping(value = "/listOpts", method = RequestMethod.GET)
	public ResponseBean listOpts(HttpServletRequest request, Integer pageSize, Integer pageNumber, String bookname,
			String submitTime1,String submitTime2) {
		PageParameter<TopicOPtsManagerVO> pageParameter = new PageParameter(pageNumber, pageSize);
		TopicOPtsManagerVO topicOPtsManagerVO = new TopicOPtsManagerVO();
		topicOPtsManagerVO.setBookname(bookname);
		if (StringUtil.isEmpty(submitTime1)) {
			topicOPtsManagerVO.setSubmitTime1(null);
		} else {
			topicOPtsManagerVO.setSubmitTime1(DateUtil.str2Timestam(submitTime1));
		}
		if (StringUtil.isEmpty(submitTime2)) {
			topicOPtsManagerVO.setSubmitTime2(null);
		} else {
			topicOPtsManagerVO.setSubmitTime2(DateUtil.str2Timestam(submitTime2));
		}
		String sessionId = CookiesUtil.getSessionId(request);
		pageParameter.setParameter(topicOPtsManagerVO);
		return new ResponseBean(topicService.listOpts(sessionId, pageParameter));
	}

	/**
	 * 
	 * 
	 * 功能描述：运维人员分配选题申报给部门
	 *
	 * @param id
	 *            选题id
	 * @param departmentId
	 *            部门id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "运维人员分配选题给部门")
	@RequestMapping(value = "/put/optsHandling", method = RequestMethod.PUT)
	public ResponseBean optsHandling(HttpServletRequest request, Long id, Long departmentId,String openid,String bookname,Long adminId) {
		String sessionId = CookiesUtil.getSessionId(request);
		TopicLog topicLog = new TopicLog();
		topicLog.setTopicId(id);
		topicLog.setTopicEvent("运维人员分配选题给部门");
		Topic topic = new Topic();
		topic.setId(id);
		topic.setDepartmentId(departmentId);
		topic.setIsDirectorHandling(true);
		topic.setBookname(bookname);
		return new ResponseBean(topicService.update(topicLog, sessionId, topic,openid,adminId));
	}

	/**
	 * 
	 * Description:后台部门主任查看可操作的选题
	 * 
	 * @author:lyc
	 * @date:2017年12月22日下午2:59:37
	 * @param request
	 * @param pageSize
	 *            当前页条数
	 * @param pageNumber
	 *            当前页数
	 * @param bookname
	 *            选题名称
	 * @param submitTime
	 *            提交时间
	 * @return ResponseBean
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "部门主任查看可操作的选题")
	@RequestMapping(value = "/listDirector", method = RequestMethod.GET)
	public ResponseBean listDirector(HttpServletRequest request, Integer pageSize, Integer pageNumber, String bookname,
			String submitTime1,String submitTime2) {
		PageParameter<TopicDirectorVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		TopicDirectorVO topicDirectorVO = new TopicDirectorVO();
		topicDirectorVO.setBookname(bookname);
		if (StringUtil.isEmpty(submitTime1)) {
			topicDirectorVO.setSubmitTime1(null);
		} else {
			topicDirectorVO.setSubmitTime1(DateUtil.str2Timestam(submitTime1));
		}
		if (StringUtil.isEmpty(submitTime2)) {
			topicDirectorVO.setSubmitTime2(null);
		} else {
			topicDirectorVO.setSubmitTime2(DateUtil.str2Timestam(submitTime2));
		}
		String sessionId = CookiesUtil.getSessionId(request);
		pageParameter.setParameter(topicDirectorVO);
		return new ResponseBean(topicService.listTopicDirectorVOs(sessionId, pageParameter));
	}

	/**
	 * 
	 * 
	 * 功能描述：主任操作选题流程
	 *
	 * @param id
	 *            选题申报id
	 * @param editorId
	 *            部门编辑id
	 * @param isRejectedByDirector
	 *            主任是否退回
	 * @param reasonDirector
	 *            退回原因
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "主任分配选题给部门编辑")
	@RequestMapping(value = "/put/directorHandling", method = RequestMethod.PUT)
	public ResponseBean directorHandling(HttpServletRequest request, Long id, Long editorId,
			Boolean isRejectedByDirector, String reasonDirector,String openid,String bookname) {
		String sessionId = CookiesUtil.getSessionId(request);
		TopicLog topicLog = new TopicLog();
		topicLog.setTopicId(id);
		Topic topic = new Topic();
		topic.setId(id);
		topic.setBookname(bookname);
		if (ObjectUtil.isNull(isRejectedByDirector)) {
			isRejectedByDirector = false;
		}
		topic.setIsRejectedByDirector(isRejectedByDirector);
		if (isRejectedByDirector) {
			topicLog.setTopicEvent("主任退回选题给运维人员");
			topic.setIsRejectedByEditor(false);
			topic.setReasonEditor("-");
			topic.setIsDirectorHandling(false);
			topic.setReasonDirector(reasonDirector);
		} else {
			topicLog.setTopicEvent("主任分配选题给部门编辑");
			topic.setEditorId(editorId);
			topic.setIsRejectedByEditor(false);
			topic.setIsEditorHandling(true);
			//topic.setIsDirectorHandling(null);
		}
		return new ResponseBean(topicService.update(topicLog, sessionId, topic,openid,editorId));
	}

	/**
	 * 
	 * Description:后台部门编辑查看可操作的申报选题
	 * 
	 * @author:lyc
	 * @date:2017年12月22日下午3:10:12
	 * @param request
	 * @param pageSize
	 *            当前页条数
	 * @param pageNumber
	 *            当前页数
	 * @param bookname
	 *            选题名称
	 * @param submitTime
	 *            提交时间
	 * @return ResponseBean
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "部门编辑查看可操作的申报选题")
	@RequestMapping(value = "/listEditor", method = RequestMethod.GET)
	public ResponseBean listEditor(HttpServletRequest request, Integer pageSize, Integer pageNumber, String bookname,
			String submitTime1,String submitTime2) {
		PageParameter<TopicEditorVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		TopicEditorVO topicEditorVO = new TopicEditorVO();
		topicEditorVO.setBookname(bookname);
		if (StringUtil.isEmpty(submitTime1)) {
			topicEditorVO.setSubmitTime1(null);
		} else {
			topicEditorVO.setSubmitTime1(DateUtil.str2Timestam(submitTime1));
		}
		if (StringUtil.isEmpty(submitTime2)) {
			topicEditorVO.setSubmitTime2(null);
		} else {
			topicEditorVO.setSubmitTime2(DateUtil.str2Timestam(submitTime2));
		}
		String sessionId = CookiesUtil.getSessionId(request);
		pageParameter.setParameter(topicEditorVO);
		return new ResponseBean(topicService.listTopicEditorVOs(sessionId, pageParameter));
	}

	/**
	 *
	 *
	 * 功能描述：编辑操作选题申报
	 *
	 * @param id
	 *            选题申报id
	 * @param authProgress
	 *            审核进度
	 * @param authFeedback
	 *            审核意见
	 * @param
	 *
	 * @param isAccepted
	 *            编辑是否受理
	 * @param isRejectedByEditor
	 *            是否退回上级
	 * @param reasonEditor
	 *            退回原因
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "编辑对选题进行操作")
	@RequestMapping(value = "/put/editorHandling", method = RequestMethod.PUT)
	public ResponseBean editorHandling(HttpServletRequest request, Long id, Integer authProgress, String authFeedback,
			Boolean isRejectedByEditor, Boolean isAccepted, String reasonEditor) {
		String sessionId = CookiesUtil.getSessionId(request);
		TopicLog topicLog = new TopicLog();
		topicLog.setTopicId(id);
		topicLog.setTopicEvent("编辑受理选题");
		Topic topic = new Topic();
		topic.setId(id);
		topic.setIsAccepted(isAccepted);
		if (ObjectUtil.isNull(isAccepted)) {
			if (ObjectUtil.isNull(isRejectedByEditor)) {
				isRejectedByEditor = false;
			}
			topic.setIsRejectedByEditor(isRejectedByEditor);
			if (isRejectedByEditor) {
				topicLog.setTopicEvent("编辑退回选题给主任");
				topic.setEditorId(0L);
				topic.setIsEditorHandling(false);
				topic.setReasonEditor(reasonEditor);
			} else {
				topicLog.setTopicEvent("编辑审核选题申报");
				topic.setAuthDate(DateUtil.getCurrentTime());
				topic.setAuthFeedback(authFeedback);
				topic.setAuthProgress(authProgress);
			}
		}
		return new ResponseBean(topicService.update(topicLog, sessionId, topic,"",null));
	}

	/**
	 * 
	 * 
	 * 功能描述：获取选题申报详情
	 *
	 * @param id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取选题申报详情")
	@RequestMapping(value = "/get/topicText", method = RequestMethod.GET)
	public ResponseBean topicText(HttpServletRequest request, Long id) {
		String sessionId = CookiesUtil.getSessionId(request);
		TopicLog topicLog = new TopicLog();
		topicLog.setTopicId(id);
		topicLog.setTopicEvent("获取选题申报详情");
		return new ResponseBean(topicService.topicTextVO(topicLog, sessionId, id));
	}

	/**
	 * 
	 * 
	 * 功能描述：查看选题申报
	 * 
	 * @param authProgress
	 *            申报状态
	 * @param bookname
	 *            申报标题
	 * @param submitTime
	 *            提交日期
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看选题申报")
	@RequestMapping(value = "/list/checkTopic", method = RequestMethod.GET)
	public ResponseBean checkTopic(String authProgress, String bookname, String submitTime1,String submitTime2, Integer pageSize,
			Integer pageNumber,HttpServletRequest request) {
		String[] strs = authProgress.split(",");
		List<Long> progress = new ArrayList<>();
		for (String str : strs) {
			progress.add(Long.valueOf(str));
		}
		PageParameter<TopicDeclarationVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		TopicDeclarationVO topicDeclarationVO = new TopicDeclarationVO();
		topicDeclarationVO.setBookname(bookname);
		if (StringUtil.isEmpty(submitTime1)) {
			topicDeclarationVO.setSubmitTime1(null);
		} else {
			topicDeclarationVO.setSubmitTime1(DateUtil.str2Timestam(submitTime1));
		}
		if (StringUtil.isEmpty(submitTime2)) {
			topicDeclarationVO.setSubmitTime2(null);
		} else {
			topicDeclarationVO.setSubmitTime2(DateUtil.str2Timestam(submitTime2));
		}
		pageParameter.setParameter(topicDeclarationVO);
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(topicService.listCheckTopic(progress, pageParameter,sessionId));
	}

	/**
	 * 
	 * 
	 * 功能描述：查看选题申报详情
	 *
	 * @param id
	 * @return
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看选题申报详情")
	@RequestMapping(value = "/get/detail", method = RequestMethod.GET)
	public ResponseBean detail(HttpServletRequest request, Long id) {
		String sessionId = CookiesUtil.getSessionId(request);
		TopicLog topicLog = new TopicLog();
		topicLog.setTopicId(id);
		topicLog.setTopicEvent("查看选题申报详情");
		TopicTextVO topicTextVO = topicService.topicTextVO(topicLog, sessionId, id);
		topicTextVO.setIsAccepted(null);
		topicTextVO.setIsDirectorHandling(null);
		topicTextVO.setIsEditorHandling(null);
		return new ResponseBean(topicTextVO);
	}

	/**
	 * 
	 * Description:部门主任获取部门人员信息列表
	 * 
	 * @author:lyc
	 * @date:2017年12月27日下午5:02:46
	 * @param
	 * @return ResponseBean
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取部门编辑列表")
	@RequestMapping(value = "/listEditors", method = RequestMethod.GET)
	public ResponseBean listEditors(Long departmentId, String realName, Integer pageSize, Integer pageNumber) {
		PageParameter<PmphEditorVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
		PmphEditorVO pmphEditorVO = new PmphEditorVO();
		pmphEditorVO.setDepartmentId(departmentId);
		pmphEditorVO.setRealName(realName);
		pageParameter.setParameter(pmphEditorVO);
		return new ResponseBean(pmphUserService.listEditors(pageParameter));
	}

	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "判断当前用户的身份")
	@RequestMapping(value = "/identity", method = RequestMethod.GET)
	public ResponseBean identity(HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(pmphUserService.identity(sessionId));
	}
}
