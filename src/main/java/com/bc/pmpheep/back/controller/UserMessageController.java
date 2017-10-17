package com.bc.pmpheep.back.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.vo.MessageStateVO;
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
    private UserMessageService userMessageService;

    /**
     * 分页查询条件查询MessageState 列表
     * 
     * @author Mryang
     * @createDate 2017年9月26日 上午9:46:19
     * @return 分页数据集
     */
    @RequestMapping(value = "/message/{msgId}/state", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean listMessageState(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, MessageStateVO messageStateVO,
    @RequestParam("sessionId") String sessionId) {
        PageParameter<MessageStateVO> pageParameter =
        new PageParameter<MessageStateVO>(pageNumber, pageSize, messageStateVO);
        return new ResponseBean(userMessageService.listMessageState(pageParameter, sessionId));
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
    @RequestMapping(value = "/message/new", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addUserMessage(Message message, @RequestParam("sendType") Integer sendType,
    @RequestParam("orgIds") String orgIds, @RequestParam("userIds") String userIds,
    @RequestParam("bookids") String bookids, @RequestParam("sessionId") String sessionId) {
        try {
            return new ResponseBean(userMessageService.addOrUpdateUserMessage(message,
                                                                              sendType,
                                                                              orgIds,
                                                                              userIds,
                                                                              bookids,
                                                                              true,
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
    @RequestMapping(value = "/message/again", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addUserMessageAgain(Message message,
    @RequestParam("sendType") Integer sendType, @RequestParam("orgIds") String orgIds,
    @RequestParam("userIds") String userIds, @RequestParam("bookIds") String bookIds,
    @RequestParam("sessionId") String sessionId) {
        try {
            return new ResponseBean(userMessageService.addOrUpdateUserMessage(message,
                                                                              sendType,
                                                                              orgIds,
                                                                              userIds,
                                                                              bookIds,
                                                                              false,
                                                                              sessionId));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 单纯修改消息
     * 
     * @author Mryang
     * @createDate 2017年9月29日 下午4:30:14
     * @param message
     * @return 影响行数
     */
    @RequestMapping(value = "/update/message", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseBean updateUserMessage(Message message) {
        return new ResponseBean(userMessageService.updateUserMessage(message));
    }

    /**
     * 撤回消息
     * 
     * @author Mryang
     * @createDate 2017年9月29日 下午5:18:20
     * @param userMessage
     * @return
     */
    @RequestMapping(value = "/withdraw/message", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseBean withdrawUserMessage(UserMessage userMessage) {
        return new ResponseBean(
                                userMessageService.updateToWithdraw(new UserMessage(
                                                                                    userMessage.getMsgId(),
                                                                                    true)));
    }

    /**
     * 
     * <pre>
     * 功能描述：逻辑删除（通过消息id批量更新UserMessage_is_deleted字段）
     * 使用示范：
     *
     * @param msgId 数组
     * @return
     * </pre>
     */
    @RequestMapping(value = "/delete/message/{msgId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseBean deleteUserMessage(@PathVariable("msgId") String msgId) {
        return new ResponseBean(userMessageService.updateUserMessageIsDeletedByMsgId(msgId));

    }

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
    @RequestMapping(value = "/list/message", method = RequestMethod.GET)
    public ResponseBean listMessage(@RequestParam("pageNumber") Integer pageNumber,
    @RequestParam("pageSize") Integer pageSize, @RequestParam("title") String title,
    @RequestParam("sessionId") String sessionId) {
        PageParameter<UserMessageVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        UserMessageVO userMessageVO = new UserMessageVO();
        userMessageVO.setTitle(title);
        pageParameter.setParameter(userMessageVO);
        return new ResponseBean(userMessageService.listMessage(pageParameter, sessionId));
    }
}
