package com.bc.pmpheep.back.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.MyMessageVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;

/**
 * @author MrYang
 * @CreateDate 2017年9月27日 下午2:50:12
 * 
 **/
@Controller
@RequestMapping(value = "/messages")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserMessageController {

    @Autowired
    private UserMessageService  userMessageService;
    @Autowired
    private TextbookService     textbookService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "系统消息";

    /**
     * 
     * 
     * 功能描述：初始化/消息标题查询系统消息
     * 
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @param title 标题
     * @return
     * 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
    @RequestMapping(value = "/list/message", method = RequestMethod.GET)
    public ResponseBean message(@RequestParam("pageNumber") Integer pageNumber,
    @RequestParam("pageSize") Integer pageSize, @RequestParam("title") String title,
    @RequestParam("sessionId") String sessionId) {
        PageParameter<UserMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        UserMessageVO userMessageVO = new UserMessageVO();
        if (StringUtil.notEmpty(title)) {
            userMessageVO.setTitle(title.replaceAll(" ", ""));// 去除空格
        }
        pageParameter.setParameter(userMessageVO);
        return new ResponseBean(userMessageService.listMessage(pageParameter, sessionId));
    }

    /**
     * 分页查询条件查询MessageState 列表
     * 
     * @author Mryang
     * @createDate 2017年9月26日 上午9:46:19
     * @return 分页数据集
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息状态列表")
    @RequestMapping(value = "/message/{msgId}/state", method = RequestMethod.GET)
    public ResponseBean state(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @PathVariable("msgId") String msgId, @RequestParam(name = "pageSize") Integer pageSize,
    MessageStateVO messageStateVO, @RequestParam("sessionId") String sessionId) {
        PageParameter<MessageStateVO> pageParameter =
        new PageParameter<MessageStateVO>(pageNumber, pageSize, messageStateVO);
        return new ResponseBean(userMessageService.listMessageState(pageParameter, sessionId));
    }

    /**
     * 
     * <pre>
	 * 功能描述：初始化数据(选择向各个对象发送消息)
	 * 使用示范：
	 *
	 * @param sendType //1 发送给学校管理员 //2 所有人 //3指定用户 //4发送给教材所有报名者
	 * @param pageNumber
	 * @param pageSize
	 * @param orgName 机构名称
	 * @param userNameOrUserCode 用户姓名/用户账号
	 * @param materialName 教材名称
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "加载选择对象业务数据")
    @RequestMapping(value = "/message/sendObject", method = RequestMethod.GET)
    public ResponseBean sendObject(@RequestParam("sendType") Integer sendType,
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, @RequestParam("orgName") String orgName,
    @RequestParam("userNameOrUserCode") String userNameOrUserCode,
    @RequestParam("materialName") String materialName) {
        return new ResponseBean(userMessageService.listSendOject(sendType,
                                                                 pageNumber,
                                                                 pageSize,
                                                                 orgName,
                                                                 userNameOrUserCode,
                                                                 materialName));
    }

    /**
     * 
     * <pre>
	 * 功能描述：根据教材ID查询书籍列表
	 * 使用示范：
	 *
	 * @param materialId 教材ID
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询书籍列表")
    @RequestMapping(value = "/message/send_object/{materialId}/textBook", method = RequestMethod.GET)
    public ResponseBean textBook(@PathVariable("materialId") Long materialId) {
        return new ResponseBean(textbookService.getTextBookByMaterialId(materialId));
    }

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
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "选择向各个对象发送消息")
    @RequestMapping(value = "/newMessage", method = RequestMethod.POST)
    public ResponseBean newMessage(Message message, @RequestParam("title") String title,
    @RequestParam("sendType") Integer sendType, @RequestParam("orgIds") String orgIds,
    @RequestParam("senderId") Long senderId, @RequestParam("userIds") String userIds,
    @RequestParam("bookIds") String bookids, @RequestParam("file") String[] files,
    @RequestParam("sessionId") String sessionId) {
        try {
            return new ResponseBean(userMessageService.addOrUpdateUserMessage(message,
                                                                              title,
                                                                              sendType,
                                                                              orgIds,
                                                                              senderId,
                                                                              userIds,
                                                                              bookids,
                                                                              true,
                                                                              files,
                                                                              sessionId));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 向各个对象补发消息
     * 
     * @author Mryang
     * @createDate 2017年9月28日 下午4:29:27
     * @param message
     * @param sendType //1 发送给学校管理员 //2 所有人 //3指定用户 //4发送给教材所有报名者
     * @param orgIds
     * @param userIds
     * @param bookids
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "选择向各个对象补发消息")
    @RequestMapping(value = "/message/again", method = RequestMethod.POST)
    public ResponseBean again(Message message, @RequestParam("title") String title,
    @RequestParam("sendType") Integer sendType, @RequestParam("orgIds") String orgIds,
    @RequestParam("senderId") Long senderId, @RequestParam("userIds") String userIds,
    @RequestParam("bookIds") String bookIds, @RequestParam("file") String[] files,
    @RequestParam("sessionId") String sessionId) {
        try {
            return new ResponseBean(userMessageService.addOrUpdateUserMessage(message,
                                                                              title,
                                                                              sendType,
                                                                              orgIds,
                                                                              senderId,
                                                                              userIds,
                                                                              bookIds,
                                                                              false,
                                                                              files,
                                                                              sessionId));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 
     * <pre>
	 * 功能描述：查看单条消息内容
	 * 使用示范：
	 *
	 * @param userMsgId UserMessage 主键ID
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看单条消息内容")
    @RequestMapping(value = "/message/content", method = RequestMethod.GET)
    public ResponseBean content(@RequestParam("userMsgId") Long userMsgId) {
        return new ResponseBean(userMessageService.getUserMessageById(userMsgId));
    }

    /**
     * 
     * <pre>
	 * 功能描述：单纯修改消息
	 * 使用示范：
	 *
	 * @param message 消息对象
	 * @param msgId Message主键Id
	 * @param msgTitle 消息标题
	 * @return 影响行数
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改消息内容")
    @RequestMapping(value = "/updateMessage", method = RequestMethod.PUT)
    public ResponseBean updateMessage(Message message, @RequestParam("msgId") String msgId,
    @RequestParam("msgTitle") String msgTitle, @RequestParam("file") String[] files,
    @RequestParam("attachment") String[] attachment) {
        try {
            return new ResponseBean(userMessageService.updateUserMessage(message,
                                                                         msgId,
                                                                         msgTitle,
                                                                         files,
                                                                         attachment));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 撤回消息
     * 
     * @author Mryang
     * @createDate 2017年9月29日 下午5:18:20
     * @param userMessage
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "撤回消息")
    @RequestMapping(value = "/withdraw", method = RequestMethod.PUT)
    public ResponseBean withdraw(UserMessage userMessage) {
        return new ResponseBean(
                                userMessageService.updateToWithdraw(new UserMessage(
                                                                                    userMessage.getMsgId(),
                                                                                    true)));
    }

    /**
     * 
     * <pre>
	 * 功能描述：新增消息附件上传
	 * 使用示范：
	 *
	 * @param request
	 * @param files
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "上传附件")
    @RequestMapping(value = "/message/file", method = RequestMethod.POST)
    public ResponseBean file(@RequestParam("file") MultipartFile file) {
        return new ResponseBean(userMessageService.msgUploadFiles(file));
    }

    /**
     * 
     * <pre>
	 * 功能描述：逻辑删除（通过消息id批量更新UserMessage_is_deleted字段）
	 * 使用示范：
	 *
	 * @param ids 数组
	 * @return
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除消息")
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.PUT)
    public ResponseBean deleteMessage(@RequestParam("msgIds") List<String> msgIds) {
        return new ResponseBean(userMessageService.updateUserMessageIsDeletedByMsgId(msgIds));

    }

    /**
     * 
     * 
     * 功能描述：获取我的消息列表
     * 
     * @param pageSize 当页条数
     * @param pageNumber 当前页码
     * @param title 标题
     * @param isRead 是否已读
     * @param userId 用户id
     * @param userType 用户类型
     * @return
     * 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询我的消息列表")
    @RequestMapping(value = "/list/mymessage", method = RequestMethod.GET)
    public ResponseBean listMyMessage(Integer pageSize, Integer pageNumber, String title,
    Boolean isRead, Long userId, Integer userType) {
        PageParameter<MyMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        MyMessageVO myMessageVO = new MyMessageVO();
        myMessageVO.setUserId(userId);
        myMessageVO.setUserType(userType);
        myMessageVO.setTitle(title);
        myMessageVO.setIsRead(isRead);
        pageParameter.setParameter(myMessageVO);
        return new ResponseBean(userMessageService.listMyMessage(pageParameter));
    }

    /**
     * 
     * 
     * 功能描述：获取前几条未读消息
     * 
     * @param pageSize 前几条消息
     * @param pageNumber 当前页码
     * @param userId 用户id
     * @param userType 用户类型
     * @return
     * 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取前几条未读消息")
    @RequestMapping(value = "/icon/mymessage", method = RequestMethod.GET)
    public ResponseBean listMyMessageOfIcon(Integer pageSize, Integer pageNumber, Long userId,
    Integer userType) {
        PageParameter<MyMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        MyMessageVO myMessageVO = new MyMessageVO();
        myMessageVO.setUserId(userId);
        myMessageVO.setUserType(userType);
        myMessageVO.setIsRead(false);
        pageParameter.setParameter(myMessageVO);
        return new ResponseBean(userMessageService.listMyMessageOfIcon(pageParameter));
    }

    /**
     * 
     * 
     * 功能描述： 获取消息详情
     * 
     * @param id 消息id
     * @return
     * 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取我收到消息详情")
    @RequestMapping(value = "/detail/mymessage", method = RequestMethod.GET)
    public ResponseBean updateMyMessageDetail(Long id) {
        return new ResponseBean(userMessageService.updateMyMessageDetail(id));
    }

    /**
     * 
     * 
     * 功能描述：逻辑删除消息
     * 
     * @param ids 需要删除的消息id
     * @return
     * 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除我收到消息")
    @RequestMapping(value = "/delete/mymessage", method = RequestMethod.PUT)
    public ResponseBean updateMyMessage(Long[] ids) {
        return new ResponseBean(userMessageService.updateMyMessage(ids));
    }
}
