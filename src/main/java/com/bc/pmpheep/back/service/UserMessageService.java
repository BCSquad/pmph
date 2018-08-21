package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.MyMessageVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年9月27日 下午2:51:25
 * 
 **/
public interface UserMessageService {
    /**
     * 批量插入 UserMessage (UserMessage只插入了 UserMessage.msgId UserMessage.msgType UserMessage.title
     * UserMessage.senderId UserMessage.senderType UserMessage.receiverId UserMessage.receiverType
     * 字段
     * 
     * @author Mryang
     * @createDate 2017年11月17日 上午10:07:23
     * @param userMessageList
     * @throws CheckedServiceException
     */
    void addUserMessageBatch(List<UserMessage> userMessageList) throws CheckedServiceException;

    /**
     * 单条数据插入 UserMessage
     * 
     * @author Mryang
     * @createDate 2017年9月28日 下午3:35:46
     * @param userMessage
     * @throws CheckedServiceException
     * @return userMessage带主键
     */
    UserMessage addUserMessage(UserMessage userMessage) throws CheckedServiceException;

    /**
     * @param pageParameter 带有分页参数和查询条件参数
     * @return PageResult<MessageStateVO> 包含 List<MessageStateVO>以及分页数据
     * @throws CheckedServiceException
     */
    PageResult<MessageStateVO> listMessageState(PageParameter<MessageStateVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    /**
     * 
     * 
     * 功能描述：初始化/消息标题查询系统消息
     * 
     * @param pageParameter 传入的分页参数以及查询参数
     * @return 已经分页的消息结果集
     * @throws CheckedServiceException
     * 
     */
    PageResult<UserMessageVO> listMessage(PageParameter<UserMessageVO> pageParameter,
    String sessionId) throws CheckedServiceException;

    /**
     * 
     * <pre>
	 * 功能描述：系统消息——发送新消息——发送对象（学校管理员、所有人、指定用户、教材所有报名者）页面数据加载
	 * 使用示范：
	 *
	 * @param sendType //1 发送给学校管理员 //2 所有人 //3指定用户 //4发送给教材所有报名者
	 * @param pageNumber
     * @param pageSize
     * @param orgName 机构名称
     * @param materialId 教材ID
     * @param userNameOrUserCode 用户姓名/用户账号
     * @param materialName 教材名称
	 * @return
	 * @throws CheckedServiceException
	 * </pre>
     */
    Map<String, Object> listSendOject(Integer sendType, Integer pageNumber, Integer pageSize,
    String orgName, Long materialId, String userNameOrUserCode, String materialName)
    throws CheckedServiceException;

    /**
     * 向各个对象发送消息
     * 
     * @author Mryang
     * @createDate 2017年9月28日 下午4:29:27
     * @param message
     * @param sendType //1 发送给学校管理员 //2 所有人 //3指定用户 //4发送给教材所有报名者
     * @param orgIds
     * @param userIds
     * @param bookids
     * @param isSave true新增，false补发
     * @return
     */
    Integer addOrUpdateUserMessage(HttpServletRequest request, Message message, String title,
    Integer sendType, String orgIds, Long senderId, String userIds, String bookIds, boolean isSave,
    String[] files, String sessionId) throws CheckedServiceException, IOException;

    /**
     * 
     * <pre>
	 * 功能描述：单纯修改消息
	 * 使用示范：
	 *
	 * @param message 消息对象
	 * @param msgId Message主键ID
	 * @param msgTitle 消息标题
	 * @return 影响行数
	 * @throws CheckedServiceException IOException
	 * </pre>
     */
    Integer updateUserMessage(HttpServletRequest request, Message message, String msgId,
    String msgTitle, String[] files, String[] attachment) throws CheckedServiceException,
    IOException;

    /**
     * 
     * <pre>
	 * 功能描述：修改消息 ，按主键ID查询
	 * 使用示范：
	 *
	 * @param userMsgId　UserMessage主键ID
	 * @return
	 * </pre>
     */
    Map<String, Object> getUserMessageById(Long userMsgId);

    /**
     * 
     * <pre>
	 * 功能描述：逻辑删除（通过msgId 动态更新UserMessage_IsDeleted字段）
	 * 使用示范：
	 *
	 * @param msgIds  Message主键id
	 * @return   影响行数
	 * @throws CheckedServiceException
	 * </pre>
     */
    Integer updateUserMessageIsDeletedByMsgId(List<String> msgIds) throws CheckedServiceException;

    /**
     * 撤回消息
     * 
     * @author Mryang
     * @createDate 2017年9月29日 下午4:44:35
     * @param userMessage
     * @return 影响行数
     */
    Integer updateToWithdraw(UserMessage userMessage) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：取消撤回消息
     * 使用示范：
     *
     * @param msgId  Message主键id
     * @return  影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCancelToWithdraw(String msgId) throws CheckedServiceException;

    /**
     * 
     * <pre>
	 * 功能描述：通过消息id删除UserMessage
	 * 使用示范：
	 *
	 * @param msgIds  Message主键id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 * </pre>
     */
    Integer deleteMessageByMsgId(List<String> msgIds) throws CheckedServiceException;

    /**
     * 
     * <pre>
	 * 功能描述：消息附件上传
	 * 使用示范：
	 *
	 * @param file 消息附件
	 * @return 上传成功后消息附件路径
	 * @throws CheckedServiceException
	 * </pre>
     */
    String msgUploadFiles(HttpServletRequest request, MultipartFile file)
    throws CheckedServiceException, IOException;

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
    PageResult<MyMessageVO> listMyMessage(PageParameter<MyMessageVO> pageParameter)
    throws CheckedServiceException;

    /**
     * 
     * 
     * 功能描述：小图标获取一部分系统未读消息
     * 
     * @param pageParameter 分页参数,isRead 是否已读
     * @return
     * @throws CheckedServiceException
     * 
     */
    PageResult<MyMessageVO> listMyMessageOfIcon(PageParameter<MyMessageVO> pageParameter)
    throws CheckedServiceException;

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
    MyMessageVO updateMyMessageDetail(Long id) throws CheckedServiceException;

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
    Integer updateMyMessage(Long[] ids) throws CheckedServiceException;

    /**
     * 通过id 动态更新UserMessage
     */
    Integer updateUserMessage(UserMessage userMessage);

    /**
     * 向单个用户发送私信
     * 
     * @author tyc
     * @createDate 2017年11月22日 下午5:04:00
     * @param message
     * @param receiverId 接收者id
     * @param sessionId
     * @return
     * @throws CheckedServiceException
     * @throws IOException
     */
    Integer addOneUserMessage(Message message, Long receiverId, String sessionId)
    throws CheckedServiceException, IOException;

    /**
	 *
	 * <pre>
	 * 功能描述：系统消息——发送新消息——发送对象（学校管理员、所有人、指定用户、教材所有报名者）页面数据加载
	 * 使用示范：
	 *
	 * @param sendType //1 发送给学校管理员 //2 所有人 //3指定用户 //4发送给教材所有报名者
	 * @param pageNumber
	 * @param pageSize
	 * @param orgName 机构名称
	 * @param materialId 教材ID
	 * @param userNameOrUserCode 用户姓名/用户账号
	 * @param materialName 教材名称
	 * @return
	 * @throws CheckedServiceException
	 * </pre>
	 */
	Map<String, Object> listSendClinicalmessageOject(Integer sendType, Integer pageNumber, Integer pageSize,
									  String orgName, Long productId, String userNameOrUserCode, String productName)
			throws CheckedServiceException;

}
