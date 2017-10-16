package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.websocket.MyWebSocketHandler;
import com.bc.pmpheep.websocket.WebScocketMessage;

/**
 * @author MrYang
 * @CreateDate 2017年9月27日 下午2:53:37
 * 
 **/
@Service
@SuppressWarnings("all")
public class UserMessageServiceImpl extends BaseService implements UserMessageService {

	@Autowired
	private UserMessageDao userMessageDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private WriterUserService writerUserService;

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private MyWebSocketHandler handler;

	@Autowired
	private DecPositionService decPositionService;

	@Override
	public PageResult<MessageStateVO> listMessageState(PageParameter<MessageStateVO> pageParameter)
			throws CheckedServiceException {
		PmphUser pmphUser = ShiroSession.getPmphUser();
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (null == pageParameter.getParameter().getMsgId()
				|| "".equals(pageParameter.getParameter().getMsgId().trim())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息为空");
		}
		pageParameter.getParameter().setSenderId(pmphUser.getId());
		PageResult<MessageStateVO> pageResult = new PageResult<MessageStateVO>();
		// 将页面大小和页面页码拷贝
		Tools.CopyPageParameter(pageParameter, pageResult);
		// 包含数据总条数的数据集
		List<MessageStateVO> messageStateList = userMessageDao.listMessageState(pageParameter);
		if (null != messageStateList && messageStateList.size() > 0) {
			Integer count = messageStateList.get(0).getCount();
			pageResult.setTotal(count);
			pageResult.setRows(messageStateList);
		}
		return pageResult;
	}

