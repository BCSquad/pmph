package com.bc.pmpheep.back.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsContentDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.CmsExtra;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.SummaryUtil;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.websocket.MyWebSocketHandler;
import com.bc.pmpheep.websocket.WebScocketMessage;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 接口实现
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-10-25
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
@Service
public class CmsContentServiceImpl implements CmsContentService {
    @Autowired
    CmsContentDao                   cmsContentDao;
    @Autowired
    ContentService                  contentService;
    @Autowired
    CmsScheduleService              cmsScheduleService;
    @Autowired
    private FileService             fileService;
    @Autowired
    CmsExtraService                 cmsExtraService;
    @Autowired
    MaterialExtraService            materialExtraService;
    @Autowired
    MaterialNoticeAttachmentService materialNoticeAttachmentService;
    @Autowired
    MaterialNoteAttachmentService   materialNoteAttachmentService;
    @Autowired
    WriterUserTrendstService        writerUserTrendstService;
    @Autowired
    WriterPointRuleService          writerPointRuleService;
    @Autowired
    WriterPointLogService           writerPointLogService;
    @Autowired
    WriterPointService              writerPointService;
    @Autowired
    MyWebSocketHandler              myWebSocketHandler;
    @Autowired
    UserMessageService              userMessageService;
    @Autowired
    MessageService                  messageService;

