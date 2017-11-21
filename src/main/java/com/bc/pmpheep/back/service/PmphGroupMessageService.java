package com.bc.pmpheep.back.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.vo.PmphGroupMessageVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupMessageService 接口
 * 
 * @author Mryang
 */
public interface PmphGroupMessageService {

	/**
	 * 
	 * @param pmphGroupMessage
	 *            实体对象
	 * @return 带主键的 PmphGroupMessage
	 * @throws CheckedServiceException
	 */
	PmphGroupMessage addPmphGroupMessage(PmphGroupMessage pmphGroupMessage) throws CheckedServiceException;

	/**
	 * 
	 * @param PmphGroupMessage
	 *            必须包含主键ID
	 * @return PmphGroupMessage
	 * @throws CheckedServiceException
	 */
	PmphGroupMessage getPmphGroupMessageById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	String deletePmphGroupMessageById(Long id, String sessionId) throws CheckedServiceException, IOException;

	/**
	 * @param pmphGroupMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupMessage(PmphGroupMessage pmphGroupMessage) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述： 我的小组发送消息
	 *
	 * @param msgConrent
	 *            消息内容
	 * @param groupId
	 *            小组id
	 * @param sessionId
	 *            当前用户sessionid
	 * @param senderType
	 *            发送类型
	 * @return 是否成功
	 * @throws CheckedServiceException
	 * @throws IOException
	 *
	 */
	String addGroupMessage(String msgConrent, Long groupId, String sessionId, Short senderType)
			throws CheckedServiceException, IOException;

	/**
	 * 
	 * 
	 * 功能描述：进入小组是加载历史消息
	 *
	 * @param pageParameter
	 *            分页参数 以及 小组id和进入小组时间
	 * @return 分页消息结果集
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<PmphGroupMessageVO> listPmphGroupMessage(PageParameter<PmphGroupMessageVO> pageParameter)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据小组id删除小组消息
	 *
	 * @param groupId
	 *            小组id
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Integer deletePmphGroupMessageByGroupId(Long groupId) throws CheckedServiceException;

}
