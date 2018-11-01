package com.bc.pmpheep.back.controller.teacherTraining;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Activity;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.service.ActivityManagementService;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mchange.lang.LongUtils;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 功能描述：师资培训活动管理 控制器
 * 使用示范：
 *
 *
 * @author (作者) zz
 *
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-10-18
 *
 * </pre>
 */
@Controller
@RequestMapping(value = "/activity")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ActivityManagementController {
    @Autowired
    ActivityManagementService activityManagementService;
    @Autowired
    MaterialService materialService;
    @Autowired
    CmsContentService cmsContentService;
    @Autowired
    ContentService contentService;
    private static final String BUSSINESS_TYPE = "活动管理";


    /**
     * 功能描述:更新活动的状态
     *
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新活动状态")
    @RequestMapping(value = "/updateActivityStatus", method = RequestMethod.GET)
    public void setActivityStatus(HttpServletRequest request) throws IOException {
        Integer status = Integer.parseInt(request.getParameter("status"));
        Long id = Long.parseLong(request.getParameter("id"));
        String sessionId = CookiesUtil.getSessionId(request);
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(status);
        activity.setGmtUpdate(DateUtil.getCurrentTime());
        activityManagementService.setActivityStatus(activity);
    }

    /**
     * 功能描述:置顶活动,取消置顶
     *
     * @param request
     * @author ZZ
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新置顶")
    @RequestMapping(value = "/updateSetTop", method = RequestMethod.GET)
    public void setTop(HttpServletRequest request) {
        Boolean isSetTop = Boolean.parseBoolean(request.getParameter("isSetTop"));
        Long id = Long.parseLong(request.getParameter("id"));
        Activity activity = new Activity();
        activity.setId(id);
        activity.setIsSetTop(isSetTop);
        if (isSetTop) {
            activity.setGmtSetTop(DateUtil.getCurrentTime());
        } else {
            activity.setGmtSetTop(null);
        }
        activityManagementService.setActivitySetTop(activity);
    }


    /**
     * 功能描述:查询活动列表
     *
     * @param pageNumber
     * @param pageSize
     * @param activityVO
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @author ZZ
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询活动管理列表")
    @RequestMapping(value = "/getActivityList", method = RequestMethod.GET)
    public ResponseBean getActivityList(
            @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize, ActivityVO activityVO,
            HttpServletRequest request) throws UnsupportedEncodingException {
       /* if (StringUtil.notEmpty(activityVO.getActivityName())) {
            String str = activityVO.getActivityName();
            byte[] bytes = str.getBytes("ISO-8859-1");
            activityVO.setActivityName(new String(bytes, "utf-8"));
        }*/
        PageParameter<ActivityVO> pageParameter =
                new PageParameter<ActivityVO>(pageNumber, pageSize, activityVO);
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(activityManagementService.listActivity(pageParameter, sessionId));
    }

    /**
     * 功能描述:根据id查询活动的内容
     *
     * @param id
     * @return
     * @author ZZ
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询活动详情")
    @RequestMapping(value = "/getActivity/{id}/search", method = RequestMethod.GET)
    public ResponseBean search(@PathVariable("id") Long id) {
        return new ResponseBean(activityManagementService.getActivitDetailsyById(id));
    }

    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增活动")
    @RequestMapping(value = "/newActivity", method = RequestMethod.POST)
    public ResponseBean newActivity(HttpServletRequest request) {


        String sessionId = CookiesUtil.getSessionId(request);
        String imgFile = request.getParameter("imgFile");
        String content = request.getParameter("content");
        Activity activity = parseActivity(request);
        Integer integer = activityManagementService.checkedActivityByName(activity.getActivityName());
        if(integer>0){
            ResponseBean responseBean = new ResponseBean();
            responseBean.setCode(2);
            return new ResponseBean(responseBean);
        }
        if (StringUtil.notEmpty(imgFile)) {
            activity.setCover(imgFile);
        }
        return new ResponseBean(activityManagementService.addActivity(activity, content,
                sessionId,
                request));
    }

    /**
     * 功能描述: 更新活动信息
     *
     *
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "修改活动")
    @RequestMapping(value = "/updateActivity", method = RequestMethod.PUT)
    public ResponseBean updateActivity(HttpServletRequest request) {
        String content = request.getParameter("content");
        String sessionId = CookiesUtil.getSessionId(request);
        Activity activity = parseActivity(request);
        List<Activity> activityByNames = activityManagementService.getActivityByName(activity.getActivityName());
        if(activityByNames.size()>0){
            for(Activity ac:activityByNames){
                if(ac.getId()!=activity.getId()){
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setCode(2);
                    return new ResponseBean(responseBean);
                }
            }
        }
        String imgFile = request.getParameter("imgFile");
        if (StringUtil.notEmpty(imgFile)) {
            if (!imgFile.equals(activity.getCover())) {
                activity.setCover(imgFile);
            }
        }
        return new ResponseBean(activityManagementService.updateActivity(activity, content,
                sessionId,
                request));

    }

    /**
     * 功能描述 :查询教材列表
     *
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询教材列表")
    @RequestMapping(value = "/getMaterialist", method = RequestMethod.GET)
    public ResponseBean list(HttpServletRequest request, Integer pageSize, Integer pageNumber, Boolean isMy,
                             String state, String materialName, String contactUserName) throws UnsupportedEncodingException {
        /*if (StringUtil.notEmpty(request.getParameter("materialName"))) {
            String str = request.getParameter("materialName");
            byte[] bytes = str.getBytes("ISO-8859-1");
            materialName = new String(bytes, "utf-8");
        }*/
        String sessionId = CookiesUtil.getSessionId(request);
        PageParameter<MaterialListVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        MaterialListVO materialListVO = new MaterialListVO();
        materialListVO.setIsMy(isMy);
        materialListVO.setState(state);
        materialListVO.setContactUserName(contactUserName);
        materialListVO.setMaterialName(materialName);
        pageParameter.setParameter(materialListVO);

        return new ResponseBean(materialService.listMaterials(pageParameter, sessionId));
    }

    /**
     * 功能描述:查询信息快报列表
     *
     *
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询信息快报列表")
    @RequestMapping(value = "/getLetters", method = RequestMethod.GET)
    public ResponseBean letters(
            @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize, ActivityInfoExpressVO activityInfoExpressVO,
            HttpServletRequest request) {
        String title = activityInfoExpressVO.getTitle();
        if (StringUtil.notEmpty(title)) {
            activityInfoExpressVO.setTitle(StringUtil.toAllCheck(title));
        }
        PageParameter<ActivityInfoExpressVO> pageParameter =
                new PageParameter<ActivityInfoExpressVO>(pageNumber, pageSize, activityInfoExpressVO);
        String sessionId = CookiesUtil.getSessionId(request);

        return new ResponseBean(activityManagementService.listCmsContent(pageParameter, sessionId));
    }

    /**
     * 功能描述:删除活动视频
     *
     * @param id
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "检查活动名称")
    @RequestMapping(value = "/checkedName", method = RequestMethod.GET)
    public ResponseBean delete(HttpServletRequest request) {
        int flag=0;
        String activityName = request.getParameter("activityName");
        return new ResponseBean(activityManagementService.checkedActivityByName(activityName));
    }


    /**
     * 功能描述:解析活动对象
     *
     * @param request
     *
     */
    public Activity parseActivity(HttpServletRequest request) {
        Activity activity = new Activity();
        String id = request.getParameter("id");
        String gmtSetTop = request.getParameter("gmtSetTop");
        String gmtUpdate = request.getParameter("gmtUpdate");
        String activityDate = request.getParameter("activityDate");
        String activityName = request.getParameter("activityName");
        String cover = request.getParameter("cover");
        String infoExpressCmsId = request.getParameter("infoExpressCmsId");
        String activityDescCmsId = request.getParameter("activityDescCmsId");
        String isSetTop = request.getParameter("isSetTop");
        String materialId = request.getParameter("materialId");
        String status = request.getParameter("status");
        if (StringUtil.notEmpty(gmtSetTop) && ObjectUtil.notNull(gmtSetTop)) {
            activity.setGmtSetTop(new Timestamp(Long.parseLong(gmtSetTop)));
        }
        if (StringUtil.notEmpty(activityDate) && ObjectUtil.notNull(activityDate)) {
            activity.setActivityDate(new Timestamp(Long.parseLong(activityDate)));
        }
        if (StringUtil.notEmpty(gmtUpdate) && ObjectUtil.notNull(gmtUpdate)) {
            activity.setGmtUpdate(new Timestamp(Long.parseLong(gmtUpdate)));
        }
        if (StringUtil.notEmpty(id) && ObjectUtil.notNull(id)) {
            activity.setId(Long.parseLong(id));
        }

        if (StringUtil.notEmpty(activityName)) {
            activity.setActivityName(activityName);
        }
        if (StringUtil.notEmpty(cover)) {
            activity.setCover(cover);
        }
        if (StringUtil.notEmpty(infoExpressCmsId) && ObjectUtil.notNull(infoExpressCmsId)) {
            activity.setInfoExpressCmsId(Long.parseLong(infoExpressCmsId));
        }
        if (StringUtil.notEmpty(activityDescCmsId)) {
            activity.setActivityDescCmsId(activityDescCmsId);
        }
        if (StringUtil.notEmpty(isSetTop) && ObjectUtil.notNull(isSetTop)) {
            activity.setIsSetTop(Boolean.parseBoolean(isSetTop));
        }
        if (StringUtil.notEmpty(materialId)) {
            activity.setMaterialId(materialId);
        }
        if (StringUtil.notEmpty(status) && ObjectUtil.notNull(status)) {
            activity.setStatus(Integer.parseInt(status));
        }
        return activity;
    }


}
