package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.MyMessageVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * UserMessage 实体类数据访问层接口
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月27日 下午2:33:53
 * 
 */
@Repository
public interface UserMessageDao {

    /**
     * 
     * 获取MessageState列表（同时查询分页数据和总条数）
     * 
     * @author Mryang
     * @createDate 2017年9月27日 上午10:36:10
     * @param pageParameter
     * @return List<MessageStateVO>
     */
    List<MessageStateVO> listMessageState(PageParameter<MessageStateVO> pageParameter);

    /**
     * 
     * <pre>
	 * 功能描述：根据主键ID查询 
	 * 使用示范：
	 *
	 * &#64;param id 
	 * &#64;return
	 * </pre>
     */
    UserMessage getUserMessageById(@Param("id") Long id);

    /**
     * 
     * 
     * 功能描述：根据条件查询所有的条数
     * 
     * @param title 标题
     * @return 总数
     * 
     */
    Integer getMessageTotal(PageParameter<UserMessageVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：初始化/模糊查询系统消息 分页
     * 
     * @param pageParameter 分页条件以及查询条件
     * @return 一页的结果集
     * 
     */
    List<UserMessageVO> listMessage(PageParameter<UserMessageVO> pageParameter);
    
    /**
     * 单条数据插入 UserMessage
     * 
     * @author Mryang
     * @createDate 2017年9月28日 下午3:35:46
     * @param userMessage
     * @return 影响行数
     */
    Integer addUserMessage(UserMessage userMessage);

    /**
     * 批量插入 UserMessage
     * 
     * @author Mryang
     * @createDate 2017年9月28日 下午3:35:46
     * @param userMessageList
     */
    void addUserMessageBatch(List<UserMessage> userMessageList);

    /**
     * 根据消息msgId 获取 UserMessage集
     * 
     * @author Mryang
     * @createDate 2017年9月29日 下午3:17:56
     * @param msgId
     * @return
     */
    List<UserMessage> getMessageByMsgId(String msgId);

    /**
     * 
     * <pre>
	 * 功能描述：通过msgId撤销消息
	 * 使用示范：
	 *
	 * &#64;param msgId message主键ID
	 * &#64;return 影响行数
	 * </pre>
     */
    Integer updateUserMessageWithdrawByMsgId(String msgId);

    /**
     * 
     * <pre>
	 * 功能描述：逻辑删除（通过msgId 动态更新UserMessage_IsDeleted字段）
	 * 使用示范：
	 *
	 * &#64;param msgIds 数组
	 * &#64;return
	 * </pre>
     */
    Integer updateUserMessageIsDeletedByMsgId(List<String> msgIds);

    /**
     * 通过id 动态更新UserMessage
     */
    Integer updateUserMessageById(UserMessage userMessage);

    /**
     * 
     * 
     * 功能描述：获取我的消息列表
     * 
     * @param pageParameter 分页参数,title 消息标题,isRead 是否已读,userId 用户id,userType 用户类型
     * @return
     * @throws CheckedServiceException
     * 
     */
    List<MyMessageVO> listMyMessage(PageParameter<MyMessageVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：获取总条数
     * 
     * @param pageParameter
     * @return
     * 
     */
    Integer listMyMessageTotal(PageParameter<MyMessageVO> pageParameter);

    /**
     * 
     * 
     * 功能描述：获取消息详情
     * 
     * @param id 消息id
     * @return
     * @throws CheckedServiceException
     * 
     */
    MyMessageVO getMyMessageDetail(Long id);

    /**
     * 
     * 
     * 功能描述：逻辑删除我的消息
     * 
     * @param ids 需要删除的消息id
     * @return
     * @throws CheckedServiceException
     * 
     */
    Integer updateMyMessage(Long[] ids);

    /**
     * 
     * <pre>
	 * 功能描述：通过MsgId 更新消息
	 * 使用示范：
	 *
	 * &#64;param msgId message 主键Id
	 * &#64;param title 消息标题
	 * &#64;return
	 * </pre>
     */
    Integer updateUserMessageTitleByMsgId(UserMessage userMessage);

    /**
     * 通过消息id删除UserMessage
     */
    Integer deleteMessageByMsgId(List<String> msgIds);

}
