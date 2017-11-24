package com.bc.pmpheep.back.controller.cms;

import java.io.IOException;

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
 * 功能描述：信息快报管理 控制器
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
public class CmsInfoLettersManagementController {
    @Autowired
    CmsContentService           cmsContentService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "信息快报管理";

    /**
     * 
     * <pre>
	 * 功能描述：分页查询条件查询《信息快报管理》列表
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询信息快报列表")
    @RequestMapping(value = "/letters", method = RequestMethod.GET)
    public ResponseBean letters(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO,
    HttpServletRequest request) {
        cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_2);
        String title = cmsContentVO.getTitle();
        if (StringUtil.notEmpty(title)) {
            cmsContentVO.setTitle(title.replaceAll(" ", ""));
        }
        PageParameter<CmsContentVO> pageParameter =
        new PageParameter<CmsContentVO>(pageNumber, pageSize, cmsContentVO);
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsContentService.listCmsContent(pageParameter, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：CMS-新增信息快报管理
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增信息快报")
    @RequestMapping(value = "/newLetters", method = RequestMethod.POST)
    public ResponseBean newLetters(CmsContent cmsContent, @RequestParam("file") String[] files,
    @RequestParam("content") String content, @RequestParam("scheduledTime") String scheduledTime,
    HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.addCmsContent(cmsContent,
                                                                    files,
                                                                    content,
                                                                    scheduledTime,
                                                                    sessionId));
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看信息快报内容")
    @RequestMapping(value = "/letters/content/{id}/detail", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.getCmsContentAndContentAndAttachmentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：信息快报内容修改
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "信息快报内容修改")
    @RequestMapping(value = "/letters/update", method = RequestMethod.PUT)
    public ResponseBean update(CmsContent cmsContent, @RequestParam("file") String[] files,
    @RequestParam("content") String content, @RequestParam("attachment") String[] attachment,
    @RequestParam("scheduledTime") String scheduledTime, HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.updateCmsContent(cmsContent,
                                                                       files,
                                                                       content,
                                                                       attachment,
                                                                       scheduledTime,
                                                                       sessionId));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 
     * <pre>
	 * 功能描述：内容隐藏
	 * 使用示范：
	 *
	 * @param id 主键ID
	 * @return 影响行数
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "信息快报消息隐藏")
    @RequestMapping(value = "/letters/content/{id}/hide", method = RequestMethod.PUT)
    public ResponseBean hide(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.hideCmsContentById(id));
    }

    /**
     * 
     * <pre>
	 * 功能描述：内容删除
	 * 使用示范：
	 *
	 * @param id 主键ID
	 * @return 影响行数
	 * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除一条信息快报")
    @RequestMapping(value = "/letters/content/{id}/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.deleteCmsContentById(id));
    }
}
