package com.bc.pmpheep.back.controller.teacherTraining;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Activity;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.ActivitySourceService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.mchange.lang.LongUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/activitySource")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ActivitySourceController {
    @Autowired
    ActivitySourceService activitySourceService;
    private static final String BUSSINESS_TYPE = "新增资源";

    /**
     * 功能描述: 新增活动资源
     * @param activitySource
     * @param files
     * @param sourceName
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增资源")
    @RequestMapping(value = "/newSource", method = RequestMethod.POST)
    public ResponseBean newSource(ActivitySource activitySource, @RequestParam("file") String[] files, @RequestParam("sourceName") String sourceName, HttpServletRequest request) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(activitySourceService.addSource(files, activitySource,
                sessionId,
                request));

    }
    /**
     * 功能描述: 根据id排序移动
     *
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "排序移动")
    @RequestMapping(value = "/updateSort", method = RequestMethod.GET)
    public ResponseBean updateSort(ActivitySourceVO ActivitySourceVO, @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(name = "pageSize") Integer pageSize, HttpServletRequest request) {
        Integer id=null;
        String type =null;
        if(StringUtil.notEmpty(request.getParameter("id"))){
            id=Integer.parseInt(request.getParameter("id"));
        }
        if(StringUtil.notEmpty(request.getParameter("type"))){
            type = request.getParameter("type");

        }
        PageParameter<ActivitySourceVO> pageParameter =
                new PageParameter<ActivitySourceVO>(pageNumber, pageSize, ActivitySourceVO);
        return new ResponseBean(activitySourceService.updateSort(id,pageParameter, type));
    }




    /**
     * 功能描述: 新增活动资源: 获取活动资源列表
     * @param ActivitySourceVO
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "获取资源列表")
    @RequestMapping(value = "/getSourceList", method = RequestMethod.GET)
    public ResponseBean getSourceList(ActivitySourceVO ActivitySourceVO, @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                      @RequestParam(name = "pageSize") Integer pageSize, HttpServletRequest request) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
      /*  if(StringUtil.notEmpty(ActivitySourceVO.getSourceName())){
            String title = ActivitySourceVO.getSourceName();
            byte[] bytes = title.getBytes("ISO-8859-1");
            ActivitySourceVO.setSourceName(new String(bytes, "utf-8"));

        }*/

        PageParameter<ActivitySourceVO> pageParameter =
                new PageParameter<ActivitySourceVO>(pageNumber, pageSize, ActivitySourceVO);
        return new ResponseBean(activitySourceService.listSource(pageParameter,
                sessionId));

    }

    /**
     * 功能描述: 活动资源与活动关联
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增资源关联")
    @RequestMapping(value = "/addActivitySourceChain", method = RequestMethod.GET)
    public ResponseBean addActivitySourceChain(HttpServletRequest request) throws IOException {
        String sources = request.getParameter("sources");
        String activityId = request.getParameter("activityId");
        String[] strings = StringUtil.StrList(sources);
        activitySourceService.addActivitySourceChain(activityId, strings);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(1);
        return responseBean;
    }


    /**
     * 功能描述: 根据id删除活动资源
     * @param id
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除活动资源")
    @RequestMapping(value = "/deleteSourceById//{id}/del", method = RequestMethod.GET)
    public ResponseBean delete(@PathVariable("id") Long id) {
        return new ResponseBean(activitySourceService.deleteSourceByIds(id));
    }


}
