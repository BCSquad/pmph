package com.bc.pmpheep.back.controller.teacherTraining;

import com.alibaba.druid.support.json.JSONUtils;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.dao.ActivityVideoDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.ActivityVideoService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.JsonUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/activityVideo")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ActivityVideoController {
    private static final String BUSSINESS_TYPE = "视频维护";
    @Autowired
    ActivityVideoService activityVideoService;


    /**
     * 功能描述: 保存活动视频信息
     * @param request
     * @param title
     * @param origPath
     * @param origFileName
     * @param origFileSize
     * @param path
     * @param fileName
     * @param fileSize
     * @param cover
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/addActivityVideo", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存微视频信息")
    public ResponseBean<Integer> addActivityVideo(HttpServletRequest request,Long activityId,
                                              String title, String origPath, String origFileName, Long origFileSize,
                                              String path, String fileName, Long fileSize,
                                              @RequestParam("cover") MultipartFile cover) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.USER_SESSION, "尚未登录或session已过期"));
        }
        ActivityVideo activityVideo = new ActivityVideo();
        Integer integer = activityVideoService.checkedName(title);
        if(integer>0){
            ResponseBean responseBean = new ResponseBean();
            responseBean.setCode(2);
            return new ResponseBean(responseBean);
        }
        activityVideo.setTitle(title);
        activityVideo.setOrigPath(origPath);
        activityVideo.setOrigFileName(origFileName);
        activityVideo.setOrigFileSize(origFileSize);
        activityVideo.setPath(path);
        activityVideo.setFileName(fileName);
        activityVideo.setFileSize(fileSize);
        return new ResponseBean(activityVideoService.addActivityVideo(activityId,sessionId, activityVideo, cover));
    }

    @ResponseBody
    @RequestMapping(value = "/addMultiActivityVideo", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存微视频信息")
    public ResponseBean<Integer> addMultiActivityVideo(HttpServletRequest request,Long activityId,
                                                        String transCoding,
                                                       @RequestParam("cover") MultipartFile cover) throws Exception {
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.USER_SESSION, "尚未登录或session已过期"));
        }
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = JSONArray.fromObject(transCoding);


        List<Map<String,Object>> mapListJson = (List)jsonArray;
        boolean flag=true;
        for (Map<String,Object> m:mapListJson) {
            ActivityVideo activityVideo = new ActivityVideo();
            activityVideo.setTitle(m.get("origFileName").toString());
            activityVideo.setOrigPath(m.get("origPath").toString());
            activityVideo.setOrigFileName(m.get("origFileName").toString());
            activityVideo.setOrigFileSize(Long.parseLong(m.get("origFileSize").toString()));
            activityVideo.setPath(m.get("path").toString());
            activityVideo.setFileName(m.get("fileName").toString());
            activityVideo.setFileSize( Long.parseLong(m.get("fileSize").toString()));

            Integer integer = activityVideoService.addActivityVideo(activityId, sessionId, activityVideo, cover);
            if(integer==null){
                flag=false;
            }
        }
        ResponseBean responseBean = new ResponseBean();
        if(flag){
            responseBean.setCode(1);
        }else{
            responseBean.setCode(0);
        }

        return responseBean;
    }


    /**
     * 功能描述: 获取活动频列表
     * @param activityVideoVO
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     * @throws IOException
     */
        @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取视频列表")
    @RequestMapping(value = "/getVideoList", method = RequestMethod.GET)
    public ResponseBean getSourceList(ActivityVideoVO activityVideoVO,@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                      @RequestParam(name = "pageSize") Integer pageSize, HttpServletRequest request) throws IOException {
           /* if(StringUtil.notEmpty(activityVideoVO.getTitle())){
                String title = activityVideoVO.getTitle();
                byte[] bytes = title.getBytes("ISO-8859-1");
                activityVideoVO.setTitle(new String(bytes, "utf-8"));

            }*/
        String sessionId = CookiesUtil.getSessionId(request);

        PageParameter<ActivityVideoVO> pageParameter =
                new PageParameter<ActivityVideoVO>(pageNumber, pageSize, activityVideoVO);
        return new ResponseBean(activityVideoService.listVideo(pageParameter,
                sessionId));

    }

    /**
     * 功能描述: 活动与视频关联
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "活动与视频关联")
    @RequestMapping(value = "/addActivitySourceChain", method = RequestMethod.GET)
    public ResponseBean<Integer> addActivitySourceChain(HttpServletRequest request) throws IOException {
        String videos = request.getParameter("videos");
        String  activityId = request.getParameter("activityId");
        String[] ids = StringUtil.StrList(videos);
        return new ResponseBean(activityVideoService.addActivityVideoChain(activityId,ids));
    }


    /**
     * 功能描述:删除活动视频
     * @param id
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除活动视频")
    @RequestMapping(value = "/deleteVideoById/{id}/del", method = RequestMethod.GET)
    public ResponseBean delete(@PathVariable("id") Long id) {
        return new ResponseBean(activityVideoService.deleteVideoByIds(id));
    }

    /**
     * 功能描述:根据id移动排序
     * @param id
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "排序移动")
    @RequestMapping(value = "/updateSort", method = RequestMethod.GET)
    public ResponseBean updateSort(ActivityVideoVO activityVideoVO, @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(name = "pageSize") Integer pageSize, HttpServletRequest request) {
        Integer id=null;
        String type =null;
        if(StringUtil.notEmpty(request.getParameter("id"))){
            id=Integer.parseInt(request.getParameter("id"));
        }
        if(StringUtil.notEmpty(request.getParameter("type"))){
            type = request.getParameter("type");

        }
        PageParameter<ActivityVideoVO> pageParameter =
                new PageParameter<ActivityVideoVO>(pageNumber, pageSize, activityVideoVO);
        return new ResponseBean(activityVideoService.updateSort(id,pageParameter, type));
    }


    /**
     * 功能描述: 根据id删除活动资源
     * @param id
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除活动资源")
    @RequestMapping(value = "/getVideoChain", method = RequestMethod.GET)
    public ResponseBean getVideoChain(HttpServletRequest request) {
        String id = request.getParameter("id");
        return new ResponseBean(activityVideoService.getVideoChain(Long.parseLong(id)));
    }
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取资源列表")
    @RequestMapping(value = "/getChainList", method = RequestMethod.GET)
    public ResponseBean getChainList(ActivityVideoVO ActivitySourceVO, @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                     @RequestParam(name = "pageSize") Integer pageSize, HttpServletRequest request) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
        PageParameter<ActivityVideoVO> pageParameter =
                new PageParameter<ActivityVideoVO>(pageNumber, pageSize, ActivitySourceVO);
        return new ResponseBean(activityVideoService.getChainList(pageParameter,
                sessionId));

    }

    /**
     * 功能描述: 根据id删除活动资源
     * @param id
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除活动资源")
    @RequestMapping(value = "/delChainVideoByid", method = RequestMethod.GET)
    public ResponseBean delChainSourceByid(HttpServletRequest request) {

        Long activityId=null;
        Long activityVideoId=null;
        if(StringUtil.notEmpty(   request.getParameter("activityId"))){
            activityId=Long.parseLong(request.getParameter("activityId"));
        }

        if(StringUtil.notEmpty( request.getParameter("activityVideoId"))){
            activityVideoId=Long.parseLong(request.getParameter("activityVideoId"));
        }

        return new ResponseBean(activityVideoService.delChainVideoByid(activityId,activityVideoId));
    }

    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "排序移动")
    @RequestMapping(value = "/updateChainSort", method = RequestMethod.GET)
    public ResponseBean updateChainSort(ActivityVideoChain activityVideoChain,HttpServletRequest request
    ) {
        String type="";
        if(StringUtil.notEmpty(request.getParameter("type"))){
            type = request.getParameter("type");

        }
        Map<String, Long> map = new HashMap<>();
        map.put("sort",activityVideoChain.getSort());
        map.put("activityId",activityVideoChain.getActivityId());

        if("up".equals(type)){
            Long sort=null;
            ActivityVideoChain upChianById = activityVideoService.getUpChainById(map);
            if(upChianById!=null){
                sort=upChianById.getSort();

            upChianById.setSort(activityVideoChain.getSort());
            activityVideoChain.setSort(sort);
            activityVideoService.updateChainSort(upChianById);
            return  new ResponseBean (activityVideoService.updateChainSort(activityVideoChain));
            }
        }
        if("down".equals(type)){
            Long sort=null;
            ActivityVideoChain upChianById = activityVideoService.getDownChainnById(map);

            if(upChianById!=null){
                sort=upChianById.getSort();

            upChianById.setSort(activityVideoChain.getSort());
            activityVideoChain.setSort(sort);
            activityVideoService.updateChainSort(upChianById);
            return  new ResponseBean (activityVideoService.updateChainSort(activityVideoChain));
            }
        }
        return new ResponseBean (0);

    }
}
