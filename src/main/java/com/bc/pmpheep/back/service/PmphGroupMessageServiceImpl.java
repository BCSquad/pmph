package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupDao;
import com.bc.pmpheep.back.dao.PmphGroupMessageDao;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	 */
	@Override
	public Integer deletePmphGroupMessageById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMessageDao.deletePmphGroupMessageById(id);
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
	public String addGroupMessage(String msgConrent, Long groupId, String sessionId) throws CheckedServiceException {
		PmphUser pmphUser = ShiroSession.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		Long memberId = pmphUser.getId();
		PmphGroupMessage pmphGroupMessage = new PmphGroupMessage(groupId, memberId, msgConrent);
		pmphGroupMessageDao.addPmphGroupMessage(pmphGroupMessage);
		PmphGroup pmphGroup = new PmphGroup();// 将该条消息创建时间作为最后一条消息时间放入该小组中
		pmphGroup.setId(groupId);
		pmphGroup.setGmtLastMessage(pmphGroupMessage.getGmtCreate());
		pmphGroupDao.addPmphGroup(pmphGroup);
		
		return null;
	}
}
