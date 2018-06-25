package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Topic;
import com.bc.pmpheep.back.po.TopicLog;
import com.bc.pmpheep.back.vo.TopicDeclarationVO;
import com.bc.pmpheep.back.vo.TopicDirectorVO;
import com.bc.pmpheep.back.vo.TopicEditorVO;
import com.bc.pmpheep.back.vo.TopicOPtsManagerVO;
import com.bc.pmpheep.back.vo.TopicTextVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：选题申报业务层
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
public interface TopicService {
	/**
	 * 
	 * 
	 * 功能描述：初始化/查询可以操作的选题申报
	 *
	 * @param sessionId
	 * @param pageParameter
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	PageResult<TopicOPtsManagerVO> listOpts(String sessionId, PageParameter<TopicOPtsManagerVO> pageParameter)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：修改选题申报
	 *
	 * @param topic
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	String update(TopicLog topicLog, String sessionId, Topic topic,String openId,Long adminId) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：获取选题申报详情
	 *
	 * @param id
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	TopicTextVO topicTextVO(TopicLog topicLog, String sessionId, Long id) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：查看选题申报
	 *
	 * @param authProgress
	 *            审核进度
	 * @return
	 *
	 */
	PageResult<TopicDeclarationVO> listCheckTopic(List<Long> authProgress,
			PageParameter<TopicDeclarationVO> pageParameter ,String sessionId) throws CheckedServiceException;

	/**
	 * Description:获取主任可以查询到的选题申报信息
	 * 
	 * @author:lyc
	 * @date:2017年12月22日上午9:16:27
	 * @param
	 * @return PageResult<TopicDirectorVO>
	 */
	PageResult<TopicDirectorVO> listTopicDirectorVOs(String sessionId, PageParameter<TopicDirectorVO> pageParameter)
			throws CheckedServiceException;

	/**
	 * 
	 * Description:获取编辑可以查询到的选题申报信息
	 * 
	 * @author:lyc
	 * @date:2017年12月22日上午9:18:19
	 * @param
	 * @return PageResult<TopicEditorVO>
	 */
	PageResult<TopicEditorVO> listTopicEditorVOs(String sessionId, PageParameter<TopicEditorVO> pageParameter)
			throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：新增topic
	 *
	 * @param topic
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Topic add(Topic topic) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：定期获取中间表的申报进度
	 *
	 * @throws CheckedServiceException
	 *
	 */
	void updateByErp() throws CheckedServiceException;
	
	/**
	 * 查询当前用户相关的选题申报
	 * @param progress
	 * @param pageParameter3
	 * @return
	 */
	PageResult<TopicDeclarationVO> listMyTopic(List<Long> authProgress, PageParameter<TopicDeclarationVO> pageParameter3,Long editorId)
			throws CheckedServiceException;

	/**
	 *  由主任受理
	 * @param userId
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<TopicDirectorVO> listIsDirectorTopic(Long userId,PageParameter<TopicDirectorVO> pageParameter)throws CheckedServiceException;

	/**由运维人员受理
	 *
	 * @param userId
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<TopicOPtsManagerVO> listIsOptsTopic(Long userId,PageParameter<TopicOPtsManagerVO> pageParameter)throws CheckedServiceException;
	
	/**由编辑受理
	 * 
	 * @param userId
	 * @return
	 * @throws CheckedServiceException
	 */
	PageResult<TopicEditorVO> listIsEditor(Long userId,PageParameter<TopicEditorVO> pageParameter)throws CheckedServiceException;

	
	
}