	@Override
	public Integer addOrUpdateUserMessage(Message message, Integer sendType, String orgIds, String userIds,
			String bookIds, boolean isSave) throws CheckedServiceException, IOException {
		if (null == sendType || "".equals(sendType)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"参数错误!");
		}
		// 如果 补发不进行消息插入
		if (isSave) {
			// MongoDB 消息插入
			message = messageService.add(message);
		}
		if (null == message.getId() || "".equals(message.getId().trim())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.OBJECT_NOT_FOUND,
					"储存失败!");
		}
		// 发送者id
		PmphUser pmphUser = ShiroSession.getPmphUser();
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.OBJECT_NOT_FOUND,
					"操作人为空!");
		}
		Long senderId = pmphUser.getId();
		// 装储存数据
		List<UserMessage> userMessageList = new ArrayList<UserMessage>();
		// 1 发送给学校管理员 //2 所有人
		if (sendType == 1 || sendType == 2) {
			if (null == orgIds || "".equals(userIds.trim())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
						"参数错误!");
			}
			String[] orgIds1 = orgIds.split(",");
			List<Long> orgIds2 = new ArrayList<Long>(orgIds1.length);
			for (String id : orgIds1) {
				if (null != id && Tools.isNumeric(id)) {
					orgIds2.add(Long.parseLong(id));
				}
			}
			List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(orgIds2);
			// 机构用户
			for (OrgUser orgUser : orgUserList) {
				userMessageList.add(
						new UserMessage(message.getId(), (short) 1, senderId, (short) 1, orgUser.getId(), (short) 3));
			}
			// 作家用户
			if (sendType == 2) {
				List<WriterUser> writerUserList = writerUserService.getWriterUserListByOrgIds(orgIds2);
				for (WriterUser writerUser : writerUserList) {
					userMessageList.add(new UserMessage(message.getId(), (short) 1, senderId, (short) 1,
							writerUser.getId(), (short) 2));
				}
			}
		}
		// 3 指定用户
		if (sendType == 3) {
			if (null == userIds || "".equals(userIds)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
						"没有选中发送人!");
			}
			String[] ids = userIds.split(",");
			for (String id : ids) {
				if (null != id && !"".equals(id)) {
					String userType = id.split("_")[0];
					String userId = id.split("_")[1];
					if (null != userId && Tools.isNumeric(userId)) {
						userMessageList.add(new UserMessage(message.getId(), (short) 1, senderId, (short) 1,
								Long.parseLong(userId), new Short(userType)));
					}
				}
			}
		}
		// 4 发送给教材所有报名者
		if (sendType == 4) {
			if (null == bookIds || "".equals(bookIds)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
						"书籍为空!");
			}
			String[] ids = bookIds.split(",");
			for (String id : ids) {
				List<DecPosition> list = decPositionService.listDecPositionsByTextbookId(Long.valueOf(id));
				for (DecPosition position : list) {
					
				}
				// 获取到书籍id然后根据书籍id在dec_position表中获取到申报表id根据申报表id在declaration表中获取作家id放入userMessage的接收人中
			}

		}
		// 如果是补发,进入下面操作 进行已发人员筛出
		if (!isSave) {
			List<UserMessage> temp = new ArrayList<UserMessage>();
			// 已经发送的人员列表
			List<UserMessage> sendedList = userMessageDao.getMessageByMsgId(message.getId());
			for (UserMessage userMessage : userMessageList) {
				boolean flag = false;// 没有发送
				for (UserMessage uMessage : sendedList) {
					if (userMessage.getReceiverId() == uMessage.getReceiverId()
							&& userMessage.getReceiverType() == uMessage.getReceiverType()) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					temp.add(userMessage);
				}
			}
			userMessageList = temp;
		}
		// 插入消息发送对象数据
		if (null != userMessageList && userMessageList.size() > 0) {
			userMessageDao.addUserMessageBatch(userMessageList);
		}
		// websocket发送的id集合
		List<String> websocketUserIds = new ArrayList<String>();
		for (UserMessage userMessage : userMessageList) {
			websocketUserIds.add(userMessage.getReceiverType() + "_" + userMessage.getReceiverId());
		}
		// webscokt发送消息
		if (null != websocketUserIds && websocketUserIds.size() > 0) {
			WebScocketMessage webScocketMessage = new WebScocketMessage(message.getId(), (short) 1, senderId,
					pmphUser.getRealname(), (short) 1, (short) 0, message.getContent(), message.getContent(),
					Tools.getCurrentTime());
			handler.sendWebSocketMessageToUser(websocketUserIds, webScocketMessage);
		}
		return userMessageList.size();
	}

	@Override
	public Integer updateUserMessage(Message message) throws CheckedServiceException {
		if (null == message) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息更新对象为空");
		}
		if (null == message.getId() || message.getId().isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息更新对象id为空");
		}
		if (null == message.getContent() || message.getContent().isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息更新对象内容为空");
		}
		messageService.update(message);
		return 1;
	}

	@Override
	public Integer updateToWithdraw(UserMessage userMessage) throws CheckedServiceException {
		if (Tools.isEmpty(userMessage.getMsgId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"消息更新对象id为空");
		}
		// 已经发送的人员列表
		List<UserMessage> sendedList = userMessageDao.getMessageByMsgId(userMessage.getMsgId());
		for (UserMessage userMessage2 : sendedList) {
			if (null != userMessage2.getIsRead() && userMessage2.getIsRead()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
						"消息已有人读取，无法撤销！");
			}
		}
		return userMessageDao.updateUserMessageByMsgId(userMessage.getMsgId());
	}

	@Override
	public Integer deleteMessageByMsgId(String msgId) throws CheckedServiceException {
		if (null == msgId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"id为空");
		}
		Integer sum = userMessageDao.deleteMessageByMsgId(msgId);
		// messageService. 删除
		return sum;
	}

	@Override
	public PageResult<UserMessageVO> listMessage(PageParameter<UserMessageVO> pageParameter)
			throws CheckedServiceException {
		PmphUser pmphUser = ShiroSession.getPmphUser();
		if (null == pmphUser) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (null != pageParameter.getParameter().getTitle() && pageParameter.getParameter().getTitle().equals("")) {
			String title = pageParameter.getParameter().getTitle();
			title = title.trim();
			title = title.replace(" ", "%");
			title = "%" + title + "%";
			pageParameter.getParameter().setTitle(title);
		}
		pageParameter.getParameter().setSenderId(pmphUser.getId());
		PageResult<UserMessageVO> pageResult = new PageResult<>();
		Tools.CopyPageParameter(pageParameter, pageResult);
		int total = userMessageDao.getMessageTotal(pageParameter);
		if (total > 0) {
			List<UserMessageVO> list = userMessageDao.listMessage(pageParameter);
			for (UserMessageVO userMessageVO : list) {
				Message message = messageService.get(userMessageVO.getMsgId());
				userMessageVO.setContent(message.getContent());
			}
			pageResult.setRows(list);
		}
		pageResult.setPageTotal(total);

		return pageResult;
	}

}
