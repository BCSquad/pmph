package com.bc.pmpheep.back.service.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bc.pmpheep.back.dao.DeclarationDao;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.*;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.general.bean.ProductTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.websocket.MyWebSocketHandler;
import com.bc.pmpheep.websocket.WebScocketMessage;

/**
 * 发送系统消息公共服务类 以下方法推荐放在操作完成后
 * 
 * @author MrYang
 * @CreateDate 2017年11月17日 上午8:55:49
 * 
 **/

@Service
public final class SystemMessageService {

	private final String messageTitle = "系统消息";

	@Autowired
	private UserMessageService userMessageService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private WriterUserService writerUserService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private TextbookService textbookService;

	@Autowired
	private DecPositionService decPositionService;

	@Autowired
	private DeclarationService declarationService;

	@Autowired
	private PmphGroupService pmphGroupService;

	@Autowired
	private PmphGroupMemberService pmphGroupMemberService;

	@Autowired
	private MaterialProjectEditorService materialProjectEditorService;

	@Autowired
	private CmsContentService cmsContentService;

	@Autowired
	private OrgService orgService;

	@Autowired
	private ExpertationService expertationService;

	@Autowired
	private ProductService productService;
	@Autowired
	DeclarationDao declarationDao;

	/**
	 * 遴选公告发布时，给学校管理员和学校教师发送消息，通知他们留意报名情况或者是参加报名
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 上午9:28:13
	 *  materialName
	 *            教材名称
	 *  ids
	 *            发送的机构id集合（新增或者增加的机构）
	 *  msgId
	 *            消息id，没有发布过 则为null
	 * @throws CheckedServiceException
	 * @throws IOException
	 * @return
	 */
	public void materialSend(Long materialId, List<Long> ids,PmphUser pmphUser,Boolean isProduct) throws CheckedServiceException, IOException {
		this.materialSend(materialId, ids, false,pmphUser,isProduct);
	}

