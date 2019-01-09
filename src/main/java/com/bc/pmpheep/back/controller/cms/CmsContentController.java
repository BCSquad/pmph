package com.bc.pmpheep.back.controller.cms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.back.vo.BookVO;
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
 * 功能描述：CMS 控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-31
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsContentController {
    @Autowired
    CmsContentService           cmsContentService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "文章管理";

    /**
     * 
     * <pre>
     * 功能描述：分页查询条件查询CmsContent 内容发布列表
     * 使用示范：
     *
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @param cmsContentVO 
     * @ sessionId
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询文章管理列表")
    @RequestMapping(value = "/contents", method = RequestMethod.GET)
    public ResponseBean contents(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO,
    HttpServletRequest request) {
        cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_1);
        String title = cmsContentVO.getTitle();
        String userName = cmsContentVO.getUsername();
        if (StringUtil.notEmpty(title)) {// 文章标题
            cmsContentVO.setTitle(StringUtil.toAllCheck(title));
        }
        if (StringUtil.notEmpty(userName)) {// 作者，状态
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
     * 功能描述：CMS-添加内容
     * 使用示范：
     *
     * @param cmsContent CmsContent对象
     * @param files 上传附件数组
     * @param content 内容信息
     * @param scheduledTime 定时发布时间
     * @ sessionId sessionId
     * @ loginType 用户类型
     * @return CmsContent 对象
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增文章")
    @RequestMapping(value = "/newContent", method = RequestMethod.POST)
    public ResponseBean newContent(CmsContent cmsContent, @RequestParam("file") String[] files,
    @RequestParam("content") String content, @RequestParam("scheduledTime") String scheduledTime,
                                   @RequestParam("authorname") String authorname, HttpServletRequest request) {
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
     * 功能描述：内容发布
     * 使用示范：
     *
     * @param id 主键ID
     * @return  影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "文章发布")
    @RequestMapping(value = "/content/{id}/publish", method = RequestMethod.PUT)
    public ResponseBean publish(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsContentService.publishCmsContentById(id, sessionId));
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看文章详情")
    @RequestMapping(value = "/content/{id}/detail", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.getCmsContentAndContentAndAttachmentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：内容修改页面查找带回
     * 使用示范：
     *
     * @param id 主键ID
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询文章详情")
    @RequestMapping(value = "/content/{id}/search", method = RequestMethod.GET)
    public ResponseBean search(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.getCmsContentAndContentAndAttachmentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：内容审核(通过/拒绝)
     * 使用示范：
     *
     * &#64;param id 主键ID
     * &#64;param authStatus 审核状态
     * &#64;return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "内容审核")
    @RequestMapping(value = "/content/check", method = RequestMethod.PUT)
    public ResponseBean check(@RequestParam("id") Long id,
                              @RequestParam("authStatus") Short authStatus,
                              @RequestParam(value = "isOriginal",defaultValue = "false")Boolean isOriginal,
                              HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsContentService.checkContentById(id,
                                                                   authStatus,
                                                                   Const.CMS_CATEGORY_ID_1,
                                                                   sessionId,isOriginal));
    }

    /**
     * 
     * <pre>
     * 功能描述：内容修改
     * 使用示范：
     *
     *  id 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "文章修改")
    @RequestMapping(value = "/content/update", method = RequestMethod.PUT)
    public ResponseBean update(CmsContent cmsContent, @RequestParam(value = "file",required = false) String[] files,
    @RequestParam(value="imgFile",required = false) String[] imgFile, @RequestParam(value="content",required = false) String content,
    @RequestParam(value="attachment",required = false) String[] attachment,
    @RequestParam(value = "imgAttachment",required = false) String[] imgAttachment,
    @RequestParam(value="scheduledTime",required = false) String scheduledTime, HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.updateCmsContent(cmsContent,
                                                                       files,
                                                                       imgFile,
                                                                       content,
                                                                       attachment,
                                                                       imgAttachment,
                                                                       scheduledTime,
                                                                       sessionId,
                                                                       request));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }
    /**
     * 功能描述: 初始化/条件查询推荐文章
     * @param recommendPageSize
     * @param recommendPageNumber
     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "初始化/条件查询推荐文章")
    @RequestMapping(value = "/recommendlist", method = RequestMethod.GET)
    public ResponseBean recommendlist(Integer recommendPageSize, Integer recommendPageNumber, Long currentCmsId,Boolean relationCms,String cmsTitle,String cmsAuthorName) {
        return new ResponseBean(cmsContentService.recommendlist( recommendPageSize,  recommendPageNumber,  currentCmsId, relationCms, cmsTitle,cmsAuthorName));
    }
    /**
     * 功能描述: 推荐文章

     * @return
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "推荐文章")
    @RequestMapping(value = "/recommendcheck", method = RequestMethod.GET)
    public ResponseBean recommendcheck(Long currentCmsId,Boolean relationCms,Long relationCmsId) {

        return new ResponseBean(cmsContentService.recommendcheck( currentCmsId, relationCms, relationCmsId));
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "文章隐藏")
    @RequestMapping(value = "/content/{id}/hide", method = RequestMethod.PUT)
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "文章删除")
    @RequestMapping(value = "/content/{id}/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.deleteCmsContentById(id));
    }
}
