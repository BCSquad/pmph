package com.bc.pmpheep.back.controller.cms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：内容评论 控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-22
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsCommentController {
    @Autowired
    CmsContentService           cmsContentService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "内容评论";

    /**
     * 
     * <pre>
     * 功能描述：分页查询条件查询CmsContent 内容评论列表
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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询内容评论列表")
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ResponseBean comments(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO,
    HttpServletRequest request) {
        cmsContentVO.setCategoryId(Const.CMS_CATEGORY_ID_0);
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
        return new ResponseBean(cmsContentService.listCmsComment(pageParameter, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：评论审核(通过/拒绝)
     * 使用示范：
     *
     * @param id 主键ID
     * @param authStatus 审核状态
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "评论审核")
    @RequestMapping(value = "/comment/check", method = RequestMethod.PUT)
    public ResponseBean check(@RequestParam("id") Long id,
    @RequestParam("authStatus") Short authStatus, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsContentService.checkContentById(id,
                                                                   authStatus,
                                                                   Const.CMS_CATEGORY_ID_0,
                                                                   sessionId,true));
    }

    /**
     * 
     * <pre>
     * 功能描述：内容评论(批量逻辑删除)
     * 使用示范：
     *
     * @param ids 主键ID集合
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除内容评论")
    @RequestMapping(value = "/comment/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@RequestParam("ids") List<Long> ids) {
        return new ResponseBean(cmsContentService.deleteCmsContentByIds(ids));
    }
}
