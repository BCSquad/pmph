package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ActivityVideoDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ActivityVideoServiceImpl implements ActivityVideoService {
    @Autowired
    ActivityVideoDao activityVideoDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private CmsExtraService cmsExtraService;

    @Override
    public PageResult<ActivityVideoVO> listVideo(PageParameter<ActivityVideoVO> pageParameter, String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<ActivityVideoVO> pageResult = new PageResult<ActivityVideoVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
        // 包含数据总条数的数据集
        List<ActivityVideoVO> sourcesList = activityVideoDao.listActivityVideo(pageParameter);
            if (CollectionUtil.isNotEmpty(sourcesList)) {
                Integer count = sourcesList.get(0).getCount();
                pageResult.setTotal(count);
                pageResult.setRows(sourcesList);
            }
            // }
            return pageResult;
        }

        @Override
        public Integer addActivityVideo (String sessionId, ActivityVideo activityVideo, MultipartFile cover) throws
        IOException {
            PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
            if (ObjectUtil.isNull(pmphUser)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "用户为空");
            }
            if (StringUtil.isEmpty(activityVideo.getTitle())
                    || StringUtil.isEmpty(activityVideo.getFileName()) || StringUtil.isEmpty(activityVideo.getPath())
                    || StringUtil.isEmpty(activityVideo.getOrigFileName()) || StringUtil.isEmpty(activityVideo.getOrigPath())
                    || ObjectUtil.isNull(activityVideo.getFileSize()) || ObjectUtil.isNull(activityVideo.getOrigFileSize())) {
                throw new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO, CheckedExceptionResult.NULL_PARAM, "参数为空");
            }
            activityVideo.setSort(activityVideoDao.getMaxSort()+1);
            activityVideo.setUserId(pmphUser.getId());
            activityVideo.setIsAuth(true);
            activityVideo.setAuthUserId(pmphUser.getId());
            activityVideo.setAuthDate(new Date());
            activityVideo.setCover("DEFAULT");//暂设为默认值
            activityVideo.setGmtCreate(DateUtil.getCurrentTime());
            activityVideoDao.addActivityVideo(activityVideo);
            /* 保存封面 */
            String coverId = fileService.save(cover, FileType.BOOKVEDIO_CONER, activityVideo.getId());
            activityVideo.setCover(coverId);
            return activityVideoDao.updateActivityVideo(activityVideo);
        }

        @Override
        public Integer addActivityVideoChain (String activityId, String[]ids){
            try {
                for (String id : ids) {
                    ActivityVideoChain activitySourceChain = new ActivityVideoChain();
                    activitySourceChain.setActivityId(Long.parseLong(activityId));
                    activitySourceChain.setActivityVideoId(Long.parseLong(id));
                    addActivityVideoChain(activitySourceChain);
                }
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }

        }

        @Override
        public Integer deleteVideoByIds (Long id){
            if (ObjectUtil.isNull(id)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                        CheckedExceptionResult.NULL_PARAM, "参数为空");

            }
            return activityVideoDao.deleteVideoById(id);
        }

        void addActivityVideoChain (ActivityVideoChain activityVideoChain){
            activityVideoDao.addActivityVideochain(activityVideoChain);
        }

    }
