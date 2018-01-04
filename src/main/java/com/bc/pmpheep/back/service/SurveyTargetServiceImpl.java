package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyTargetDao;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.po.SurveyTarget;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.mail.JavaMailSenderUtil;
import com.bc.pmpheep.back.vo.SurveyTargetVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查发起问卷业务层实现类
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
public class SurveyTargetServiceImpl implements SurveyTargetService {
    @Autowired
    SurveyTargetDao   surveyTargetDao;
    @Autowired
    OrgUserService    orgUserService;
    @Autowired
    WriterUserService writerUserService;
    @Autowired
    MessageService    messageService;
    @Autowired
    SurveyService     surveyService;

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
                                              CheckedExceptionResult.NULL_PARAM, "问卷为空");
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
    String sessionId) throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyTargetVO.getSurveyId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷表Id为空");
        }
        if (CollectionUtil.isEmpty(surveyTargetVO.getOrgIds())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "学校Id为空");
        }
        if (StringUtil.isEmpty(surveyTargetVO.getStartTime())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷开始时间为空");
        }
        if (StringUtil.isEmpty(surveyTargetVO.getEndTime())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷结束时间为空");
        }
        Integer count = 0;
        Long userId = pmphUser.getId();// 当前用户
        surveyService.updateSurvey(new Survey(surveyTargetVO.getSurveyId(),
                                              DateUtil.str2Timestam(surveyTargetVO.getStartTime()),
                                              DateUtil.str2Timestam(surveyTargetVO.getEndTime())));
        List<SurveyTarget> list = new ArrayList<SurveyTarget>(surveyTargetVO.getOrgIds().size());
        for (Long orgId : surveyTargetVO.getOrgIds()) {
            list.add(new SurveyTarget(userId, surveyTargetVO.getSurveyId(), orgId));
        }
        count = surveyTargetDao.batchSaveSurveyTargetByList(list);// 保存发起问卷中间表
        if (count > 0) {
            // MongoDB 消息插入
            message = messageService.add(message);
            if (StringUtil.isEmpty(message.getId())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
                                                  CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
            }
            // 发送消息
            List<WriterUser> writerUserList =
            writerUserService.getWriterUserListByOrgIds(surveyTargetVO.getOrgIds());// 作家用户
            List<UserMessage> userMessageList = new ArrayList<UserMessage>(writerUserList.size()); // 系统消息
            for (WriterUser writerUser : writerUserList) {
                userMessageList.add(new UserMessage(message.getId(), surveyTargetVO.getTitle(),
                                                    Const.MSG_TYPE_1, userId, Const.SENDER_TYPE_1,
                                                    writerUser.getId(), Const.RECEIVER_TYPE_2, 0L));
            }

            List<OrgUser> orgUserList =
            orgUserService.getOrgUserListByOrgIds(surveyTargetVO.getOrgIds());// 获取学校管理员集合
            List<String> orgUserEmail = new ArrayList<String>(orgUserList.size());// 收件人邮箱
            for (OrgUser orgUser : orgUserList) {
                orgUserEmail.add(orgUser.getEmail());// 获取学校管理员邮箱地址
            }
            Integer size = orgUserEmail.size();
            String[] toEmail = (String[]) orgUserEmail.toArray(new String[size]);
            // 发送邮件
            JavaMailSenderUtil javaMailSenderUtil = new JavaMailSenderUtil();
            try {
                // 给学校管理员发送邮件
                javaMailSenderUtil.sendMail(surveyTargetVO.getTitle(),
                                            "<p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-align: left;'><span style='font-family: 黑体, SimHei;'>您好：</span></p><p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-align: left;'><span style='font-family: 黑体, SimHei;'>&nbsp; &nbsp; 现有一份《XXXX问卷调查》需要您登陆下面地址，填写您宝贵意见。</span></p><p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-align: left;'><span style='font-family: 黑体, SimHei;'>&nbsp;&nbsp;&nbsp;&nbsp;登陆地址：<a href='http://www.baidu.com'>人卫E教平台</a><br/></span></p><p style='margin: 5px 0px; color: rgb(0, 0, 0); font-family: sans-serif; font-size: 16px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; word-spacing: 0px;'><br/></p>",
                                            toEmail);
            } catch (Exception e) {
                throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                                  CheckedExceptionResult.OBJECT_NOT_FOUND, "邮件发送失败");
            }
        }
        return 1;
    }
}
