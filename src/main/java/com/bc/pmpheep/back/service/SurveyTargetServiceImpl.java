package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.SurveyTargetDao;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.SurveyTarget;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
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
    SurveyTargetDao surveyTargetDao;
    @Autowired
    OrgUserService  orgUserService;

    @Override
    public Integer batchSaveSurveyTargetByList(Long surveyId, List<Long> orgIds, String sessionId)
    throws CheckedServiceException {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(surveyId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "问卷表Id为空");
        }
        if (CollectionUtil.isEmpty(orgIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.QUESTIONNAIRE_SURVEY,
                                              CheckedExceptionResult.NULL_PARAM, "学校Id为空");
        }
        Integer count = 0;
        Long userId = pmphUser.getId();// 当前用户
        List<SurveyTarget> list = new ArrayList<SurveyTarget>(orgIds.size());
        for (Long orgId : orgIds) {
            list.add(new SurveyTarget(userId, surveyId, orgId));
        }
        count = surveyTargetDao.batchSaveSurveyTargetByList(list);// 保存发起问卷中间表
        if (count > 0) {
            List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(orgIds);// 获取学校管理员集合
            List<String> orgUserEmail = null;
            for (OrgUser orgUser : orgUserList) {
                orgUserEmail.add(orgUser.getEmail());// 获取学校管理员邮箱地址
            }
            // 给学校管理员发送邮件

        }
        return 1;
    }
}
