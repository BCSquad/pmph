package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.MessageAttachment;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CastUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.FileService;
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
    private UserMessageDao           userMessageDao;

    @Autowired
    private MessageService           messageService;

    @Autowired
    private WriterUserService        writerUserService;

    @Autowired
    private OrgUserService           orgUserService;

    @Autowired
    private PmphUserService          pmphUserService;

    @Autowired
    private OrgService               orgService;

    @Autowired
    private MaterialService          materialService;

    @Autowired
    private FileService              fileService;

    @Autowired
    private MessageAttachmentService messageAttachmentService;

    @Autowired
    private MyWebSocketHandler       handler;

    @Autowired
    private DecPositionService       decPositionService;

    @Override
    public PageResult<MessageStateVO> listMessageState(PageParameter<MessageStateVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.isEmpty(pageParameter.getParameter().getMsgId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息为空");
        }
        pageParameter.getParameter().setSenderId(pmphUser.getId());
        PageResult<MessageStateVO> pageResult = new PageResult<MessageStateVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<MessageStateVO> messageStateList = userMessageDao.listMessageState(pageParameter);
        if (!messageStateList.isEmpty() && messageStateList.size() > 0) {
            Integer count = messageStateList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(messageStateList);
        }
        return pageResult;
    }

    @Override
    public Map<String, Object> listSendOject(Integer sendType, Integer pageNumber,
    Integer pageSize, String orgName, String userNameOrUserCode, String materialName)
    throws CheckedServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(sendType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送对象未选择，请选择发送对象!");
        }
        // 1 发送给学校管理员 //2 所有人
        if (Const.SEND_OBJECT_1 == sendType || Const.SEND_OBJECT_2 == sendType) {
            resultMap.put("orgVo", orgService.listSendToSchoolAdminOrAllUser(orgName));
        }
        // 指定用户
        if (Const.SEND_OBJECT_3 == sendType) {
            // PMPH_User
            PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
            pmphUserManagerVO.setName(userNameOrUserCode);
            PageParameter<PmphUserManagerVO> pmphPageParameter =
            new PageParameter<PmphUserManagerVO>(pageNumber, pageSize, pmphUserManagerVO);
            // Writer_User
            WriterUserManagerVO writerUserManagerVO = new WriterUserManagerVO();
            writerUserManagerVO.setName(userNameOrUserCode);
            writerUserManagerVO.setOrgName(orgName);
            // writerUserManagerVO.setRank(0);
            PageParameter<WriterUserManagerVO> writerPageParameter =
            new PageParameter<WriterUserManagerVO>(pageNumber, pageSize, writerUserManagerVO);
            // Org_User
            OrgUserManagerVO orgUserManagerVO = new OrgUserManagerVO();
            orgUserManagerVO.setUsername(userNameOrUserCode);
            orgUserManagerVO.setOrgName(orgName);
            PageParameter<OrgUserManagerVO> orgPageParameter =
            new PageParameter<OrgUserManagerVO>(pageNumber, pageSize, orgUserManagerVO);
            resultMap.put("pmphUser", pmphUserService.getListPmphUser(pmphPageParameter));
            resultMap.put("writerUser", writerUserService.getListWriterUser(writerPageParameter));
            resultMap.put("orgUser", orgUserService.getListOrgUser(orgPageParameter));
        }
        // 教材所有报名者
        if (Const.SEND_OBJECT_4 == sendType) {
            resultMap.put("Material", materialService.getListMaterial(materialName));
        }
        return resultMap;
    }

    @Override
    public Integer addOrUpdateUserMessage(Message message, String title, Integer sendType,
    String orgIds, String userIds, String bookIds, boolean isSave, String[] files, String sessionId)
    throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(sendType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送对象未选择，请选择!");
        }
        // 如果 补发不进行消息插入
        if (isSave) {
            // MongoDB 消息插入
            message = messageService.add(message);
        }
        if (ObjectUtil.isNull(message.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
        }

        // 发送者id
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "操作人为空!");
        }
        Long senderId = pmphUser.getId();
        // 装储存数据
        List<UserMessage> userMessageList = new ArrayList<UserMessage>();
        // 1 发送给学校管理员 //2 所有人
        if (Const.SEND_OBJECT_1 == sendType || Const.SEND_OBJECT_2 == sendType) {
            if (StringUtil.isEmpty(orgIds)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "参数错误!");
            }
            String[] orgIds1 = orgIds.split(",");
            List<Long> orgIds2 = new ArrayList<Long>(orgIds1.length);
            for (String id : orgIds1) {
                if (StringUtil.notEmpty(id)) {
                    orgIds2.add(Long.parseLong(id));
                }
            }
            List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(orgIds2);
            // 机构用户
            for (OrgUser orgUser : orgUserList) {
                userMessageList.add(new UserMessage(message.getId(), title, Const.MSG_TYPE_1,
                                                    senderId, Const.SENDER_TYPE_1, orgUser.getId(),
                                                    Const.RECEIVER_TYPE_3));
            }
            // 作家用户
            if (Const.SEND_OBJECT_2 == sendType) {
                List<WriterUser> writerUserList =
                writerUserService.getWriterUserListByOrgIds(orgIds2);
                for (WriterUser writerUser : writerUserList) {
                    userMessageList.add(new UserMessage(message.getId(), title, Const.MSG_TYPE_1,
                                                        senderId, Const.SENDER_TYPE_1,
                                                        writerUser.getId(), Const.RECEIVER_TYPE_2));
                }
            }
        }
        // 3 指定用户
        if (Const.SEND_OBJECT_3 == sendType) {
            if (StringUtil.isEmpty(userIds)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "没有选中发送人!");
            }
            String[] ids = userIds.split(",");
            for (String id : ids) {
                if (StringUtil.notEmpty(id)) {
                    String userType = id.split("_")[0];
                    String userId = id.split("_")[1];
                    if (StringUtil.notEmpty(userId) && StringUtil.isNumeric(userId)) {
                        userMessageList.add(new UserMessage(message.getId(), title,
                                                            Const.MSG_TYPE_1, senderId,
                                                            Const.SENDER_TYPE_1,
                                                            Long.parseLong(userId),
                                                            new Short(userType)));
                    }
                }
            }
        }
        // 4 发送给教材所有报名者
        if (Const.SEND_OBJECT_4 == sendType) {
            if (StringUtil.isEmpty(bookIds)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "书籍为空!");
            }
            String[] ids = bookIds.split(",");
            for (String id : ids) {
                List<Long> userIdList = decPositionService.listDecPositionsByTextbookIds(ids);
                for (Long userId : userIdList) {
                    userMessageList.add(new UserMessage(message.getId(), title, Const.MSG_TYPE_1,
                                                        senderId, Const.SENDER_TYPE_1, userId,
                                                        Const.RECEIVER_TYPE_2));
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
        if (!userMessageList.isEmpty() && userMessageList.size() > 0) {
            userMessageDao.addUserMessageBatch(userMessageList);
        }
        // websocket发送的id集合
        List<String> websocketUserIds = new ArrayList<String>();
        for (UserMessage userMessage : userMessageList) {
            websocketUserIds.add(userMessage.getReceiverType() + "_" + userMessage.getReceiverId());
        }
        // webscokt发送消息
        if (!websocketUserIds.isEmpty() && websocketUserIds.size() > 0) {
            WebScocketMessage webScocketMessage =
            new WebScocketMessage(message.getId(), Const.MSG_TYPE_1, senderId,
                                  pmphUser.getRealname(), Const.SEND_MSG_TYPE_1,
                                  Const.SEND_MSG_TYPE_0, message.getContent(),
                                  message.getContent(), DateUtil.getCurrentTime());
            handler.sendWebSocketMessageToUser(websocketUserIds, webScocketMessage);
            // 添加附件到MongoDB表中
            if (ArrayUtil.isNotEmpty(files)) {
                for (int i = 0; i < files.length; i++) {
                    File file = FileUpload.getFileByFilePath(files[i]);
                    // 循环获取file数组中得文件
                    if (StringUtil.notEmpty(file.getName())) {
                        String gridFSFileId =
                        fileService.saveLocalFile(file,
                                                  FileType.MSG_FILE,
                                                  CastUtil.castLong(message.getId()));// 上传文件到MongoDB
                        if (StringUtil.isEmpty(gridFSFileId)) {
                            throw new CheckedServiceException(
                                                              CheckedExceptionBusiness.MESSAGE,
                                                              CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                              "文件上传失败!");
                        }
                        // 保存对应数据
                        MessageAttachment mAttachment =
                        messageAttachmentService.addMessageAttachment(new MessageAttachment(
                                                                                            message.getId(),
                                                                                            gridFSFileId,
                                                                                            file.getName()));
                        if (ObjectUtil.isNull(mAttachment.getId())) {
                            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                              CheckedExceptionResult.NULL_PARAM,
                                                              "MessageAttachment对象保存失败!");
                        }
                        messageAttachmentService.updateMessageAttachment(new MessageAttachment(
                                                                                               mAttachment.getId(),
                                                                                               "/file/download/"
                                                                                               + gridFSFileId));
                    }
                }
            }
        }
        for (int i = 0; i < files.length; i++) {
            FileUtil.delFile(files[i]);// 删除本地临时文件
        }
        return userMessageList.size();
    }

    @Override
    public Integer updateUserMessage(Message message, Long userMsgId, String msgTitle)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(message)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象为空");
        }
        if (ObjectUtil.isNull(message.getId()) || ObjectUtil.isNull(userMsgId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象id为空");
        }
        if (StringUtil.isEmpty(message.getContent()) || StringUtil.isEmpty(msgTitle)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象内容为空");
        }
        userMessageDao.updateUserMessageById(new UserMessage(userMsgId, msgTitle));
        messageService.update(message);
        return 1;
    }

    @Override
    public Integer updateToWithdraw(UserMessage userMessage) throws CheckedServiceException {
        if (StringUtil.isEmpty(userMessage.getMsgId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象id为空");
        }
        // 已经发送的人员列表
        List<UserMessage> sendedList = userMessageDao.getMessageByMsgId(userMessage.getMsgId());
        for (UserMessage userMessage2 : sendedList) {
            if (null != userMessage2.getIsRead() && userMessage2.getIsRead()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM,
                                                  "消息已有人读取，无法撤销！");
            }
        }
        return userMessageDao.updateUserMessageByMsgId(userMessage.getId());
    }

    @Override
    public Integer updateUserMessageIsDeletedByMsgId(String[] ids) throws CheckedServiceException {
        if (ArrayUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象id为空");
        }
        return userMessageDao.updateUserMessageIsDeletedByMsgId(ids);
    }

    @Override
    public Integer deleteMessageByMsgId(String[] ids) throws CheckedServiceException {
        if (ArrayUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "id为空");
        }
        return userMessageDao.deleteMessageByMsgId(ids);
    }

    @Override
    public PageResult<UserMessageVO> listMessage(PageParameter<UserMessageVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        pageParameter.getParameter().setSenderId(pmphUser.getId());
        PageResult<UserMessageVO> pageResult = new PageResult<>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        Integer total = userMessageDao.getMessageTotal(pageParameter);
        if (total > 0) {
            List<UserMessageVO> list = userMessageDao.listMessage(pageParameter);
            Message message;
            for (UserMessageVO userMessageVO : list) {
                message = messageService.get(userMessageVO.getMsgId());
                if (ObjectUtil.notNull(message)) {
                    userMessageVO.setContent(message.getContent());
                }
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public String msgUploadFiles(MultipartFile file) throws CheckedServiceException {
        if (ObjectUtil.isNull(file)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息附件为空！");
        }
        String filePath = "";
        // 循环获取file数组中得文件
        if (StringUtil.notEmpty(file.getOriginalFilename())) {
            String fullFileName = file.getOriginalFilename();// 完整文件名
            String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));// 去掉后缀的文件名称
            FileUpload.fileUp(file, Const.MSG_FILE_PATH_FILE, fileName);// 上传文件
            filePath = Const.MSG_FILE_PATH_FILE + fullFileName;
        }
        return filePath;
    }

    @Override
    public Map<String, Object> getUserMessageById(Long userMsgId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(userMsgId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息ID为空！");
        }
        UserMessage userMessage = userMessageDao.getUserMessageById(userMsgId);
        if (ObjectUtil.isNull(userMessage)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户消息对象为空！");
        }
        resultMap.put("title", userMessage.getTitle());
        Message message = messageService.get(userMessage.getMsgId());
        if (ObjectUtil.isNull(message)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息对象为空！");
        }
        resultMap.put("content", message.getContent());
        List<MessageAttachment> messageAttachments =
        messageAttachmentService.getMessageAttachmentByMsgId(message.getId());
        resultMap.put("MessageAttachment", messageAttachments);
        return resultMap;
    }
}