    @Override
    public CmsContent addCmsContent(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "CmsContent对象参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getParentId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "上级id参数为空");
        }
        if (StringUtil.isEmpty(cmsContent.getPath())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "根节点路径参数为空");
        }
        if (StringUtil.isEmpty(cmsContent.getMid())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容id参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getCategoryId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容类型参数为空");
        }
        if (StringUtil.isEmpty(cmsContent.getTitle())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容标题参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getAuthorType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "作者类型参数为空");
        }

        if (cmsContent.getTitle().length() > 100) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.ILLEGAL_PARAM,
                                              "消息标题不能超过100个字！");
        }
        cmsContentDao.addCmsContent(cmsContent);
        return cmsContent;
    }

    @Override
    public CmsContent addCmsContent(CmsContent cmsContent, String[] files, String content,
    String scheduledTime, String sessionId, HttpServletRequest request)
    throws CheckedServiceException, IOException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.isEmpty(content)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getCategoryId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "所属栏目不能为空");

        }
        if (ObjectUtil.isNull(cmsContent.getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID不能为空");

        }
        if (ObjectUtil.isNull(cmsContent.getAuthorname())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "作者姓名参数为空");
        }
        // MongoDB 内容插入
        Content contentObj = contentService.add(new Content(content));
        if (StringUtil.isEmpty(contentObj.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED,
                                              "Content对象内容保存失败");
        }
        // 内容保存
        cmsContent.setParentId(cmsContent.getCategoryId());// 上级id
        // cmsContent.setPath(cmsContent.getPath());// 根节点路径
        cmsContent.setMid(contentObj.getId());// 内容id
        cmsContent.setAuthorType(Const.CMS_AUTHOR_TYPE_1);// 作者类型
        cmsContent.setAuthorId(pmphUser.getId());// 作者id
        // cmsContent.setMaterialId(cmsContent.getMaterialId());// 教材ID，为0表示未选择教材
        String summary = SummaryUtil.htmlToText(content);
        summary = summary.substring(0, Math.min(summary.length(), 60));
        cmsContent.setSummary(summary);
        if (Const.TRUE == cmsContent.getIsPublished()) {
            cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
        }
        // 信息快报/公告管理(发布)，审核时间就为当前时间
        if (ObjectUtil.notNull(cmsContent.getAuthStatus())) {
            if (Const.CMS_AUTHOR_STATUS_2.shortValue() == cmsContent.getAuthStatus()) {
                cmsContent.setAuthUserId(pmphUser.getId());
                cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
                cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                                DateUtil.getCurrentTime()));
                cmsContent.setIsPublished(true);
            }
        }
        if (ObjectUtil.notNull(cmsContent.getIsStaging())) {
            if (Const.TRUE.booleanValue() == cmsContent.getIsStaging().booleanValue()) {
                // 信息快报/公告管理(暂存)
                cmsContent.setAuthUserId(pmphUser.getId());
                cmsContent.setAuthStatus(null);
                cmsContent.setAuthDate(null);
                cmsContent.setIsPublished(false);
            }
        }
        Long contentId = this.addCmsContent(cmsContent).getId();// 获取新增后的主键ID
        if (ObjectUtil.isNull(contentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED,
                                              "CmsContent添加内容失败");
        }
        // 定时发布
        // if (Const.TRUE.booleanValue() == cmsContent.getIsScheduled().booleanValue())
        // {
        // if (StringUtil.isEmpty(scheduledTime)) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
        // CheckedExceptionResult.NULL_PARAM, "定时发布时间参数为空");
        // }
        // cmsScheduleService.addCmsSchedule(new CmsSchedule(contentId,
        // DateUtil.str2Timestam(scheduledTime)));
        // }
        // 保存附件到MongoDB
        this.saveFileToMongoDB(request, files, null, contentId);
        return cmsContent;
    }

    @Override
    public Integer updateCmsContent(CmsContent cmsContent, String[] files, String[] imgFile,
    String content, String[] attachment, String[] imgAttachment, String scheduledTime,
    String sessionId, HttpServletRequest request) throws CheckedServiceException, IOException {
        Integer count = 0;
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.notEmpty(content)) {
            // 更新MongoDB 内容
            contentService.update(new Content(cmsContent.getMid(), content));
        }
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsContent.getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID为空");
        }
        // 信息快报/公告管理(发布)，审核时间就为当前时间
        if (Const.CMS_AUTHOR_STATUS_2 == cmsContent.getAuthStatus().shortValue()) {
            cmsContent.setAuthUserId(pmphUser.getId());
            cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
            cmsContent.setIsStaging(Const.FALSE);
            cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                            DateUtil.getCurrentTime()));
            cmsContent.setIsPublished(true);
        } else if (Const.CMS_AUTHOR_STATUS_0 == cmsContent.getAuthStatus().shortValue()) {
            // if (cmsContent.getIsStaging()) {
            // 信息快报/公告管理(暂存)
            cmsContent.setAuthUserId(pmphUser.getId());
            cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_0);
            cmsContent.setAuthDate(null);
            cmsContent.setIsPublished(false);
            cmsContent.setIsStaging(true);
            // }
        } else if (Const.CMS_AUTHOR_STATUS_1.shortValue() == cmsContent.getAuthStatus()
                                                                       .shortValue()) {
            // 文章管理,退回
            cmsContent.setAuthUserId(pmphUser.getId());
            cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_1);
            cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                            DateUtil.getCurrentTime()));
            cmsContent.setIsDeleted(false);
            cmsContent.setIsPublished(false);
        }
        if (cmsContent.getCategoryId() == Const.CMS_CATEGORY_ID_1
            && cmsContent.getAuthorType() == Const.CMS_AUTHOR_TYPE_2
            && cmsContent.getAuthStatus() == Const.CMS_AUTHOR_STATUS_0
            && Const.TRUE == cmsContent.getIsStaging()) {
            cmsContent.setIsStaging(false);
        }
        // 再次编辑时间
        cmsContent.setGmtReedit(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                         DateUtil.getCurrentTime()));
        // 撤销
        if (null != cmsContent.getIsPublished()&& 2 != cmsContent.getAuthStatus()) {
            cmsContent.setIsStaging(cmsContent.getIsPublished());
        } else {
            cmsContent.setIsStaging(false);
        }
        count = cmsContentDao.updateCmsContent(cmsContent);
        if (count > 0// 内容管理，退回发送消息
            && Const.CMS_AUTHOR_STATUS_1.shortValue() == cmsContent.getAuthStatus().shortValue()) {
            // MongoDB 消息插入
            String categoryName = "文章管理";
            if (Const.CMS_CATEGORY_ID_2.longValue() == cmsContent.getCategoryId().longValue()) {
                categoryName = "信息快报管理";
            } else if (Const.CMS_CATEGORY_ID_3.longValue() == cmsContent.getCategoryId()
                                                                        .longValue()) {
                categoryName = "公告管理";
            }
            // 退回时发送消息内容
            StringBuilder sb = new StringBuilder();
            sb.append(categoryName);
            sb.append("中您添加的《 ");
            sb.append(cmsContent.getTitle());
            sb.append(" 》已被退回 ！");
            if (StringUtil.notEmpty(cmsContent.getReturnReason())) {
                sb.append("<br/><br/>退回理由为：");
                sb.append(cmsContent.getReturnReason());
            }
            Message message = messageService.add(new Message(sb.toString()));
            if (StringUtil.isEmpty(message.getId())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
            }
            String returnContentTitle = "内容管理审核退回";
            List<UserMessage> userMessageList = new ArrayList<UserMessage>(4); // 系统消息
            userMessageList.add(new UserMessage(message.getId(), returnContentTitle,
                                                Const.MSG_TYPE_1, pmphUser.getId(),
                                                Const.SENDER_TYPE_1, cmsContent.getAuthorId(),
                                                cmsContent.getAuthorType(), 0L));
            // 发送消息
            if (CollectionUtil.isNotEmpty(userMessageList)) {
                userMessageService.addUserMessageBatch(userMessageList); // 插入消息发送对象数据
                // websocket发送的id集合
                List<String> websocketUserIds = new ArrayList<String>();
                for (UserMessage userMessage : userMessageList) {
                    websocketUserIds.add(userMessage.getReceiverType() + "_"
                                         + userMessage.getReceiverId());
                }
                // webscokt发送消息
                if (CollectionUtil.isNotEmpty(websocketUserIds)) {
                    WebScocketMessage webScocketMessage =
                    new WebScocketMessage(message.getId(), Const.MSG_TYPE_1, pmphUser.getId(),
                                          pmphUser.getRealname(), Const.SENDER_TYPE_1,
                                          Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR,
                                          returnContentTitle, message.getContent(),
                                          DateUtil.getCurrentTime());
                    myWebSocketHandler.sendWebSocketMessageToUser(websocketUserIds,
                                                                  webScocketMessage);
                }
            }
        }
        // 当文章通过的时候给用户增加积分
        if (Const.CMS_CATEGORY_ID_1.longValue() == cmsContent.getCategoryId().longValue()
            && Const.CMS_AUTHOR_STATUS_2.shortValue() == cmsContent.getAuthStatus().shortValue()
            && Const.CMS_AUTHOR_TYPE_2 == cmsContent.getAuthorType()) {
            String ruleName = "发表文章";
            writerPointLogService.addWriterPointLogByRuleName(ruleName, cmsContent.getAuthorId());
            //原创
            if(cmsContent.getIsOriginal() != null && cmsContent.getIsOriginal()){
                writerPointLogService.addWriterPointLogByRuleName("原创文章", cmsContent.getAuthorId());
            }
        }
        // 删除附件
        if (ArrayUtil.isNotEmpty(attachment)) {
            // 删除CmsExtra 表
            cmsExtraService.deleteCmsExtraByAttachment(attachment);
            // 删除MongoDB对应的文件
            for (int i = 0; i < attachment.length; i++) {
                fileService.remove(attachment[i]);
            }
        }
        // 文章封面
        if (ArrayUtil.isNotEmpty(imgAttachment)) {
            // 删除CmsExtra 表
            cmsExtraService.deleteCmsExtraByAttachment(imgAttachment);
            // 删除MongoDB对应的文件
            for (int i = 0; i < imgAttachment.length; i++) {
                fileService.remove(imgAttachment[i]);
            }
            if (ArrayUtil.isEmpty(imgFile)) {// 如果删除了封面没上传，就使用默认封面
                this.updateCmsContent(new CmsContent(cmsContent.getId(), "DEFAULT"));
            }
        }
        // 保存附件到MongoDB
        this.saveFileToMongoDB(request, files, imgFile, cmsContent.getId());
        return count;
    }

    @Override
    public Integer publishCmsContentById(Long id, String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        // count = cmsContentDao.publishCmsContentById(id);
        Integer count =
        cmsContentDao.updateCmsContent(new CmsContent(
                                                      id,
                                                      Boolean.TRUE,
                                                      Boolean.FALSE,
                                                      Const.CMS_AUTHOR_STATUS_2,
                                                      pmphUser.getId(),
                                                      DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                                               DateUtil.getCurrentTime())));
        CmsContent cmsContent = this.getCmsContentById(id);
        writerUserTrendstService.addWriterUserTrendst(new WriterUserTrendst(
                                                                            cmsContent.getAuthorId(),
                                                                            Const.WRITER_USER_TRENDST_TYPE_1,
                                                                            id));
        return count;
    }

    @Override
    public Integer hideCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.hideCmsContentById(id);
    }

    @Override
    public Integer checkContentById(Long id, Short authStatus, Long categoryId, String sessionId,Boolean isOriginal)
    throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(id) || ObjectUtil.isNull(authStatus)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Boolean isPublished = false;
        Boolean isStaging = false;
        if (Const.CMS_AUTHOR_STATUS_2 == authStatus) { // 审核通过
            isStaging = true;
        }
        //发布
        if(3==authStatus){
            isPublished = true;
            authStatus=2;
        }
        //撤回
        if(4==authStatus){
            isPublished = false;
            authStatus=2;
        }
        if(5==authStatus){
            isStaging = true;
            isPublished = true;
            authStatus=2;
        }
        Integer count = 0;
        count =
        cmsContentDao.checkContentById(new CmsContent(
                                                      id,
                                                      authStatus,
                                                      pmphUser.getId(),
                                                      DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",
                                                                               DateUtil.getCurrentTime()),
                                                      isPublished, isStaging,
                                                      Const.MATERIAL_TYPE_ID
                                                      ));
        CmsContent cmsContent = this.getCmsContentById(id);
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "获取对象为空");
        }
        // 评论审核通过，评论数加1
        if (Const.CMS_CATEGORY_ID_0.longValue() == categoryId.longValue() && isPublished) {
            this.updatCmsContentCommentsById(id, 1);
            this.updateCmsContentByParentId(cmsContent.getParentId(), 1);
        }
        Integer type = 0;
        if (Const.CMS_CATEGORY_ID_0.longValue() == categoryId.longValue()) {
            type = Const.WRITER_USER_TRENDST_TYPE_2;
        } else if (Const.CMS_CATEGORY_ID_1.longValue() == categoryId.longValue()) {
            type = Const.WRITER_USER_TRENDST_TYPE_1;
        }
        if (isPublished) {
            writerUserTrendstService.addWriterUserTrendst(new WriterUserTrendst(
                                                                                cmsContent.getAuthorId(),
                                                                                type, id));
        }

        // 当文章通过的时候 给用户增加积分
        if (Const.CMS_CATEGORY_ID_1.longValue() == categoryId.longValue() && isPublished
            && Const.CMS_AUTHOR_TYPE_2 == cmsContent.getAuthorType()) {
            String ruleName = "发表文章";
            writerPointLogService.addWriterPointLogByRuleName(ruleName, cmsContent.getAuthorId());
            //原创
            if(cmsContent.getIsOriginal()!=null && cmsContent.getIsOriginal()){
                writerPointLogService.addWriterPointLogByRuleName("原创文章", cmsContent.getAuthorId());
            }
        }
        return count;
    }

    @Override
    public PageResult<CmsContentVO> listCmsContent(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (Const.CMS_CATEGORY_ID_1.longValue() == pageParameter.getParameter().getCategoryId()) {
            pageParameter.getParameter().setIsAdmin(true);
        } else {
            pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
        }
        pageParameter.getParameter().setAuthorId(pmphUser.getId());
        PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
        // 包含数据总条数的数据集
        List<CmsContentVO> cmsContentList = cmsContentDao.listCmsContent(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsContentList)) {
            Integer count = cmsContentList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsContentList);
        }
        // }
        return pageResult;
    }

    @Override
    public List<CmsContent> getCmsContentList(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.getCmsContentList(cmsContent);
    }

    @Override
    public Map<String, Object> getCmsContentAndContentAndAttachmentById(Long id)
    throws CheckedServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        // 按id 获取CmsContent对象
        CmsContent cmsContent = cmsContentDao.getCmsContentById(id);
        String authDate = cmsContent.getAuthDate();
        if (StringUtil.notEmpty(authDate)) {
            cmsContent.setAuthDate(DateUtil.date2Str(DateUtil.str2Date(authDate)));
        }
        String deadlinePromote = cmsContent.getDeadlinePromote();
        if (StringUtil.notEmpty(deadlinePromote)) {
            cmsContent.setDeadlinePromote(DateUtil.date2Str(DateUtil.str2Date(deadlinePromote)));
        }
        String deadlineStick = cmsContent.getDeadlineStick();
        if (StringUtil.notEmpty(deadlineStick)) {
            cmsContent.setDeadlineStick(DateUtil.date2Str(DateUtil.str2Date(deadlineStick)));
        }
        String deadlineHot = cmsContent.getDeadlineHot();
        if (StringUtil.notEmpty(deadlineHot)) {
            cmsContent.setDeadlineHot(DateUtil.date2Str(DateUtil.str2Date(deadlineHot)));
        }
        resultMap.put("cmsContent", cmsContent);
        // 判断内容是否已经发布或审核通过
        String fileDownLoadType = null;
        if (cmsContent.getIsPublished()
            || Const.CMS_AUTHOR_STATUS_2.shortValue() == cmsContent.getAuthStatus().shortValue()) {
            fileDownLoadType = Const.CMS_FILE_DOWNLOAD;
        } else {
            fileDownLoadType = Const.FILE_DOWNLOAD;
        }
        // 按mid 获取Content对象
        Content content = contentService.get(cmsContent.getMid());
        if (ObjectUtil.isNull(content)) {
            content = new Content();
            content.setId(cmsContent.getMid());
            content.setContent("");
        }
        resultMap.put("content", content);
        // 按contentId 获取CMS内容附件
        List<CmsExtra> cmsExtras = cmsExtraService.getCmsExtraByContentId(id);
        List<CmsExtra> cmsList = new ArrayList<CmsExtra>(cmsExtras.size());
        for (CmsExtra cmsExtra : cmsExtras) {
            String attachment = cmsExtra.getAttachment();
            if (!attachment.equals(cmsContent.getCover())) {
                cmsExtra.setAttachment(fileDownLoadType + attachment);// 拼接附件下载路径
                cmsList.add(cmsExtra);
            }
        }
        resultMap.put("cmsExtras", cmsList);
        // 根据MaterialId 获取教材备注附件
        List<MaterialNoteAttachment> materialNoteAttachments = null;
        if (cmsContent.getIsMaterialEntry()) {
            MaterialExtra materialExtra =
            materialExtraService.getMaterialExtraByMaterialId(cmsContent.getMaterialId());
            if (ObjectUtil.notNull(materialExtra)) {
                // 教材备注附件
                materialNoteAttachments =
                materialNoteAttachmentService.getMaterialNoteAttachmentByMaterialExtraId(materialExtra.getId());
            }
        }
        resultMap.put("MaterialNoteAttachment", materialNoteAttachments);
        if (Const.CMS_CATEGORY_ID_1.longValue() == cmsContent.getCategoryId().longValue()) {
            // 文章封面图片
            CmsExtra cmsExtra = cmsExtraService.getCmsExtraByAttachment(cmsContent.getCover());
            String imgFileName = "默认封面.png";
            String imgFilePath = RouteUtil.DEFAULT_USER_AVATAR;
            if (ObjectUtil.notNull(cmsExtra)) {
                imgFileName = cmsExtra.getAttachmentName();
            } else {
                GridFSDBFile file = fileService.get(cmsContent.getCover());
                if (ObjectUtil.notNull(file)) {
                    imgFileName = file.getFilename();
                }
            }
            resultMap.put("imgFileName", imgFileName);
            if (!"DEFAULT".equals(cmsContent.getCover())) {
                imgFilePath = cmsContent.getCover();
            }
            resultMap.put("imgFilePath", imgFilePath);
        }
        return resultMap;
    }

    @Override
    public CmsContent getCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.getCmsContentById(id);
    }

    @Override
    public Integer getCmsContentCount(Long categoryId) throws CheckedServiceException {
        return cmsContentDao.getCmsContentCount(categoryId);
    }

    @Override
    public CmsContent getCmsContentByMaterialId(Long materialId) throws CheckedServiceException {
        if (ObjectUtil.isNull(materialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "教材ID参数为空!");
        }
        return cmsContentDao.getCmsContentByMaterialId(materialId);
    }

    @Override
    public Integer deleteCmsContentById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        List<CmsContent> list = this.getCmsContentByParentId(id);
        List<Long> ids = new ArrayList<Long>(list.size());
        for (CmsContent cmsContent : list) {
            ids.add(cmsContent.getId());
        }
        Integer count = 0;
        count = cmsContentDao.deleteCmsContentById(id);
        if (CollectionUtil.isNotEmpty(ids)) {
            count = this.deleteCmsContentByIds(ids);
        }
        return count;
    }

    @Override
    public Integer deleteCmsContentByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        Long comments = 0L;
        for (Long id : ids) {
            CmsContent cmsContent = this.getCmsContentById(id);
            if (cmsContent.getIsPublished()) {
                if (cmsContent.getComments() > comments.longValue()) {
                    this.updatCmsContentCommentsById(id, -1);
                }
                CmsContent cmContent = this.getCmsContentById(cmsContent.getParentId());
                if (cmContent.getComments().longValue() > comments.longValue()) {
                    this.updateCmsContentByParentId(cmsContent.getParentId(), -1);
                }
            }
        }
        return cmsContentDao.deleteCmsContentByIds(ids);
    }

    @Override
    public Integer updateCmsContent(CmsContent cmsContent) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }

        return cmsContentDao.updateCmsContent(cmsContent);
    }

    @Override
    public Integer updateCmsContentByIds(List<CmsContent> csmContents)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(csmContents)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.updateCmsContentByIds(csmContents);
    }

    /**
     * 
     * <pre>
	 * 功能描述：保存文件到MongoDB
	 * 使用示范：
	 *
	 * &#64;param files 临时文件路径
	 * &#64;param contentId cms内容ID
	 * &#64;throws CheckedServiceException
	 * </pre>
     */
    private void saveFileToMongoDB(HttpServletRequest request, String[] files, String[] imgFile,
    Long contentId) throws CheckedServiceException, IOException {
        // 保存附件到MongoDB
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(files[i]);
                String fileName =
                (String) request.getSession(false).getAttribute("fileName_" + files[i]);
                InputStream sbs = new ByteArrayInputStream(fileByte);
                String gridFSFileId =
                fileService.save(sbs, fileName, FileType.CMS_ATTACHMENT, contentId);
                if (StringUtil.isEmpty(gridFSFileId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                      CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                      "文件上传失败!");
                }
                // 保存对应数据
                CmsExtra cmsExtra =
                cmsExtraService.addCmsExtra(new CmsExtra(contentId, gridFSFileId, fileName, null));
                if (ObjectUtil.isNull(cmsExtra.getId())) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                      CheckedExceptionResult.PO_ADD_FAILED,
                                                      "CmsExtra对象新增失败");
                }
            }
        }
        if (ArrayUtil.isNotEmpty(imgFile)) {
            for (int i = 0; i < imgFile.length; i++) {
                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(imgFile[i]);
                String gridFSFileId = imgFile[i];
                if (ObjectUtil.notNull(fileByte)) {
                    String fileName =
                    (String) request.getSession(false).getAttribute("fileName_" + imgFile[i]);
                    InputStream sbs = new ByteArrayInputStream(fileByte);
                    gridFSFileId =
                    fileService.save(sbs, fileName, ImageType.CMS_CONTENT_COVER_IMG, contentId);
                    if (StringUtil.isEmpty(gridFSFileId)) {
                        throw new CheckedServiceException(
                                                          CheckedExceptionBusiness.CMS,
                                                          CheckedExceptionResult.FILE_UPLOAD_FAILED,
                                                          "文件上传失败!");
                    }
                    // 保存对应数据
                    CmsExtra cmsExtra =
                    cmsExtraService.addCmsExtra(new CmsExtra(contentId, gridFSFileId, fileName,
                                                             null));
                    if (ObjectUtil.isNull(cmsExtra.getId())) {
                        throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                                          CheckedExceptionResult.PO_ADD_FAILED,
                                                          "上传封面失败");
                    }
                }

                this.updateCmsContent(new CmsContent(contentId, gridFSFileId));// 更新封面ID

            }
        }
    }

    @Override
    public Integer updateCmsContentByMaterialId(Long MaterialId) throws CheckedServiceException {
        if (ObjectUtil.isNull(MaterialId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "教材id为空");
        }
        return cmsContentDao.updateCmsContentByMaterialId(MaterialId);
    }

    @Override
    public PageResult<CmsContentVO> listCmsComment(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (Const.CMS_CATEGORY_ID_0.longValue() == pageParameter.getParameter().getCategoryId()) {
            pageParameter.getParameter().setIsAdmin(true);
        } else {
            pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
        }
        PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<CmsContentVO> cmsContentList = cmsContentDao.listCmsComment(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsContentList)) {
            Integer count = cmsContentList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsContentList);
        }
        return pageResult;
    }

    @Override
    public Integer updatCmsContentCommentsById(Long id, Integer comments)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return cmsContentDao.updatCmsContentCommentsById(id, comments);
    }

    @Override
    public Integer updateCmsContentByParentId(Long id, Integer comments)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return cmsContentDao.updateCmsContentByParentId(id, comments);
    }

    @Override
    public List<CmsContent> getCmsContentByParentId(Long parentId) throws CheckedServiceException {
        if (ObjectUtil.isNull(parentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "上级id为空");
        }
        return cmsContentDao.getCmsContentByParentId(parentId);
    }

    @Override
    public PageResult<CmsContentVO> listHelp(PageParameter<CmsContentVO> pageParameter,
    String sessionId) throws CheckedServiceException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 包含数据总条数的数据集
        List<CmsContentVO> cmsContentList = cmsContentDao.listHelp(pageParameter);
        if (CollectionUtil.isNotEmpty(cmsContentList)) {
            Integer count = cmsContentList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(cmsContentList);
        }
        return pageResult;
    }

    @Override
    public CmsContent addHelp(CmsContent cmsContent, String content, String sessionId,
    HttpServletRequest request) throws CheckedServiceException, IOException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.isEmpty(content)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "内容参数为空");
        }
        List<CmsContent> listContents = this.listCmsContentByTitle(cmsContent.getTitle());
        if (CollectionUtil.isNotEmpty(listContents)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.ILLEGAL_PARAM,
                                              "添加的常见问题已存在，请确认后再添加");
        }
        // MongoDB 内容插入
        Content contentObj = contentService.add(new Content(content));
        if (StringUtil.isEmpty(contentObj.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED,
                                              "Content对象内容保存失败");
        }
        // 内容保存
        cmsContent.setCategoryId(Const.CMS_CATEGORY_ID_4);
        cmsContent.setParentId(Const.CMS_CATEGORY_ID_4);// 上级id
        cmsContent.setPath("0");
        cmsContent.setMid(contentObj.getId());// 内容id
        cmsContent.setAuthorType(Const.CMS_AUTHOR_TYPE_1);// 作者类型
        cmsContent.setAuthorId(pmphUser.getId());// 作者id
        Long contentId = this.addCmsContent(cmsContent).getId();// 获取新增后的主键ID
        if (ObjectUtil.isNull(contentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED,
                                              "CmsContent添加内容失败");
        }
        return cmsContent;
    }

    @Override
    public Map<String, Object> getHelpDetail(Long id) throws CheckedServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        // 按id 获取CmsContent对象
        CmsContent cmsContent = cmsContentDao.getCmsContentById(id);
        resultMap.put("cmsContent", cmsContent);
        // 按mid 获取Content对象
        Content content = contentService.get(cmsContent.getMid());
        resultMap.put("content", content);
        return resultMap;
    }

    @Override
    public Integer updateHelp(CmsContent cmsContent, String content, String sessionId,
    HttpServletRequest request) throws CheckedServiceException, IOException {
        // 获取当前登陆用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (StringUtil.notEmpty(content)) {
            // 更新MongoDB 内容
            contentService.update(new Content(cmsContent.getMid(), content));
        }
        if (ObjectUtil.isNull(cmsContent)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return cmsContentDao.updateCmsContent(cmsContent);
    }

    @Override
    public List<CmsContent> listCmsContentByTitle(String title) throws CheckedServiceException {
        if (StringUtil.isEmpty(title)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return cmsContentDao.listCmsContentByTitle(title);
    }

    @Override
    public  PageResult<Map<String, Object>> recommendlist(Integer recommendPageSize, Integer recommendPageNumber, Long currentCmsId, Boolean relationCms, String cmsTitle,String cmsAuthorName) {
        if(ObjectUtil.isNull(currentCmsId)){
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        CmsContent mainArticle = cmsContentDao.getCmsContentById(currentCmsId);
        PageParameter<Map<String,Object>> pageParameter = new PageParameter<>(recommendPageNumber, recommendPageSize);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("currentCmsId",currentCmsId);
        params.put("relationCms",relationCms);
        params.put("cmsTitle",cmsTitle);
        params.put("cmsAuthorName",cmsAuthorName);
        params.put("apporpc",3-mainArticle.getApporpc());
        PageResult<Map<String, Object> > pageResult = new PageResult<>();
        pageParameter.setParameter(params);
        int total = cmsContentDao.recommendTotal(pageParameter);
        if (total > 0) {
            PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
            pageResult.setRows(cmsContentDao.recommendlist(pageParameter));
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public Boolean recommendcheck(Long currentCmsId, Boolean relationCms, Long relationCmsId) {
        if (ObjectUtil.isNull(currentCmsId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(relationCms)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(relationCmsId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if(relationCms){
            //数据库中是否存在关系 如果存在 就不用插入，如果不存在就插入
            Boolean isExisting = cmsContentDao.recommendisExist(currentCmsId, relationCmsId);
            if(!isExisting){
                cmsContentDao.insertrecommend(currentCmsId, relationCmsId);
            }
        }else{
            //取消 文章之间的关系 删除 关系
            Boolean isExisting = cmsContentDao.recommendisExist(currentCmsId, relationCmsId);
            if(isExisting){
                cmsContentDao.deleterecommend(currentCmsId,relationCmsId);
            }
        }


        return relationCms;
    }
}
