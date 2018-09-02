package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.WriterUserCertificationDao;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * @introduction WriterUserCertificationService 实现
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月19日 上午9:46:59
 * 
 */
@Service
public class WriterUserCertificationServiceImpl extends BaseService implements
WriterUserCertificationService {

    @Autowired
    private WriterUserCertificationDao writerUserCertificationDao;
    @Autowired
    WriterUserService writerUserService;
    @Autowired
    SystemMessageService systemMessageService;
    @Autowired
    OrgUserService orgUserService;
    @Autowired
    PmphUserService pmphUserService;
    @Autowired
    OrgService orgService;
    /**
     * 
     * @introduction 新增一个WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:50:09
     * @param writerUserCertification
     * @return 带主键的WriterUserCertification
     * @throws CheckedServiceException
     */
    @Override
    public WriterUserCertification addWriterUserCertification(
    WriterUserCertification writerUserCertification) throws CheckedServiceException {
        if (ObjectUtil.isNull(writerUserCertification.getUserId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "作家为空");
        }
        if (ObjectUtil.isNull(writerUserCertification.getOrgId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "作家学校为空");
        }
        writerUserCertificationDao.addWriterUserCertification(writerUserCertification);
        return writerUserCertification;
    }

    /**
     * 
     * @introduction 根据id查询 WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:51:41
     * @param id
     * @return WriterUserCertification
     * @throws CheckedServiceException
     */
    @Override
    public WriterUserCertification getWriterUserCertificationById(Long id)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return writerUserCertificationDao.getWriterUserCertificationById(id);
    }

    /**
     * 
     * @introduction 根据id删除WriterUserCertification
     * @author Mryang
     * @createDate 2017年9月19日 上午9:52:18
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer deleteWriterUserCertificationById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return writerUserCertificationDao.deleteWriterUserCertificationById(id);
    }

    /**
     * 
     * @introduction 根据id 更新writerUserCertification不为null和''的字段
     * @author Mryang
     * @createDate 2017年9月19日 上午9:53:00
     * @param writerUserCertification
     * @return 影响行数
     * @throws CheckedServiceException
     */
    @Override
    public Integer updateWriterUserCertification(WriterUserCertification writerUserCertification)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(writerUserCertification.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                              CheckedExceptionResult.NULL_PARAM, "主键为空");
        }
        return writerUserCertificationDao.updateWriterUserCertification(writerUserCertification);
    }

    @Override
    public Integer updateWriterUserCertificationProgressByUserId(Short progress, Long[] userIds,String backReason,HttpServletRequest request)
    throws CheckedServiceException, Exception {
    	String sessionId = CookiesUtil.getSessionId(request);
    	PmphUser pmphuser = SessionUtil.getPmphUserBySessionId(sessionId);
        List<WriterUserCertification> writerUserCertifications =
        this.getWriterUserCertificationByUserIds(userIds);
        if (ObjectUtil.isNull(progress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Integer count = 0;
        List<WriterUserCertification> wUserCertifications =
        new ArrayList<WriterUserCertification>(writerUserCertifications.size());
        List<WriterUser> writerUsers = new ArrayList<>();
        for (WriterUserCertification writerUserCertification : writerUserCertifications) {
            if (Const.WRITER_PROGRESS_0 == writerUserCertification.getProgress()) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.NULL_PARAM, "用户信息未提交，不能审核");
            }
           // || Const.WRITER_PROGRESS_3 == writerUserCertification.getProgress()
            if (Const.WRITER_PROGRESS_2 == writerUserCertification.getProgress()
                ) {
                throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT,
                                                  CheckedExceptionResult.NULL_PARAM, "已审核的用户不能再次审核");
            }
            wUserCertifications.add(new WriterUserCertification(
                                                                writerUserCertification.getUserId(),
                                                                progress,backReason));
            writerUsers.add(new WriterUser(writerUserCertification.getUserId()));
        }
        if (CollectionUtil.isNotEmpty(wUserCertifications)) {//教师审核通过的同时修改普通用户级别为教师
            count =
            writerUserCertificationDao.updateWriterUserCertificationProgressByUserId(wUserCertifications);
            List<WriterUser> list = writerUserService.getWriterUserRankList(writerUsers);
            for (WriterUser writerUser : list) {
            	// 当级别为0并且是通过的时候修改
				if (0 == writerUser.getRank() && 3 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setRank(1);
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(true);
						writerUserService.updateWriterUserRank(wrs);
					}
				// 当级别为0并且是退回的时候修改
				} else if (0 == writerUser.getRank() && 2 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(false);
						writerUserService.updateWriterUser(wrs);
					}
				// 当级别为1并且是通过的时候修改
				} else if (1 == writerUser.getRank() && 3 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(true);
						writerUserService.updateWriterUserRank(wrs);
					}
				// 当级别为1并且是退回的时候修改
				} else if (1 == writerUser.getRank() && 2 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(false);
						writerUserService.updateWriterUser(wrs);
					}
				// 当级别为2并且是通过的时候修改
				} else if (2 == writerUser.getRank() && 3 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(true);
						writerUserService.updateWriterUserRank(wrs);
					}
				// 当级别为2并且是退回的时候修改
				} else if (2 == writerUser.getRank() && 2 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(false);
						writerUserService.updateWriterUser(wrs);
					}
				// 当级别为3并且是通过的时候修改
				} else if (3 == writerUser.getRank() && 3 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(true);
						writerUserService.updateWriterUserRank(wrs);
					}
				// 当级别为2并且是退回的时候修改
				} else if (3 == writerUser.getRank() && 2 == progress.intValue()) {
					for (WriterUser wrs : writerUsers) {
						wrs.setAuthUserType(1);
						wrs.setAuthUserId(pmphuser.getId());
						wrs.setIsTeacher(false);
						writerUserService.updateWriterUser(wrs);
					}
				}
			}
        }
        //认证通过或退回的推送消息
        Boolean isPass = null;
        if (2 == progress) {
        	isPass=false;
        }
        if (3 == progress) {
        	isPass=true;
        }
        if (null != isPass) {
        	List<Long> teacherIds = new ArrayList<>();
            for (int i = 0; i < userIds.length; i++) {
              	teacherIds.add(userIds[0]);
      		}
        	//获取用户认证类型和认证人
        	List<WriterUser> users = writerUserService.getWriterUserList(userIds);
        	for (WriterUser writerUser : users) {
        		if (1 == writerUser.getAuthUserType()) {//社内用户
        			PmphUser pmphUser = pmphUserService.get(writerUser.getAuthUserId());
        			systemMessageService.sendWhenTeacherCertificationAudit(pmphUser.getRealname(), teacherIds, isPass,pmphUser);
        		}
        		if (2 == writerUser.getAuthUserType()) {//学校机构用户
        			OrgUser orgUsers = orgUserService.getOrgUserById(writerUser.getAuthUserId());
        			Org org = orgService.getOrgById(orgUsers.getOrgId());
        			systemMessageService.sendWhenTeacherCertificationAudit(org.getOrgName(), teacherIds, isPass,orgUsers);
        		}
			}
        }
        return count;
    }

    @Override
    public List<WriterUserCertification> getWriterUserCertificationByUserIds(Long[] userIds)
    throws CheckedServiceException {
        if (ArrayUtil.isEmpty(userIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return writerUserCertificationDao.getWriterUserCertificationByUserIds(userIds);
    }

}
