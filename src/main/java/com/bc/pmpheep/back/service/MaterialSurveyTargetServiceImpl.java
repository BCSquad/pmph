package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.MaterialSurveyTargetDao;
import com.bc.pmpheep.back.dao.SurveyTargetDao;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.util.mail.JavaMailSenderUtil;
import com.bc.pmpheep.back.vo.SurveyTargetVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.websocket.MyWebSocketHandler;
import com.bc.pmpheep.websocket.WebScocketMessage;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * 功能描述：调研表发起调研表业务层实现类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Service
@Alias("com.bc.pmpheep.back.service.MaterialSurveyTargetServiceImpl")
public class MaterialSurveyTargetServiceImpl implements MaterialSurveyTargetService {
    @Autowired
    MaterialSurveyTargetDao surveyTargetDao;
    @Autowired
    OrgUserService              orgUserService;
    @Autowired
    WriterUserService           writerUserService;
    @Autowired
    MessageService              messageService;
    @Autowired
    MaterialSurveyService               surveyService;
    @Autowired
    MyWebSocketHandler          myWebSocketHandler;
    @Autowired
    UserMessageService          userMessageService;
    @Autowired
    MaterialSurveyQuestionAnswerService surveyQuestionAnswerService;

    @Value("#{spring['login2front.url']}")
    private String              login2frontUrl;

    /**
     * @return the login2frontUrl
     */
    public String getLogin2frontUrl() {
        return login2frontUrl;
    }

    /**
     * @param login2frontUrl the login2frontUrl to set
     */
    public void setLogin2frontUrl(String login2frontUrl) {
        this.login2frontUrl = login2frontUrl;
    }

