package com.bc.pmpheep.back.controller.teacherTraining;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.dao.ActivityVideoDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.ActivitySource;
import com.bc.pmpheep.back.po.ActivityVideo;
import com.bc.pmpheep.back.po.BookVideo;
import com.bc.pmpheep.back.service.ActivityVideoService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ActivitySourceVO;
import com.bc.pmpheep.back.vo.ActivityVideoVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    public ResponseBean<Integer> addActivityVideo(HttpServletRequest request,
                                              String title, String origPath, String origFileName, Long origFileSize,
                                              String path, String fileName, Long fileSize,
                                              @RequestParam("cover") MultipartFile cover) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.USER_SESSION, "尚未登录或session已过期"));
        }
        ActivityVideo activityVideo = new ActivityVideo();
        activityVideo.setTitle(title);
        activityVideo.setOrigPath(origPath);
        activityVideo.setOrigFileName(origFileName);
        activityVideo.setOrigFileSize(origFileSize);
        activityVideo.setPath(path);
        activityVideo.setFileName(fileName);
        activityVideo.setFileSize(fileSize);
        return new ResponseBean(activityVideoService.addActivityVideo(sessionId, activityVideo, cover));
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

            if(StringUtil.notEmpty(activityVideoVO.getTitle())){
                String title = activityVideoVO.getTitle();
                byte[] bytes = title.getBytes("ISO-8859-1");
                activityVideoVO.setTitle(new String(bytes, "utf-8"));

            }
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
        activityVideoService.addActivityVideoChain(activityId,ids);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(1);
        return responseBean;
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


}
