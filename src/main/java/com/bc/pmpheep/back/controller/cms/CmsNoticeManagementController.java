package com.bc.pmpheep.back.controller.cms;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：公告管理 控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-2
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsNoticeManagementController {
    @Autowired
    CmsContentService           cmsContentService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "公告管理";

    /**
     * 
     * <pre>
	 * 功能描述：分页查询条件查询《公告管理》列表
	 * 使用示范：
	 *
	 * @param pageNumber 当前页
	 * @param pageSize 页面数据条数
	 * @param cmsContentVO
	 * @param sessionId
	 * @return 分页数据集
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询公告管理列表")
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public ResponseBean notice(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO,
    HttpServletRequest request) {
        cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_3);
        String title = cmsContentVO.getTitle();
        String userName = cmsContentVO.getUsername();
        if (StringUtil.notEmpty(title)) {
            cmsContentVO.setTitle(StringUtil.toAllCheck(title));
        }
        if (StringUtil.notEmpty(userName)) {
            cmsContentVO.setUsername(StringUtil.toAllCheck(userName));
        }
        PageParameter<CmsContentVO> pageParameter =
        new PageParameter<CmsContentVO>(pageNumber, pageSize, cmsContentVO);
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsContentService.listCmsContent(pageParameter, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：CMS-新增公告
     * 使用示范：
     *
     * @param cmsContent CmsContent对象
     * @param files 上传附件数组
     * @param content 内容信息
     * @param scheduledTime 定时发布时间
     * @param sessionId sessionId
     * @param loginType 用户类型
     * @return CmsContent 对象
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增公告")
    @RequestMapping(value = "/newNotice", method = RequestMethod.POST)
    public ResponseBean newNotice(CmsContent cmsContent, @RequestParam("file") String[] files,
    @RequestParam("content") String content, @RequestParam("scheduledTime") String scheduledTime,
    HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.addCmsContent(cmsContent,
                                                                    files,
                                                                    content,
                                                                    scheduledTime,
                                                                    sessionId,
                                                                    request));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：点击标题查看内容
     * 使用示范：
     *
     * @param id CmsContent_id 主键
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看公告")
    @RequestMapping(value = "/notice/content/{id}/detail", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.getCmsContentAndContentAndAttachmentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：公告内容修改
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "公告修改")
    @RequestMapping(value = "/notice/update", method = RequestMethod.PUT)
    public ResponseBean update(CmsContent cmsContent, @RequestParam("file") String[] files,
    @RequestParam("content") String content, @RequestParam("attachment") String[] attachment,
    @RequestParam("scheduledTime") String scheduledTime, HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.updateCmsContent(cmsContent,
                                                                       files,
                                                                       null,
                                                                       content,
                                                                       attachment,
                                                                       null,
                                                                       scheduledTime,
                                                                       sessionId,
                                                                       request));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：公告管理操作(逻辑删除)
     * 使用示范：
     *
     * @param ids 主键ID集合
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除一条公告管理数据")
    @RequestMapping(value = "/notice/{id}/remove", method = RequestMethod.DELETE)
    public ResponseBean remove(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.deleteCmsContentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：公告管理操作(批量逻辑删除)
     * 使用示范：
     *
     * @param ids 主键ID集合
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除公告管理数据")
    @RequestMapping(value = "/notice/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@RequestParam("ids") List<Long> ids) {
        return new ResponseBean(cmsContentService.deleteCmsContentByIds(ids));
    }
}