    @Override
    public SurveyTarget addSurveyTarget(SurveyTarget surveyTarget) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTarget)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(surveyTarget.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "发起人为空");
        }
        if (ObjectUtil.isNull(surveyTarget.getSurveyId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表为空");
        }
        if (ObjectUtil.isNull(surveyTarget.getOrgId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "机构为空");
        }
        surveyTargetDao.addSurveyTarget(surveyTarget);
        return surveyTarget;
    }

    @Override
    public Integer deleteSurveyTargetById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTargetDao.deleteSurveyTargetById(id);
    }

    @Override
    public Integer updateSurveyTarget(SurveyTarget surveyTarget) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyTarget)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTargetDao.updateSurveyTarget(surveyTarget);
    }

    @Override
    public SurveyTarget getSurveyTargetById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return surveyTargetDao.getSurveyTargetById(id);
    }

    @Override
    public Integer batchSaveSurveyTargetByList(Message message, SurveyTargetVO surveyTargetVO,
    String sessionId) throws Exception {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyTargetVO.getSurveyId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表表Id为空");
        }
        if (CollectionUtil.isEmpty(surveyTargetVO.getOrgIds())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "学校Id为空");
        }
        if (StringUtil.isEmpty(surveyTargetVO.getStartTime())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表开始时间为空");
        }
        if (StringUtil.isEmpty(surveyTargetVO.getEndTime())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表结束时间为空");
        }
        Timestamp statTime = DateUtil.str2Timestam(surveyTargetVO.getStartTime());
        Timestamp endTime = DateUtil.str2Timestam(surveyTargetVO.getEndTime());
        if (statTime.getTime() > DateUtil.getCurrentTimeByYMD().getTime()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表开始时间不能大于今天");
        }
        if (endTime.getTime() < DateUtil.getCurrentTimeByYMD().getTime()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表结束时间不能小于今天");
        }
        if (statTime.getTime() > endTime.getTime()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "开始日期不能大于结束日期");
        }
        List<Long> orgIds = this.listOrgIdBySurveyId(surveyTargetVO.getSurveyId());
        Integer count = 0;
        Long userId = pmphUser.getId();// 当前用户
        List<Long> listOrgId = new ArrayList<Long>();
        surveyService.updateSurvey(new Survey(
                surveyTargetVO.getSurveyId(),
                Const.SURVEY_STATUS_1,
                DateUtil.str2Timestam(surveyTargetVO.getStartTime()),
                DateUtil.str2Timestam(surveyTargetVO.getEndTime())));
        if (CollectionUtil.isEmpty(orgIds)) {// 第一次发布
            listOrgId.addAll(surveyTargetVO.getOrgIds());
        } else {// 第二次发布
            for (Long id : surveyTargetVO.getOrgIds()) {
                if (!orgIds.contains(id)) {
                    listOrgId.add(id);
                }
            }
        }
        List<SurveyTarget> list = new ArrayList<SurveyTarget>(listOrgId.size());
        for (Long orgId : listOrgId) {
            list.add(new SurveyTarget(userId, surveyTargetVO.getSurveyId(), orgId));
        }
        if (CollectionUtil.isNotEmpty(list)) {
            count = surveyTargetDao.batchSaveSurveyTargetByList(list);// 保存发起调研表中间表
        }

        /*
        if (count > 0) {
            // MongoDB 消息插入
            message = messageService.add(message);
            if (StringUtil.isEmpty(message.getId())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
            }
            // 发送消息
            List<WriterUser> writerUserList =
            writerUserService.getWriterUserListByOrgIds(listOrgId);// 作家用户
            List<UserMessage> userMessageList = new ArrayList<UserMessage>(writerUserList.size()); // 系统消息
            for (WriterUser writerUser : writerUserList) {
                userMessageList.add(new UserMessage(message.getId(), surveyTargetVO.getTitle(),
                                                    Const.MSG_TYPE_1, userId, Const.SENDER_TYPE_1,
                                                    writerUser.getId(), Const.RECEIVER_TYPE_2, 0L));
            }
            List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(listOrgId);// 获取学校管理员集合
            List<String> orgUserEmail = new ArrayList<String>(orgUserList.size());// 收件人邮箱
            for (OrgUser orgUser : orgUserList) {
                userMessageList.add(new UserMessage(message.getId(), surveyTargetVO.getTitle(),
                                                    Const.MSG_TYPE_1, userId, Const.SENDER_TYPE_1,
                                                    orgUser.getId(), Const.RECEIVER_TYPE_3, 0L));
                if (!"-".equals(orgUser.getEmail()) && !"null".equals(orgUser.getEmail())) {
                    orgUserEmail.add(orgUser.getEmail());// 获取学校管理员邮箱地址
                }
            }
            Integer size = orgUserEmail.size();
            // String[] emails =
            // new String[] { "515944204@qq.com", "2310870657@qq.com", "501331000@qq.com" };
            String[] toEmail = (String[]) orgUserEmail.toArray(new String[size]);
            if (ArrayUtil.isEmpty(toEmail)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                                  CheckedExceptionResult.NULL_PARAM, "收件人邮箱为空");
            }

            // 发送邮件
            JavaMailSenderUtil javaMailSenderUtil = new JavaMailSenderUtil();
            String serverUrl =
            getLogin2frontUrl().substring(0, getLogin2frontUrl().lastIndexOf("/"));
            // 给学校管理员发送邮件
            javaMailSenderUtil.sendMail(surveyTargetVO.getTitle(),
            // message.getContent(),
                                        "<p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-align: left;'><span style='font-family: 黑体, SimHei;'>您好：</span></p><p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-align: left;'><span style='font-family: 黑体, SimHei;'>&nbsp; &nbsp; 现有一份《"
                                        + surveyTargetVO.getTitle()
                                        + "》需要您登陆下面地址，填写您宝贵意见。</span></p><p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-align: left;'><span style='font-family: 黑体, SimHei;'>&nbsp;&nbsp;&nbsp;&nbsp;登陆地址：<a href='"
                                        + serverUrl
                                        + "/survey/writeSurvey.action?surveyId="
                                        + surveyTargetVO.getSurveyId()
                                        + "'>人卫E教平台</a><br/></span></p><p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; word-spacing: 0px;'><br/></p>",
                                        toEmail);
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
                    new WebScocketMessage(message.getId(), Const.MSG_TYPE_1, userId,
                                          pmphUser.getRealname(), Const.SENDER_TYPE_1,
                                          Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR,
                                          surveyTargetVO.getTitle(), message.getContent(),
                                          DateUtil.getCurrentTime());
                    myWebSocketHandler.sendWebSocketMessageToUser(websocketUserIds,
                                                                  webScocketMessage);
                }
            }
        }
        */

        return count;
    }

    @Override
    public List<Long> listOrgIdBySurveyId(Long surveyId) throws CheckedServiceException {
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表ID为空");
        }
        return surveyTargetDao.listOrgIdBySurveyId(surveyId);
    }

    @Override
    public Integer reissueSurveyMessage(Message message, String title, Long surveyId,
    String sessionId) throws CheckedServiceException, IOException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表表Id为空");
        }
        if (StringUtil.isEmpty(title)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "调研表名称为空");
        }
        // MongoDB 消息插入
        message = messageService.add(message);
        if (StringUtil.isEmpty(message.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
        }
        List<Long> listUserId = surveyQuestionAnswerService.getUserIdBySurveyId(surveyId);
        Integer count = 0;
        List<Long> orgIds = this.listOrgIdBySurveyId(surveyId);
        if (CollectionUtil.isNotEmpty(orgIds)) {
            count = orgIds.size();
            Long userId = pmphUser.getId();
            String surveyTitle = title + "-(调研表催办)";
            // 发送消息
            List<WriterUser> writerUserList = writerUserService.getWriterUserListByOrgIds(orgIds);// 作家用户
            List<UserMessage> userMessageList = new ArrayList<UserMessage>(writerUserList.size()); // 系统消息
            for (WriterUser writerUser : writerUserList) {
                if (!listUserId.contains(writerUser.getId())) {
                    userMessageList.add(new UserMessage(message.getId(), surveyTitle,
                                                        Const.MSG_TYPE_1, userId,
                                                        Const.SENDER_TYPE_1, writerUser.getId(),
                                                        Const.RECEIVER_TYPE_2, 0L));
                }
            }
            // List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(orgIds);//
            // 获取学校管理员集合
            // for (OrgUser orgUser : orgUserList) {
            // if (!listUserId.contains(orgUser.getId())) {
            // userMessageList.add(new UserMessage(message.getId(), surveyTitle,
            // Const.MSG_TYPE_1, userId,
            // Const.SENDER_TYPE_1, orgUser.getId(),
            // Const.RECEIVER_TYPE_3, 0L));
            // }
            // }
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
                    new WebScocketMessage(message.getId(), Const.MSG_TYPE_1, userId,
                                          pmphUser.getRealname(), Const.SENDER_TYPE_1,
                                          Const.SEND_MSG_TYPE_0, RouteUtil.DEFAULT_USER_AVATAR,
                                          surveyTitle, message.getContent(),
                                          DateUtil.getCurrentTime());
                    myWebSocketHandler.sendWebSocketMessageToUser(websocketUserIds,
                                                                  webScocketMessage);
                }
            }
        }
        return count;
    }
}