	/**
	 * 遴选公告发布时，给学校管理员和学校教师发送消息，通知他们留意报名情况或者是参加报名
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 上午9:28:13
	 *            教材名称
	 * @param ids
	 *            发送的机构id集合（新增或者增加的机构）
	 *            消息id，没有发布过 则为null
	 * @param isOnlyManager
	 *            是否只发给管理员
	 * @throws CheckedServiceException
	 * @throws IOException
	 * @return
	 */
	public void materialSend(Long materialId, List<Long> ids, boolean isOnlyManager,PmphUser pmphUser,Boolean isProduct)
			throws CheckedServiceException, IOException {

		String materialName = isProduct?productService.getProductNameById(materialId):materialService.getMaterialNameById(materialId);
		if (StringUtils.isEmpty(materialName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息体为空");
		}
		if (null == ids || ids.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息接收对象为空");
		}
		// 向教师发送消息
		if (!isOnlyManager) {
			String tercherMsg = "《<font color='red'>" + materialName + "</font>》已经开始申报，请您留意";
			String msg_id = null;
			// mogodb保存消息体
			Message message = new Message(tercherMsg);
			message = messageService.add(message);
			msg_id = message.getId();
			// 获取这些机构启用的作家用户
			List<WriterUser> writerUserList = writerUserService.getWriterUserListByOrgIds(ids);
			if (null != writerUserList && writerUserList.size() > 0) {
				List<UserMessage> userMessageList = new ArrayList<UserMessage>(writerUserList.size());
				List<String> userIds = new ArrayList<String>(writerUserList.size());
				for (WriterUser writerUser : writerUserList) {
					// 信息是由系统发出
					UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
							writerUser.getId(), new Short("2"), materialId,isProduct);
					userMessageList.add(userMessage);
					userIds.add("2_" + writerUser.getId());
				}
				// 批量插入消息
				userMessageService.addUserMessageBatch(userMessageList);
				// websocket推送页面消息
				WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
						Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle,
						tercherMsg, DateUtil.getCurrentTime());
				myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			}
		}
		// 向管理员发送消息
		String managerMsg = "《<font color='red'>" + materialName + "</font>》已经开始申报，请您留意教职工的报名情况";
		Message message = new Message(managerMsg);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 获取这些机构的管理员
		List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(ids);
		if (null != orgUserList && orgUserList.size() > 0) {
			List<UserMessage> userMessageList = new ArrayList<UserMessage>(orgUserList.size());
			List<String> userIds = new ArrayList<String>(orgUserList.size());
			for (OrgUser orgUser : orgUserList) {
				UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
						orgUser.getId(), new Short("3"), materialId,isProduct);
				userMessageList.add(userMessage);
				userIds.add("3_" + orgUser.getId());
			}
			// 批量插入消息
			userMessageService.addUserMessageBatch(userMessageList);
			// websocket推送页面消息
			WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
					Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, managerMsg,
					DateUtil.getCurrentTime());
			myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		}
		return;
	}

	/**
	 * 遴选主编副主编时点击发布按钮之后向主编、副主编发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午1:57:04
	 * @param bookId
	 *            教材书籍id
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenConfirmFirstEditor(Long bookId, List<DecPositionPublished> newMessage,PmphUser pmphUser)
			throws CheckedServiceException, IOException {
		// 获取教材书籍
		Textbook textbook = textbookService.getTextbookById(bookId);
		if (textbook.getIsPublished()) {
			sendWhenPubfinalResult(bookId, newMessage,pmphUser);
			return;
		}
		// 获取教材
		Material material = materialService.getMaterialById(textbook.getMaterialId());
		if (null == material) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"该书籍没有找到对应的教材");
		}
		// 获取这本书的申报遴选列表
		HashMap<String, Object> paraMap = new HashMap<>();
		paraMap.put("material_id",textbook.getMaterialId());
		String materialCreateDate = declarationDao.findMaterialCreateDate(paraMap);
		Date date1 = DateUtil.fomatDate(materialCreateDate);
		Date date = DateUtil.fomatDate("2019-2-12 12:00");
		for (DecPositionPublished decPosition : newMessage) {
			if (null != decPosition && null != decPosition.getChosenPosition() && null != decPosition.getRank()) {// 筛选出主编、副主编
				Declaration declaration = declarationService.getDeclarationById(decPosition.getDeclarationId());
				// 消息内容
				String msgContent = "";

				if(date1.getTime()>date.getTime()) {

					if (decPosition.getChosenPosition() == 1) {
						if (decPosition.getRank() == 1) {
							msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
									+ "</font>》<font color='red'>[" + textbook.getTextbookName()
									+ "</font>]的第一主编，您现在可以开始遴选编委了，最终结果以遴选结果公告为准";
						} else {

							msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
									+ "</font>》<font color='red'>[" + textbook.getTextbookName()
									+ "</font>]的主编，最终结果以遴选结果公告为准";
						}

					}
					if (decPosition.getChosenPosition() == 2) {
						msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
								+ "</font>》<font color='red'>[" + textbook.getTextbookName()
								+ "</font>]的副主编，最终结果以遴选结果公告为准";


					}

				}else{
					if (decPosition.getChosenPosition() == 4 || decPosition.getChosenPosition() == 12) {
						if (decPosition.getRank() == 1) {
							if (decPosition.getChosenPosition() == 12) {
								msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
										+ "</font>》<font color='red'>[" + textbook.getTextbookName()
										+ "</font>]的第一主编和数字编委，您现在可以开始遴选编委了，最终结果以遴选结果公告为准";
							} else {
								msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
										+ "</font>》<font color='red'>[" + textbook.getTextbookName()
										+ "</font>]的第一主编，您现在可以开始遴选编委了，最终结果以遴选结果公告为准";
							}

						} else {
							if (decPosition.getChosenPosition() == 12) {
								msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
										+ "</font>》<font color='red'>[" + textbook.getTextbookName()
										+ "</font>]的主编与数字编委，最终结果以遴选结果公告为准";
							} else {
								msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
										+ "</font>》<font color='red'>[" + textbook.getTextbookName()
										+ "</font>]的主编，最终结果以遴选结果公告为准";
							}
						}
					}
					if (decPosition.getChosenPosition() == 2 || decPosition.getChosenPosition() == 10) {
						if (decPosition.getChosenPosition() == 10) {
							msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
									+ "</font>》<font color='red'>[" + textbook.getTextbookName()
									+ "</font>]的副主编和数字编委，最终结果以遴选结果公告为准";
						} else {
							msgContent = "恭喜您被选为《<font color='red'>" + material.getMaterialName()
									+ "</font>》<font color='red'>[" + textbook.getTextbookName()
									+ "</font>]的副主编，最终结果以遴选结果公告为准";
						}

					}


				}
				Message message = new Message(msgContent);
				message = messageService.add(message);
				String msg_id = message.getId();
				UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
						declaration.getUserId(), new Short("2"), null);
				userMessageService.addUserMessage(userMessage);
				// websocket推送页面消息
				WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
						Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle,
						msgContent, DateUtil.getCurrentTime());
				List<String> userIds = new ArrayList<String>(1);
				userIds.add("2_" + declaration.getUserId());
				myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			}
		}
	}

	/**
	 * 任意用户被邀请进入小组 向被邀请人发出
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午2:16:36
	 * @param inviterName
	 *            邀请人名字
	 * @param groupId
	 *            小组id
	 * @param invitedPersonIds
	 *            被邀请人ids
	 * @param invitedPersonType
	 *            被邀请人类型: 1=社内用户/2=作家/3=机构用户
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenInviteJoinGroup(String inviterName, Long groupId, List<Long> invitedPersonIds,
			short invitedPersonType,PmphUser pmphUser) throws CheckedServiceException, IOException {
		if (StringUtils.isEmpty(inviterName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"邀请人为空");
		}
		if (null == invitedPersonIds || invitedPersonIds.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"被邀请人为空");
		}
		// 获取小组
		PmphGroup pmphGroup = pmphGroupService.getPmphGroupById(groupId);
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组不存在");
		}
		String msgContent = "您被[<font color='red'>" + inviterName + "</font>]邀请加入[<font color='red'>"
				+ pmphGroup.getGroupName() + "]</font>小组";
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 组装消息和消息对象
		List<UserMessage> userMessageList = new ArrayList<UserMessage>(invitedPersonIds.size());
		List<String> userIds = new ArrayList<String>(invitedPersonIds.size());
		for (Long id : invitedPersonIds) {
			UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"), id,
					invitedPersonType, null);
			userMessageList.add(userMessage);
			userIds.add(invitedPersonType + "_" + id);
		}
		// 发送消息
		userMessageService.addUserMessageBatch(userMessageList);
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
				Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 任意用户被踢出进入小组 向被踢出人发出
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午2:16:36
	 * @param inviterName
	 *            踢出人名字
	 * @param groupId
	 *            小组id
	 * @param invitedPersonIds
	 *            被踢出人ids
	 * @param invitedPersonType
	 *            被踢出人类型: 1=社内用户/2=作家/3=机构用户
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenKickingOutGroup(String inviterName, Long groupId, List<Long> invitedPersonIds,
			short invitedPersonType) throws CheckedServiceException, IOException {
		if (StringUtils.isEmpty(inviterName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"邀请人为空");
		}
		if (null == invitedPersonIds || invitedPersonIds.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"被邀请人为空");
		}
		// 获取小组
		PmphGroup pmphGroup = pmphGroupService.getPmphGroupById(groupId);
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组不存在");
		}
		String msgContent = "您被[<font color='red'>" + inviterName + "</font>]移出群组[<font color='red'>"
				+ pmphGroup.getGroupName() + "]</font>";
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 组装消息和消息对象
		List<UserMessage> userMessageList = new ArrayList<UserMessage>(invitedPersonIds.size());
		List<String> userIds = new ArrayList<String>(invitedPersonIds.size());
		for (Long id : invitedPersonIds) {
			UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"), id,
					invitedPersonType, null);
			userMessageList.add(userMessage);
			userIds.add(invitedPersonType + "_" + id);
		}
		// 发送消息
		userMessageService.addUserMessageBatch(userMessageList);
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, 0L, "系统",
				Const.SENDER_TYPE_0, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 任意用户退出小组 给小组创建者和管理员发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午2:41:26
	 * @param exitPersonName
	 *            退出人姓名
	 * @param groupId
	 *            退出的小组id
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenQuitGroup(String exitPersonName, Long groupId) throws CheckedServiceException, IOException {
		if (StringUtils.isEmpty(exitPersonName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"退出人为空");
		}
		// 获取小组
		PmphGroup pmphGroup = pmphGroupService.getPmphGroupById(groupId);
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组不存在");
		}
		List<PmphGroupMemberVO> PmphGroupMemberVOList = pmphGroupMemberService.listPmphGroupMember(groupId, null);
		if (null == PmphGroupMemberVOList || PmphGroupMemberVOList.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组没有成员");
		}
		// 存入消息主体
		String msgContent = "[<font color='red'>" + exitPersonName + "</font>]退出了[" + pmphGroup.getGroupName() + "]小组";
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 组装消息和消息对象
		List<UserMessage> userMessageList = new ArrayList<UserMessage>(PmphGroupMemberVOList.size());
		List<String> userIds = new ArrayList<String>(PmphGroupMemberVOList.size());
		for (PmphGroupMemberVO pmphGroupMemberVO : PmphGroupMemberVOList) {
			if (pmphGroupMemberVO.getIsFounder() || pmphGroupMemberVO.getIsAdmin()) {// 给创建者和管理员发送
				UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"),
						pmphGroupMemberVO.getUserId(), pmphGroupMemberVO.getUserType(), null);
				userMessageList.add(userMessage);
				userIds.add(pmphGroupMemberVO.getUserType() + "_" + pmphGroupMemberVO.getUserId());
			}
		}
		// 发送消息
		userMessageService.addUserMessageBatch(userMessageList);
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, 0L, "系统",
				Const.SENDER_TYPE_0, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 
	 * 教师认证审 向教师用户发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午2:52:53
	 * @param auditorOrgName
	 *            认证机构名称，如：首都医科大学,如是人卫社认证的审核，则为：人民卫生出版社
	 * @param teacherIds
	 *            教师ids
	 * @param isPass
	 *            true 通过/false 退回
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenTeacherCertificationAudit(String auditorOrgName, List<Long> teacherIds, boolean isPass,Object user)
			throws CheckedServiceException, IOException {
		Long sendId = 0L;
		String sendName = "系统";
		short senderType = new Short("0");
		if(user instanceof PmphUser){
			sendId = ((PmphUser) user).getId();
			sendName =  ((PmphUser) user).getRealname();
			senderType = new Short("1");
		}else if(user instanceof OrgUser){
			sendId = ((OrgUser) user).getId();
			sendName =  ((OrgUser) user).getRealname();
			senderType = new Short("3");
		}
		if (StringUtils.isEmpty(auditorOrgName)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK, CheckedExceptionResult.NULL_PARAM,
					"审核机构为空");
		}
		if (null == teacherIds || teacherIds.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK, CheckedExceptionResult.NULL_PARAM,
					"认证的教师为空");
		}
		// 存入消息主体
		String msgContent = "抱歉，您提交的教师认证资料已被[<font color='red'>" + auditorOrgName + "</font>]管理员退回，请您核对后重新提交";// 退回
		if (isPass) {// 通过
			msgContent = "恭喜！您提交的教师认证资料已通过[<font color='red'>" + auditorOrgName + "</font>]管理员审核";
		}
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 组装消息和消息对象
		List<UserMessage> userMessageList = new ArrayList<UserMessage>(teacherIds.size());
		List<String> userIds = new ArrayList<String>(teacherIds.size());
		// 发送消息
		for (Long id : teacherIds) {
			UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), sendId, senderType, id,
					new Short("2"), null);
			userMessageList.add(userMessage);
			userIds.add("2_" + id);
		}

		userMessageService.addUserMessageBatch(userMessageList);
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, sendId, sendName,
				senderType, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 学校管理员认证 向机构用户发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午3:01:42
	 * @param orguserIds
	 *            机构用户ids
	 * @param isPass
	 *            true 通过/false 退回
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenManagerCertificationAudit(List<Long> orguserIds, boolean isPass,String backReason,PmphUser pmphUser)
			throws CheckedServiceException, IOException {
		if (null == orguserIds || orguserIds.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.SCHOOL_ADMIN_CHECK,
					CheckedExceptionResult.NULL_PARAM, "认证的管理员为空");
		}
		// 存入消息主体
		String msgContent = "抱歉，您提交的管理员认证资料已被退回，退回原因："+backReason+"，请您修改后重新提交";// 退回
		if (isPass) {// 通过
			msgContent = "恭喜！您提交的管理员认证资料已通过审核";
		}
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 组装消息和消息对象
		List<UserMessage> userMessageList = new ArrayList<UserMessage>(orguserIds.size());
		List<String> userIds = new ArrayList<String>(orguserIds.size());
		// 发送消息
		for (Long id : orguserIds) {
			UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("1"), pmphUser.getId(), new Short("1"), id,
					new Short("3"), null);
			userMessageList.add(userMessage);
			userIds.add("3_" + id);
		}

		userMessageService.addUserMessageBatch(userMessageList);
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
				Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 作家教材申报表 给学校管理员发送或者人卫教材相关人员发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午4:31:19
	 * @param declarationId
	 *            申报id
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenSubmitDeclarationForm(Long declarationId) throws CheckedServiceException, IOException {
		// 获取申报表
		Declaration declaration = declarationService.getDeclarationById(declarationId);
		if (null == declaration) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"申报表不存在");
		}
		if (null == declaration.getOrgId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"认证的管理员为空");
		}
		Material material = materialService.getMaterialById(declaration.getMaterialId());
		if (declaration.getOrgId() == 0) {// 提交的人卫社
			// 项目编辑 策划编辑 主任 id
			String msgContent = "[<font color='red'>" + declaration.getRealname() + "</font>]提交了《<font color='red'>"
					+ material.getMaterialName() + "</font>》申报表，请及时进行资料审核";
			// 存入消息主体
			Message message = new Message(msgContent);
			message = messageService.add(message);
			String msg_id = message.getId();
			// 主任id
			Long directorId = material.getDirector();
			// 项目编辑列表
			List<MaterialProjectEditorVO> materialProjectEditorList = materialProjectEditorService
					.listMaterialProjectEditors(material.getId());
			// 批量保存的消息集合
			List<UserMessage> userMessageList = new ArrayList<UserMessage>(materialProjectEditorList.size() + 1);
			List<String> userIds = new ArrayList<String>(materialProjectEditorList.size() + 1);
			userMessageList.add(new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"), directorId,
					new Short("1"), null));
			userIds.add("1_" + directorId);
			for (MaterialProjectEditorVO materialProjectEditor : materialProjectEditorList) {
				UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"),
						materialProjectEditor.getEditorId(), new Short("1"), null);
				userMessageList.add(userMessage);
				userIds.add("1_" + materialProjectEditor.getEditorId());
			}
			// 策划编辑
			List<DecPosition> decPositionList = decPositionService.listDecPositions(declarationId);// 我申报的书籍
			for (DecPosition decPosition : decPositionList) {
				Textbook textbook = textbookService.getTextbookById(decPosition.getTextbookId());
				if (null != textbook && null != textbook.getPlanningEditor()) {
					UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"),
							textbook.getPlanningEditor(), new Short("1"), null);
					userMessageList.add(userMessage);
					userIds.add("1_" + textbook.getPlanningEditor());
				}
			}
			// 发送消息
			userMessageService.addUserMessageBatch(userMessageList);
			// websocket推送页面消息
			WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, 0L, "系统",
					Const.SENDER_TYPE_0, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
					DateUtil.getCurrentTime());
			myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		} else {// 提交的机构
			String msgContent = "贵校老师[<font color='red'>" + declaration.getRealname() + "</font>]提交了《<font color='red'>"
					+ material.getMaterialName() + "</font>》申报表，请及时进行资料审核、打印并快递申报纸质表";
			// 存入消息主体
			Message message = new Message(msgContent);
			message = messageService.add(message);
			String msg_id = message.getId();
			// 获取机构用户
			List<Long> orgIds = new ArrayList<Long>(1);
			orgIds.add(declaration.getOrgId());
			List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(orgIds);
			List<UserMessage> userMessageList = new ArrayList<UserMessage>(orgUserList.size());
			List<String> userIds = new ArrayList<String>(orgUserList.size());
			for (OrgUser orgUser : orgUserList) {
				UserMessage userMessage = new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"),
						orgUser.getId(), new Short("3"), null);
				userMessageList.add(userMessage);
				userIds.add("3_" + orgUser.getId());
			}
			// 发送消息
			userMessageService.addUserMessageBatch(userMessageList);
			// websocket推送页面消息
			WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, 0L, "系统",
					Const.SENDER_TYPE_0, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
					DateUtil.getCurrentTime());
			myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		}
	}

	/**
	 * 教材申报表审核 向作家用户发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午4:42:14
	 * @param declarationId
	 *            申报id
	 * @param isPass
	 *            true 通过/false 退回
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenDeclarationFormAudit(Long declarationId, boolean isPass, String returnCause,PmphUser pmphUser)
			throws CheckedServiceException, IOException {
		// 获取申报表
		Declaration declaration = declarationService.getDeclarationById(declarationId);
		if (null == declaration) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"申报表不存在");
		}
		if (null == declaration.getOrgId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"认证的管理员为空");
		}
		// 获取教材
		Material material = materialService.getMaterialById(declaration.getMaterialId());
		String msgContent = "";
		if (declaration.getOrgId() == 0) {// 提交的人卫社
			msgContent = "抱歉，您提交的《<font color='red'>" + material.getMaterialName()
					+ "</font>》申报表被[<font color='red'>出版社</font>]退回，退回原因：" + returnCause 
					+ "，请您核对后重新提交";
			if (isPass) {// 通过
				msgContent = "恭喜！您提交的《<font color='red'>" + material.getMaterialName()
						+ "</font>》申报表已通过[<font color='red'>出版社</font>]审核";
			}
		} else {// 提交的机构
			msgContent = "抱歉，您提交的《<font color='red'>" + material.getMaterialName()
					+ "</font>》申报表被[<font color='red'>学校管理员</font>]退回，退回原因：" + returnCause 
					+ "，请您核对后重新提交";
			if (isPass) {// 通过
				msgContent = "恭喜！您提交的《<font color='red'>" + material.getMaterialName()
						+ "</font>》申报表已通过[<font color='red'>学校管理员</font>]审核";
			}
		}
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 发送消息
		userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
				declaration.getUserId(), new Short("2"), null));
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
				Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		List<String> userIds = new ArrayList<String>(1);
		userIds.add("2_" + declaration.getUserId());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 临床申报表审核 向用户发送消息
	 *
	 * @createDate 2017年11月17日 下午4:42:14
	 * @param expertationId
	 *            申报id
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenExpertationFormAudit(Long expertationId, PmphUser pmphUser)
			throws CheckedServiceException, IOException {
		// 获取申报表
		ExpertationVO expertation = expertationService.getExpertationById(expertationId);
		if (null == expertation) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
					"申报表不存在");
		}
		if (null == expertation.getOrg_id()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
					"认证的管理员为空");
		}

		String returnCause = expertation.getReturn_cause();

		String productTypeName = ProductTypeEnum.getName(expertation.getExpert_type());

		// 获取教材
		ProductVO product = productService.getProductByType(expertation.getExpert_type(),null);

		String msgContent = "";
		Long receiverId = null;
		Short receiverType = null;

		if(expertation.getFinalResult() && expertation.getPmphAudit() == 1){ //通过最终审核

			/*msgContent = "恭喜！您提交的《<font color='red'>" + product.getProduct_name()
					+ "</font>》"+productTypeName+"专家申报表已通过<font color='red'>出版社</font>审核";*/
			msgContent = "恭喜您被选为中国临床决策辅助系统--人卫"+productTypeName+"的专家工作委员会成员！";
			receiverId = expertation.getUser_id();
			receiverType = 2;
			sendMessage(receiverId,receiverType, msgContent,pmphUser);

			if(expertation.getOrg_id() >0 ){
				/*msgContent = "恭喜！您校"+expertation.getRealname()+"提交的《<font color='red'>" + product.getProduct_name()
						+ "</font>》"+productTypeName+"专家申报表已通过<font color='red'>出版社</font>审核";*/
				msgContent = "恭喜！您单位的<font color='red'>"+expertation.getRealname()+"</font>被选为中国临床决策辅助系统--人卫"+productTypeName+"的专家工作委员会成员！";
				OrgUser orgUser = orgUserService.getOrgUserByOrgId(expertation.getOrg_id());
				receiverId = orgUser.getId();
				receiverType = 3;
				sendMessage(receiverId,receiverType, msgContent,pmphUser);
			}

		}else if(5 == expertation.getOnline_progress()){ //出版社退回个人
			/*msgContent = "抱歉，您提交的《<font color='red'>" + product.getProduct_name()
					+ "</font>》"+productTypeName+"专家申报表被<font color='red'>出版社</font>退回，退回原因：" + returnCause
					+ "，请您核对后重新提交";*/
			msgContent = "抱歉，您提交的<font color='red'>" +productTypeName+"</font>专家申报表被<font color='red'>出版社</font>退回，退回原因：" + returnCause
					+ "，请您核对后重新提交";
			receiverId = expertation.getUser_id();
			receiverType = 2;
			sendMessage(receiverId,receiverType, msgContent,pmphUser);
		}else if(4 == expertation.getOnline_progress()){ //出版社退回申报单位
			/*msgContent = "抱歉，您校<font color='red'>"+expertation.getRealname()+"</font>老师提交的《<font color='red'>" + product.getProduct_name()
					+ "</font>》"+productTypeName+"专家申报表被<font color='red'>出版社</font>退回，退回原因：" + returnCause
					+ "，请及时处理";*/
			msgContent = "抱歉，您单位的<font color='red'>"+expertation.getRealname()+"</font>提交的<font color='red'>" + productTypeName
					+ "</font>专家申报表被<font color='red'>出版社</font>退回，退回原因：" + returnCause;


			OrgUser orgUser = orgUserService.getOrgUserByOrgId(expertation.getOrg_id());
			receiverId = orgUser.getId();
			receiverType = 3;
			sendMessage(receiverId,receiverType, msgContent,pmphUser);
		}

	}

	/**
	 * 后台用户向前台发送消息 先插入mongdb，再插入usermessage表,最后推送websocket
	 * @param receiverId
	 * @param receiverType
	 * @param msgContent
	 * @param pmphUser
	 * @return
	 */
	private void sendMessage(Long receiverId,short receiverType,String msgContent,PmphUser pmphUser) throws IOException {
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		List<String> userIds = new ArrayList<String>(1);
		userIds.add(receiverType +"_" + receiverId);

		// 发送消息
		userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
				receiverId, receiverType, null));

		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
				Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	};

	/**
	 * 人卫社审核教材申报表 向机构用户发送信息
	 * 
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年1月22日 上午10:12:34
	 * @param declarationId
	 * @param isPass
	 * @param returnCause 退回原因
	 * @param onlineProgress 4 == 退回给学校，5 == 退回给个人
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenDeclarationFormAuditToOrgUser(Long declarationId, boolean isPass, String returnCause, Integer onlineProgress,PmphUser pmphUser)
			throws CheckedServiceException, IOException {
		// 获取申报表
		Declaration declaration = declarationService.getDeclarationById(declarationId);
		if (null == declaration) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"申报表不存在");
		}
		if (null == declaration.getOrgId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"认证的管理员为空");
		}
		// 获取教材
		Material material = materialService.getMaterialById(declaration.getMaterialId());
		String msgContent = null;
		if (isPass) { // 通过
			msgContent = "恭喜！您校[" + declaration.getRealname() + "]提交的《<font color='red'>" + material.getMaterialName()
					+ "</font>》申报表已通过[<font color='red'>出版社</font>]审核";
		} else { // 退回
			// 出版社退回给学校==4
			if (4 == onlineProgress.intValue()) {
				msgContent = "抱歉！您校[" + declaration.getRealname() + "]提交的《<font color='red'>" + material.getMaterialName()
						+ "</font>》申报表被[<font color='red'>出版社</font>]退回，退回原因：" + returnCause 
						+ "，请核对后重试";
			} else if (5 == onlineProgress.intValue()) { // 出版社退回给个人==5
				msgContent = "抱歉！您提交的《<font color='red'>" + material.getMaterialName()
						+ "</font>》申报表被[<font color='red'>出版社</font>]退回，退回原因：" + returnCause 
						+ "，请您核对后重新提交";
			}
		}
		WebScocketMessage webScocketMessage = null;
		List<String> userIds = null;
		if (4 == onlineProgress.intValue()) {
			// 获取机构用户
			List<Long> orgIds = new ArrayList<Long>(1);
			if (CollectionUtil.isEmpty(orgIds) || orgIds.size() == 0) {
				orgIds.add(declaration.getOrgId());
			}
			List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(orgIds);
			// 存入消息主体
			Message message = new Message(msgContent);
			message = messageService.add(message);
			String msg_id = message.getId();
			// 消息集合
			List<UserMessage> userMessageList = new ArrayList<UserMessage>();
			userIds = new ArrayList<String>();
			for (OrgUser orgUser : orgUserList) {
				userMessageList.add(new UserMessage(msg_id, // 消息内容id
						messageTitle, // 消息标题
						new Short("0"), // 消息类型
						pmphUser.getId(), // 发送者id 0- 系统
						new Short("1"), // 发送者类型 0- 系统
						orgUser.getId(), // 接收者id
						new Short("3"), // 接收者类型 （3- 机构用户 ）
						null // 教材id
				));
				userIds.add("3_" + orgUser.getId().toString());
			}
	
			// 发送消息
			// 批量插入消息
			userMessageService.addUserMessageBatch(userMessageList);
			// websocket推送页面消息
			webScocketMessage = new WebScocketMessage(msg_id, // 消息id
					Const.MSG_TYPE_0, // 消息类型 0=系统消息/1=站内群发/2=站内私信(作家和机构用户不能群发)/3 小组互动
					pmphUser.getId(), // 发送者id 0=系统/其他=用户id
					pmphUser.getRealname(), // 发送者姓名
					Const.SENDER_TYPE_1, // 发送者类型 0=系统/1=社内用户/2=作家用户/3=机构用户
					Const.SEND_MSG_TYPE_0, // 发送类型 0 新增 1 撤回 2 删除
					"", // 头像
					messageTitle, // 消息标题
					msgContent, // 消息内容
					DateUtil.getCurrentTime() // 发送时间
			);
		} else if (5 == onlineProgress.intValue()) {
			// 存入消息主体
			Message message = new Message(msgContent);
			message = messageService.add(message);
			String msg_id = message.getId();
			// 发送消息
			userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
					declaration.getUserId(), new Short("2"), null));
			// websocket推送页面消息
			webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
					Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
					DateUtil.getCurrentTime());
			userIds = new ArrayList<String>(1);
			userIds.add("2_" + declaration.getUserId());
		}
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}

	/**
	 * 出版社收到纸质表 审核
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午5:08:49
	 * @param declarationId
	 * @param isPass
	 *            true收到资质表 / false退回资质表
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenReceiptAudit(Long declarationId, boolean isPass,PmphUser pmphUser) throws CheckedServiceException, IOException {
		Declaration declaration = declarationService.getDeclarationById(declarationId);
		if (null == declaration) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"申报表不存在");
		}
		if (null == declaration.getOrgId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"管理员为空");
		}
		Material material = materialService.getMaterialById(declaration.getMaterialId());
		String orgMsgContent = "抱歉，贵校老师[<font color='red'>" + declaration.getRealname() + "</font>]在《<font color='red'>"
				+ material.getMaterialName() + "</font>》提交的申报纸质表被退回";
		String writerMsgContent = "抱歉，您在《<font color='red'>" + material.getMaterialName()
				+ "</font>》提交的申报纸质表被退回，请您核对后重新提交";
		if (isPass) {// 收到
			orgMsgContent = "您好，人民卫生出版社已收到贵校老师[<font color='red'>" + declaration.getRealname()
					+ "</font>]提交的《<font color='red'>" + material.getMaterialName() + "</font>》申报纸质表";
			writerMsgContent = "您好，人民卫生出版社已收到您提交的《<font color='red'>" + material.getMaterialName()
					+ "</font>》申报纸质表，感谢您的参与，请耐心等待遴选结果";
		}else{//取消收到
			orgMsgContent = "抱歉，贵校老师提交的[<font color='red'>" + declaration.getRealname() + "</font>]在《<font color='red'>"
					+ material.getMaterialName() + "</font>》申报纸质表被退回";
			writerMsgContent ="抱歉，您在《<font color='red'>" + material.getMaterialName()
			+ "</font>》提交的申报纸质表被退回，请您核对后重新提交";
		}
		// 存入消息主体
		Message orgUserMessage = new Message(orgMsgContent);
		orgUserMessage = messageService.add(orgUserMessage);
		String orgMsg_id = orgUserMessage.getId();
		List<Long> orgIds = new ArrayList<Long>(1);
		orgIds.add(declaration.getOrgId());
		List<OrgUser> OrgUserList = orgUserService.getOrgUserListByOrgIds(orgIds);
		List<UserMessage> userMessageList = new ArrayList<UserMessage>(OrgUserList.size());
		List<String> userIds = new ArrayList<String>(1);
		for (OrgUser orgUser : OrgUserList) {
			userMessageList.add(new UserMessage(orgMsg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
					orgUser.getId(), new Short("3"), null));
			userIds.add("3_" + orgUser.getId());
		}
		// 有学校管理员发信息
		if (null != OrgUserList && OrgUserList.size() > 0) {
			// 发送消息给管理员
			userMessageService.addUserMessageBatch(userMessageList);
			// websocket推送页面消息
			WebScocketMessage webScocketMessage = new WebScocketMessage(orgMsg_id, Const.MSG_TYPE_0,  pmphUser.getId(),  pmphUser.getRealname(),
					Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle,
					orgMsgContent, DateUtil.getCurrentTime());
			myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		}
		// ------------------------给申报者发送消息--------------------
		// 存入消息主体
		Message writerUserMessage = new Message(writerMsgContent);
		writerUserMessage = messageService.add(writerUserMessage);
		String writerMsg_id = writerUserMessage.getId();
		// 发送消息给申报者
		userMessageService.addUserMessage(new UserMessage(writerMsg_id, messageTitle, new Short("0"), pmphUser.getId(),
				new Short("1"), declaration.getUserId(), new Short("2"), null));
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(writerMsg_id, Const.MSG_TYPE_0, pmphUser.getId(),  pmphUser.getRealname(),
				Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle,
				writerMsgContent, DateUtil.getCurrentTime());
		userIds = new ArrayList<String>(1);
		userIds.add("2_" + declaration.getUserId());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);

	}

	/**
	 * 随笔文章审核 向作家用户发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午5:23:16
	 * @param cmsContentId
	 * @param isPass
	 *            true通过/false 不通过
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenInformalEssayAudit(Long cmsContentId, boolean isPass)
			throws CheckedServiceException, IOException {
		CmsContent cmsContent = cmsContentService.getCmsContentById(cmsContentId);
		if (null == cmsContent) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "文章不存在");
		}
		String msgContent = "抱歉，您的文章《<font color='red'>" + cmsContent.getTitle() + "</font>》没有通过审核，请您修改后提交 ";
		if (isPass) {
			msgContent = "恭喜！您的文章《<font color='red'>" + cmsContent.getTitle() + "</font>》已通过审核 ";
		}
		// 存入消息主体
		Message message = new Message(msgContent);
		message = messageService.add(message);
		String msg_id = message.getId();
		// 发送消息给申报者
		userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), 0L, new Short("0"),
				cmsContent.getAuthorId(), new Short("2"), null));
		// websocket推送页面消息
		WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, 0L, "系统",
				Const.SENDER_TYPE_0, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msgContent,
				DateUtil.getCurrentTime());
		List<String> userIds = new ArrayList<String>(1);
		userIds.add("2_" + cmsContent.getAuthorId());
		myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}
	
	/**
	 * 某一本书的最终结果公布 或者 整套教材全部公布时 向当选者和学校管理员发送消息
	 * 
	 * @author Mryang
	 * @createDate 2017年11月19日 上午11:24:31
	 * @param textBookId
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenPubfinalResult(Long textBookId, List<DecPositionPublished> decPositionPublishedLst,PmphUser pmphUser)
			throws CheckedServiceException, IOException {
		Textbook textbook = textbookService.getTextbookById(textBookId);
		Material material = materialService.getMaterialById(textbook.getMaterialId());
		// 给主编、副主编、编委、数字编委发送

		HashMap<String, Object> paraMap = new HashMap<>();
		paraMap.put("material_id",textbook.getMaterialId());
		String materialCreateDate = declarationDao.findMaterialCreateDate(paraMap);
		Date date1 = DateUtil.fomatDate(materialCreateDate);
		Date date = DateUtil.fomatDate("2019-2-12 12:00");
		String msg = "";
		for (DecPositionPublished decPosition : decPositionPublishedLst) {
			if(date1.getTime()>date.getTime()) {

				if (decPosition.getChosenPosition() == 1) {
					if (null != decPosition.getRank() && decPosition.getRank() == 1) {
						if (decPosition.getChosenPosition() == 1) {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的第一主编";
						} else {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的第一主编、数字编委";
						}
					} else {
						if (decPosition.getChosenPosition() == 1) {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的主编";
						} else {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的主编、数字编委";
						}
					}

				}
				if (decPosition.getChosenPosition() == 2) {
					if (decPosition.getChosenPosition() == 2) {
						msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的副主编";
					}
				}

				if (decPosition.getChosenPosition() == 3) {
					if (decPosition.getChosenPosition() == 3) {
						msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的编委";
					}
				}

			}else{


			if (null != decPosition.getChosenPosition()) {
				if (decPosition.getChosenPosition() == 8) {
					msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
							+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
							+ textbook.getTextbookName() + "</font>]的数字编委";
				}
				if (decPosition.getChosenPosition() == 4 || decPosition.getChosenPosition() == 12) {
					if (null != decPosition.getRank() && decPosition.getRank() == 1) {
						if (decPosition.getChosenPosition() == 4) {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的第一主编";
						} else {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的第一主编、数字编委";
						}
					} else {
						if (decPosition.getChosenPosition() == 4) {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的主编";
						} else {
							msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
									+ textbook.getTextbookName() + "</font>]的主编、数字编委";
						}
					}
				}

				if (decPosition.getChosenPosition() == 2 || decPosition.getChosenPosition() == 10) {
					if (decPosition.getChosenPosition() == 2) {
						msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的副主编";
					} else {
						msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的副主编、数字编委";
					}
				}

				if (decPosition.getChosenPosition() == 1 || 9 == decPosition.getChosenPosition()) {
					if (decPosition.getChosenPosition() == 1) {
						msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的编委";
					} else {
						msg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的最终结果已公布，恭喜您当选[<font color='red'>"
								+ textbook.getTextbookName() + "</font>]的编委、数字编委";
					}
				}
			}
				// 获取申报表
				Declaration declaration = declarationService.getDeclarationById(decPosition.getDeclarationId());
				// 存入消息主体
				Message message = new Message(msg);
				message = messageService.add(message);
				String msg_id = message.getId();
				// 发送消息给申报者
				userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(),
						new Short("1"), declaration.getUserId(), new Short("2"), null));
				// websocket推送页面消息
				WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0,  pmphUser.getId(),  pmphUser.getRealname(),
						Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msg,
						DateUtil.getCurrentTime());
				List<String> userIds = new ArrayList<String>(1);
				userIds.add("2_" + declaration.getUserId());
				myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			}
		}
		// 给学校管理员发送消息
		if (material.getIsAllTextbookPublished()) {// 所有都发布了
			String orgMsg = "《<font color='red'>" + material.getMaterialName()
					+ "</font>》的编写团队遴选已结束，贵校共sum位老师当选，名单如下：";
			// 根据教材Id查询对应的书籍集合
			List<Textbook> textbooks = textbookService.getTextbookByMaterialId(material.getId());
			List<Long> bookIds = new ArrayList<Long>();
			for (Textbook book : textbooks) {
				bookIds.add(book.getId());
			}
			// 根据书籍获取当选了该书籍的人员所属机构
			List<Org> orgs = orgService.listBeElectedOrgByBookIds(bookIds);
			for (Org org : orgs) {
				// 根据orgid和bookid获取该机构某些已公布的书的申报职位
				List<DecPosition> decPositions = decPositionService.listDecPositionsByTextbookIdAndOrgid(bookIds,
						org.getId());
				if (null != decPositions && decPositions.size() > 0) {
					String msgContent = orgMsg;
					int sum = 0;
					for (int i = 0; i < decPositions.size(); i++) {
						DecPosition decPosition = decPositions.get(i);
						Declaration declaration = declarationService.getDeclarationById(decPosition.getDeclarationId());
						msgContent += "</br>" + "[<font color='red'>" + declaration.getRealname() + "</font>]";
						msgContent += " - "
								+ textbookService.getTextbookById(decPosition.getTextbookId()).getTextbookName()
								+ " - ";
						if (null != decPosition.getChosenPosition()) {
							if (decPosition.getChosenPosition() == 8) {
								msgContent += "数字编委";
							}
							if (decPosition.getChosenPosition() == 4 || decPosition.getChosenPosition() == 12) {
								if (decPosition.getRank() == 1) {
									if (decPosition.getChosenPosition() == 4) {
										msgContent += "第一主编";
									} else {
										msgContent += "第一主编、数字编委";
									}
								} else {
									if (decPosition.getChosenPosition() == 4) {
										msgContent += "主编";
									} else {
										msgContent += "主编、数字编委";
									}
								}

							}
							if (decPosition.getChosenPosition() == 2 || decPosition.getChosenPosition() == 10) {
								if (decPosition.getChosenPosition() == 2) {
									msgContent += "副主编";
								} else {
									msgContent += "副主编、数字编委";
								}
							}
							if (decPosition.getChosenPosition() == 1 || decPosition.getChosenPosition() == 9) {

								if (decPosition.getChosenPosition() == 1) {
									msgContent += "编委";
								} else {
									msgContent += "编委、数字编委";
								}
							}
						}
						sum++;
					}
					Pattern r = Pattern.compile("sum");
					Matcher m = r.matcher(msgContent);
					msgContent = m.replaceAll(sum+"");
					// 存入消息主体
					Message message = new Message(msgContent);
					message = messageService.add(message);
					String msg_id = message.getId();
					// 获取机构管理员
					OrgUser orgUser = orgUserService.getOrgUserByOrgId(org.getId());
					// 发送消息给申报者
					userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(),
							new Short("1"), orgUser.getId(), new Short("3"), null));
					// websocket推送页面消息
					WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
							Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle,
							msgContent, DateUtil.getCurrentTime());
					List<String> userIds = new ArrayList<String>(1);
					userIds.add("3_" + orgUser.getId());
					myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);

				}
			}

			// 《全国高等学校五年制临床医学专业第九轮规划教材》的编写团队遴选已结束，贵校共[5]位老师当选，名单如下：
			// [丁志国] - 局部解剖学 - 第一主编
			// [王海滨] - 医学影像学 - 第二主编
			// [雷国华] - 医学计算机应用 - 副主编
			// [孙风梅] - 医学文献检索与论文写作 - 编委
			// [宋守君] - 医患沟通 - 编委
		} else {
			String orgMsg = "《<font color='red'>" + material.getMaterialName() + "</font>》[<font color='red'>"
					+ textbook.getTextbookName() + "</font>]的最终结果已公布，贵校老师";
			// 《全国高等学校五年制临床医学专业第九轮规划教材》[传染病学]的最终结果已公布，贵校老师[丁志国]当选第一主编,[丁志国]当选第一主编,[丁志国]当选第一主编
			// 先要将学校分队
			List<Long> bookIds = new ArrayList<Long>();
			bookIds.add(textBookId);
			// 根据教材Id查询对应的书籍集合
			List<Org> orgs = orgService.listBeElectedOrgByBookIds(bookIds);
			for (Org org : orgs) {
				// 根据orgid和bookid获取该机构某些已公布的书的申报职位
				List<DecPosition> decPositions = decPositionService.listDecPositionsByTextbookIdAndOrgid(bookIds,
						org.getId());
				if (null != decPositions && decPositions.size() > 0) {
					// 拼装消息
					String msgContent = orgMsg;
					for (int i = 0; i < decPositions.size(); i++) {
						DecPosition decPosition = decPositions.get(i);
						Declaration declaration = declarationService.getDeclarationById(decPosition.getDeclarationId());
						if (i > 0) {
							msgContent += ",";
						}
						msgContent += "[<font color='red'>" + declaration.getRealname() + "</font>]当选";
						if (null != decPosition.getChosenPosition()) {
							if (decPosition.getChosenPosition() == 8) {
								msgContent += "数字编委";
							}
							if (decPosition.getChosenPosition() == 4 || decPosition.getChosenPosition() == 12) {
								if (decPosition.getRank() == 1) {
									if (decPosition.getChosenPosition() == 4) {
										msgContent += "第一主编";
									} else {
										msgContent += "第一主编、数字编委";
									}
								} else {
									if (decPosition.getChosenPosition() == 4) {
										msgContent += "主编";
									} else {
										msgContent += "主编、数字编委";
									}
								}

							}
							if (decPosition.getChosenPosition() == 2 || decPosition.getChosenPosition() == 10) {
								if (decPosition.getChosenPosition() == 2) {
									msgContent += "副主编";
								} else {
									msgContent += "副主编、数字编委";
								}
							}
							if (decPosition.getChosenPosition() == 1 || decPosition.getChosenPosition() == 9) {

								if (decPosition.getChosenPosition() == 1) {
									msgContent += "编委";
								} else {
									msgContent += "编委、数字编委";
								}
							}
						}
					}
					// 存入消息主体
					Message message = new Message(msgContent);
					message = messageService.add(message);
					String msg_id = message.getId();
					// 获取机构管理员
					OrgUser orgUser = orgUserService.getOrgUserByOrgId(org.getId());
					// 发送消息给申报者
					userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(),
							new Short("1"), orgUser.getId(), new Short("3"), null));
					// websocket推送页面消息
					WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
							Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle,
							msgContent, DateUtil.getCurrentTime());
					List<String> userIds = new ArrayList<String>(1);
					userIds.add("3_" + orgUser.getId());
					myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
				}
			}
		}
	}
	
	/**
	 * 
	 * 主编排位数字转成中文
	 * 
	 * @author Mryang
	 * @createDate 2017年11月20日 下午3:23:59
	 * @param rank
	 *            (1,2,3,4,5,6,7,8,9,10) 数字之一
	 * @return
	 */
	private static String rank(int rank) {
		String res = "";
		switch (rank) {
		case 1:
			res = "一";
			break;
		case 2:
			res = "二";
			break;
		case 3:
			res = "三";
			break;
		case 4:
			res = "四";
			break;
		case 5:
			res = "五";
			break;
		case 6:
			res = "六";
			break;
		case 7:
			res = "七";
			break;
		case 8:
			res = "八";
			break;
		case 9:
			res = "九";
			break;
		case 10:
			res = "十";
			break;
		default:
			res = String.valueOf(rank);
			break;
		}
		return res;
	}

	/**
	 * 
	 * 主编排位中文转数字
	 * 
	 * @author Mryang
	 * @createDate 2017年11月20日 下午3:23:59
	 * @param rank
	 *            ("一","二","三","四","五","六","七","八","九","十") 之一
	 * @return
	 */
	public static int rank(String rank) {
		int res = 0;
		switch (rank) {
		case "一":
			res = 1;
			break;
		case "二":
			res = 2;
			break;
		case "三":
			res = 3;
			break;
		case "四":
			res = 4;
			break;
		case "五":
			res = 5;
			break;
		case "六":
			res = 6;
			break;
		case "七":
			res = 7;
			break;
		case "八":
			res = 8;
			break;
		case "九":
			res = 9;
			break;
		case "十":
			res = 10;
			break;
		default:
			break;
		}
		return res;
	}
	
	/**
	 * 给教材已结束并且未遴选上的作家推送消息
	 * 
	 * @param materialId
	 *  declaration
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	public void sendWhenPositionChooserLoss(Long materialId,List<Declaration> declarations,PmphUser pmphUser)
			throws CheckedServiceException, IOException{
		Material material = materialService.getMaterialById(materialId);
		if(ObjectUtil.isNull(material)){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
					"教材为空");
		}
		String msg=null;
		if(material.getIsAllTextbookPublished()&&declarations.size()>0){
			msg="《<font color='red'>" + material.getMaterialName()
			+ "</font>》教材遴选已结束，未选中，感谢您的支持与参与。";
		}
		//当消息不为空时才进行下面操作
		if(StringUtil.notEmpty(msg)){
			// 存入消息主体
			Message message = new Message(msg);
			message = messageService.add(message);
			String msg_id = message.getId();
			for (Declaration declaration : declarations) {
				// 发送消息给申报者
				userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle, new Short("0"), pmphUser.getId(), new Short("1"),
						declaration.getUserId(), new Short("2"), null));
				// websocket推送页面消息
				WebScocketMessage webScocketMessage = new WebScocketMessage(msg_id, Const.MSG_TYPE_0, pmphUser.getId(), pmphUser.getRealname(),
						Const.SENDER_TYPE_1, Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, messageTitle, msg,
						DateUtil.getCurrentTime());
				List<String> userIds = new ArrayList<String>(1);
				userIds.add("2_" + declaration.getUserId());
				myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			}
		}
	}
}
