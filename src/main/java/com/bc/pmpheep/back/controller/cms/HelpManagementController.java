package com.bc.pmpheep.back.controller.cms;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * 功能描述：帮助管理控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-3-8
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@RestController
@RequestMapping(value = "/help")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HelpManagementController {
    @Autowired
    CmsContentService           cmsContentService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "帮助管理";

    /**
     * 
     * <pre>
     * 功能描述：帮助管理列表
     * 使用示范：
     *
     * @param pageNumber 
     * @param pageSize
     * @param cmsContentVO
     * @param request
     * @return
     * </pre>
     */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询文章管理列表")
    @GetMapping(value = "/list")
    public ResponseBean list(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO,
    HttpServletRequest request) {
        cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_4);
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
     * 功能描述：帮助管理添加
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增帮助")
    @PostMapping(value = "/newHelp")
    public ResponseBean newHelp(CmsContent cmsContent, @RequestParam("content") String content,
    HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.addHelp(cmsContent,
                                                              content,
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
    @GetMapping(value = "/{id}/detail")
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查看帮助内容详情")
    public ResponseBean detail(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.getHelpDetail(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：帮助修改
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "帮助修改")
    @PutMapping(value = "/update")
    public ResponseBean update(CmsContent cmsContent, @RequestParam("content") String content,
    HttpServletRequest request) {
        try {
            String sessionId = CookiesUtil.getSessionId(request);
            return new ResponseBean(cmsContentService.updateHelp(cmsContent,
                                                                 content,
                                                                 sessionId,
                                                                 request));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

}
