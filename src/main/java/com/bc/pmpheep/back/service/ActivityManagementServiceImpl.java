package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ActivityManagementDao;
import com.bc.pmpheep.back.dao.CmsContentDao;
import com.bc.pmpheep.back.dao.MaterialDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ActivityVO;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityManagementServiceImpl implements ActivityManagementService {
    @Autowired
    ActivityManagementDao activityManagementDao;
    @Autowired
    MaterialDao materialDao;
    @Autowired
    ContentService contentService;
    @Autowired
    MaterialService materialService;
    @Autowired
    private FileService fileService;
    @Autowired
    CmsContentDao cmsContentDao;


    @Override
    public PageResult<ActivityVO> listActivity(PageParameter<ActivityVO> pageParameter, String sessionId) {
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        PageResult<ActivityVO> pageResult = new PageResult<ActivityVO>();
        // 将页面大小和页面页码拷贝
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
        // 包含数据总条数的数据集
        List<ActivityVO> activityList = activityManagementDao.listActivity(pageParameter);
        if (CollectionUtil.isNotEmpty(activityList)) {
            Integer count = activityList.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(activityList);
        }
        // }
        return pageResult;
    }

    @Override
    public Activity addActivity(Activity activity, String content, String sessionId, HttpServletRequest request) {
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
        // MongoDB 内容插入
        Content contentObj = contentService.add(new Content(content));
        if (StringUtil.isEmpty(contentObj.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.PO_ADD_FAILED,
                    "Content对象内容保存失败");
        }
        if (activity.getIsSetTop()) {
            activity.setGmtSetTop(DateUtil.getCurrentTime());
        } else {
            activity.setGmtSetTop(null);
        }
        activity.setFounderId(pmphUser.getId());
        activity.setActivityDescCmsId(contentObj.getId());


        Long activityId = this.addActivity(activity).getId();// 获取新增后的主键ID
        if (ObjectUtil.isNull(activityId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.PO_ADD_FAILED,
                    "activity添加内容失败");
        }

        return activity;
    }

    @Override
    public Activity addActivity(Activity activity) {
        if (ObjectUtil.isNull(activity)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "activity对象参数为空");
        }
        if (StringUtil.isEmpty(activity.getActivityName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "活动标题为空");
        }


        if (activity.getActivityName().length() > 100) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.ILLEGAL_PARAM,
                    "动标题不能超过100个字！");
        }
        activityManagementDao.addActivity(activity);
        return activity;
    }

    @Override
    public Map<String, Object> getActivitDetailsyById(Long id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        // 按id 获取Activity对象
        Activity activityById = activityManagementDao.getActivityById(id);

        resultMap.put("activity", activityById);

        // 按ActivityDescCmsId 获取Content对象
        Content content = contentService.get(activityById.getActivityDescCmsId());
        if (ObjectUtil.isNull(content)) {
            content = new Content();
            content.setId(activityById.getActivityDescCmsId());
            content.setContent("");
        }
        resultMap.put("content", content);
        // 根据MaterialId 获取教材信息
        Material materialById = materialDao.getMaterialById(activityById.getMaterialId());
        resultMap.put("material", materialById);
        // 根据InfoExpressCmsId 获取快报列表
        CmsContent cmsContentById = cmsContentDao.getCmsContentById(activityById.getInfoExpressCmsId());
        resultMap.put("infoExpress", cmsContentById);
        String imgFileName = "默认封面.png";
        String imgFilePath = RouteUtil.DEFAULT_USER_AVATAR;
        // 文章封面图片
        GridFSDBFile file = null;
        if (activityById.getCover() != null) {
            file = fileService.get(activityById.getCover());
        }
        if (ObjectUtil.notNull(file)) {
            imgFileName = file.getFilename();
        }
        resultMap.put("imgFileName", imgFileName);
        if (!"DEFAULT".equals(activityById.getCover())) {
            imgFilePath = activityById.getCover();
        }
        resultMap.put("imgFilePath", imgFilePath);

        return resultMap;
    }

    @Override
    public Integer updateActivity(Activity activity, String content, String sessionId, HttpServletRequest request) {
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
        contentService.update(new Content(activity.getActivityDescCmsId(), content));

        Integer integer = activityManagementDao.updateActivity(activity);


        return integer;
    }

    @Override
    public void setActivityStatus(Activity activity) {
        activityManagementDao.updateActivityStatusById(activity);
    }

    @Override
    public void setActivitySetTop(Activity activity) {
        activityManagementDao.updateActivitySetTopById(activity);
    }
}
