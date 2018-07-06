package com.bc.pmpheep.back.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.MessageAttachment;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CastUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.back.vo.MyMessageVO;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.back.vo.UserMessageVO;
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
    private MyWebSocketHandler       myWebSocketHandler;

    @Autowired
    private DecPositionService       decPositionService;

    @Autowired
    private PmphDepartmentService    pmphDepartmentService;

    @Override
    public UserMessage addUserMessage(UserMessage userMessage) throws CheckedServiceException {
        if (null == userMessage) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (StringUtil.isEmpty(userMessage.getMsgId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息为空");
        }
        if (null == userMessage.getMsgType()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息类型为空");
        }
        if (StringUtil.isEmpty(userMessage.getTitle())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息标题为空");
        }
        if (null == userMessage.getSenderId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送者id为空");
        }
        if (null == userMessage.getSenderType()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送者类型为空");
        }
        if (null == userMessage.getReceiverId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "接收者id为空");
        }
        if (null == userMessage.getReceiverType()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "接收者类型为空");
        }
        userMessageDao.addUserMessage(userMessage);
        return userMessage;
    }

    @Override
    public void addUserMessageBatch(List<UserMessage> userMessageList)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(userMessageList)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息为空");
        }
        for (UserMessage userMessage : userMessageList) {
            if (null == userMessage) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "消息为空");
            }
            if (StringUtil.isEmpty(userMessage.getMsgId())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "消息为空");
            }
            if (null == userMessage.getMsgType()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "消息类型为空");
            }
            if (StringUtil.isEmpty(userMessage.getTitle())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "消息标题为空");
            }
            if (null == userMessage.getSenderId()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "发送者id为空");
            }
            if (null == userMessage.getSenderType()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "发送者类型为空");
            }
            if (null == userMessage.getReceiverId()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "接收者id为空");
            }
            if (null == userMessage.getReceiverType()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "接收者类型为空");
            }
        }
        userMessageDao.addUserMessageBatch(userMessageList);
    }

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
        if (StringUtil.isEmpty(pageParameter.getParameter().getMsgId())
            && ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息为空");
        }
        // 搜索条件，按收件人或单位搜索
        String orgNameOrReceiver = pageParameter.getParameter().getName();
        // 防止输入空格查询，如果为" "
        if (StringUtil.isEmpty(orgNameOrReceiver)) {
            pageParameter.getParameter().setName(null);
        } else {
            pageParameter.getParameter().setName(orgNameOrReceiver.replaceAll(" ", ""));
        }
        if (Const.FALSE.booleanValue() == pmphUser.getIsAdmin().booleanValue()) {
            //这里是为了过滤权限 自己可以看到自己发送的，主任可以看到下属发送的。
            if(pmphUser.getDepartmentId()==userMessageDao.getSenderDepartmentId(pageParameter.getParameter().getSenderId())&&pmphUser.getIsDirector()){

            }else{
                pageParameter.getParameter().setSenderId(pmphUser.getId());
            }

        }

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
    Integer pageSize, String orgName, Long materialId, String userNameOrUserCode,
    String materialName) throws CheckedServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(sendType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送对象未选择，请选择发送对象!");
        }
        // 1 发送给学校管理员 //2 所有人
        if (Const.SEND_OBJECT_1.intValue() == sendType.intValue()
            || Const.SEND_OBJECT_2.intValue() == sendType.intValue()) {
            resultMap.put("orgVo", orgService.listSendToSchoolAdminOrAllUser(orgName, materialId));
        }
        // 指定用户
        if (Const.SEND_OBJECT_3.intValue() == sendType.intValue()) {
            // 调用用户管理的接口
        }
        // 教材所有报名者
        if (Const.SEND_OBJECT_4.intValue() == sendType.intValue()) {
            resultMap.put("Material", materialService.getListMaterial(materialName));
        }
        return resultMap;
    }

    @Override
    public Integer addOrUpdateUserMessage(HttpServletRequest request, Message message,
    String title, Integer sendType, String orgIds, Long senderId, String userIds, String bookIds,
    boolean isSave, String[] files, String sessionId) throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(sendType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送对象未选择，请选择!");
        }
        if (title.length() > 50) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息标题不能超过50个字");
        }
        // 如果 补发不进行消息插入
        if (isSave) {
            // MongoDB 消息插入
            message = messageService.add(message);
        }
        if (StringUtil.isEmpty(message.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
        }
        // 发送者id
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "操作人为空!");
        }
        // 是否为补发消息设置（发送者ID）
        Long senderUserId;
        if (ObjectUtil.isNull(senderId)) {// 新发消息,发送者Id为登陆用户ID
            senderUserId = pmphUser.getId();
        } else {
            senderUserId = senderId;// 补发消息,发送者Id为当前补发消息的发送者ID
        }
        // 装储存数据
        List<UserMessage> userMessageList = new ArrayList<UserMessage>();
        // 1 发送给学校管理员 //2 所有人
        if (Const.SEND_OBJECT_1.intValue() == sendType.intValue()
            || Const.SEND_OBJECT_2.intValue() == sendType.intValue()) {
            if (StringUtil.isEmpty(orgIds)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "参数错误!");
            }
            String[] orgIds1 = StringUtil.str2StrArray(orgIds);
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
                                                    senderUserId, Const.SENDER_TYPE_1,
                                                    orgUser.getId(), Const.RECEIVER_TYPE_3, 0L,sendType.shortValue()));
            }
            // 作家用户
            if (Const.SEND_OBJECT_2.intValue() == sendType.intValue()) {
                List<WriterUser> writerUserList =
                writerUserService.getWriterUserListByOrgIds(orgIds2);
                for (WriterUser writerUser : writerUserList) {
                    userMessageList.add(new UserMessage(message.getId(), title, Const.MSG_TYPE_1,
                                                        senderUserId, Const.SENDER_TYPE_1,
                                                        writerUser.getId(), Const.RECEIVER_TYPE_2,
                                                        0L,sendType.shortValue()));
                }
            }
        }
        // 3 指定用户
        if (Const.SEND_OBJECT_3.intValue() == sendType.intValue()) {
            if (StringUtil.isEmpty(userIds)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "没有选中发送人!");
            }
            String[] ids = StringUtil.str2StrArray(userIds);
            for (String id : ids) {
                if (StringUtil.notEmpty(id)) {
                    String userType = id.split("_")[0];
                    String userId = id.split("_")[1];
                    if (StringUtil.notEmpty(userId) && StringUtil.isNumeric(userId)) {
                        userMessageList.add(new UserMessage(message.getId(), title,
                                                            Const.MSG_TYPE_1, senderUserId,
                                                            Const.SENDER_TYPE_1,
                                                            Long.parseLong(userId),
                                                            new Short(userType), 0L,sendType.shortValue()));
                    }
                }
            }
        }
        // 4 发送给教材所有报名者
        if (Const.SEND_OBJECT_4.intValue() == sendType.intValue()) {
            if (StringUtil.isEmpty(bookIds)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM, "书籍为空!");
            }
            String[] ids = StringUtil.str2StrArray(bookIds);
            List<Long> userIdList = decPositionService.listDecPositionsByTextbookIds(ids);
            for (Long userId : userIdList) {
                userMessageList.add(new UserMessage(message.getId(), title, Const.MSG_TYPE_1,
                                                    senderUserId, Const.SENDER_TYPE_1, userId,
                                                    Const.RECEIVER_TYPE_2, 0L,sendType.shortValue()));
            }
            // 获取到书籍id然后根据书籍id在dec_position表中获取到申报表id根据申报表id在declaration表中获取作家id放入userMessage的接收人中
        }
        // 如果是补发,进入下面操作 进行已发人员筛出
        if (!isSave) {
            // 查询当前消息内容
            Message msg = messageService.get(message.getId());
            if (ObjectUtil.notNull(msg)) {
                message.setContent(msg.getContent());
            }
            List<UserMessage> temp = new ArrayList<UserMessage>();
            // 已经发送的人员列表
            List<UserMessage> sendedList = userMessageDao.getMessageByMsgId(message.getId());
            // 已发送消息是否撤回
            Boolean isWithdraw = false;
            //消息是否已撤回

            for (UserMessage userMessage : userMessageList) {
                boolean flag = false;// 没有发送
                for (UserMessage uMessage : sendedList) {
//                    if (!isWithdraw) {
//                        if (uMessage.getIsWithdraw()) {// 判断消息是否撤回
//                            isWithdraw = true;
//                        }
//                    }
                    if (userMessage.getReceiverId().longValue() == uMessage.getReceiverId()
                                                                           .longValue()
                        && userMessage.getReceiverType().shortValue() == uMessage.getReceiverType()
                                                                                 .shortValue()) {
                        flag = true;
                        break;
                    }

                }
                if (!flag) {
                    temp.add(userMessage);
                }
            }
            userMessageList = temp;
            // 补发，取消撤回当前已发送的消息
            //消息是否已撤回
            if (userMessageDao.getMessageIsWithDraw(message.getId())>0) {
                this.updateCancelToWithdraw(message.getId());
            }
        }
        // 插入消息发送对象数据
        if (CollectionUtil.isNotEmpty(userMessageList)) {
            userMessageDao.addUserMessageBatch(userMessageList);
        }
        // websocket发送的id集合
        List<String> websocketUserIds = new ArrayList<String>();
        for (UserMessage userMessage : userMessageList) {
            websocketUserIds.add(userMessage.getReceiverType() + "_" + userMessage.getReceiverId());
        }
        // webscokt发送消息
        if (CollectionUtil.isNotEmpty(websocketUserIds)) {
            WebScocketMessage webScocketMessage =
            new WebScocketMessage(message.getId(), Const.MSG_TYPE_1, senderUserId,
                                  pmphUser.getRealname(), Const.SENDER_TYPE_1,
                                  Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR, title,
                                  message.getContent(), DateUtil.getCurrentTime());
            myWebSocketHandler.sendWebSocketMessageToUser(websocketUserIds, webScocketMessage);
            // 添加附件到MongoDB表中
            saveFileToMongoDB(request, files, message.getId());
        }
        return userMessageList.size();
    }

    @Override
    public Integer updateUserMessage(HttpServletRequest request, Message message, String msgId,
    String msgTitle, String[] files, String[] attachment) throws CheckedServiceException,
    IOException {
        Integer count = 0;
        // 更新消息内容Message
        if (StringUtil.notEmpty(msgId) && ObjectUtil.notNull(message.getContent())) {
            message.setId(msgId);
            messageService.update(message);
            count = 1;
        }
        // 更新用户消息UserMessage
        if (StringUtil.notEmpty(msgId) && StringUtil.notEmpty(msgTitle)) {
            count = userMessageDao.updateUserMessageTitleByMsgId(new UserMessage(msgId, msgTitle));
        }
        // 添加附件到MongoDB表中
        if (StringUtil.notEmpty(msgId)) {
            saveFileToMongoDB(request, files, msgId);
        }
        // 是否有消息附件删除
        if (ArrayUtil.isNotEmpty(attachment)) {
            messageAttachmentService.deleteMessageAttachmentByAttachment(attachment);
            for (int i = 0; i < attachment.length; i++) {
                fileService.remove(attachment[i]);
            }
            count = 1;
        }
        return count;
    }

    /**
     * 
     * <pre>
	 * 功能描述：保存文件到MongoDB
	 * 使用示范：
	 *
	 * @param files 临时文件路径
	 * @param msgId messageId
	 * @throws CheckedServiceException
	 * </pre>
     */
    private void saveFileToMongoDB(HttpServletRequest request, String[] files, String msgId)
    throws IOException {
        // 添加附件到MongoDB表中
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(files[i]);
                String fileName =
                (String) request.getSession(false).getAttribute("fileName_" + files[i]);
                InputStream sbs = new ByteArrayInputStream(fileByte);
                String gridFSFileId =
                fileService.save(sbs, fileName, FileType.MSG_FILE, CastUtil.castLong(msgId));
                if (StringUtil.isEmpty(gridFSFileId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                      CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                      "文件上传失败!");
                }
                // 保存对应数据
                MessageAttachment mAttachment =
                messageAttachmentService.addMessageAttachment(new MessageAttachment(msgId,
                                                                                    gridFSFileId,
                                                                                    fileName));
                if (ObjectUtil.isNull(mAttachment.getId())) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                      CheckedExceptionResult.NULL_PARAM,
                                                      "MessageAttachment对象保存失败!");
                }
                // String localFile = files[i];
                // String fileDirectory =
                // localFile.substring(0, localFile.lastIndexOf(File.separatorChar));
                // FileUtil.delete(fileDirectory);// 删除本地临时文件
            }
        }
    }

    @Override
    public Integer updateToWithdraw(UserMessage userMessage) throws CheckedServiceException {
        if (StringUtil.isEmpty(userMessage.getMsgId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象id为空");
        }
        // 已经发送的人员列表
        List<UserMessage> sendedList = userMessageDao.getMessageByMsgId(userMessage.getMsgId());
        // for (UserMessage userMessage2 : sendedList) {
        if (CollectionUtil.isNotEmpty(sendedList)) {
            // if (ObjectUtil.notNull(userMessage2.getIsRead()) && userMessage2.getIsRead()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息已有人读取，无法撤销！");
            // }
            // }
        }
        return userMessageDao.updateUserMessageWithdrawByMsgId(userMessage.getMsgId());
    }

    @Override
    public Integer updateUserMessageIsDeletedByMsgId(List<String> msgIds)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(msgIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息更新对象id为空");
        }
        return userMessageDao.updateUserMessageIsDeletedByMsgId(msgIds);
    }

    @Override
    public Integer deleteMessageByMsgId(List<String> msgIds) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(msgIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "id为空");
        }
        return userMessageDao.deleteMessageByMsgId(msgIds);
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
        // 如果是系统管理员，则查询所有，否则查询对应的消息
        if (Const.FALSE == pmphUser.getIsAdmin()) {
            List<Long> ids = new ArrayList<Long>();
            // 如果是主任，获取主任所在部门下的所有用户
            if (Const.TRUE == pmphUser.getIsDirector()) {
                // 社内部门父级节点ID
                Long parentId = 1L;
                PmphDepartment pmphDepartment =
                pmphDepartmentService.getPmphDepartmentById(pmphUser.getDepartmentId());
                // 如果是人卫社主任，则可以查看所有用户发送的消息
                if (parentId.longValue() == pmphDepartment.getId().longValue()) {
                    ids = null;
                } else {
                    // 如果是子级部门主任，则只可以查看子级部门下的用户发送的消息
                    PageParameter<PmphUserManagerVO> parameter = new PageParameter<>(1, 2000);
                    PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
                    pmphUserManagerVO.setPath(pmphDepartment.getPath());
                    pmphUserManagerVO.setDepartmentId(pmphDepartment.getId());
                    parameter.setParameter(pmphUserManagerVO);
                    PageResult<PmphUserManagerVO> listPageResult =
                            pmphUserService.getListPmphUser(parameter, null);
                    List<PmphUserManagerVO> listPmphUserManagerVOs = listPageResult.getRows();
                    for (PmphUserManagerVO pmManagerVO : listPmphUserManagerVOs) {
                        ids.add(pmManagerVO.getId());
                    }
                }
            } else {
                ids.add(pmphUser.getId());
            }
            pageParameter.getParameter().setSenderIds(ids);
        }
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
    public String msgUploadFiles(HttpServletRequest request, MultipartFile file)
    throws CheckedServiceException, IOException {
        if (file.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "附件为空！");
        }
        String tempFileId = "";
        // 循环获取file数组中得文件
        if (StringUtil.notEmpty(file.getOriginalFilename())) {
            String fullFileName = file.getOriginalFilename();// 完整文件名
            if (fullFileName.length() > 80) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.NULL_PARAM,
                                                  "附件名称超出80个字符长度，请修改后上传！");
            }
            // String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));//
            // 去掉后缀的文件名称
            // String beforeDate = DateUtil.date2Str(new Date(), "yyyyMMddHHmmss") +
            // File.separator;// 获取当前时间拼接路径
            // FileUpload.fileUp(file, Const.MSG_FILE_PATH_FILE + beforeDate, fileName);// 上传文件
            // filePath = Const.MSG_FILE_PATH_FILE + beforeDate + fullFileName;

            UUID uuid = UUID.randomUUID();
            tempFileId = uuid.toString();
            request.getSession(false).setAttribute("fileName_" + tempFileId,
                                                   file.getOriginalFilename());
            byte[] fileByte = file.getBytes();
            request.getSession(false).setAttribute(tempFileId, fileByte);
        }
        return tempFileId;
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
        String realName = null;
        if (Const.SENDER_TYPE_0 == userMessage.getSenderType()
            || Const.SENDER_TYPE_1 == userMessage.getSenderType()) {
            PmphUser pmphUser = pmphUserService.get(userMessage.getSenderId());
            realName = pmphUser.getRealname();
        }
        if (Const.SENDER_TYPE_2 == userMessage.getSenderType()) {
            WriterUser writerUser = writerUserService.get(userMessage.getSenderId());
            realName = writerUser.getRealname();
        }
        if (Const.SENDER_TYPE_3 == userMessage.getSenderType()) {
            OrgUser orgUser = orgUserService.getOrgUserById(userMessage.getSenderId());
            realName = orgUser.getRealname();
        }

        resultMap.put("msgId", userMessage.getId());// 主键ID
        resultMap.put("title", userMessage.getTitle());// 标题
        resultMap.put("senderName", realName);// 发送者
        resultMap.put("senderDate", userMessage.getGmtCreate());// 发送时间
        Message message = messageService.get(userMessage.getMsgId());// 获取消息内容
        if (ObjectUtil.notNull(message)) {
            // throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
            // CheckedExceptionResult.NULL_PARAM, "消息对象为空！");
            resultMap.put("content", message.getContent());// 内容
            List<MessageAttachment> messageAttachments =
            messageAttachmentService.getMessageAttachmentByMsgId(message.getId());
            if (CollectionUtil.isNotEmpty(messageAttachments)) {
                for (MessageAttachment mAttachment : messageAttachments) {
                    String attachmentId = mAttachment.getAttachment();
                    mAttachment.setAttachment(Const.FILE_DOWNLOAD + attachmentId);
                }
            }
            resultMap.put("MessageAttachment", messageAttachments);// 内容附件
        }

        return resultMap;
    }

    @Override
    public PageResult<MyMessageVO> listMyMessage(PageParameter<MyMessageVO> pageParameter)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户id为空！");
        }
        if (ObjectUtil.isNull(pageParameter.getParameter().getUserType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户类型为空！");
        }
        PageResult<MyMessageVO> pageResult = new PageResult<MyMessageVO>();
        Integer total = userMessageDao.listMyMessageTotal(pageParameter);
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        if (total > 0) {
            List<MyMessageVO> list = userMessageDao.listMyMessage(pageParameter);
            for (MyMessageVO myMessageVO : list) {
                switch (myMessageVO.getSenderType()) {
                case 0:
                    myMessageVO.setSenderName("系统");
                    break;
                case 1:
                    PmphUser pmphUser = pmphUserService.get(myMessageVO.getSenderId());
                    myMessageVO.setSenderAvatar(pmphUser.getAvatar());
                    myMessageVO.setSenderName(pmphUser.getRealname());
                    break;

                case 2:
                    WriterUser writerUser = writerUserService.get(myMessageVO.getSenderId());
                    myMessageVO.setSenderAvatar(writerUser.getAvatar());
                    myMessageVO.setSenderName(writerUser.getRealname());
                    break;

                case 3:
                    // 现在没有机构用户
                    break;

                default:
                    throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                      CheckedExceptionResult.NULL_PARAM,
                                                      "发送者类型不正确！");
                }
            }
            pageResult.setRows(list);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public Integer updateMyMessage(Long[] ids) throws CheckedServiceException {
        if (ArrayUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "没有删除的消息");
        }
        return userMessageDao.updateMyMessage(ids);
    }

    @Override
    public PageResult<MyMessageVO> listMyMessageOfIcon(PageParameter<MyMessageVO> pageParameter)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户id为空！");
        }
        if (ObjectUtil.isNull(pageParameter.getParameter().getUserType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "用户类型为空！");
        }
        PageResult<MyMessageVO> pageResult = new PageResult<MyMessageVO>();
        Integer total = userMessageDao.listMyMessageTotal(pageParameter);
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        if (total > 0) {
            List<MyMessageVO> list = userMessageDao.listMyMessage(pageParameter);
            for (MyMessageVO myMessageVO : list) {
                switch (myMessageVO.getSenderType()) {
                case 0:
                    myMessageVO.setSenderName("系统");
                    break;
                case 1:
                    PmphUser pmphUser = pmphUserService.get(myMessageVO.getSenderId());
                    myMessageVO.setSenderAvatar(pmphUser.getAvatar());
                    myMessageVO.setSenderName(pmphUser.getRealname());
                    break;

                case 2:
                    WriterUser writerUser = writerUserService.get(myMessageVO.getSenderId());
                    myMessageVO.setSenderAvatar(writerUser.getAvatar());
                    myMessageVO.setSenderName(writerUser.getRealname());
                    break;

                case 3:
                    // 现在没有机构用户
                    break;

                default:
                    throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                      CheckedExceptionResult.NULL_PARAM,
                                                      "发送者类型不正确！");
                }
                Message message = messageService.get(myMessageVO.getMsgId());
                if (ObjectUtil.isNull(message)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                      CheckedExceptionResult.NULL_PARAM, "没有获取到内容！");
                }
                myMessageVO.setContent(message.getContent());
            }
            pageResult.setRows(list);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public MyMessageVO updateMyMessageDetail(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "消息id为空！");
        }
        MyMessageVO myMessageVO = userMessageDao.getMyMessageDetail(id);
        switch (myMessageVO.getSenderType()) {
        case 0:
            myMessageVO.setSenderName("系统");
            break;
        case 1:
            PmphUser pmphUser = pmphUserService.get(myMessageVO.getSenderId());
            myMessageVO.setSenderAvatar(pmphUser.getAvatar());
            myMessageVO.setSenderName(pmphUser.getRealname());
            break;

        case 2:
            WriterUser writerUser = writerUserService.get(myMessageVO.getSenderId());
            myMessageVO.setSenderAvatar(writerUser.getAvatar());
            myMessageVO.setSenderName(writerUser.getRealname());
            break;

        case 3:
            // 现在没有机构用户
            break;

        default:
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "发送者类型不正确！");
        }
        Message message = messageService.get(myMessageVO.getMsgId());
        if (ObjectUtil.isNull(message)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "没有获取到消息详情");
        }
        myMessageVO.setContent(message.getContent());
        List<MessageAttachment> messageAttachments =
        messageAttachmentService.getMessageAttachmentByMsgId(myMessageVO.getMsgId());
        for (MessageAttachment messageAttachment : messageAttachments) {
            messageAttachment.setAttachment(RouteUtil.MONGODB_FILE
                                            + messageAttachment.getAttachment());
        }
        myMessageVO.setMessageAttachments(messageAttachments);
        UserMessage userMessage = new UserMessage();
        userMessage.setId(id);
        userMessage.setIsRead(true);
        userMessageDao.updateUserMessage(userMessage);
        return myMessageVO;
    }

    @Override
    public Integer updateUserMessage(UserMessage userMessage) throws CheckedServiceException {
        if (null == userMessage.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空！");
        }
        return userMessageDao.updateUserMessage(userMessage);
    }

    @Override
    public Integer updateCancelToWithdraw(String msgId) throws CheckedServiceException {
        if (StringUtil.isEmpty(msgId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return userMessageDao.updateUserMessageCancelWithdrawByMsgId(msgId);
    }

    @Override
    public Integer addOneUserMessage(Message message, Long receiverId, String sessionId)
    throws CheckedServiceException, IOException {
        // 发送者id
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "发送人为空!");
        }
        // MongoDB 消息插入
        message = messageService.add(message);
        if (StringUtil.isEmpty(message.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
        }
        Long senderUserId = pmphUser.getId();// 新发消息,发送者Id为登陆用户ID
        // 装储存数据
        UserMessage userMessage = new UserMessage();
        // 私信发送
        if (null == receiverId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.NULL_PARAM, "接收人为空!");
        } else {
            userMessage =
            new UserMessage(message.getId(), "系统消息", Const.MSG_TYPE_0, senderUserId,
                            Const.SENDER_TYPE_1, receiverId, Const.RECEIVER_TYPE_2);
        }
        // 插入消息发送对象数据
        Integer res = userMessageDao.addUserMessage(userMessage);
        // websocket发送的id
        List<String> websocketUserId = new ArrayList<String>();
        websocketUserId.add(userMessage.getReceiverType() + "_" + userMessage.getReceiverId());
        // webscokt发送消息
        if (CollectionUtil.isNotEmpty(websocketUserId)) {
            WebScocketMessage webScocketMessage =
            new WebScocketMessage(message.getId(), Const.MSG_TYPE_0, senderUserId,
                                  pmphUser.getRealname(), Const.SENDER_TYPE_1,
                                  Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR,
                                  message.getContent(), DateUtil.getCurrentTime());
            myWebSocketHandler.sendWebSocketMessageToUser(websocketUserId, webScocketMessage);
        }
        return res;
    }
}
