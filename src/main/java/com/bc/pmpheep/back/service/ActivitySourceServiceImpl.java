package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ActivitySourceDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ActivitySourceServiceImpl implements ActivitySourceService {
    @Autowired
    ActivitySourceDao activitySourceDao;

    @Autowired
    private FileService fileService;

    @Override
    public ActivitySource addSource(String[] files, ActivitySource activitySource, String sessionId, HttpServletRequest request) throws IOException {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        activitySource.setGmtUpload(DateUtil.getCurrentTime());
        activitySource.setUploaderId(pmphUser.getId());

        /*     activitySourceDao.addSource(activitySource);// 获取新增后的主键ID*/
        Long id = this.addSource(activitySource).getId();
        ActivitySource source = null;
        if (ArrayUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.length; i++) {
                byte[] fileByte = (byte[]) request.getSession(false).getAttribute(files[i]);
                String fileName =
                        (String) request.getSession(false).getAttribute("fileName_" + files[i]);
                InputStream sbs = new ByteArrayInputStream(fileByte);
                String gridFSFileId =
                        fileService.save(sbs, fileName, FileType.CMS_ATTACHMENT, id);
                if (StringUtil.isEmpty(gridFSFileId)) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.FILE_UPLOAD_FAILED,
                            "文件上传失败!");
                }
                activitySource.setFileId(gridFSFileId);
                // 保存对应数据
                activitySourceDao.updateSource(activitySource);
                if (ObjectUtil.isNull(activitySource.getId())) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                            CheckedExceptionResult.PO_ADD_FAILED,
                            "CmsExtra对象新增失败");
                }
            }
        }
        return activitySource;
    }

    @Override
    public PageResult<ActivitySourceVO> listSource(PageParameter<ActivitySourceVO> pageParameter, String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<ActivitySourceVO> pageResult = new PageResult<ActivitySourceVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
        // 包含数据总条数的数据集
        List<ActivitySourceVO> sourcesList = activitySourceDao.listActivitySource(pageParameter);
        if (CollectionUtil.isNotEmpty(sourcesList)) {
            Integer count = sourcesList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(sourcesList);
        }
        // }
        return pageResult;
    }

    @Override
    public void addActivitySourceChain(String activityId, String[] sourceIds) {
        for (String sourceId : sourceIds) {
            ActivitySourceChain activitySourceChain = new ActivitySourceChain();
            activitySourceChain.setActivityId(Long.parseLong(activityId));
            activitySourceChain.setActivitySourceId(Long.parseLong(sourceId));
            addActivitySourcechin(activitySourceChain);
        }

    }

    @Override
    public Integer deleteSourceByIds(Long id) {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return activitySourceDao.deleteSourceById(id);
    }

    public  void addActivitySourcechin(ActivitySourceChain activitySourceChain){
        activitySourceDao.addActivitySourceChain(activitySourceChain);
      }

    public ActivitySource addSource(ActivitySource activitySource) {
        activitySourceDao.addSource(activitySource);
        return activitySource;
    }
}
