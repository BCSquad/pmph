package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupDao;
import com.bc.pmpheep.back.dao.PmphGroupMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.back.vo.PmphGroupMessageVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.websocket.MyWebSocketHandler;
import com.bc.pmpheep.websocket.WebScocketMessage;

/**
 * PmphGroupMessageService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphGroupMessageServiceImpl extends BaseService implements PmphGroupMessageService {
	@Autowired
	private PmphGroupMessageDao pmphGroupMessageDao;
	@Autowired
	private PmphGroupDao pmphGroupDao;
	@Autowired
	private PmphGroupMemberService pmphGroupMemberService;
	@Autowired
	private MyWebSocketHandler handler;
	@Autowired
	private WriterUserService writerUserService;
	@Autowired
	private PmphUserService pmphUserService;

	/**
	 * 
	 * @param pmphGroupMessage
	 *            实体对象
	 * @return 带主键的 PmphGroupMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupMessage addPmphGroupMessage(PmphGroupMessage pmphGroupMessage) throws CheckedServiceException {
		if (null == pmphGroupMessage.getMsgContent()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"消息内容为空");
		}
		pmphGroupMessageDao.addPmphGroupMessage(pmphGroupMessage);
		return pmphGroupMessage;
	}

	/**
	 * 
	 * @param id
	 * @return PmphGroupMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupMessage getPmphGroupMessageById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMessageDao.getPmphGroupMessageById(id);
	}

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 * @throws IOException
	 */
	@Override
	public String deletePmphGroupMessageById(Long id, String sessionId) throws CheckedServiceException, IOException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		PmphGroupMessage pmphGroupMessage = pmphGroupMessageDao.getPmphGroupMessageById(id);
		Long groupId = pmphGroupMessage.getGroupId();
		int num = pmphGroupMessageDao.deletePmphGroupMessageById(id);
		if (num > 0) {// 删除成功 撤销消息
			WebScocketMessage webScocketMessage = new WebScocketMessage();
			webScocketMessage.setId(String.valueOf(id));
			webScocketMessage.setGroupId(groupId);
			webScocketMessage.setSendType(Const.SEND_MSG_TYPE_1);
			List<PmphGroupMemberVO> list = pmphGroupMemberService.listPmphGroupMember(groupId, sessionId);
			List<String> ids = new ArrayList<String>();
			for (PmphGroupMemberVO groupMemberVO : list) {
				String tempId = (groupMemberVO.getIsWriter() ? "2" : "1") + "_" + groupMemberVO.getUserId();
				ids.add(tempId);
			}
			handler.sendWebSocketMessageToUser(ids, webScocketMessage);
		}
		return "SUCCESS";
	}

	/**
	 * @param pmphGroupMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updatePmphGroupMessage(PmphGroupMessage pmphGroupMessage) throws CheckedServiceException {
		if (null == pmphGroupMessage.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMessageDao.updatePmphGroupMessage(pmphGroupMessage);
	}
	
	@Override
	public String addGroupMessage(String msgConrent, Long groupId, Long senderId, Short senderType)
			throws CheckedServiceException, IOException {
		if (null == senderId ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,"用户为空");
		}
		PmphGroupMemberVO pmphGroupMemberVO = null;
		PmphGroupMessage pmphGroupMessage;
		if (senderType == 0) {
			pmphGroupMessage = new PmphGroupMessage(groupId, 0L, msgConrent);
		} else {
			pmphGroupMemberVO = pmphGroupMemberService.getPmphGroupMemberByMemberId(groupId, senderId,senderType.intValue() == 2);// 获取用户
			pmphGroupMessage = new PmphGroupMessage(groupId, pmphGroupMemberVO.getId(), msgConrent);
		}
		pmphGroupMessageDao.addPmphGroupMessage(pmphGroupMessage);
		pmphGroupMessage = pmphGroupMessageDao.getPmphGroupMessageById(pmphGroupMessage.getId());
		PmphGroup pmphGroup = new PmphGroup();// 将该条消息创建时间作为最后一条消息时间放入该小组中
		pmphGroup.setId(groupId);
		pmphGroup.setGmtLastMessage(pmphGroupMessage.getGmtCreate());
		pmphGroupDao.updatePmphGroup(pmphGroup);
		// 进行推送消息步骤1.查询接收人的id 2.进行推送
		List<PmphGroupMemberVO> list = pmphGroupMemberService.listPmphGroupMember(groupId, null);
		List<String> ids = new ArrayList<String>();
		for (PmphGroupMemberVO groupMemberVO : list) {
			String tempId = (groupMemberVO.getIsWriter() ? "2" : "1") + "_" + groupMemberVO.getUserId();
			ids.add(tempId);
		}
		WebScocketMessage webScocketMessage = new WebScocketMessage(String.valueOf(pmphGroupMessage.getId()),
				Const.MSG_TYPE_3, senderType == 0?0:senderId, senderType == 0?"系统":pmphGroupMemberVO.getDisplayName(), senderType, Const.SEND_MSG_TYPE_0, null,
				null, msgConrent, pmphGroupMessage.getGmtCreate());
		webScocketMessage.setGroupId(groupId);
		webScocketMessage.setSenderIcon(senderType == 0 ?"":pmphGroupMemberVO.getAvatar());
		handler.sendWebSocketMessageToUser(ids, webScocketMessage);
		return "SUCCESS";
	}
	
	@Override
	public String addGroupMessage(String msgConrent, Long groupId, String sessionId, Short senderType)
			throws CheckedServiceException, IOException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		Long userId = pmphUser.getId();
		PmphGroupMemberVO pmphGroupMemberVO = pmphGroupMemberService.getPmphGroupMemberByMemberId(groupId, userId,
				false);// 获取后台用户
		PmphGroupMessage pmphGroupMessage;
		if (senderType == 0) {
			pmphGroupMessage = new PmphGroupMessage(groupId, 0L, msgConrent);
		} else {
			pmphGroupMessage = new PmphGroupMessage(groupId, pmphGroupMemberVO.getId(), msgConrent);
		}
		pmphGroupMessageDao.addPmphGroupMessage(pmphGroupMessage);
		pmphGroupMessage = pmphGroupMessageDao.getPmphGroupMessageById(pmphGroupMessage.getId());
		PmphGroup pmphGroup = new PmphGroup();// 将该条消息创建时间作为最后一条消息时间放入该小组中
		pmphGroup.setId(groupId);
		pmphGroup.setGmtLastMessage(pmphGroupMessage.getGmtCreate());
		pmphGroupDao.updatePmphGroup(pmphGroup);
		// 进行推送消息步骤1.查询接收人的id 2.进行推送
		List<PmphGroupMemberVO> list = pmphGroupMemberService.listPmphGroupMember(groupId, sessionId);
		List<String> ids = new ArrayList<String>();
		for (PmphGroupMemberVO groupMemberVO : list) {
			String tempId = (groupMemberVO.getIsWriter() ? "2" : "1") + "_" + groupMemberVO.getUserId();
			ids.add(tempId);
		}
		WebScocketMessage webScocketMessage = new WebScocketMessage(String.valueOf(pmphGroupMessage.getId()),
				Const.MSG_TYPE_3, userId, pmphGroupMemberVO.getDisplayName(), senderType, Const.SEND_MSG_TYPE_0, null,
				null, msgConrent, pmphGroupMessage.getGmtCreate());
		webScocketMessage.setGroupId(groupId);
		webScocketMessage.setSenderIcon(pmphGroupMemberVO.getAvatar());
		handler.sendWebSocketMessageToUser(ids, webScocketMessage);
		return "SUCCESS";
	}

	@Override
	public PageResult<PmphGroupMessageVO> listPmphGroupMessage(PageParameter<PmphGroupMessageVO> pageParameter)
			throws CheckedServiceException {
		if (null == pageParameter.getParameter().getGroupId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id为空");
		}
		if (null == pageParameter.getParameter().getGmtCreate()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"进入小组时间为空");
		}
		PageResult<PmphGroupMessageVO> pageResult = new PageResult<>();
		int total = pmphGroupMessageDao.getPmphGroupMessageTotal(pageParameter);
		if (total > 0) {
			PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
			List<PmphGroupMessageVO> list = pmphGroupMessageDao.listPmphGroupMessage(pageParameter);
			Collections.reverse(list);
			for (PmphGroupMessageVO pmphGroupMessageVO : list) {
				if (0 != pmphGroupMessageVO.getMemberId()) {
					if (null == pmphGroupMessageVO.getIsWriter()) {
						pmphGroupMessageVO.setAvatar(RouteUtil.DEFAULT_USER_AVATAR);
						pmphGroupMessageVO.setMemberName("该人员已经退出小组");
					} else {
						if (pmphGroupMessageVO.getIsWriter()) {
							pmphGroupMessageVO.setUserType(Const.SENDER_TYPE_2);
							pmphGroupMessageVO.setAvatar(RouteUtil
									.userAvatar(writerUserService.get(pmphGroupMessageVO.getUserId()).getAvatar()));
						} else {
							pmphGroupMessageVO.setAvatar(RouteUtil
									.userAvatar(pmphUserService.get(pmphGroupMessageVO.getUserId()).getAvatar()));
							pmphGroupMessageVO.setUserType(Const.SENDER_TYPE_1);
						}
					}
				} else {
					pmphGroupMessageVO.setUserId(0L);
					pmphGroupMessageVO.setUserType(Const.SENDER_TYPE_0);
				}
			}
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public Integer deletePmphGroupMessageByGroupId(Long groupId) throws CheckedServiceException {
		if (ObjectUtil.isNull(groupId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id为空");
		}
		return pmphGroupMessageDao.deletePmphGroupMessageByGroupId(groupId);
	}

}
